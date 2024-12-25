plugins {
    alias(kfc.plugins.library)
    alias(libs.plugins.seskar)
    `popper-declarations`
}

dependencies {
    jsMainImplementation(npmv("@popperjs/core"))

    jsMainImplementation(kotlinWrappers.browser)
}

val syncWithWrappers by tasks.registering(SyncWrappers::class) {
    from(generatedDir)
    into(kotlinWrappersDir("kotlin-popper"))
}
