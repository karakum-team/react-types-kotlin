plugins {
    kfc("library")
    kfc("wrappers")
    seskar()
    `browser-declarations`
}

dependencies {
    implementation(npmv("typescript"))
    implementation(npmv("@webref/idl"))

    implementation(wrappers("js"))
    implementation(wrappers("web"))
}
