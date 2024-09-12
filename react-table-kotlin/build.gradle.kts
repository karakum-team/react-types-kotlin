plugins {
    alias(libs.plugins.kfc.library)
    alias(libs.plugins.seskar)
    `react-table-declarations`
}

dependencies {
    jsMainImplementation(npmv("@tanstack/react-table"))

    jsMainImplementation(kotlinWrappers.browser)
    jsMainImplementation(kotlinWrappers.reactCore)
}
