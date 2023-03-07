package com.example.detectorsismos.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.detectorsismos.Earthquake
import com.example.detectorsismos.R
import com.example.detectorsismos.databinding.ListItemBinding


class EqAdapter: ListAdapter<Earthquake, RecyclerView.ViewHolder>(EqDIffCallback()) {

    private lateinit var context: Context
    lateinit var onItemClickListener: (Earthquake) -> Unit

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding = ListItemBinding.bind(view)
    }

    class EqDIffCallback: DiffUtil.ItemCallback<Earthquake>() {
        override fun areItemsTheSame(oldItem: Earthquake, newItem: Earthquake) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Earthquake, newItem: Earthquake) = oldItem == newItem

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val eqEarth = getItem(position)

        with(holder as ViewHolder){

            binding.tvMagText.text = eqEarth.magnitude.toString()
            binding.tvPlace.text = eqEarth.place
            binding.root.setOnClickListener{
                onItemClickListener(eqEarth)

            }
        }
    }



}