package com.example.mid_prep_ps

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mid_prep_ps.databinding.ActivityMainBinding
import com.example.mid_prep_ps.pages.FoodHomeFragment
import com.example.mid_prep_ps.pages.HomeFragment
import com.example.mid_prep_ps.repository.RecipesRepository
import com.example.mid_prep_ps.repository.WeatherRepository
import com.example.mid_prep_ps.viewmodels.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var fitnessHomeFragment = FitnessHomeFragment()
    var foodHomeFragment = FoodHomeFragment()
    var homeFragment = HomeFragment()
    lateinit var recipeViewModel:RecipesViewModel
    lateinit var weatherViewModel: WeatherViewModel
    lateinit var bmiViewModel: BmiViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val recipesRepository = RecipesRepository()
        val weatherRepository = WeatherRepository()
        recipeViewModel=ViewModelProvider(this,RecipesViewModelFactory(recipesRepository)).get(RecipesViewModel::class.java)
        weatherViewModel = ViewModelProvider(this,WeatherViewModelFactory(weatherRepository)).get(WeatherViewModel::class.java)
        bmiViewModel = ViewModelProvider(this,BmiViewModelFactory(application)).get(BmiViewModel::class.java)

    }

    override fun onStart() {
        super.onStart()
        val navController = Navigation.findNavController(this, R.id.fragmentContainerView)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment,R.id.fitnessHomeFragment,R.id.foodHomeFragment))
        setupActionBarWithNavController(navController,appBarConfiguration)
        binding.bottomNavigation.setupWithNavController(navController)
    }
}


