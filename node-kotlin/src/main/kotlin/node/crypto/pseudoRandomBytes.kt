// Automatically generated - do not modify!

@file:JsModule("node:crypto")
@file:JsNonModule

package node.crypto

import node.buffer.Buffer

/**
 * Generates cryptographically strong pseudorandom data. The `size` argument
 * is a number indicating the number of bytes to generate.
 *
 * If a `callback` function is provided, the bytes are generated asynchronously
 * and the `callback` function is invoked with two arguments: `err` and `buf`.
 * If an error occurs, `err` will be an `Error` object; otherwise it is `null`. The`buf` argument is a `Buffer` containing the generated bytes.
 *
 * ```js
 * // Asynchronous
 * const {
 *   randomBytes
 * } = await import('crypto');
 *
 * randomBytes(256, (err, buf) => {
 *   if (err) throw err;
 *   console.log(`${buf.length} bytes of random data: ${buf.toString('hex')}`);
 * });
 * ```
 *
 * If the `callback` function is not provided, the random bytes are generated
 * synchronously and returned as a `Buffer`. An error will be thrown if
 * there is a problem generating the bytes.
 *
 * ```js
 * // Synchronous
 * const {
 *   randomBytes
 * } = await import('crypto');
 *
 * const buf = randomBytes(256);
 * console.log(
 *   `${buf.length} bytes of random data: ${buf.toString('hex')}`);
 * ```
 *
 * The `crypto.randomBytes()` method will not complete until there is
 * sufficient entropy available.
 * This should normally never take longer than a few milliseconds. The only time
 * when generating the random bytes may conceivably block for a longer period of
 * time is right after boot, when the whole system is still low on entropy.
 *
 * This API uses libuv's threadpool, which can have surprising and
 * negative performance implications for some applications; see the `UV_THREADPOOL_SIZE` documentation for more information.
 *
 * The asynchronous version of `crypto.randomBytes()` is carried out in a single
 * threadpool request. To minimize threadpool task length variation, partition
 * large `randomBytes` requests when doing so as part of fulfilling a client
 * request.
 * @since v0.5.8
 * @param size The number of bytes to generate. The `size` must not be larger than `2**31 - 1`.
 * @return if the `callback` function is not provided.
 */
external fun pseudoRandomBytes(
    size: Number,
): Buffer


/**
 * Generates cryptographically strong pseudorandom data. The `size` argument
 * is a number indicating the number of bytes to generate.
 *
 * If a `callback` function is provided, the bytes are generated asynchronously
 * and the `callback` function is invoked with two arguments: `err` and `buf`.
 * If an error occurs, `err` will be an `Error` object; otherwise it is `null`. The`buf` argument is a `Buffer` containing the generated bytes.
 *
 * ```js
 * // Asynchronous
 * const {
 *   randomBytes
 * } = await import('crypto');
 *
 * randomBytes(256, (err, buf) => {
 *   if (err) throw err;
 *   console.log(`${buf.length} bytes of random data: ${buf.toString('hex')}`);
 * });
 * ```
 *
 * If the `callback` function is not provided, the random bytes are generated
 * synchronously and returned as a `Buffer`. An error will be thrown if
 * there is a problem generating the bytes.
 *
 * ```js
 * // Synchronous
 * const {
 *   randomBytes
 * } = await import('crypto');
 *
 * const buf = randomBytes(256);
 * console.log(
 *   `${buf.length} bytes of random data: ${buf.toString('hex')}`);
 * ```
 *
 * The `crypto.randomBytes()` method will not complete until there is
 * sufficient entropy available.
 * This should normally never take longer than a few milliseconds. The only time
 * when generating the random bytes may conceivably block for a longer period of
 * time is right after boot, when the whole system is still low on entropy.
 *
 * This API uses libuv's threadpool, which can have surprising and
 * negative performance implications for some applications; see the `UV_THREADPOOL_SIZE` documentation for more information.
 *
 * The asynchronous version of `crypto.randomBytes()` is carried out in a single
 * threadpool request. To minimize threadpool task length variation, partition
 * large `randomBytes` requests when doing so as part of fulfilling a client
 * request.
 * @since v0.5.8
 * @param size The number of bytes to generate. The `size` must not be larger than `2**31 - 1`.
 * @return if the `callback` function is not provided.
 */
external fun pseudoRandomBytes(
    size: Number,
    callback: (
        error: Error?,
        buf: Buffer,
    ) -> Unit,
)
