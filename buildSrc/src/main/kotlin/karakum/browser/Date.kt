package karakum.browser

import java.io.File

private const val DATE = "Date"

internal fun dateDeclarations(
    definitionsDir: File,
): Sequence<ConversionResult> {
    val members = dateMembers(dateRawContent(definitionsDir, DATE))
    val staticMembers = dateMembers(dateRawContent(definitionsDir, "DateConstructor"), static = true)
    val constructors = staticMembers.filter { "constructor(" in it }

    val body = """
    external class $DATE() {
        ${constructors.joinToString("\n")}
        ${members.joinToString("\n")}

        companion object {
            ${(staticMembers - constructors).joinToString("\n")}
        }
    }
    """.trimIndent()

    return sequenceOf(
        ConversionResult(
            name = DATE,
            body = body,
            pkg = "js.date",
        )
    )
}

private fun dateMembers(
    content: String,
    static: Boolean = false,
): List<String> =
    content.splitToSequence(";\n")
        .map { it.trim() }
        .filter { it.isNotEmpty() }
        .mapNotNull { dateMember(it, static = static) }
        .toList()

private fun dateMember(
    content: String,
    static: Boolean,
): String? {
    if ("\n" in content) {
        val comment = content.substringBeforeLast("\n")
        val member = dateMember(content.substringAfterLast("\n"), static = static)
            ?: return null

        return comment + "\n" + member
    }

    if (static) {
        return dateMember(content.replace("): number", "): JsLong"), static = false)
    }

    if (content.startsWith("new (")) {
        return content
            .replace("new (", "constructor(")
            .replace("): $DATE", ")")
            .replace("(value: number)", "(value: JsLong)")
            .replace("?: number", ": Int = definedExternally")
            .replace(": number", ": Int")
            .replace(": string", ": String")
            .replace(", ", ", \n")
    }

    return "fun " + content
        .replace("valueOf(): number", "valueOf(): JsLong")
        .replace("getTime(): number", "getTime(): JsLong")
        .replace("setTime(time: number): number", "setTime(time: JsLong): JsLong")
        .replace("?: number", ": Int = definedExternally")
        .replace(": number", ": Int")
        .replace(": string", ": String")
        .replace(", ", ", \n")
}

private fun dateRawContent(
    definitionsDir: File,
    interfaceName: String,
): String =
    definitionsDir.listFiles()!!
        .filter { it.name.endsWith(".date.d.ts") || it.name.endsWith(".core.d.ts") || it.name == "lib.es5.d.ts" }
        .filter { it.name != "lib.es2017.date.d.ts" }
        .sortedBy { file ->
            file.name
                .removePrefix("lib.es")
                .substringBefore(".")
                .toIntOrNull()
                ?: 3000
        }
        .map {
            it.readText()
                .replace("\r\n", "\n")
                .substringAfter("\ninterface $interfaceName {\n", "")
                .substringBefore("\n}")
                .trimIndent()
                .replace("\n\n", "\n")
        }
        .filter { it.isNotEmpty() }
        .joinToString("\n")
        .replace("new (): Date;\n", "")
        .replace("\nnew (value: number | string | Date): Date;", "")
        .replace("new (value: number | string): Date;\n", "new (value: number | string | Date): Date;\n")
        .replace("\n(): string;\n", "\n")
        .replace("readonly prototype: Date;\n", "")
        .splitUnion("number | string | Date")
        .splitUnion(
            "LocalesArgument",
            "UnicodeBCP47LocaleIdentifier | Locale | UnicodeBCP47LocaleIdentifier[] | Locale[]",
        )
