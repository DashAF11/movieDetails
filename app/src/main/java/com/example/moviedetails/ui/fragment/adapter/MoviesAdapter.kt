package com.example.moviedetails.ui.fragment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviedetails.data.pojo.MovieData
import com.example.moviedetails.databinding.ItemListTopMoviesLayoutBinding
import timber.log.Timber

class MoviesAdapter(context: Context, private val moviesList: List<MovieData>) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    private var mContext: Context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemListTopMoviesLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val moviesData: MovieData = moviesList[position]
        holder.bind(moviesData, mContext)
    }

    override fun getItemCount(): Int = moviesList.size

    class ViewHolder(private val itemBinding: ItemListTopMoviesLayoutBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(moviesData: MovieData, mContext: Context) {

            itemBinding.movie = moviesData

            val url = "https://image.tmdb.org/t/p/w200" + moviesData.backdrop_path
            Timber.i(url)

            Glide.with(mContext)
                .load(url)
                .into(itemBinding.ivMovieBG)
        }
    }
}