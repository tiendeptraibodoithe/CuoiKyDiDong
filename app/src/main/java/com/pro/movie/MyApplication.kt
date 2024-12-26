package com.pro.movie

import android.app.Application
import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.pro.movie.prefs.DataStoreManager

class MyApplication : Application() {

    private var mFirebaseDatabase: FirebaseDatabase? = null

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        mFirebaseDatabase = FirebaseDatabase.getInstance(FIREBASE_URL)
        DataStoreManager.init(applicationContext)
    }

    fun getMovieDatabaseReference(): DatabaseReference? {
        return mFirebaseDatabase?.getReference("movie")
    }

    fun getMovieDetailDatabaseReference(id: Int): DatabaseReference? {
        return mFirebaseDatabase?.getReference("movie/$id")
    }

    companion object {
        operator fun get(context: Context?): MyApplication {
            return context?.applicationContext as MyApplication
        }
        private const val FIREBASE_URL = "https://movieapp-ec827-default-rtdb.firebaseio.com"
    }
}