package karakum.browser

internal class EventInfo(
    val fqn: String,
    val existed: Boolean = false,
) {
    val name: String = fqn.substringAfterLast(".")
    val pkg: String = fqn.substringBeforeLast(".")
}

internal val EVENT_DATA = listOf(
    EventInfo("web.events.Event", existed = true),

    EventInfo("web.animations.AnimationEvent"),
    EventInfo("web.animations.AnimationPlaybackEvent"),
    EventInfo("web.clipboard.ClipboardEvent"),
    EventInfo("dom.events.CompositionEvent"),
    EventInfo("dom.events.DragEvent"),
    EventInfo("dom.events.FocusEvent"),
    EventInfo("dom.events.KeyboardEvent"),
    EventInfo("dom.events.MouseEvent"),
    EventInfo("dom.events.TouchEvent"),
    EventInfo("dom.events.PointerEvent"),
    EventInfo("dom.events.TransitionEvent"),
    EventInfo("dom.events.UIEvent"),
    EventInfo("dom.events.WheelEvent"),

    EventInfo("web.errors.ErrorEvent"),
    EventInfo("web.messaging.MessageEvent", existed = true),
    EventInfo("web.events.ProgressEvent"),
    EventInfo("dom.events.InputEvent"),
    EventInfo("dom.events.FormDataEvent"),
    EventInfo("dom.events.SubmitEvent"),
    EventInfo("dom.events.MediaEncryptedEvent"),
    EventInfo("web.idb.IDBVersionChangeEvent"),
    EventInfo("media.key.MediaKeyMessageEvent"),
    EventInfo("web.cssom.MediaQueryListEvent"),
    EventInfo("media.recorder.BlobEvent"),
    EventInfo("media.streams.MediaStreamTrackEvent"),
    EventInfo("web.audio.OfflineAudioCompletionEvent"),
    EventInfo("webrtc.RTCDTMFToneChangeEvent"),
    EventInfo("webrtc.RTCDataChannelEvent"),
    EventInfo("webrtc.RTCPeerConnectionIceEvent"),
    EventInfo("webrtc.RTCTrackEvent"),
    EventInfo("webrtc.RTCErrorEvent"),
    EventInfo("webrtc.RTCPeerConnectionIceErrorEvent"),
    EventInfo("webgl.WebGLContextEvent"),
    EventInfo("web.speech.SpeechSynthesisEvent"),
    EventInfo("web.speech.SpeechSynthesisErrorEvent"),
    EventInfo("webvtt.TrackEvent"),
    EventInfo("websockets.CloseEvent"),
    EventInfo("web.device.DeviceMotionEvent"),
    EventInfo("web.device.DeviceOrientationEvent"),
    EventInfo("web.gamepad.GamepadEvent"),
    EventInfo("dom.events.BeforeUnloadEvent"),
    EventInfo("web.history.HashChangeEvent"),
    EventInfo("web.history.PageTransitionEvent"),
    EventInfo("web.history.PopStateEvent"),
    EventInfo("dom.events.PromiseRejectionEvent"),
    EventInfo("web.storage.StorageEvent"),
    EventInfo("web.csp.SecurityPolicyViolationEvent"),
)

internal val WORKER_EVENT_DATA = listOf(
    EventInfo("web.serviceworker.ExtendableEvent"),
    EventInfo("web.serviceworker.ExtendableMessageEvent"),
    EventInfo("web.serviceworker.FetchEvent"),
    EventInfo("web.serviceworker.NotificationEvent"),
)

internal val EVENT_INFO_MAP = (EVENT_DATA + WORKER_EVENT_DATA)
    .associate { it.name to it }

internal val EVENT_CORRECTION_MAP = mapOf(
    "DOMContentLoaded" to "dom_content_loaded",
    "addsourcebuffer" to "add_source_buffer",
    "addtrack" to "add_track",
    "afterprint" to "after_print",
    "animationcancel" to "animation_cancel",
    "animationend" to "animation_end",
    "animationiteration" to "animation_iteration",
    "animationstart" to "animation_start",
    "audioprocess" to "audio_process",
    "auxclick" to "aux_click",
    "beforeinput" to "before_input",
    "beforeprint" to "before_print",
    "beforeunload" to "before_unload",
    "bufferedamountlow" to "buffered_amount_low",
    "canplay" to "can_play",
    "canplaythrough" to "can_play_through",
    "compositionend" to "composition_end",
    "compositionstart" to "composition_start",
    "compositionupdate" to "composition_update",
    "connectionstatechange" to "connection_state_change",
    "contextmenu" to "context_menu",
    "controllerchange" to "controller_change",
    "cuechange" to "cue_change",
    "dataavailable" to "data_available",
    "datachannel" to "data_channel",
    "dblclick" to "dbl_click",
    "devicechange" to "device_change",
    "devicemotion" to "device_motion",
    "deviceorientation" to "device_orientation",
    "dragend" to "drag_end",
    "dragenter" to "drag_enter",
    "dragleave" to "drag_leave",
    "dragover" to "drag_over",
    "dragstart" to "drag_start",
    "durationchange" to "duration_change",
    "enterpictureinpicture" to "enter_picture_in_picture",
    "focusin" to "focus_in",
    "focusout" to "focus_out",
    "formdata" to "form_data",
    "fullscreenchange" to "fullscreen_change",
    "fullscreenerror" to "fullscreen_error",
    "gamepadconnected" to "gamepad_connected",
    "gamepaddisconnected" to "gamepad_disconnected",
    "gatheringstatechange" to "gathering_state_change",
    "gotpointercapture" to "got_pointer_capture",
    "hashchange" to "hash_change",
    "icecandidate" to "ice_candidate",
    "icecandidateerror" to "ice_candidate_error",
    "iceconnectionstatechange" to "ice_connection_state_change",
    "icegatheringstatechange" to "ice_gathering_state_change",
    "keydown" to "key_down",
    "keypress" to "key_press",
    "keystatuseschange" to "key_statuses_change",
    "keyup" to "key_up",
    "languagechange" to "language_change",
    "leavepictureinpicture" to "leave_picture_in_picture",
    "loadeddata" to "loaded_data",
    "loadedmetadata" to "loaded_metadata",
    "loadend" to "load_end",
    "loading" to "loading",
    "loadingdone" to "loading_done",
    "loadingerror" to "loading_error",
    "loadstart" to "load_start",
    "lostpointercapture" to "lost_pointer_capture",
    "messageerror" to "message_error",
    "midimessage" to "midi_message",
    "mousedown" to "mouse_down",
    "mouseenter" to "mouse_enter",
    "mouseleave" to "mouse_leave",
    "mousemove" to "mouse_move",
    "mouseout" to "mouse_out",
    "mouseover" to "mouse_over",
    "mouseup" to "mouse_up",
    "negotiationneeded" to "negotiation_needed",
    "pagehide" to "page_hide",
    "pageshow" to "page_show",
    "paymentmethodchange" to "payment_method_change",
    "pointercancel" to "pointer_cancel",
    "pointerdown" to "pointer_down",
    "pointerenter" to "pointer_enter",
    "pointerleave" to "pointer_leave",
    "pointerlockchange" to "pointer_lock_change",
    "pointerlockerror" to "pointer_lock_error",
    "pointermove" to "pointer_move",
    "pointerout" to "pointer_out",
    "pointerover" to "pointer_over",
    "pointerup" to "pointer_up",
    "popstate" to "pop_state",
    "processorerror" to "process_or_error", //?
    "ratechange" to "rate_change",
    "readystatechange" to "ready_state_change",
    "rejectionhandled" to "rejection_handled",
    "removesourcebuffer" to "remove_source_buffer",
    "removetrack" to "remove_track",
    "resourcetimingbufferfull" to "resource_timing_buffer_full",
    "securitypolicyviolation" to "security_policy_violation",
    "selectionchange" to "selection_change",
    "selectstart" to "select_start",
    "signalingstatechange" to "signaling_state_change",
    "slotchange" to "slot_change",
    "sourceclose" to "source_close",
    "sourceended" to "source_ended",
    "sourceopen" to "source_open",
    "statechange" to "state_change",
    "timeupdate" to "time_update",
    "tonechange" to "tone_change",
    "touchcancel" to "touch_cancel",
    "touchend" to "touch_end",
    "touchmove" to "touch_move",
    "touchstart" to "touch_start",
    "transitioncancel" to "transition_cancel",
    "transitionend" to "transition_end",
    "transitionrun" to "transition_run",
    "transitionstart" to "transition_start",
    "unhandledrejection" to "unhandled_rejection",
    "updateend" to "update_end",
    "updatefound" to "update_found",
    "updatestart" to "update_start",
    "upgradeneeded" to "upgradeneeded",
    "versionchange" to "version_change",
    "visibilitychange" to "visibility_change",
    "voiceschanged" to "voices_changed",
    "volumechange" to "volume_change",
    "waitingforkey" to "waiting_for_key",
    "webkitanimationend" to "webkit_animation_end",
    "webkitanimationiteration" to "webkit_animation_iteration",
    "webkitanimationstart" to "webkit_animation_start",
    "webkitfullscreenchange" to "webkit_fullscreen_change",
    "webkittransitionend" to "webkit_transition_end",

    // OffscreenCanvas
    "contextlost" to "context_lost",
    "contextrestored" to "context_restored",

    // Notifications
    "notificationclick" to "notification_click",
    "notificationclose" to "notification_close",
)
