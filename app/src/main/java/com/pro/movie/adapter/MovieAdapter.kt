package com.pro.movie.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pro.movie.R
import com.pro.movie.adapter.MovieAdapter.MovieViewHolder
import com.pro.movie.model.Movie
import com.pro.movie.utils.GlideUtils
import com.pro.movie.utils.GlobalFunction

class MovieAdapter(private val listMovies: MutableList<Movie>?,
                   private val iClickItemListener: IClickItemListener?) : RecyclerView.Adapter<MovieViewHolder?>() {
    interface IClickItemListener {
        fun onClickItem(movie: Movie)
        fun onClickFavorite(movie: Movie, favorite: Boolean)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = listMovies?.get(position) ?: return
        holder.tvTitleMovie?.text = movie.getTitle()
        GlideUtils.loadUrl(movie.getImage(), holder.imgMovie!!)
        val isFavorite = GlobalFunction.isFavorite(movie)
        if (isFavorite) {
            holder.imgFavorite?.setImageResource(R.drawable.icon_favorite_big_on)
        } else {
            holder.imgFavorite?.setImageResource(R.drawable.icon_favorite_big_off)
        }
        holder.layoutItem?.setOnClickListener { iClickItemListener?.onClickItem(movie) }
        holder.imgFavorite?.setOnClickListener { iClickItemListener?.onClickFavorite(movie, !isFavorite) }
    }

    override fun getItemCount(): Int {
        return listMovies?.size ?: 0
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgMovie: ImageView? = itemView.findViewById(R.id.img_movie)
        val imgFavorite: ImageView? = itemView.findViewById(R.id.img_favorite)
        val tvTitleMovie: TextView? = itemView.findViewById(R.id.tv_title_movie)
        val layoutItem: LinearLayout? = itemView.findViewById(R.id.layout_item)

    }
}