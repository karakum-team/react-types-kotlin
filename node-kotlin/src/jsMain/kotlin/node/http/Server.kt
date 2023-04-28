// Automatically generated - do not modify!

@file:JsModule("node:http")

package node.http

import node.buffer.Buffer
import node.events.Event
import node.events.EventType
import node.net.Socket
import node.stream.Duplex

open external class Server<
        Request : IncomingMessage,
        Response : ServerResponse<*>,
        > : node.net.Server {
    constructor(requestListener: RequestListener<Request, Response> = definedExternally)
    constructor(
        options: ServerOptions<Request, Response>,
        requestListener: RequestListener<Request, Response> = definedExternally,
    )

    /**
     * Sets the timeout value for sockets, and emits a `'timeout'` event on
     * the Server object, passing the socket as an argument, if a timeout
     * occurs.
     *
     * If there is a `'timeout'` event listener on the Server object, then it
     * will be called with the timed-out socket as an argument.
     *
     * By default, the Server does not timeout sockets. However, if a callback
     * is assigned to the Server's `'timeout'` event, timeouts must be handled
     * explicitly.
     * @since v0.9.12
     * @param [msecs=0 (no timeout)]
     */
    open fun setTimeout(
        msecs: Number = definedExternally,
        callback: () -> Unit = definedExternally,
    ) /* : this */

    open fun setTimeout(callback: () -> Unit) /* : this */

    /**
     * Limits maximum incoming headers count. If set to 0, no limit will be applied.
     * @since v0.7.0
     */
    open var maxHeadersCount: Number?

    /**
     * The maximum number of requests socket can handle
     * before closing keep alive connection.
     *
     * A value of `0` will disable the limit.
     *
     * When the limit is reached it will set the `Connection` header value to `close`,
     * but will not actually close the connection, subsequent requests sent
     * after the limit is reached will get `503 Service Unavailable` as a response.
     * @since v16.10.0
     */
    open var maxRequestsPerSocket: Number?

    /**
     * The number of milliseconds of inactivity before a socket is presumed
     * to have timed out.
     *
     * A value of `0` will disable the timeout behavior on incoming connections.
     *
     * The socket timeout logic is set up on connection, so changing this
     * value only affects new connections to the server, not any existing connections.
     * @since v0.9.12
     */
    open var timeout: Number

    /**
     * Limit the amount of time the parser will wait to receive the complete HTTP
     * headers.
     *
     * If the timeout expires, the server responds with status 408 without
     * forwarding the request to the request listener and then closes the connection.
     *
     * It must be set to a non-zero value (e.g. 120 seconds) to protect against
     * potential Denial-of-Service attacks in case the server is deployed without a
     * reverse proxy in front.
     * @since v11.3.0, v10.14.0
     */
    open var headersTimeout: Number

    /**
     * The number of milliseconds of inactivity a server needs to wait for additional
     * incoming data, after it has finished writing the last response, before a socket
     * will be destroyed. If the server receives new data before the keep-alive
     * timeout has fired, it will reset the regular inactivity timeout, i.e.,`server.timeout`.
     *
     * A value of `0` will disable the keep-alive timeout behavior on incoming
     * connections.
     * A value of `0` makes the http server behave similarly to Node.js versions prior
     * to 8.0.0, which did not have a keep-alive timeout.
     *
     * The socket timeout logic is set up on connection, so changing this value only
     * affects new connections to the server, not any existing connections.
     * @since v8.0.0
     */
    open var keepAliveTimeout: Number

    /**
     * Sets the timeout value in milliseconds for receiving the entire request from
     * the client.
     *
     * If the timeout expires, the server responds with status 408 without
     * forwarding the request to the request listener and then closes the connection.
     *
     * It must be set to a non-zero value (e.g. 120 seconds) to protect against
     * potential Denial-of-Service attacks in case the server is deployed without a
     * reverse proxy in front.
     * @since v14.11.0
     */
    open var requestTimeout: Number

    /**
     * Closes all connections connected to this server.
     * @since v18.2.0
     */
    open fun closeAllConnections()

    /**
     * Closes all connections connected to this server which are not sending a request or waiting for a response.
     * @since v18.2.0
     */
    open fun closeIdleConnections()
    override fun addListener(
        event: EventType,
        listener: Function<Unit>,
    ) /* : this */

    override fun addListener(
        event: Event.CLOSE,
        listener: () -> Unit,
    ) /* : this */

    override fun addListener(
        event: Event.CONNECTION,
        listener: (socket: Socket) -> Unit,
    ) /* : this */

    override fun addListener(
        event: Event.ERROR,
        listener: (error: Error) -> Unit,
    ) /* : this */

    override fun addListener(
        event: Event.LISTENING,
        listener: () -> Unit,
    ) /* : this */

    open fun addListener(
        event: Event.CHECK_CONTINUE,
        listener: RequestListener<Request, Response>,
    ) /* : this */

    open fun addListener(
        event: Event.CHECK_EXPECTATION,
        listener: RequestListener<Request, Response>,
    ) /* : this */

    open fun addListener(
        event: Event.CLIENT_ERROR,
        listener: (
            error: Error,
            socket: Duplex,
        ) -> Unit,
    ) /* : this */

    open fun addListener(
        event: Event.CONNECT,
        listener: (
            req: Request,
            socket: Duplex,
            head: Buffer,
        ) -> Unit,
    ) /* : this */

    open fun addListener(
        event: Event.DROP_REQUEST,
        listener: (
            req: Request,
            socket: Duplex,
        ) -> Unit,
    ) /* : this */

    open fun addListener(
        event: Event.REQUEST,
        listener: RequestListener<Request, Response>,
    ) /* : this */

    open fun addListener(
        event: Event.UPGRADE,
        listener: (
            req: Request,
            socket: Duplex,
            head: Buffer,
        ) -> Unit,
    ) /* : this */

    override fun emit(
        event: EventType,
        vararg args: Any,
    ): Boolean

    override fun emit(event: Event.CLOSE): Boolean
    override fun emit(
        event: Event.CONNECTION,
        socket: Socket,
    ): Boolean

    override fun emit(
        event: Event.ERROR,
        err: Error,
    ): Boolean

    override fun emit(event: Event.LISTENING): Boolean
    open fun emit(
        event: Event.CHECK_CONTINUE,
        req: Request,
        res: Response,
    ): Boolean

    open fun emit(
        event: Event.CHECK_EXPECTATION,
        req: Request,
        res: Response,
    ): Boolean

    open fun emit(
        event: Event.CLIENT_ERROR,
        err: Error,
        socket: Duplex,
    ): Boolean

    open fun emit(
        event: Event.CONNECT,
        req: Request,
        socket: Duplex,
        head: Buffer,
    ): Boolean

    open fun emit(
        event: Event.DROP_REQUEST,
        req: Request,
        socket: Duplex,
    ): Boolean

    open fun emit(
        event: Event.REQUEST,
        req: Request,
        res: Response,
    ): Boolean

    open fun emit(
        event: Event.UPGRADE,
        req: Request,
        socket: Duplex,
        head: Buffer,
    ): Boolean

    override fun on(
        event: EventType,
        listener: Function<Unit>,
    ) /* : this */

    override fun on(
        event: Event.CLOSE,
        listener: () -> Unit,
    ) /* : this */

    override fun on(
        event: Event.CONNECTION,
        listener: (socket: Socket) -> Unit,
    ) /* : this */

    override fun on(
        event: Event.ERROR,
        listener: (error: Error) -> Unit,
    ) /* : this */

    override fun on(
        event: Event.LISTENING,
        listener: () -> Unit,
    ) /* : this */

    open fun on(
        event: Event.CHECK_CONTINUE,
        listener: RequestListener<Request, Response>,
    ) /* : this */

    open fun on(
        event: Event.CHECK_EXPECTATION,
        listener: RequestListener<Request, Response>,
    ) /* : this */

    open fun on(
        event: Event.CLIENT_ERROR,
        listener: (
            error: Error,
            socket: Duplex,
        ) -> Unit,
    ) /* : this */

    open fun on(
        event: Event.CONNECT,
        listener: (
            req: Request,
            socket: Duplex,
            head: Buffer,
        ) -> Unit,
    ) /* : this */

    open fun on(
        event: Event.DROP_REQUEST,
        listener: (
            req: Request,
            socket: Duplex,
        ) -> Unit,
    ) /* : this */

    open fun on(
        event: Event.REQUEST,
        listener: RequestListener<Request, Response>,
    ) /* : this */

    open fun on(
        event: Event.UPGRADE,
        listener: (
            req: Request,
            socket: Duplex,
            head: Buffer,
        ) -> Unit,
    ) /* : this */

    override fun once(
        event: EventType,
        listener: Function<Unit>,
    ) /* : this */

    override fun once(
        event: Event.CLOSE,
        listener: () -> Unit,
    ) /* : this */

    override fun once(
        event: Event.CONNECTION,
        listener: (socket: Socket) -> Unit,
    ) /* : this */

    override fun once(
        event: Event.ERROR,
        listener: (error: Error) -> Unit,
    ) /* : this */

    override fun once(
        event: Event.LISTENING,
        listener: () -> Unit,
    ) /* : this */

    open fun once(
        event: Event.CHECK_CONTINUE,
        listener: RequestListener<Request, Response>,
    ) /* : this */

    open fun once(
        event: Event.CHECK_EXPECTATION,
        listener: RequestListener<Request, Response>,
    ) /* : this */

    open fun once(
        event: Event.CLIENT_ERROR,
        listener: (
            error: Error,
            socket: Duplex,
        ) -> Unit,
    ) /* : this */

    open fun once(
        event: Event.CONNECT,
        listener: (
            req: Request,
            socket: Duplex,
            head: Buffer,
        ) -> Unit,
    ) /* : this */

    open fun once(
        event: Event.DROP_REQUEST,
        listener: (
            req: Request,
            socket: Duplex,
        ) -> Unit,
    ) /* : this */

    open fun once(
        event: Event.REQUEST,
        listener: RequestListener<Request, Response>,
    ) /* : this */

    open fun once(
        event: Event.UPGRADE,
        listener: (
            req: Request,
            socket: Duplex,
            head: Buffer,
        ) -> Unit,
    ) /* : this */

    override fun prependListener(
        event: EventType,
        listener: Function<Unit>,
    ) /* : this */

    override fun prependListener(
        event: Event.CLOSE,
        listener: () -> Unit,
    ) /* : this */

    override fun prependListener(
        event: Event.CONNECTION,
        listener: (socket: Socket) -> Unit,
    ) /* : this */

    override fun prependListener(
        event: Event.ERROR,
        listener: (error: Error) -> Unit,
    ) /* : this */

    override fun prependListener(
        event: Event.LISTENING,
        listener: () -> Unit,
    ) /* : this */

    open fun prependListener(
        event: Event.CHECK_CONTINUE,
        listener: RequestListener<Request, Response>,
    ) /* : this */

    open fun prependListener(
        event: Event.CHECK_EXPECTATION,
        listener: RequestListener<Request, Response>,
    ) /* : this */

    open fun prependListener(
        event: Event.CLIENT_ERROR,
        listener: (
            error: Error,
            socket: Duplex,
        ) -> Unit,
    ) /* : this */

    open fun prependListener(
        event: Event.CONNECT,
        listener: (
            req: Request,
            socket: Duplex,
            head: Buffer,
        ) -> Unit,
    ) /* : this */

    open fun prependListener(
        event: Event.DROP_REQUEST,
        listener: (
            req: Request,
            socket: Duplex,
        ) -> Unit,
    ) /* : this */

    open fun prependListener(
        event: Event.REQUEST,
        listener: RequestListener<Request, Response>,
    ) /* : this */

    open fun prependListener(
        event: Event.UPGRADE,
        listener: (
            req: Request,
            socket: Duplex,
            head: Buffer,
        ) -> Unit,
    ) /* : this */

    override fun prependOnceListener(
        event: EventType,
        listener: Function<Unit>,
    ) /* : this */

    override fun prependOnceListener(
        event: Event.CLOSE,
        listener: () -> Unit,
    ) /* : this */

    override fun prependOnceListener(
        event: Event.CONNECTION,
        listener: (socket: Socket) -> Unit,
    ) /* : this */

    override fun prependOnceListener(
        event: Event.ERROR,
        listener: (error: Error) -> Unit,
    ) /* : this */

    override fun prependOnceListener(
        event: Event.LISTENING,
        listener: () -> Unit,
    ) /* : this */

    open fun prependOnceListener(
        event: Event.CHECK_CONTINUE,
        listener: RequestListener<Request, Response>,
    ) /* : this */

    open fun prependOnceListener(
        event: Event.CHECK_EXPECTATION,
        listener: RequestListener<Request, Response>,
    ) /* : this */

    open fun prependOnceListener(
        event: Event.CLIENT_ERROR,
        listener: (
            error: Error,
            socket: Duplex,
        ) -> Unit,
    ) /* : this */

    open fun prependOnceListener(
        event: Event.CONNECT,
        listener: (
            req: Request,
            socket: Duplex,
            head: Buffer,
        ) -> Unit,
    ) /* : this */

    open fun prependOnceListener(
        event: Event.DROP_REQUEST,
        listener: (
            req: Request,
            socket: Duplex,
        ) -> Unit,
    ) /* : this */

    open fun prependOnceListener(
        event: Event.REQUEST,
        listener: RequestListener<Request, Response>,
    ) /* : this */

    open fun prependOnceListener(
        event: Event.UPGRADE,
        listener: (
            req: Request,
            socket: Duplex,
            head: Buffer,
        ) -> Unit,
    ) /* : this */
}
