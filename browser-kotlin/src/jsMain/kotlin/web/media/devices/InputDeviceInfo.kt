// Automatically generated - do not modify!

package web.media.devices

import web.media.streams.MediaTrackCapabilities

/**
 * Available only in secure contexts.
 *
 * [MDN Reference](https://developer.mozilla.org/docs/Web/API/InputDeviceInfo)
 */
sealed external class InputDeviceInfo :
    MediaDeviceInfo {
    /** [MDN Reference](https://developer.mozilla.org/docs/Web/API/InputDeviceInfo/getCapabilities) */
    fun getCapabilities(): MediaTrackCapabilities
}
