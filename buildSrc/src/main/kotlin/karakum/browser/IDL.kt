package karakum.browser

import java.io.File

private sealed class MemberNumberData {
    abstract val className: String
}

private data class ParameterData(
    override val className: String,
    val methodName: String,
    val parameterName: String,
    val parameterType: String,
) : MemberNumberData()

private data class MethodReturnData(
    override val className: String,
    val methodName: String,
    val returnType: String,
) : MemberNumberData()

private val NUMBER_TYPE_MAP = mapOf(
    "unsigned short" to "Short",

    "float" to "Float",

    "double" to "Double",
    "unrestricted double" to "Double",

    "long" to "Int",
    "unsigned long" to "Int",

    "long long" to "JsLong",
    "unsigned long long" to "JsLong",
)

internal object IDLRegistry {
    lateinit var rootDirectory: File

    private val idlData: List<String> by lazy {
        rootDirectory
            .listFiles { file -> file.extension == "idl" }!!
            .map { it.readText() }
    }

    private fun hasContent(
        memberContent: String,
    ): Set<String> =
        idlData.asSequence()
            .flatMap { content ->
                content
                    .splitToSequence("\ninterface ")
                    .drop(1)
                    .map { it.substringBefore("\n};") }
                    .filter { memberContent in it }
                    .map { it.substringBefore(" ") }
            }
            .toSet()

    private val typesWithEmptyConstructors: Set<String> by lazy {
        hasContent("  constructor();")
    }

    private val typesWithHtmlConstructors: Set<String> by lazy {
        hasContent("[HTMLConstructor] constructor();")
    }

    private val memberNumberData: List<MemberNumberData> by lazy {
        idlData.flatMap { content ->
            content
                .splitToSequence(
                    "\ninterface ",
                    "]interface ",
                    "\npartial interface ",
                    "]partial interface "
                )
                .drop(1)
                .map { it.substringBefore("\n};") }
                .flatMap { classBody ->
                    val className = classBody
                        .removePrefix("mixin ")
                        .substringBefore("\n")
                        .substringBefore(" ")

                    classBody
                        .substringAfter(" {\n")
                        .removeSuffix(";")
                        .splitToSequence(";\n")
                        .map { it.trim() }
                        .flatMap { line -> getMemberNumberData(className = className, line = line) }
                }
        }
    }

    private fun getMemberNumberData(
        className: String,
        line: String,
    ): Sequence<MemberNumberData> {
        if (line.startsWith("["))
            return getMemberNumberData(className, line.substringAfter("] ", ""))

        val source = line
            .substringAfter("(", "")
            .substringBeforeLast(")", "")
            .ifEmpty { return emptySequence() }

        val methodName = line
            .substringBefore("(")
            .trim()
            .substringAfterLast(" ")

        val parametersData = source
            .splitToSequence(",")
            .map { it.trim() }
            .map { it.substringBefore(" = ") }
            .map { it.substringAfter("] ") }
            .map { it.removePrefix("optional ") }
            .mapNotNull { psource ->
                val type = getNumberType(psource.substringBeforeLast(" "))
                    ?: return@mapNotNull null

                ParameterData(
                    className = className,
                    methodName = methodName,
                    parameterName = psource.substringAfterLast(" "),
                    parameterType = type,
                )
            }

        val returnType = getNumberType(line.substringBefore(" $methodName"))
            ?: return parametersData

        return parametersData.plus(
            MethodReturnData(
                className = className,
                methodName = methodName,
                returnType = returnType,
            )
        )
    }

    private fun getNumberType(
        source: String,
    ): String? {
        val type = NUMBER_TYPE_MAP[source]
        if (type != null)
            return type

        if (!source.startsWith("("))
            return null

        return source
            .substringAfter("(")
            .substringBefore(")")
            .splitToSequence(" or ")
            .mapNotNull { getNumberType(it) }
            .firstOrNull()
    }

    private val parameterTypeMap: Map<Pair<String, String>, String> by lazy {
        memberNumberData.asSequence()
            .filterIsInstance<ParameterData>()
            .associate { (it.className to it.parameterName) to it.parameterType }
            .plus(
                sequenceOf(
                    // TODO: copy from `Document`?
                    ("DocumentOrShadowRoot" to "x") to "Double",
                    ("DocumentOrShadowRoot" to "y") to "Double",
                    ("HTMLCanvasElement" to "quality") to "Double",

                    ("DateTimeFormat" to "date") to "JsLong",
                    ("DateTimeFormat" to "endDate") to "JsLong",
                    ("DateTimeFormat" to "startDate") to "JsLong",
                    ("NumberFormat" to "end") to "Number",
                    ("NumberFormat" to "number") to "Number",
                    ("NumberFormat" to "start") to "Number",
                    ("NumberFormat" to "value") to "Number",
                    ("PluralRules" to "n") to "Int",
                    ("RelativeTimeFormat" to "value") to "Number",
                    ("Segments" to "codeUnitIndex") to "Int",
                )
            )
    }

    fun hasEmptyConstructor(type: String): Boolean =
        type in typesWithEmptyConstructors

    fun hasHtmlConstructor(type: String): Boolean =
        type in typesWithHtmlConstructors

    fun getParameterType(
        className: String,
        parameterName: String,
    ): String =
        parameterTypeMap.getValue(className to parameterName)
}
