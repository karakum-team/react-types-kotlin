// Automatically generated - do not modify!

package web.streams

import js.errors.JsError

sealed external class ReadableStreamDefaultController<R> {
    val desiredSize: Number?
    fun close()
    fun enqueue(chunk: R = definedExternally)
    fun error(error: JsError = definedExternally)
}
