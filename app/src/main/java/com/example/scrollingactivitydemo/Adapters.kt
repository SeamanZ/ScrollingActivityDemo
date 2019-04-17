package com.example.scrollingactivitydemo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Seaman on 2019/4/2.
 * Bangggood Ltd
 */
class CardsAdapter : RecyclerView.Adapter<ListViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ListViewHolder(inflater.inflate(R.layout.item_card, parent, false))
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 120
    }
}

class IndicesAdapter : RecyclerView.Adapter<ListViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ListViewHolder(inflater.inflate(R.layout.item_linear, parent, false))
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            Log.v("IndicesAdapter", "setOnClickListener position = ${holder.adapterPosition}")
        }
    }

    override fun getItemCount(): Int {
        return 15
    }
}

class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
