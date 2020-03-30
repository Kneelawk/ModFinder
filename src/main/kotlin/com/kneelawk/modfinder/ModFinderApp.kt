package com.kneelawk.modfinder

import com.kneelawk.modfinder.curse.Addon
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.ListCell
import javafx.scene.control.ListView
import javafx.scene.layout.Priority
import javafx.util.Callback
import tornadofx.*

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
class ModFinderView : View("Mod Finder") {

    /**
     * The mod finder controller that contains the logic for this ui.
     */
    private val controller: ModFinderController by inject()

    /**
     * The property that contains the currently entered mod name filter.
     */
    private val modName = SimpleStringProperty()

    init {
        primaryStage.width = 500.0
        primaryStage.height = 500.0
    }

    /**
     * The root ui element of this view.
     */
    override val root = vbox {
        padding = insets(10.0)
        spacing = 5.0
        hbox {
            spacing = 5.0
            textfield(modName) {
                hboxConstraints {
                    hGrow = Priority.ALWAYS
                }
                promptText = "Mod Name"
            }
            button("Search") {
                action {
                    controller.searchForMod(modName.value)
                }
            }
        }
        listview(controller.values) {
            vboxConstraints {
                vGrow = Priority.ALWAYS
            }
            cellFactory = Callback<ListView<Addon>, ListCell<Addon>> { AddonListCell() }
        }
    }
}
