package com.pro.movie.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.pro.movie.MyApplication
import com.pro.movie.R
import com.pro.movie.adapter.MovieAdapter
import com.pro.movie.model.Movie
import com.pro.movie.utils.GlobalFunction
import java.util.*

class HistoryFragment : Fragment() {

    private var mView: View? = null
    private var rcvHistory: RecyclerView? = null
    private var listMovies: MutableList<Movie>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_history, container, false)
        initUi()
        getListHistories()
        return mView
    }

    private fun initUi() {
        rcvHistory = mView?.findViewById(R.id.rcv_history)
        val gridLayoutManager = GridLayoutManager(activity, 3)
        rcvHistory?.layoutManager = gridLayoutManager
    }

    private fun getListHistories() {
        if (activity == null) {
            return
        }
        MyApplication[activity].getMovieDatabaseReference()
                ?.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (listMovies != null) {
                            listMovies!!.clear()
                        } else {
                            listMovies = ArrayList()
                        }
                        for (dataSnapshot in snapshot.children) {
                            val movie: Movie? = dataSnapshot.getValue(Movie::class.java)
                            if (movie != null && GlobalFunction.isHistory(movie)) {
                                listMovies?.add(0, movie)
                            }
                        }
                        displayListHistories()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(activity, getString(R.string.msg_get_date_error), Toast.LENGTH_SHORT).show()
                    }
                })
    }

    private fun displayListHistories() {
        val movieAdapter = MovieAdapter(listMovies, object : MovieAdapter.IClickItemListener {
            override fun onClickItem(movie: Movie) {
                GlobalFunction.onClickItemMovie(activity, movie)
            }

            override fun onClickFavorite(movie: Movie, favorite: Boolean) {
                GlobalFunction.onClickFavoriteMovie(activity, movie, favorite)
            }
        })
        rcvHistory?.adapter = movieAdapter
    }
}