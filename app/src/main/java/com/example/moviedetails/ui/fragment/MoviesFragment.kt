package com.example.moviedetails.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
    private val moviesAdapter: MoviesAdapter by lazy { MoviesAdapter() }
    private val moviesPagingAdapter: MoviesPagingAdapter by lazy { MoviesPagingAdapter() }
    private var moviesPagingDataList = arrayListOf<MovieData>()
    private var pageCount = 2
    var manager: LinearLayoutManager? = null
    private var visibleItemCount = 0
    private var totalItemCount = 0
    private var pastVisibleItems = 0

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
        binding?.rvBottomMovies?.isNestedScrollingEnabled = false

        moviesAdapter.listener = { _, item, _ ->
            Toast.makeText(requireContext(), "${item.id}", Toast.LENGTH_LONG).show()
        }

        moviesPagingAdapter.listener = { _, item, _ ->
            Toast.makeText(requireContext(), "${item.id}", Toast.LENGTH_LONG).show()
        }

        binding?.rvBottomMovies?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) { //check for scroll down
                    visibleItemCount = (manager as GridLayoutManager).childCount
                    totalItemCount = (manager as GridLayoutManager).itemCount
                    pastVisibleItems = (manager as GridLayoutManager).findFirstVisibleItemPosition()
                    if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                        moviesViewModel.getMoviesPaging(pageCount++)
                    }
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
                        moviesAdapter.submitList(it.baseResponseData.results)
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
                        moviesPagingAdapter.submitList(moviesPagingDataList)
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