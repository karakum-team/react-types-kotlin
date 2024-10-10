// Automatically generated - do not modify!

@file:JsModule("@cesium/engine")

package cesium.engine

import js.promise.Promise
import kotlinx.js.JsPlainObject
import seskar.js.JsAsync

/**
 * <div class="notice">
 * To construct a TileMapServiceImageryProvider, call [TileMapServiceImageryProvider.fromUrl]. Do not call the constructor directly.
 * </div>
 *
 * An imagery provider that provides tiled imagery as generated by
 * [MapTiler](http://www.maptiler.org/), [GDAL2Tiles](http://www.klokan.cz/projects/gdal2tiles/), etc.
 * ```
 * const tms = await TileMapServiceImageryProvider.fromUrl(
 *    "../images/cesium_maptiler/Cesium_Logo_Color", {
 *      fileExtension: 'png',
 *      maximumLevel: 4,
 *      rectangle: new Rectangle(
 *        Math.toRadians(-120.0),
 *        Math.toRadians(20.0),
 *        Math.toRadians(-60.0),
 *        Math.toRadians(40.0))
 * });
 * ```
 * @see <a href="https://cesium.com/docs/cesiumjs-ref-doc/TileMapServiceImageryProvider.html">Online Documentation</a>
 */
external class TileMapServiceImageryProvider
private constructor() {
    /**
     * Initialization options for the TileMapServiceImageryProvider constructor
     * @property [fileExtension] The file extension for images on the server.
     *   Default value - `'png'`
     * @property [credit] A credit for the data source, which is displayed on the canvas.
     *   Default value - `''`
     * @property [minimumLevel] The minimum level-of-detail supported by the imagery provider.  Take care when specifying
     *   this that the number of tiles at the minimum level is small, such as four or less.  A larger number is likely
     *   to result in rendering problems.
     *   Default value - `0`
     * @property [maximumLevel] The maximum level-of-detail supported by the imagery provider, or undefined if there is no limit.
     * @property [rectangle] The rectangle, in radians, covered by the image.
     *   Default value - [Rectangle.MAX_VALUE]
     * @property [tilingScheme] The tiling scheme specifying how the ellipsoidal
     *   surface is broken into tiles.  If this parameter is not provided, a [WebMercatorTilingScheme]
     *   is used.
     * @property [ellipsoid] The ellipsoid.  If the tilingScheme is specified,
     *   this parameter is ignored and the tiling scheme's ellipsoid is used instead. If neither
     *   parameter is specified, the WGS84 ellipsoid is used.
     * @property [tileWidth] Pixel width of image tiles.
     *   Default value - `256`
     * @property [tileHeight] Pixel height of image tiles.
     *   Default value - `256`
     * @property [flipXY] Older versions of gdal2tiles.py flipped X and Y values in tilemapresource.xml.
     * @property [tileDiscardPolicy] A policy for discarding tile images according to some criteria
     *   Specifying this option will do the same, allowing for loading of these incorrect tilesets.
     * @see <a href="https://cesium.com/docs/cesiumjs-ref-doc/TileMapServiceImageryProvider.html#.ConstructorOptions">Online Documentation</a>
     */
    @JsPlainObject
    interface ConstructorOptions {
        val fileExtension: String?
        val credit: Credit?
        val minimumLevel: Int?
        val maximumLevel: Int?
        val rectangle: Rectangle?
        val tilingScheme: TilingScheme?
        val ellipsoid: Ellipsoid?
        val tileWidth: Int?
        val tileHeight: Int?
        val flipXY: Boolean?
        val tileDiscardPolicy: TileDiscardPolicy?
    }

    companion object {
        /**
         * Creates a TileMapServiceImageryProvider from the specified url.
         * ```
         * const tms = await TileMapServiceImageryProvider.fromUrl(
         *    '../images/cesium_maptiler/Cesium_Logo_Color', {
         *      fileExtension: 'png',
         *      maximumLevel: 4,
         *      rectangle: new Rectangle(
         *        Math.toRadians(-120.0),
         *        Math.toRadians(20.0),
         *        Math.toRadians(-60.0),
         *        Math.toRadians(40.0))
         * });
         * ```
         * @param [url] Path to image tiles on server.
         * @param [options] Object describing initialization options.
         * @return A promise that resolves to the created TileMapServiceImageryProvider.
         * @see <a href="https://cesium.com/docs/cesiumjs-ref-doc/TileMapServiceImageryProvider.html#.fromUrl">Online Documentation</a>
         */
        @JsAsync
        suspend fun fromUrl(
            url: Resource,
            options: ConstructorOptions? = definedExternally,
        ): TileMapServiceImageryProvider

        @JsName("fromUrl")
        fun fromUrlAsync(
            url: Resource,
            options: ConstructorOptions? = definedExternally,
        ): Promise<TileMapServiceImageryProvider>

        @JsAsync
        suspend fun fromUrl(
            url: String,
            options: ConstructorOptions? = definedExternally,
        ): TileMapServiceImageryProvider

        @JsName("fromUrl")
        fun fromUrlAsync(
            url: String,
            options: ConstructorOptions? = definedExternally,
        ): Promise<TileMapServiceImageryProvider>
    }
}
