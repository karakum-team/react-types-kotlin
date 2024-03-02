// Automatically generated - do not modify!

@file:Suppress(
    "NESTED_CLASS_IN_EXTERNAL_INTERFACE",
)

package web.gpu

import seskar.js.JsValue
import seskar.js.JsVirtual

@JsVirtual
sealed external interface GPUVertexStepMode {
    companion object {
        @JsValue("vertex")
        val vertex: GPUVertexStepMode

        @JsValue("instance")
        val instance: GPUVertexStepMode
    }
}
