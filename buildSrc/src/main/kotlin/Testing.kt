object Testing {
    private const val androidTestCoreVersion = "1.5.0"
    const val androidTestCore = "androidx.test:core:$androidTestCoreVersion"

    private const val archCoreVersion = "2.2.0"
    const val archCoreTesting = "androidx.arch.core:core-testing:$archCoreVersion"

    private const val testRunnerVersion = "1.5.2"
    const val testRunner = "androidx.test:runner:$testRunnerVersion"

    const val composeUiTestJunit4 = "androidx.compose.ui:ui-test-junit4:${Compose.composeVersion}"

    const val uiTestManifest = "androidx.compose.ui:ui-test-manifest:${Compose.composeVersion}"

    private const val espressoVersion = "3.5.1"
    const val espresso = "androidx.test.espresso:espresso-core:$espressoVersion"

    private const val junitAndroidExtVersion = "1.1.5"
    const val junitAndroidExt = "androidx.test.ext:junit:$junitAndroidExtVersion"

    private const val junitVersion = "4.13.2"
    const val junit4 = "junit:junit:$junitVersion"

    const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Kotlin.coroutineVersion}"

    private const val truthVersion = "1.4.2"
    const val truth = "com.google.truth:truth:$truthVersion"

    private const val mockkVersion = "1.13.10"
    const val mockk = "io.mockk:mockk:$mockkVersion"
    const val mockkAndroid = "io.mockk:mockk-android:$mockkVersion"
    const val mockkAgent = "io.mockk:mockk-agent:$mockkVersion"

    private const val turbineVersion = "1.1.0"
    const val turbine = "app.cash.turbine:turbine:$turbineVersion"

    const val hiltTesting = "com.google.dagger:hilt-android-testing:${DaggerHilt.version}"
}