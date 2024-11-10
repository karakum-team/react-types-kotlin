// Automatically generated - do not modify!

package web.messaging

import js.array.ReadonlyArray
import js.transferable.Transferable
import web.events.*
import web.serialization.StructuredSerializeOptions

/**
 * This Channel Messaging API interface represents one of the two ports of a MessageChannel, allowing messages to be sent from one port and listening out for them arriving at the other.
 *
 * [MDN Reference](https://developer.mozilla.org/docs/Web/API/MessagePort)
 */
external class MessagePort
private constructor() :
    EventTarget,
    MessageEventSource,
    Transferable {
    /**
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/MessagePort/message_event)
     */
    var onmessage: EventHandler<MessageEvent<*>, MessagePort, MessagePort>?

    /**
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/MessagePort/messageerror_event)
     */
    var onmessageerror: EventHandler<MessageEvent<*>, MessagePort, MessagePort>?

    /**
     * Disconnects the port, so that it is no longer active.
     *
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/MessagePort/close)
     */
    fun close()

    /**
     * Posts a message through the channel. Objects listed in transfer are transferred, not just cloned, meaning that they are no longer usable on the sending side.
     *
     * Throws a "DataCloneError" DOMException if transfer contains duplicate objects or port, or if message could not be cloned.
     *
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/MessagePort/postMessage)
     */
    fun postMessage(
        message: Any?,
        transfer: ReadonlyArray<Transferable>,
    )

    fun postMessage(
        message: Any?,
        options: StructuredSerializeOptions = definedExternally,
    )

    /**
     * Begins dispatching messages received on the port.
     *
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/MessagePort/start)
     */
    fun start()

    /**
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/MessagePort/close_event)
     */
    @JsEvent("close")
    val closeEvent: EventInstance<Event, MessagePort /* this */, MessagePort /* this */>

    /**
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/MessagePort/message_event)
     */
    @JsEvent("message")
    val messageEvent: EventInstance<MessageEvent<Any?>, MessagePort /* this */, MessagePort /* this */>

    /**
     * [MDN Reference](https://developer.mozilla.org/docs/Web/API/MessagePort/messageerror_event)
     */
    @JsEvent("messageerror")
    val messageErrorEvent: EventInstance<MessageEvent<Any?>, MessagePort /* this */, MessagePort /* this */>
}
