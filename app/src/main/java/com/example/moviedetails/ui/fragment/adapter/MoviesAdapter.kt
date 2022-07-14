package com.example.moviedetails.ui.fragment.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.moviedetails.R
import com.example.moviedetails.base.BaseAdapter
import com.example.moviedetails.data.pojo.MovieData
import com.example.moviedetails.databinding.ItemListTopMoviesLayoutBinding
import com.example.moviedetails.utils.loadImage

class MoviesAdapter : BaseAdapter<MovieData, ItemListTopMoviesLayoutBinding>(object :
    DiffUtil.ItemCallback<MovieData>() {
    override fun areItemsTheSame(oldItem: MovieData, newItem: MovieData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieData, newItem: MovieData): Boolean {
        return oldItem.backdrop_path == newItem.backdrop_path
    }

}, R.layout.item_list_top_movies_layout) {
    override fun bind(viewBinding: ItemListTopMoviesLayoutBinding, item: MovieData, position: Int) {
        viewBinding.movie = item
        item.backdrop_path.loadImage(viewBinding.ivMovieBG)
        viewBinding.ivMovieBG.setOnClickListener {
            listener.invoke(it, item, position)
        }
    }
}