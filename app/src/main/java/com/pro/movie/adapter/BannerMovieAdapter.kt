package com.pro.movie.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pro.movie.R
import com.pro.movie.adapter.BannerMovieAdapter.BannerMovieViewHolder
import com.pro.movie.model.Movie
import com.pro.movie.utils.GlideUtils

class BannerMovieAdapter(private val mListMovies: MutableList<Movie>?,
                         private val iClickItemListener: IClickItemListener?) : RecyclerView.Adapter<BannerMovieViewHolder?>() {
    interface IClickItemListener {
        fun onClickItem(movie: Movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerMovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_banner_movie, parent, false)
        return BannerMovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: BannerMovieViewHolder, position: Int) {
        val movie = mListMovies?.get(position) ?: return
        GlideUtils.loadUrlBanner(movie.getBanner(), holder.imgBanner!!)
        holder.tvTitle?.text = movie.getTitle()
        holder.layoutItem?.setOnClickListener { iClickItemListener?.onClickItem(movie) }
    }

    override fun getItemCount(): Int {
        return mListMovies?.size ?: 0
    }

    class BannerMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgBanner: ImageView? = itemView.findViewById(R.id.img_banner)
        val tvTitle: TextView? = itemView.findViewById(R.id.tv_title)
        val layoutItem: RelativeLayout? = itemView.findViewById(R.id.layout_item)
    }
}