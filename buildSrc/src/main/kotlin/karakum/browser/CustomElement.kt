package karakum.browser

internal const val ATTRIBUTE_CHANGED_CALLBACK = "AttributeChangedCallback"
internal const val CUSTOM_ELEMENT_CALLBACKS = "CustomElementCallbacks"

private val CALLBACKS = mapOf(
    "connectedCallback" to "() -> Unit",
    "disconnectedCallback" to "() -> Unit",
    "adoptedCallback" to "() -> Unit",
    "attributeChangedCallback" to ATTRIBUTE_CHANGED_CALLBACK,
)

internal fun customElementTypes(): Sequence<ConversionResult> =
    sequenceOf(
        ConversionResult(
            name = ATTRIBUTE_CHANGED_CALLBACK,
            body = """
            typealias $ATTRIBUTE_CHANGED_CALLBACK = (
                name: String,
                oldValue: Any?,
                newValue: Any?,
            ) -> Unit
            """.trimIndent(),
            pkg = "web.components"
        ),
        CustomElementCallbacks(),
    )

private fun CustomElementCallbacks(): ConversionResult {
    val members = CALLBACKS.entries.joinToString("\n") { (name, type) ->
        val comment = """
        /**
         * [MDN Reference](https://developer.mozilla.org/en-US/docs/Web/API/Web_components#${name.lowercase()})
         */
        """.trimIndent()

        val declaration = """
        var $name: ($type)?
            get() = definedExternally
            set(value) = definedExternally
        """.trimIndent()

        "$comment\n$declaration"
    }

    val body = "external interface $CUSTOM_ELEMENT_CALLBACKS {\n" +
            members +
            "\n}"

    return ConversionResult(
        name = CUSTOM_ELEMENT_CALLBACKS,
        body = body,
        pkg = "web.components"
    )
}
