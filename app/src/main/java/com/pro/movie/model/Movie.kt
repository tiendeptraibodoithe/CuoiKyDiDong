package com.pro.movie.model

import java.io.Serializable
import java.util.*

class Movie : Serializable {

    private var id = 0
    private var title: String? = null
    private var image: String? = null
    private var url: String? = null
    private var featured = false
    private var banner: String? = null
    private var favorite: HashMap<String?, UserInfor?>? = null
    private var history: HashMap<String?, UserInfor?>? = null
    private var categories: List<String>? = null

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getTitle(): String? {
        return title
    }

    fun setTitle(title: String?) {
        this.title = title
    }

    fun getImage(): String? {
        return image
    }

    fun setImage(image: String?) {
        this.image = image
    }

    fun getUrl(): String? {
        return url
    }

    fun setUrl(url: String?) {
        this.url = url
    }

    fun getFavorite(): HashMap<String?, UserInfor?>? {
        return favorite
    }

    fun setFavorite(favorite: HashMap<String?, UserInfor?>?) {
        this.favorite = favorite
    }

    fun getHistory(): HashMap<String?, UserInfor?>? {
        return history
    }

    fun setHistory(history: HashMap<String?, UserInfor?>?) {
        this.history = history
    }

    fun isFeatured(): Boolean {
        return featured
    }

    fun setFeatured(featured: Boolean) {
        this.featured = featured
    }

    fun getBanner(): String? {
        return banner
    }

    fun setBanner(banner: String?) {
        this.banner = banner
    }

    fun getCategories(): List<String>? {
        return categories
    }

    fun setCategories(categories: List<String>?) {
        this.categories = categories
    }
}