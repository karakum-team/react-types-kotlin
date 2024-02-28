// Automatically generated - do not modify!

@file:Suppress(
    "NON_ABSTRACT_MEMBER_OF_EXTERNAL_INTERFACE",
)

package web.serviceworker

import seskar.js.JsValue
import web.events.EventType

sealed external interface NotificationEventTypes_deprecated {
    @Deprecated(
        message = "Legacy type declaration. Use type function instead!",
        replaceWith = ReplaceWith("NotificationEvent.notificationClick()"),
    )
    @JsValue("notificationclick")
    val NOTIFICATION_CLICK: EventType<NotificationEvent<*>>
        get() = definedExternally

    @Deprecated(
        message = "Legacy type declaration. Use type function instead!",
        replaceWith = ReplaceWith("NotificationEvent.notificationClose()"),
    )
    @JsValue("notificationclose")
    val NOTIFICATION_CLOSE: EventType<NotificationEvent<*>>
        get() = definedExternally
}
