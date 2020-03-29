package com.kneelawk.modfinder

import tornadofx.App
import tornadofx.View
import tornadofx.launch
import tornadofx.vbox

/**
 * Entry point.
 */
fun main(args: Array<String>) {
    launch<ModFinderApp>(args)
}

/**
 * Application main class.
 */
class ModFinderApp : App(ModFinderView::class)

/**
 * Application main view.
 */
class ModFinderView : View("Hello World!") {
    override val root = vbox {}
}
