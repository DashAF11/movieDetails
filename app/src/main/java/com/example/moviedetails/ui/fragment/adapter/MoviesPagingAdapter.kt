package com.example.moviedetails.ui.fragment.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.moviedetails.R
import com.example.moviedetails.base.BaseAdapter
import com.example.moviedetails.data.pojo.MovieData
import com.example.moviedetails.databinding.ItemListBottomMoviesLayoutBinding
import com.example.moviedetails.utils.loadImage

class MoviesPagingAdapter : BaseAdapter<MovieData, ItemListBottomMoviesLayoutBinding>(object :
    DiffUtil.ItemCallback<MovieData>() {
    override fun areItemsTheSame(oldItem: MovieData, newItem: MovieData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieData, newItem: MovieData): Boolean {
        return oldItem.poster_path == newItem.poster_path
    }

}, R.layout.item_list_bottom_movies_layout) {
    override fun bind(
        viewBinding: ItemListBottomMoviesLayoutBinding,
        item: MovieData,
        position: Int
    ) {
        viewBinding.movie = item
        item.poster_path.loadImage(viewBinding.ivMovieDisplayPic)
        viewBinding.ivMovieDisplayPic.setOnClickListener {
            listener(it, item, position)
        }
    }
}