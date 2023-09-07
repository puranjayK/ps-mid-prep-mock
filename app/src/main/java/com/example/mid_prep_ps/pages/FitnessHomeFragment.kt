package com.example.mid_prep_ps

import CustomMarker
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mid_prep_ps.databinding.FragmentFitnessHomeBinding
import com.example.mid_prep_ps.viewmodels.BmiViewModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.EntryXComparator
import java.text.DateFormat
import java.time.Instant
import java.util.*


class FitnessHomeFragment : Fragment() {
    private lateinit var binding: FragmentFitnessHomeBinding
    private lateinit var viewModel: BmiViewModel
    private var isExpanded = false

    private val fromBottomFabAnim: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.from_bottom_fab)
    }
    private val toBottomFabAnim: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.to_bottom_fab)
    }
    private val rotateClockWiseFabAnim: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_clockwise)
    }
    private val rotateAntiClockWiseFabAnim: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_anti_clockwise)
    }
    private val fromBottomBgAnim: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.from_bottom_animation)
    }
    private val toBottomBgAnim: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.to_bottom_animation)
    }
    private val entries = ArrayList<Entry>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        viewModel = ViewModelProvider(viewLifecycleOwner,ViewModelProvider.AndroidViewModelFactory.getInstance())
        viewModel=(activity as MainActivity).bmiViewModel
        binding = FragmentFitnessHomeBinding.inflate(inflater, container, false)
        binding.mainFabBtn.setOnClickListener {
            if (isExpanded) {
                closeFAB()
            } else {
                expandFAB()
            }
        }
        viewModel.allBmi.observe(viewLifecycleOwner, androidx.lifecycle.Observer { list->
            list?.let {
                for(i in it){
                    entries.add(Entry(i.time,i.bmi))
                }
            }
        })
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnCalculateBmi.setOnClickListener {
            val currentFrag = findNavController().getBackStackEntry(R.id.fitnessHomeFragment)
            findNavController().navigate(R.id.action_fitnessHomeFragment_to_BMIFragment)
            val dialogObserver = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_RESUME && currentFrag.savedStateHandle.contains("bmi")) {
//                    Log.i("puranjay", currentFrag.savedStateHandle.get<String>("bmi").toString())
                    val bmi = currentFrag.savedStateHandle.get<String>("bmi").toString()
//                    entries.add(Entry(Instant.now().epochSecond.toFloat(), bmi.toFloat()))
                }
            }
            val dialogLifecycle = currentFrag.lifecycle
            dialogLifecycle.addObserver(dialogObserver)
            viewLifecycleOwner.lifecycle.addObserver(LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_DESTROY) {
                    lifecycle.removeObserver(dialogObserver)
                }
            })
        }
//        val data = viewModel.getBmiDatePair()
//        if(data!=null){
//            for(i in data){
//                entries.add(Entry(i.first,i.second))
//                Log.i("date ${i.first} ","bmi ${i.second}")
//            }
//        }

//        Log.i("Puranjay check listsize", viewModel.getBmiDatePair().size.toString())
        showGraph()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showGraph() {
        Collections.sort(entries, EntryXComparator())
        //Part1
//        val entries = ArrayList<Entry>()
        println("puranjay " + Instant.now().epochSecond)
//Part2

//Part3
        val vl = LineDataSet(entries, "BMI")

//Part4
        vl.setDrawValues(false)
        vl.setDrawFilled(true)
        vl.lineWidth = 3f
        vl.fillColor = R.color.gray
        vl.fillAlpha = R.color.bCard
        val lineChart = binding.bmiChart
//Part5
        lineChart.xAxis.labelRotationAngle = 0f
        lineChart.xAxis.valueFormatter = LineChartXAxisValueFormatter()
//Part6
        lineChart.data = LineData(vl)

//Part7
        lineChart.axisRight.isEnabled = true
        lineChart.xAxis.axisMaximum = 0.1f

//Part8
        lineChart.setTouchEnabled(true)
        lineChart.setPinchZoom(true)

//Part9
        lineChart.description.text = "Days"
        lineChart.setNoDataText("Error in loading data!")

//Part10
        lineChart.animateX(300, Easing.EaseInCirc)

//Part11
        val markerView = CustomMarker(requireContext(), R.layout.graph_marker)

        lineChart.marker = markerView
    }

    private fun closeFAB() {

//        binding.transparentBg.startAnimation(toBottomBgAnim)
        binding.mainFabBtn.startAnimation(rotateAntiClockWiseFabAnim)
        binding.galleryFabBtn.startAnimation(toBottomFabAnim)
        binding.shareFabBtn.startAnimation(toBottomFabAnim)
        binding.sendFabBtn.startAnimation(toBottomFabAnim)
        binding.galleryTv.startAnimation(toBottomFabAnim)
        binding.shareTv.startAnimation(toBottomFabAnim)
        binding.sendTv.startAnimation(toBottomFabAnim)



        isExpanded = !isExpanded
    }

    private fun expandFAB() {

//        binding.transparentBg.startAnimation(fromBottomBgAnim)
        binding.mainFabBtn.startAnimation(rotateClockWiseFabAnim)
        binding.galleryFabBtn.startAnimation(fromBottomFabAnim)
        binding.shareFabBtn.startAnimation(fromBottomFabAnim)
        binding.sendFabBtn.startAnimation(fromBottomFabAnim)
        binding.galleryTv.startAnimation(fromBottomFabAnim)
        binding.shareTv.startAnimation(fromBottomFabAnim)
        binding.sendTv.startAnimation(fromBottomFabAnim)
        isExpanded = !isExpanded
    }

}

class LineChartXAxisValueFormatter : IndexAxisValueFormatter() {

    override fun getFormattedValue(value: Float): String? {
        // Convert float value to date string
        // Convert from seconds back to milliseconds to format time  to show to the user
        val emissionsMilliSince1970Time = value.toLong() * 1000

        // Show time in local version
        val timeMilliseconds = Date(emissionsMilliSince1970Time)
        val dateTimeFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault())
        return dateTimeFormat.format(timeMilliseconds)
    }

}