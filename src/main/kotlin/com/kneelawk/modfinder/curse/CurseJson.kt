package com.kneelawk.modfinder.curse

import tornadofx.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.json.JsonObject

/**
 * A single addon retrieved from curse.
 */
data class Addon(

    /**
     * The project id of this addon.
     */
    var id: Long = 0,

    /**
     * The name of this addon.
     */
    var name: String = "",

    /**
     * A list of authors that worked on this addon.
     */
    var authors: List<Author> = emptyList(),

    /**
     * a list of attachments (often images) associated with this addon.
     */
    var attachments: List<Attachment> = emptyList(),

    /**
     * The url of this addon on Curse's website.
     */
    var websiteURL: String? = null,

    /**
     * The id of the game (Minecraft, WoW, etc.) this addon is associated with (Minecraft is 432).
     */
    var gameID: Long = 432,

    /**
     * A brief summary describing this addon.
     */
    var summary: String? = null,

    /**
     * The file id of the current default file for this addon.
     */
    var defaultFileID: Long? = null,

    /**
     * A list of addon categories (Tech, Magic, Aesthetic, etc.) this addon is associated with.
     */
    var categories: List<Category> = emptyList(),

    /**
     * The category id of the primary category this addon is associated with.
     */
    var primaryCategoryID: Long? = null,

    /**
     * The overall category section (Mod, Modpack, Bukkit Plugin, etc.) this addon is part of.
     */
    var categorySection: CategorySection? = null,

    /**
     * A machine-readable name used to refer to this addon.
     */
    var slug: String? = null,

    /**
     * The last time this addon was modified.
     */
    var dateModified: LocalDateTime = LocalDateTime.now(),

    /**
     * When this addon was first created on the curse servers.
     */
    var dateCreated: LocalDateTime = LocalDateTime.now(),

    /**
     * When this addon was made public.
     */
    var dateReleased: LocalDateTime? = null,

    /**
     * Whether this addon is available.
     */
    var isAvailable: Boolean = true,

    /**
     * Whether this addon is considered 'experimental'.
     */
    var isExperiemental: Boolean = false
) : JsonModel {

    /**
     * Serializes this addon to JSON.
     */
    override fun toJSON(json: JsonBuilder) {
        with(json) {
            add("id", id)
            add("name", name)
            add("authors", authors.toJSON())
            add("attachments", attachments)
            websiteURL?.let { add("websiteUrl", it) }
            add("gameId", gameID)
            summary?.let { add("summary", it) }
            defaultFileID?.let { add("defaultFileId", it) }
            add("categories", categories.toJSON())
            primaryCategoryID?.let { add("primaryCategoryId", it) }
            categorySection?.let { add("categorySection", it) }
            slug?.let { add("slug", it) }
            add("dateModified", dateModified)
            add("dateCreated", dateCreated)
            add("dateReleased", dateReleased)
            add("isAvailable", isAvailable)
            add("isExperiemental", isExperiemental)
        }
    }

    /**
     * Loads this addon from JSON.
     */
    override fun updateModel(json: JsonObject) {
        with(json) {
            id = long("id")!!
            name = string("name")!!
            authors = jsonArray("authors")?.toModel() ?: emptyList()
            attachments = jsonArray("attachments")?.toModel() ?: emptyList()
            websiteURL = string("websiteUrl")
            gameID = long("gameId") ?: 432
            summary = string("summary")
            defaultFileID = long("defaultFileId")
            categories = jsonArray("categories")?.toModel() ?: emptyList()
            primaryCategoryID = long("primaryCategoryId")
            categorySection = jsonObject("categorySection")?.toModel()
            slug = string("slug")
            dateModified = LocalDateTime.parse(string("dateModified")!!, DateTimeFormatter.ISO_DATE_TIME)
            dateCreated = LocalDateTime.parse(string("dateCreated")!!, DateTimeFormatter.ISO_DATE_TIME)
            dateReleased = string("dateRelease")?.let { LocalDateTime.parse(it, DateTimeFormatter.ISO_DATE_TIME) }
            isAvailable = boolean("isAvailable") ?: true
            isExperiemental = boolean("isExperiemental") ?: false
        }
    }
}

/**
 * Describes an attachment associated with an addon.
 */
data class Attachment(

    /**
     * The attachment id of this attachment.
     */
    var id: Long? = null,

    /**
     * The project id of the project this attachment is associated with.
     */
    var projectID: Long? = null,

    /**
     * This attachment's description.
     */
    var description: String = "",

    /**
     * Whether this is the default attachment for its associated project.
     */
    var isDefault: Boolean = false,

    /**
     * The url of the thumbnail for this attachment.
     */
    var thumbnailURL: String? = null,

    /**
     * The title of this attachment.
     */
    var title: String? = null,

    /**
     * The url of this attachment.
     */
    var url: String = ""
) : JsonModel {

    /**
     * Serializes this attachment descriptor to JSON.
     */
    override fun toJSON(json: JsonBuilder) {
        with(json) {
            id?.let { add("id", it) }
            projectID?.let { add("projectId", it) }
            add("description", description)
            add("isDefault", isDefault)
            thumbnailURL?.let { add("thumbnailUrl", it) }
            title?.let { add("title", it) }
            add("url", url)
        }
    }

    /**
     * Loads this attachment descriptor from JSON.
     */
    override fun updateModel(json: JsonObject) {
        with(json) {
            id = long("id")
            projectID = long("projectId")
            description = string("description") ?: ""
            isDefault = boolean("isDefault") ?: false
            thumbnailURL = string("thumbnailUrl")
            title = string("title")
            url = string("url")!!
        }
    }
}

/**
 * Contains identifiers for an addon author.
 */
data class Author(

    /**
     * The username of this author.
     */
    var name: String = "",

    /**
     * The url of this author's user page on the Curse website.
     */
    var url: String? = null,

    /**
     * The Curse user id of this author.
     */
    var userID: Long? = null,

    /**
     * The Twitch user id of this author.
     */
    var twitchID: Long? = null
) : JsonModel {

    /**
     * Serializes this author descriptor to JSON.
     */
    override fun toJSON(json: JsonBuilder) {
        with(json) {
            add("name", name)
            url?.let { add("url", it) }
            userID?.let { add("userId", it) }
            twitchID?.let { add("twitchId", it) }
        }
    }

    /**
     * Loads this author descriptor from JSON.
     */
    override fun updateModel(json: JsonObject) {
        with(json) {
            name = string("name")!!
            url = string("url")
            userID = long("userId")
            twitchID = long("twitchId")
        }
    }
}

/**
 * Describes a category an addon can fit into.
 *
 * Some potential categories are things like: Tech, Magic, Aesthetic, and others.
 */
data class Category(

    /**
     * The category id of this category.
     */
    var categoryID: Long = 0,

    /**
     * The name of this category.
     */
    var name: String = "",

    /**
     * The url of the page on Curse's website for this category.
     */
    var url: String? = null,

    /**
     * The url of the icon for this category.
     */
    var avatarURL: String? = null,

    /**
     * The category id of the parent category of this category.
     */
    var parentID: Long? = null,

    /**
     * The category id of the root category of this category.
     */
    var rootID: Long? = null,

    /**
     * The game id of the game this category is associated with.
     */
    var gameID: Long = 432
) : JsonModel {

    /**
     * Serializes this category descriptor to JSON.
     */
    override fun toJSON(json: JsonBuilder) {
        with(json) {
            add("categoryId", categoryID)
            add("name", name)
            url?.let { add("url", it) }
            avatarURL?.let { add("avatarUrl", it) }
            parentID?.let { add("parentId", it) }
            rootID?.let { add("rootId", it) }
            add("gameId", gameID)
        }
    }

    /**
     * Loads this category descriptor from JSON.
     */
    override fun updateModel(json: JsonObject) {
        with(json) {
            categoryID = long("categoryId")!!
            name = string("name")!!
            url = string("url")
            avatarURL = string("avatarUrl")
            parentID = long("parentId")
            rootID = long("rootId")
            gameID = long("gameId") ?: 432
        }
    }
}

/**
 * Represents an overarching category section.
 *
 * Some examples of category sections are: Mods, Modpacks, Bukkit Plugins, and others.
 */
data class CategorySection(

    /**
     * The category section id of this category section.
     */
    var id: Long = 0,

    /**
     * The game id of the game this category section is associated with.
     */
    var gameID: Long = 432,

    /**
     * The name of this category section.
     */
    var name: String = "",

    /**
     * The packaging type used for this category section.
     */
    var packageType: Long = 0,

    /**
     * The category id of this category section.
     */
    var gameCategoryID: Long = 0
) : JsonModel {

    /**
     * Serializes this catetory section descriptor to JSON.
     */
    override fun toJSON(json: JsonBuilder) {
        with(json) {
            add("id", id)
            add("gameId", gameID)
            add("name", name)
            add("packageType", packageType)
            add("gameCategoryId", gameCategoryID)
        }
    }

    /**
     * Loads this category section descriptor from JSON.
     */
    override fun updateModel(json: JsonObject) {
        with(json) {
            id = long("id")!!
            gameID = long("gameId") ?: 432
            name = string("name")!!
            packageType = long("packageType")!!
            gameCategoryID = long("gameCategoryId")!!
        }
    }
}
