package com.example.mid_prep_ps.pages

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mid_prep_ps.BmiData
import com.example.mid_prep_ps.MainActivity
import com.example.mid_prep_ps.databinding.FragmentBmiBinding
import com.example.mid_prep_ps.viewmodels.BmiViewModel
import java.time.Instant


class BMIFragment : DialogFragment() {


    private lateinit var binding: FragmentBmiBinding
//    private val viewModel: BmiViewModel by activityViewModels()
    private lateinit var viewModel: BmiViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBmiBinding.inflate(inflater,container,false)
        viewModel=(activity as MainActivity).bmiViewModel
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         binding.btnCalculate.setOnClickListener {
             if(binding.txtHeight.text == null || binding.txtHeight.text!!.isEmpty()){
                 Log.i("height empty","")
                 binding.txtLayoutHeight.error="Please enter valid height"
             }
             if(binding.txtWeight.text == null || binding.txtWeight.text!!.isEmpty())
             {
                 binding.txtLayoutWeight.error="Please enter valid weight"
                 binding.txtLayoutWeight.boxStrokeColor=Color.RED

             }
             else{
                 val height: Float = (binding.txtHeight.text.toString().toFloat())
                 val weight: Float = (binding.txtWeight.text.toString().toFloat())
                 val bmi = weight/(height*height)
//                 val date = convertToDate(Instant.now().epochSecond)
//                 viewModel.bmiDateMap.observe(viewLifecycleOwner, androidx.lifecycle.Observer { set->
//
//                 })
                 viewModel.addBmi(BmiData(Instant.now().epochSecond.toFloat(),bmi))
//                 viewModel.addBmiDatePair(date = Instant.now().epochSecond.toFloat(), bmi = bmi)
                 println("puranjay bmi $bmi")
                 findNavController().previousBackStackEntry?.savedStateHandle?.set("bmi",bmi)
                 findNavController().popBackStack()
             }

         }
        binding.btnCancel.setOnClickListener {
            findNavController().popBackStack()
//            dismiss()
        }
    }

}