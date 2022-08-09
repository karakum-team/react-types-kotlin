// Automatically generated - do not modify!

@file:JsModule("node:fs/promises")
@file:JsNonModule

package node.fs

/**
 * Creates a new link from the `existingPath` to the `newPath`. See the POSIX [`link(2)`](http://man7.org/linux/man-pages/man2/link.2.html) documentation for more detail.
 * @since v10.0.0
 * @return Fulfills with `undefined` upon success.
 */
external fun link(
    existingPath: PathLike,
    newPath: PathLike,
): kotlin.js.Promise<Unit>
