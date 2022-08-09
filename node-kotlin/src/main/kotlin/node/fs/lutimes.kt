// Automatically generated - do not modify!

@file:JsModule("node:fs/promises")
@file:JsNonModule

package node.fs

/**
 * Changes the access and modification times of a file in the same way as `fsPromises.utimes()`, with the difference that if the path refers to a
 * symbolic link, then the link is not dereferenced: instead, the timestamps of
 * the symbolic link itself are changed.
 * @since v14.5.0, v12.19.0
 * @return Fulfills with `undefined` upon success.
 */
external fun lutimes(
    path: PathLike,
    atime: dynamic, /* string | number | Date */
    mtime: dynamic, /* string | number | Date */
): kotlin.js.Promise<Unit>
