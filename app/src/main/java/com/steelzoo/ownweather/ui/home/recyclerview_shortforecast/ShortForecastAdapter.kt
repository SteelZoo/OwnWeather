package com.steelzoo.ownweather.ui.home.recyclerview_shortforecast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.steelzoo.ownweather.databinding.ItemRecyclerviewShortforecastBinding
import com.steelzoo.ownweather.ui.model.ShortForecastItem

class ShortForecastAdapter() :
    ListAdapter<ShortForecastItem, ShortForecastAdapter.ShortForecastViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShortForecastViewHolder {
        val binding = ItemRecyclerviewShortforecastBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ShortForecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShortForecastViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class ShortForecastViewHolder(val binding: ItemRecyclerviewShortforecastBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(shortForecastItem: ShortForecastItem){
            with(binding){
                time = shortForecastItem.time
                skyImageAddress = shortForecastItem.skyState.drawableImage
                temperature = shortForecastItem.temperature
                humidity = shortForecastItem.humidity
                precipitation = shortForecastItem.precipitation
                probability = shortForecastItem.probability
            }
        }

    }

    companion object{
        val diffUtil = object : DiffUtil.ItemCallback<ShortForecastItem>(){
            override fun areItemsTheSame(
                oldItem: ShortForecastItem,
                newItem: ShortForecastItem
            ): Boolean {
                //오작동 우려가 있음
                return oldItem.time == newItem.time
            }

            override fun areContentsTheSame(
                oldItem: ShortForecastItem,
                newItem: ShortForecastItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}