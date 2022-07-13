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
import com.example.moviedetails.databinding.MoviesFragmentBinding
import com.example.moviedetails.viewModel.MoviesViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private val moviesViewModel: MoviesViewModel by viewModels()
    private var binding: MoviesFragmentBinding? = null

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
    }

    override fun onStart() {
        super.onStart()
        moviesViewModel.getMovies()
    }

    private fun setDataCollection() {
        lifecycleScope.launch {
            moviesViewModel.getMovieData.flowWithLifecycle(lifecycle).collect {
                when (it) {
                    is DataState.Error -> {
                        Timber.e(it.errorMessage)
                    }
                    DataState.Loading -> {
                        Timber.e(it.toString())
                    }
                    is DataState.Success -> {
                        Timber.e("getMovieData : %s", Gson().toJson(it))
                    }
                }
            }
        }
    }
}