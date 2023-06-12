package com.example.eggcareapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eggcareapp.R
import com.example.eggcareapp.model.InfoNewsModel

class InfoNewsAdapter(private val newsList: ArrayList<InfoNewsModel>) :
    RecyclerView.Adapter<InfoNewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_news_horz, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: InfoNewsAdapter.ViewHolder, position: Int) {
        val currentItem = newsList[position]
        holder.title.text = currentItem.title101News
        holder.newsCover.setImageResource(currentItem.img101News)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvTitle)
        val newsCover: ImageView = itemView.findViewById(R.id.newsCover)

    }
}