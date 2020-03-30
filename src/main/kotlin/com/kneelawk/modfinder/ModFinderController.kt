package com.kneelawk.modfinder

import com.kneelawk.modfinder.curse.Addon
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.Controller
import tornadofx.Rest
import tornadofx.queryString
import tornadofx.toModel

/**
 * Controller that handles calling Twitch's addon search system.
 */
class ModFinderController : Controller() {

    /**
     * The list of values resulting from a successful search operation.
     */
    val values: ObservableList<Addon> = FXCollections.observableArrayList()

    /**
     * This controller's rest client.
     */
    private val api: Rest by inject()

    init {
        api.baseURI = "https://addons-ecs.forgesvc.net/api/v2"
    }

    /**
     * Calls Twitch's addon search system with the given name filter and stores the resulting addons in values.
     * @param name the name used as the search filter for addons.
     */
    fun searchForMod(name: String) {
        runAsync {
            val params = mapOf(
                "categoryId" to 0,
                "gameId" to 432,
                "gameVersion" to "1.15.2",
                "index" to 0,
                "pageSize" to 255,
                "searchFilter" to name,
                "sectionId" to 0,
                "sort" to 0
            )
            api.get("addon/search${params.queryString}").list().toModel<Addon>()
        } ui {
            println("Result received")
            values.setAll(it)
        }
    }
}