// Automatically generated - do not modify!

package web.http

import seskar.js.JsValue
import seskar.js.JsVirtual

@JsVirtual
sealed external interface RequestRedirect {
    companion object {
        @JsValue("error")
        val error: RequestRedirect

        @JsValue("follow")
        val follow: RequestRedirect

        @JsValue("manual")
        val manual: RequestRedirect
    }
}
