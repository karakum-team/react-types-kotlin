package karakum.typescript

internal const val DYNAMIC = "dynamic"
internal const val UNIT = "Unit"

internal const val STRING = "String"

private val STANDARD_TYPE_MAP = mapOf(
    "any" to "Any",
    "object" to "Any",
    "{}" to "Any",

    "boolean" to "Boolean",
    "string" to STRING,

    "never" to "Nothing",

    "number" to "Double",

    "void" to UNIT,
    "null" to "Nothing?",
    "undefined" to "Nothing?",

    "Date" to "kotlin.js.Date",

    "false" to "Boolean /* false */",
    "true" to "Boolean /* true */",

    "MapLike<string>" to "MapLike<String>",
    "MapLike<string[]>" to "MapLike<ReadonlyArray<String>>",

    "-1" to "Double /* -1 */",

    "() => T" to "() -> T",

    "(data: string) => string" to "(data: String) -> String",
    "(message: string) => void" to "(message: String) -> Unit",
    "(project: string) => CustomTransformers" to "(project: String) -> CustomTransformers",
    // "(value: V, key: K) => void" to "(value: V, key: K) -> Unit",
    "(pos: number) => number" to "(pos: Double) -> Double",
    "Iterator<[K, V]>" to "Iterator<$DYNAMIC /* [K, V] */>",
    "Iterator<[T, T]>" to "Iterator<$DYNAMIC /* [T, T] */>",
)

internal fun kotlinType(
    type: String,
    name: String,
): String {
    if (type.startsWith("readonly "))
        return kotlinType(type.removePrefix("readonly "), name)

    if (type.endsWith(" | undefined")) {
        var result = kotlinType(type.removeSuffix(" | undefined"), name)
        if (!result.startsWith(DYNAMIC)) result += "?"
        return result
    }

    STANDARD_TYPE_MAP[type]
        ?.also { return it }

    if (" | " in type)
        return "$DYNAMIC /* $type */"

    if (type.startsWith("\""))
        return "$STRING /* $type */"

    if ("[\"" in type)
        return "$DYNAMIC /* $type */"

    if (type.endsWith("[]"))
        return "ReadonlyArray<${kotlinType(type.removeSuffix("[]"), name)}>"

    if (type.startsWith("Promise<")) {
        val parameter = kotlinType(type.removeSurrounding("Promise<", ">"), name)
        return "kotlin.js.Promise<$parameter>"
    }

    return type
}
