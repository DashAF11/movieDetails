package com.example.moviedetails.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.moviedetails.data.pojo.DataState
import com.example.moviedetails.databinding.FragmentMovieDetailBinding
import com.example.moviedetails.viewModel.MoviesViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {
    private var binding: FragmentMovieDetailBinding? = null
    private val moviesViewModel: MoviesViewModel by viewModels()
    private var movieId: Int = 453395

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDataCollection()
    }

    override fun onStart() {
        super.onStart()
        moviesViewModel.getMovieDetails(movieId)
    }

    private fun setDataCollection() {
        lifecycleScope.launch {
            moviesViewModel.getMovieDetails.flowWithLifecycle(lifecycle).collect {
                when (it) {
                    is DataState.Error -> {
                        Timber.e(it.errorMessage)
                    }
                    DataState.Loading -> {
                        Timber.e(it.toString())
                    }
                    is DataState.Success -> {
                        Timber.d("getMovieDetails", Gson().toJson(it))
                    }
                }
            }
        }
    }
}