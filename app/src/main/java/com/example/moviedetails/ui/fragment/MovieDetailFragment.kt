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
import com.example.moviedetails.data.pojo.DataState
import com.example.moviedetails.databinding.FragmentMovieDetailBinding
import com.example.moviedetails.utils.loadImage
import com.example.moviedetails.viewModel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {
    private var binding: FragmentMovieDetailBinding? = null
    private val moviesViewModel: MoviesViewModel by viewModels()
    private var movieId: Int = 453395

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = arguments
        bundle?.let {
            val args = MovieDetailFragmentArgs.fromBundle(bundle)
            movieId = args.movieId
        } ?: Timber.e("Incorrect bundle received")
    }

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
            moviesViewModel.getSingleMovieDetails.flowWithLifecycle(lifecycle).collect {
                when (it) {
                    is DataState.Error -> {
                        Toast.makeText(requireContext(), "${it.errorMessage}", Toast.LENGTH_LONG)
                            .show()
                    }
                    DataState.Loading -> {
                        Timber.e(it.toString())
                    }
                    is DataState.Success -> {
                        Log.d("getMovieDetails: ", it.toString())
                        binding?.movie = it.baseResponseData

                        binding?.ivMovieBG?.let { it1 ->
                            it.baseResponseData.backdrop_path.loadImage(
                                it1
                            )
                        }

                        binding?.ivMovieDisplayPic?.let { it1 ->
                            it.baseResponseData.poster_path.loadImage(
                                it1
                            )
                        }

                        val builder = StringBuilder()
                        for (item in it.baseResponseData.genres) {
                            builder.append("${item.name}, ")
                        }
                        if (it.baseResponseData.adult) {
                            binding?.tvAdult?.text = "Adult"
                        } else {
                            binding?.tvAdult?.text = "Non Adult"
                        }

                        binding?.tvMovieGenre?.text = builder.toString()
                    }
                }
            }
        }
    }
}