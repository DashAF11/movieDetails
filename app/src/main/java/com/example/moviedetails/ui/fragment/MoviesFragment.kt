package com.example.moviedetails.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedetails.data.pojo.DataState
import com.example.moviedetails.data.pojo.MovieData
import com.example.moviedetails.databinding.MoviesFragmentBinding
import com.example.moviedetails.ui.fragment.adapter.MoviesAdapter
import com.example.moviedetails.ui.fragment.adapter.MoviesPagingAdapter
import com.example.moviedetails.viewModel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private val moviesViewModel: MoviesViewModel by viewModels()
    private var binding: MoviesFragmentBinding? = null
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var moviesPagingAdapter: MoviesPagingAdapter
    private var moviesPagingDataList = arrayListOf<MovieData>()
    private var pageCount = 2
    var manager: LinearLayoutManager? = null
    var isScrolling = false
    var currentItems = 0
    var totalItems = 0
    var scrollOutItems = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MoviesFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDataCollection()
        manager = GridLayoutManager(activity, 3)
        (manager as GridLayoutManager).isSmoothScrollbarEnabled = true

        binding?.rvBottomMovies?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                currentItems = (manager as GridLayoutManager).childCount
                totalItems = (manager as GridLayoutManager).itemCount
                scrollOutItems = (manager as GridLayoutManager).findFirstVisibleItemPosition()
                if (isScrolling && currentItems + scrollOutItems === totalItems) {
                    isScrolling = false
                    moviesViewModel.getMoviesPaging(pageCount++)
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
        moviesViewModel.getMovies()
        moviesViewModel.getMoviesPaging(pageCount)
    }

    private fun setDataCollection() {
        lifecycleScope.launch {
            moviesViewModel.getMovieData.flowWithLifecycle(lifecycle).collect {
                when (it) {
                    is DataState.Error -> {
                        Log.e("getMovieData", it.errorMessage)
                    }
                    DataState.Loading -> {
                        binding?.progressBarShow = true
                    }
                    is DataState.Success -> {
                        binding?.progressBarShow = false
                        moviesAdapter =
                            MoviesAdapter(requireActivity(), it.baseResponseData.results)
                        binding?.rvTopMovies?.adapter = moviesAdapter
                    }
                }
            }
        }

        lifecycleScope.launch {
            moviesViewModel.getMoviePagingData.flowWithLifecycle(lifecycle).collect {
                when (it) {
                    is DataState.Error -> {
                        Log.e("getMoviePagingData", it.errorMessage)
                    }
                    DataState.Loading -> {
                        binding?.progressBarShow = true
                    }
                    is DataState.Success -> {
                        binding?.progressBarShow = false
                        val movieData = it.baseResponseData.results

                        binding?.rvBottomMovies?.layoutManager = manager
                        moviesPagingDataList.addAll(movieData)
                        moviesPagingAdapter =
                            MoviesPagingAdapter(requireActivity(), moviesPagingDataList)
                        binding?.rvBottomMovies?.adapter = moviesPagingAdapter
                    }
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        moviesPagingDataList.clear()
        pageCount = 1
    }
}