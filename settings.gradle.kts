rootProject.name = "types-kotlin"

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }

    versionCatalogs {
        create("libs") {
            val kotlinVersion = extra["kotlin.version"] as String
            plugin("kotlin-multiplatform", "org.jetbrains.kotlin.multiplatform").version(kotlinVersion)
            plugin("kotlin-js-plain-objects", "org.jetbrains.kotlin.plugin.js-plain-objects").version(kotlinVersion)

            val kfcVersion = extra["kfc.version"] as String
            plugin("kfc-library", "io.github.turansky.kfc.library").version(kfcVersion)

            val seskarVersion = extra["seskar.version"] as String
            plugin("seskar", "io.github.turansky.seskar").version(seskarVersion)

            val wrappersVersion = extra["kotlin-wrappers.version"] as String
            from("org.jetbrains.kotlin-wrappers:kotlin-wrappers-catalog:$wrappersVersion")

            val coroutinesVersion = extra["kotlinx-coroutines.version"] as String
            library("coroutines-core", "org.jetbrains.kotlinx", "kotlinx-coroutines-core").version(coroutinesVersion)
        }
    }
}

include("actions-kotlin")
include("browser-kotlin")
include("cesium-kotlin")
include("csstype-kotlin")
include("popper-kotlin")
include("react-kotlin")
include("react-query-kotlin")
include("react-table-kotlin")
include("react-virtual-kotlin")
