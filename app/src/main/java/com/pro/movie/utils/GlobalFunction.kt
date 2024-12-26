package com.pro.movie.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.pro.movie.MyApplication
import com.pro.movie.activity.PlayMovieActivity
import com.pro.movie.model.Movie
import com.pro.movie.model.UserInfor
import com.pro.movie.prefs.DataStoreManager
import java.util.*

object GlobalFunction {
    fun startActivity(context: Context?, clz: Class<*>?) {
        val intent = Intent(context, clz)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        context?.startActivity(intent)
    }

    private fun startActivity(context: Context?, clz: Class<*>?, bundle: Bundle) {
        val intent = Intent(context, clz)
        intent.putExtras(bundle)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        context?.startActivity(intent)
    }

    fun onClickItemMovie(context: Context?, movie: Movie) {
        if (context == null) {
            return
        }
        val bundle = Bundle()
        bundle.putInt("movie_id", movie.getId())
        startActivity(context, PlayMovieActivity::class.java, bundle)
    }

    fun isFavorite(movie: Movie?): Boolean {
        if (movie?.getFavorite() == null || movie.getFavorite()!!.isEmpty()) {
            return false
        }
        val listFavorite: MutableList<UserInfor?> = ArrayList(movie.getFavorite()!!.values)
        if (listFavorite.isEmpty()) {
            return false
        }
        for (userInfo in listFavorite) {
            if (DataStoreManager.getUser().getEmail() == userInfo?.getEmailUser()) {
                return true
            }
        }
        return false
    }

    fun isHistory(movie: Movie?): Boolean {
        if (movie?.getHistory() == null || movie.getHistory()!!.isEmpty()) {
            return false
        }
        val listHistory: MutableList<UserInfor?> = ArrayList(movie.getHistory()!!.values)
        if (listHistory.isEmpty()) {
            return false
        }
        for (userInfo in listHistory) {
            if (DataStoreManager.getUser().getEmail() == userInfo?.getEmailUser()) {
                return true
            }
        }
        return false
    }

    private fun getUserInfo(movie: Movie?): UserInfor? {
        var userInfo: UserInfor? = null
        if (movie?.getFavorite() == null || movie.getFavorite()!!.isEmpty()) {
            return null
        }
        val listFavorite: MutableList<UserInfor?> = ArrayList(movie.getFavorite()!!.values)
        if (listFavorite.isEmpty()) {
            return null
        }
        for (userInfoObject in listFavorite) {
            if (DataStoreManager.getUser().getEmail() == userInfoObject?.getEmailUser()) {
                userInfo = userInfoObject
                break
            }
        }
        return userInfo
    }

    fun onClickFavoriteMovie(context: Context?, movie: Movie?, isFavorite: Boolean) {
        if (context == null) {
            return
        }
        if (isFavorite) {
            val userEmail: String? = DataStoreManager.getUser().getEmail()
            val userInfo = UserInfor(System.currentTimeMillis(), userEmail)
            MyApplication[context].getMovieDatabaseReference()
                    ?.child(movie?.getId().toString())
                    ?.child("favorite")
                    ?.child(userInfo.getId().toString())
                    ?.setValue(userInfo)
            return
        }
        val userInfoFavorite = getUserInfo(movie)
        if (userInfoFavorite != null) {
            MyApplication[context].getMovieDatabaseReference()
                    ?.child(movie?.getId().toString())
                    ?.child("favorite")
                    ?.child(userInfoFavorite.getId().toString())
                    ?.removeValue()
        }
    }
}