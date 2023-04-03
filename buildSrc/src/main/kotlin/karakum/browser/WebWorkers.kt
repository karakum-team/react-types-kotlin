package karakum.browser

import java.io.File

private val WORKER_TYPES = listOf(
    "DedicatedWorkerGlobalScope",
    "WorkerGlobalScope",
    "WorkerLocation",
    "WorkerNavigator",
)

private val SERVICE_WORKER_TYPES = listOf(
    "Client",
    "Clients",
    "ClientQueryOptions",
    "WindowClient",
)

private val PKG_MAP = mapOf(
    "FrameType" to "web.serviceworker",
)

internal fun webWorkersDeclarations(
    definitionsFile: File,
): Sequence<ConversionResult> {
    val content = webWorkersContent(definitionsFile)

    val interfaces = Regex("""interface .+? \{[\s\S]+?\n}""")
        .findAll(content)
        .asSequence()
        .map { it.value }
        .mapNotNull {
            val name = it
                .substringAfter(" ")
                .substringBefore(" ")

            val predefinedPkg = when (name) {
                in WORKER_TYPES -> "web.workers"
                in SERVICE_WORKER_TYPES -> "web.serviceworker"
                else -> return@mapNotNull null
            }

            convertInterface(
                source = it,
                getStaticSource = { getStaticSource(it, content) },
                predefinedPkg = predefinedPkg,
            )
        }

    val types = convertTypes(
        content = content,
        getPkg = PKG_MAP::get,
    ).filter { it.name in PKG_MAP.keys }

    val events = workerEventDeclarations(content)

    return interfaces + types + events
}

internal fun webWorkersContent(
    definitionsFile: File,
): String =
    definitionsFile
        .readText()
        .replace(", WindowOrWorkerGlobalScope", "")
        .replace(
            """ReadonlyArray<T["type"] extends "window" ? WindowClient : Client>""",
            "ReadonlyArray<Client /* | WindowClient */>"
        )
        .splitUnion("string | string[]")
        .splitUnion("string | URL")
        .splitUnion("Response | PromiseLike<Response>", "Response | Promise<Response>")
