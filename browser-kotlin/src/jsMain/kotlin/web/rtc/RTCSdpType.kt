// Automatically generated - do not modify!

package web.rtc

import seskar.js.JsValue
import seskar.js.JsVirtual

@JsVirtual
sealed external interface RTCSdpType {
    companion object {
        @JsValue("answer")
        val answer: RTCSdpType

        @JsValue("offer")
        val offer: RTCSdpType

        @JsValue("pranswer")
        val pranswer: RTCSdpType

        @JsValue("rollback")
        val rollback: RTCSdpType
    }
}
