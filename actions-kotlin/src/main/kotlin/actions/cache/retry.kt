// Automatically generated - do not modify!

package actions.cache

import kotlin.js.Promise

external fun <T> retry(
    name: String,
    method: () -> Promise<T>,
    getStatusCode: (arg0: T) -> number?,
    maxAttempts: Number = definedExternally,
    delay: Number = definedExternally,
    onError: ((arg0: Error) -> T?)? = definedExternally,
): Promise<T>
