package karakum.browser

internal const val EVENT_TARGET = "EventTarget"

// language=kotlin
private val EVENT_TARGET_BODY: String = """
open external class EventTarget {
    fun <E : Event<EventTarget>> addEventListener(
        type: EventType<E>,
        callback: EventHandler<E>,
        options: AddEventListenerOptions? = definedExternally,
    )

    inline fun <E : Event<EventTarget>> addEventHandler(
        type: EventType<E>,
        noinline handler: EventHandler<E>,
    ): () -> Unit =
        addEventHandler(
            target = this,
            type = type,
            handler = handler,
        )

    inline fun <E : Event<EventTarget>> addEventHandler(
        type: EventType<E>,
        options: AddEventListenerOptions?,
        noinline handler: EventHandler<E>,
    ): () -> Unit =
        addEventHandler(
            target = this,
            type = type,
            handler = handler,
            options = options,
        )

    fun <E : Event<EventTarget>> removeEventListener(
        type: EventType<E>,
        callback: EventHandler<E>,
        options: EventListenerOptions? = definedExternally,
    )

    fun dispatchEvent(
        event: Event<*>,
    ): Boolean
}

@PublishedApi
internal fun <E : Event<EventTarget>> addEventHandler(
    target: EventTarget,
    type: EventType<E>,
    handler: EventHandler<E>,
): () -> Unit =
    addEventHandler(
        target = target,
        type = type,
        handler = handler,
        options = undefined,
    )

@PublishedApi
internal fun <E : Event<EventTarget>> addEventHandler(
    target: EventTarget,
    type: EventType<E>,
    handler: EventHandler<E>,
    options: AddEventListenerOptions?,
): () -> Unit {
    target.addEventListener(
        type = type,
        callback = handler,
        options = options,
    )

    return {
        target.removeEventListener(
            type = type,
            callback = handler,
            options = options,
        )
    }
}
""".trimIndent()

internal fun EventTarget(): ConversionResult =
    ConversionResult(
        name = EVENT_TARGET,
        body = EVENT_TARGET_BODY,
        pkg = "web.events",
    )
