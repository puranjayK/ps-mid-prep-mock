package com.example.mid_prep_ps.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.mid_prep_ps.MainActivity
import com.example.mid_prep_ps.databinding.FragmentRecipeBinding
import com.example.mid_prep_ps.models.Meal
import com.example.mid_prep_ps.util.Resource
import com.example.mid_prep_ps.viewmodels.RecipesViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


class RecipeFragment : Fragment() {


    private lateinit var binding: FragmentRecipeBinding
    private lateinit var viewModel: RecipesViewModel
//    private val args: RecipeFra by navArgs()
    private lateinit var youTubePlayerView: YouTubePlayerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRecipeBinding.inflate(inflater,container,false)
        viewModel=(activity as MainActivity).recipeViewModel
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        val id = arguments?.getString("RecipeID")
        viewModel.getRecipeById(id!!)

        viewModel.recipes.observe(viewLifecycleOwner, Observer { response->
            when(response){
                is Resource.Success->{
                    binding.progressBar.visibility = View.INVISIBLE
                    response.data?.let{
                        val meal= it.meals?.get(0)
                        setRecipe(meal)
                    }
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    response.message?.let {
                        Toast.makeText(requireContext(),"An error occurred", Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        })

        return binding.root
    }

    private fun setRecipe(meal: Meal?) {
        Glide.with(binding.root)
            .load(meal?.strMealThumb)
            .into(binding.recipeImage)

        binding.recipeText.text = meal?.strMeal

        binding.recipeIngredient.text = meal?.let { viewModel.getIngredients(it) }

        binding.recipeInstruction.text = meal?.strInstructions

        youTubePlayerView=binding.youtubePlayerView
        lifecycle.addObserver(youTubePlayerView)
        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = meal?.let { viewModel.getVideoId(it) }
                youTubePlayer.pause()
                if (videoId != null) {
                    youTubePlayer.cueVideo(videoId, 0f)
                }
            }
        })

    }

    override fun onPause() {
        super.onPause()
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
    }


}