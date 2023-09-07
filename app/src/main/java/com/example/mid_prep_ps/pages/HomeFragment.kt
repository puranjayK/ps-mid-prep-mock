package com.example.mid_prep_ps.pages

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mid_prep_ps.MainActivity
import com.example.mid_prep_ps.R
import com.example.mid_prep_ps.databinding.FragmentHomeBinding
import com.example.mid_prep_ps.util.Resource
import com.example.mid_prep_ps.viewmodels.RecipesViewModel
import com.example.mid_prep_ps.viewmodels.WeatherViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: WeatherViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        viewModel = (activity as MainActivity).weatherViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var job : Job? = null
        job?.cancel()
        job = MainScope().launch {
            viewModel.search(52.52f,
            13.41f,
            "2023-08-19",
            "2023-09-02",
            "rain",
            "rain_sum",
            "GMT")

        }
        viewModel.weather.observe(viewLifecycleOwner){
            Log.i("Puranjay","entered view model")
            when(it){

                is Resource.Success->{
                    Log.i("Puranjay","entered success")
                    Log.i("Puranjay", it.data.toString())
                    it.data?.toString()?.let {
                            it1 -> Log.i("Puranjay" , it1)
                    }
                }
                is Resource.Error->{
                    Log.i("Puranjay" , "error occured")
                }
                is Resource.Loading->{
                    Log.i("Puranjay" , "data loading")
                }
            }
        }
    }

}