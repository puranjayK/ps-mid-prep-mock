package com.example.mid_prep_ps.pages


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import androidx.fragment.app.Fragment
import com.example.mid_prep_ps.viewmodels.RecipesViewModel
import com.example.mid_prep_ps.databinding.FragmentFoodHomeBinding
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mid_prep_ps.MainActivity
import com.example.mid_prep_ps.R
import com.example.mid_prep_ps.adapter.FilterAdapter
import com.example.mid_prep_ps.util.Resource
import kotlinx.coroutines.*


class FoodHomeFragment : Fragment() {

    private lateinit var binding: FragmentFoodHomeBinding
    private lateinit var viewModel: RecipesViewModel
    private lateinit var filterAdapter: FilterAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFoodHomeBinding.inflate(inflater,container,false)
        binding.imgFood.visibility=View.VISIBLE
        binding.rvRecipe.visibility=View.INVISIBLE
        setUpRecyclerView()
        filterAdapter.
        setOnItemClickListener {
            val bundle=Bundle().apply {
                putString("RecipeID", it.idMeal)
            }
            findNavController().navigate(
                R.id.action_foodHomeFragment_to_recipeFragment,
                bundle
            )
        }
        viewModel=(activity as MainActivity).recipeViewModel

        var job: Job?=null
        filterAdapter.differ.submitList(listOf())

        binding.editTxtSearch.addTextChangedListener {
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                it?.let{
                    if(it.toString().isNotEmpty()){
                        binding.imgFood.visibility=View.INVISIBLE

                        viewModel.search(it.toString())
                    }
//                    if(it.toString().isEmpty()){
//                        filterAdapter.differ.submitList(listOf())
//                        binding.imgFood.visibility=View.VISIBLE
//
//                    }
                }
            }
        }
        viewModel.filters.observe(viewLifecycleOwner) { it ->
            when (it) {
                is Resource.Success -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.imgFood.visibility = View.INVISIBLE
                    binding.rvRecipe.visibility= View.VISIBLE
                    it.data?.let {
                        filterAdapter.differ.submitList(it.meals?.toList())
                    }
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    it.message?.let {
                        Toast.makeText(requireContext(), "An error occurred", Toast.LENGTH_LONG)
                            .show()
                    }
                }
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }

        return binding.root

    }

    private fun setUpRecyclerView() {
        filterAdapter = FilterAdapter()
        binding.rvRecipe.apply {
            adapter = filterAdapter
            layoutManager= LinearLayoutManager(requireContext())
        }
    }


    }


