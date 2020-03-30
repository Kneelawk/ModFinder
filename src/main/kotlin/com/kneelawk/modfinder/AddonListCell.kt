package com.kneelawk.modfinder

import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import com.google.common.cache.LoadingCache
import com.kneelawk.modfinder.curse.Addon
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.ListCell
import javafx.scene.image.Image
import javafx.scene.layout.Priority
import tornadofx.*
import java.time.Duration

class AddonListCell : ListCell<Addon>() {
    private val addonImage = SimpleObjectProperty<Image?>()
    private val addonName = SimpleStringProperty()
    private val addonId = SimpleStringProperty()
    private val addonFileId = SimpleStringProperty()

    private val root = hbox {
        padding = insets(5.0)
        spacing = 5.0
        imageview(addonImage)
        vbox {
            spacing = 5.0
            hboxConstraints {
                hGrow = Priority.ALWAYS
            }
            label(addonName)
            gridpane {
                vgap = 5.0
                hgap = 5.0
                row {
                    label("Project Id:")
                    textfield(addonId) {
                        isEditable = false
                        style {
                            fontFamily = "Monospace"
                        }
                    }
                }
                row {
                    label("Latest File Id:")
                    textfield(addonFileId) {
                        isEditable = false
                        style {
                            fontFamily = "Monospace"
                        }
                    }
                }
            }
        }
    }

    override fun updateItem(item: Addon?, empty: Boolean) {
        super.updateItem(item, empty)
        text = null

        if (empty) {
            addonImage.value = null
            addonName.value = ""
            addonId.value = ""
            addonFileId.value = ""

            graphic = null
        } else {
            addonImage.value = (item!!.attachments.find { it.isDefault } ?: item.attachments.firstOrNull())?.url?.let {
                imageCache[it]
            }
            addonName.value = item.name
            addonId.value = item.id.toString()
            addonFileId.value = item.defaultFileID?.toString() ?: "no-default"

            graphic = root
        }
    }

    companion object {
        val imageCache: LoadingCache<String, Image> =
            CacheBuilder.newBuilder().maximumSize(100).expireAfterAccess(Duration.ofMinutes(10))
                .build(CacheLoader.from { url: String? -> Image(url, 100.0, 100.0, true, true, true) })
    }
}