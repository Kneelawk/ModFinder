package com.kneelawk.modfinder

import com.kneelawk.modfinder.curse.Addon
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.Controller
import tornadofx.Rest
import tornadofx.queryString
import tornadofx.toModel

class ModFinderController : Controller() {
    val values: ObservableList<Addon> = FXCollections.observableArrayList()
    private val api: Rest by inject()

    init {
        api.baseURI = "https://addons-ecs.forgesvc.net/api/v2"
    }

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