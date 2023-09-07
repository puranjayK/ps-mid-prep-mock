package com.example.mid_prep_ps.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mid_prep_ps.databinding.RecipeItemBinding
import com.example.mid_prep_ps.models.Meal


class FilterAdapter: RecyclerView.Adapter<FilterAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding:RecipeItemBinding):RecyclerView.ViewHolder(binding.root){
        fun setFilter(meal: Meal) {
            Glide.with(binding.root)
                .load(meal.strMealThumb)
                .into(binding.rvImage)
            binding.rvTitle.text=meal.strMeal
            binding.itemSource.setOnClickListener {
                onItemClickListener?.let{it(meal)}
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterAdapter.ViewHolder {
        val binding = RecipeItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilterAdapter.ViewHolder, position: Int) {
        holder.setFilter(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    private val differCallback= object : DiffUtil.ItemCallback<Meal>(){
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal==newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem==newItem
        }
    }
    private var onItemClickListener: ((Meal) -> Unit)? = null

    fun setOnItemClickListener(listener: (Meal) -> Unit) {
        onItemClickListener = listener
    }

    val differ = AsyncListDiffer(this,differCallback)
}