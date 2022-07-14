package com.example.moviedetails

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.example.moviedetails.databinding.FragmentSearchBinding
import com.example.moviedetails.ui.fragment.adapter.MoviesPagingAdapter
import com.example.moviedetails.viewModel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private val moviesViewModel: MoviesViewModel by viewModels()
    private var binding: FragmentSearchBinding? = null
    private val moviesPagingAdapter: MoviesPagingAdapter by lazy { MoviesPagingAdapter() }
    private var moviesPagingDataList = arrayListOf<MovieData>()
    private var pageCount = 1
    var manager: LinearLayoutManager? = null
    private var visibleItemCount = 0
    private var totalItemCount = 0
    private var pastVisibleItems = 0
    private lateinit var searchedMovie: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDataCollection()
        manager = GridLayoutManager(activity, 3)
        (manager as GridLayoutManager).isSmoothScrollbarEnabled = true
        binding?.rvSearchedMovies?.isNestedScrollingEnabled = false

        moviesPagingAdapter.listener = { _, item, _ ->
            Toast.makeText(requireContext(), "${item.id}", Toast.LENGTH_LONG).show()
        }

        binding?.rvSearchedMovies?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) { //check for scroll down
                    visibleItemCount = (manager as GridLayoutManager).childCount
                    totalItemCount = (manager as GridLayoutManager).itemCount
                    pastVisibleItems = (manager as GridLayoutManager).findFirstVisibleItemPosition()
                    if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                        moviesViewModel.getSearchedMovieData(searchedMovie, pageCount++)
                    }
                }
            }
        })

        binding?.searchEditText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(searched: Editable?) {
                searchedMovie = searched.toString()
                moviesViewModel.getSearchedMovieData(searchedMovie, pageCount)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    override fun onStart() {
        super.onStart()
        moviesViewModel.getMoviesPaging(pageCount)
    }

    private fun setDataCollection() {
        lifecycleScope.launch {
            moviesViewModel.getSearchedMovieData.flowWithLifecycle(lifecycle).collect {
                when (it) {
                    is DataState.Error -> {
                        Log.e("getSearchedMovieData", it.errorMessage)
                    }
                    DataState.Loading -> {
                        binding?.progressBarShow = true
                    }
                    is DataState.Success -> {
                        binding?.progressBarShow = false
                        val movieData = it.baseResponseData.results

                        binding?.rvSearchedMovies?.layoutManager = manager

                        moviesPagingDataList.addAll(movieData)
                        moviesPagingAdapter.submitList(moviesPagingDataList)
                        binding?.rvSearchedMovies?.adapter = moviesPagingAdapter
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