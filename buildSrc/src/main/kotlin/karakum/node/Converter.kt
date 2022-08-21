package karakum.node

private val IGNORE_LIST = setOf(
    "Global",

    "Dict",
    "ReadOnlyDict",

    "Require",
    "RequireExtensions",

    "FSWatcher",
    "StatSyncFn",
    "StatWatcher",
    "WatchOptions",

    "UrlWithParsedQuery",
    "UrlWithStringQuery",

    "URL",
    "URLSearchParams",
)

internal data class ConversionResult(
    val name: String,
    val body: String,
)

internal fun convertDefinitions(
    source: String,
    pkg: Package,
): Sequence<ConversionResult> {
    val content = source
        .substringAfter("declare module '${pkg.name}' {\n", "")
        .substringBefore("\n}")
        .trimIndent()

    val namespaceStart = "namespace ${pkg.name} {\n"
    var mainContent = if (namespaceStart in content) {
        content
            .substringAfter(namespaceStart)
            .substringBefore("\n}")
            .trimIndent()
            .let { "\n$it" }
    } else content

    val globalsStart = "\nglobal {\n"
    if (globalsStart in content) {
        var globals = content
            .substringAfter(globalsStart)
            .substringBefore("\n}")
            .trimIndent()

        if (globals.startsWith("namespace NodeJS {\n") || globals.startsWith("var process: NodeJS.Process;"))
            globals = globals
                .substringAfter("namespace NodeJS {\n")
                .substringBefore("\n}")
                .trimIndent()

        mainContent += "\n\n$globals"
    } else if ("\nnamespace internal {\n" in content) {
        val internal = content
            .substringAfter("\nnamespace internal {\n")
            .substringBefore("\n}")
            .trimIndent()

        mainContent += "\n\n$internal"
    } else if ("\ndeclare namespace NodeJS {\n" in source) {
        val globals = source
            .substringAfter("\ndeclare namespace NodeJS {\n")
            .substringBefore("\n}")
            .trimIndent()

        mainContent += "\n\n$globals"
    }

    val interfaces = "\n$mainContent"
        .splitToSequence("\nexport interface ", "\ninterface ")
        .drop(1)
        .map { convertInterface(it, false) }
        .filter { it.name !in IGNORE_LIST }

    val classes = "\n$mainContent"
        .splitToSequence("\nexport class ", "\nclass ")
        .drop(1)
        .map { convertInterface(it, true) }
        .filter { it.name !in IGNORE_LIST }

    val types = "\n$mainContent"
        .splitToSequence("\ntype ")
        .drop(1)
        .mapNotNull { convertType(it) }

    return when (pkg) {
        Package("buffer") -> mergeBuffers(interfaces)

        Package("events") -> mergeEventEmitters(interfaces + classes)
            .plus(Abortable())

        Package("globals") -> interfaces
            .map { it.copy(body = it.body.replace("node.stream.", "")) }
            .plus(abortClasses())
            .plus(ConversionResult("Dict", "typealias Dict<T> = Record<String, T>"))
            .plus(ConversionResult("ReadOnlyDict", "typealias ReadOnlyDict<T> = Record<String, out T>"))

        Package("fs") -> interfaces
            .plus(convertFunctions(content, syncOnly = true))
            .plus(SymlinkType())
            .plus(WatchEventType())
            .plus(BufferEncodingOption())
            .plus(ConversionResult("PathLike", "typealias PathLike = String"))
            .plus(ConversionResult("PathOrFileDescriptor", "typealias PathOrFileDescriptor = PathLike"))
            .plus(ConversionResult("TimeLike", "typealias TimeLike = kotlin.js.Date"))
            .plus(ConversionResult("EncodingOption", "typealias EncodingOption = ObjectEncodingOptions?"))
            .plus(ConversionResult("WriteFileOptions", "typealias WriteFileOptions = node.buffer.BufferEncoding?"))
            .plus(ConversionResult("Mode", "typealias Mode = Int"))
            .plus(ConversionResult("OpenMode", "typealias OpenMode = Int"))
            .plus(ConversionResult("ReadPosition", "typealias ReadPosition = Number"))
            .plus(ConversionResult("Dir", "external class Dir"))
            .plus(ConversionResult("ReadStream", "external class ReadStream"))
            .plus(ConversionResult("WriteStream", "external class WriteStream"))

        Package("fs/promises") -> interfaces
            .plus(convertFunctions(content))

        Package("inspector") -> emptySequence()

        Package("net") -> (interfaces + classes)
            .plus(convertFunctions(content))
            .plus(
                ConversionResult(
                    "SocketConnectOpts",
                    "typealias SocketConnectOpts = ConnectOpts /* TcpSocketConnectOpts | IpcSocketConnectOpts */"
                )
            )
            .plus(
                ConversionResult(
                    "NetConnectOpts",
                    "typealias NetConnectOpts = SocketConstructorOpts /* TcpNetConnectOpts | IpcNetConnectOpts */"
                )
            )

        Package("os") -> interfaces
            .plus(convertFunctions(content))
            .plus(ConversionResult("NetworkInterfaceInfo", "typealias NetworkInterfaceInfo = NetworkInterfaceBase"))

        Package("path") -> interfaces
            .plus(rootVal("path", "PlatformPath"))

        Package("process") -> interfaces
            .plus(rootVal("process", "Process"))

        Package("querystring") -> interfaces
            .plus(convertFunctions(content))

        Package("stream") -> interfaces + classes

        Package("stream/web") -> emptySequence<ConversionResult>()
            .plus(ConversionResult("ReadableStream", "external class ReadableStream"))
            .plus(ConversionResult("WritableStream", "external class WritableStream"))

        Package("tty") -> (interfaces + classes)
            .plus(convertFunctions(content))

        Package("url") -> interfaces
            .plus(convertFunctions(content))
            .plus(ConversionResult("URL.alias", "typealias URL = org.w3c.dom.url.URL"))
            .plus(
                ConversionResult(
                    "URLSearchParams.alias",
                    "typealias URLSearchParams = org.w3c.dom.url.URLSearchParams"
                )
            )

        Package("async_hooks"),
        Package("http"),
        Package("vm"),
        Package("worker_threads"),
        -> (interfaces + classes)
            .plus(convertFunctions(content))

        else -> interfaces
    } + types
}

private fun convertType(
    source: String,
): ConversionResult? {
    val name = source.substringBefore(" =")
        .substringBefore("<")

    if (name.startsWith("Pipeline"))
        return null

    val bodySource = source.substringAfter(" =")
        .removePrefix(" ")
        .substringBefore(";")

    convertUnion(name, bodySource)?.let {
        return it
    }

    if (name == "Serializable" || name == "TransferListItem")
        return ConversionResult(
            name = name,
            body = "typealias $name = Any /* $bodySource */",
        )

    if (bodySource == "-1 | 0 | 1")
        return ConversionResult(
            name = name,
            body = "typealias $name = Int /* $bodySource */",
        )

    if (!bodySource.startsWith("("))
        return null

    var body = bodySource
        .replace("<unknown>", "<*>")
        .replace(": unknown", ": Any?")
        .replace("code: number", "code: Int")
        .replace(": string", ": String")
        .replace(": number", ": Number")
        .replace(" => void", " -> Unit")
        .replace("?: Error | null", ": Error?")
        .replace("?: any", ": Any?")
        .replace(": NodeJS.ErrnoException | null", ": ErrnoException?")

        // TEMP
        .replace(": dns.LookupOneOptions", ": $DYNAMIC /* dns.LookupOneOptions */")

    if (!body.startsWith("()"))
        body = body
            .replaceFirst("(", "(\n")
            .replace(",", ",\n")

    return ConversionResult(name, "typealias $name = $body")
}

private fun convertInterface(
    source: String,
    classMode: Boolean,
): ConversionResult {
    var name = source.substringBefore(" ")
        .substringBefore("<")
        .substringBefore("(")
        .substringBefore(":")

    if (!classMode && (name == "EventEmitter" || name == "BroadcastChannel"))
        return convertInterface("I$source", classMode)

    if (name == "internal")
        return convertInterface(source.replaceFirst("internal ", "LegacyStream "), classMode)
            .let { it.copy(body = rootModuleAnnotaion("stream") + "\n" + it.body) }

    if (" extends NodeJS.Dict<" in source) {
        val type = source
            .substringAfter(" extends NodeJS.Dict<")
            .substringBeforeLast("> {")

        return ConversionResult(
            name = name,
            body = "typealias $name = Dict<Any /* $type */>",
        )
    }

    var declaration = source
        .removeSuffix("{}")
        .substringBefore(" {}\n")
        .substringBefore(" { }\n")
        .substringBefore(" {\n")
        .replace(" extends ", " : ")
        .replace("<number>", "<Number>")
        .replace("<string>", "<String>")
        .replace("<bigint>", "<BigInt>")
        .replace("NodeJS.ArrayBufferView", "ArrayBufferView")
        .replace("NodeJS.RefCount", "RefCount")
        .replace("implements NodeJS.ReadableStream", ", node.ReadableStream")
        .replace("implements NodeJS.WritableStream", ", node.WritableStream")
        .replace("implements Writable", "/* , Writable */")
        .replace(": stream.Duplex", ": node.stream.Duplex")
        .replace(": net.Socket", ": node.net.Socket")
        .replace(": stream.Readable", ": Readable")
        .replace(": stream.Writable", ": Writable")
        .replace(": NetServer", ": node.net.Server")
        .replace(": internal", ": LegacyStream")
        .replace(" = Buffer", "")
        .replace("string | Buffer", "Any /* string | Buffer */")
        .replace(": EventEmitter", if (classMode) ": node.events.EventEmitter" else ": node.events.IEventEmitter")
        .replace(": Error", "/* : Error */")
        .replace("Partial<TcpSocketConnectOpts>", "node.net.TcpSocketConnectOpts /* Partial */")
        // TEMP???
        .replace(": StreamOptions<Readable>", ": StreamOptions<Stream /* Readable */>")
        .replace(": StreamOptions<Writable>", ": StreamOptions<Stream /* Writable */>")
        // TEMP
        .replace(": tty.ReadStream", "/* : tty.ReadStream */")
        .replace(": tty.WriteStream", "/* : tty.WriteStream */")
        .replace(": ReadWriteStream", ": node.ReadWriteStream")

    if (name == "EventEmitter" || name == "BroadcastChannel")
        declaration += " : I$name"

    val bodySource = if (!source.substringBefore("\n").let { it.endsWith("{}") || it.endsWith("{ }") }) {
        source.substringAfter(" {\n")
            .let { if (it.startsWith("}")) "" else it }
            .substringBefore("\n}")
            .trimIndent()
            .replace("): this", "): $name")
            .replace("toJSON(): {\n    type: 'Buffer';\n    data: number[];\n};", "toJSON(): any;")
            .replace(";\n *", ";--\n *")
    } else ""

    val body = convertMembers(bodySource)
        .replace(";--\n *", ";\n *")
        .let { addOverrides(name, declaration, it, classMode) }

    val type = when (name) {
        "Buffer",
        "BufferConstructor",
        -> "class"

        "IEventEmitter",
        "RefCounted",
        "ReadableStream",
        "WritableStream",
        "ReadWriteStream",

        "TcpSocketConnectOpts",
        -> "interface"

        else -> if (classMode) {
            if (name in OPEN_CLASSES) "open class" else "class"
        } else "sealed interface"
    }

    return ConversionResult(
        name = name,
        body = "external $type $declaration {\n$body\n}",
    )
}

private fun convertFunctions(
    source: String,
    syncOnly: Boolean = false,
): Sequence<ConversionResult> =
    source
        .splitToSequence("\nexport function ", "\nfunction ")
        .drop(1)
        .map { it.substringBefore(";\nexport ") }
        .map { it.substringBefore(";\ninterface ") }
        .map { it.substringBefore("\n/**") }
        // WA for `http.get`
        .map { it.substringBefore(";\nlet ") }
        .map { it.removeSuffix(";") }
        .flatMap { functionSource ->
            val comment = "/**\n" + ("\n" + source.substringBefore(functionSource))
                .substringAfterLast("\n/**\n")
                .substringBeforeLast("\n */\n")
                .let { it + "\n */" }
                .replace("* /*\n", "* ---\n")

            convertFunction(functionSource, comment, syncOnly)
        }

private fun convertFunction(
    source: String,
    comment: String,
    syncOnly: Boolean,
): Sequence<ConversionResult> {
    val name = source.substringBefore("(")

    if ("{" in source && !(name == "readFile" || name == "writeFile"))
        return emptySequence()

    if (syncOnly && !(name.endsWith("Sync") || name.endsWith("Stream")))
        return emptySequence()

    if ("parseQueryString: false" in source || "parseQueryString: true" in source)
        return emptySequence()

    val parameters = source.substringAfter("(")
        .substringBeforeLast(")")
        .splitToSequence(", ", ",")
        .map { it.trim() }
        .filter { it.isNotEmpty() }
        .map { convertParameter(it) }
        .toList()

    val returnType = kotlinType(source.substringAfter("): "), name)

    // ignore fallbacks
    if ("/* string | Buffer */" in returnType)
        return emptySequence()

    val returnDeclaration = when (returnType) {
        "Unit" -> ""
        else -> ": $returnType"
    }

    val finalName = if (returnType.startsWith("Promise<")) name + "Async" else name
    val params = parameters
        .takeIf { it.isNotEmpty() }
        ?.joinToString(",\n", "\n", ",\n")
        ?: ""

    var body = "external fun $finalName(" +
            params +
            ")$returnDeclaration"

    if (name != finalName)
        body = "@JsName(\"$name\")\n$body"

    if (comment.isNotEmpty())
        body = "$comment\n$body"

    return sequenceOf(
        ConversionResult(finalName, body),
        suspendFunctions(name, parameters, returnType)
    ).filterNotNull()
}

private fun convertParameter(
    source: String,
): Parameter {
    val name = source
        .substringBefore("?:")
        .substringBefore(":")

    val typeSource = source
        .substringAfter(":")
        .removePrefix(" ")
        .removePrefix("\n")

    val finalName = when (name) {
        "object" -> "o"
        else -> name
    }

    return Parameter(
        name = finalName,
        type = kotlinType(typeSource, name),
        optional = source.startsWith("$name?"),
    )
}

private fun suspendFunctions(
    name: String,
    parameters: List<Parameter>,
    returnType: String,
): ConversionResult? {
    val promiseResult = returnType.removeSurrounding("Promise<", ">")
    if (promiseResult == returnType)
        return null

    val endIndex = parameters.lastIndex
    val startIndex = parameters.indexOfFirst { it.optional }
        .takeIf { it != -1 }
        ?: endIndex

    var body = (startIndex..endIndex)
        .map { parameters.subList(0, it + 1) }
        .map { it.map { it.copy(optional = false) } }
        .map { params -> suspendFunction(name, params, promiseResult) }
        .joinToString("\n\n")

    return ConversionResult(name, body)
}

private fun suspendFunction(
    name: String,
    parameters: List<Parameter>,
    returnType: String,
): String {
    val declaration = "suspend fun $name(" +
            parameters.joinToString(",\n", "\n", ",\n") +
            ")"

    val call = "${name}Async(" +
            parameters.joinToString(",\n", "\n", ",\n") {
                "${it.name} = ${it.name}"
            } +
            ").await()"

    return if (returnType != "Void") {
        "$declaration : $returnType =\n $call"
    } else {
        "$declaration {\n $call \n}"
    }
}
