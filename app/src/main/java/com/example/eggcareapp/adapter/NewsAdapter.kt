package com.example.eggcareapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eggcareapp.R
import com.example.eggcareapp.model.NewsModel

class NewsAdapter(private val newsList: ArrayList<NewsModel>) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = newsList[position]
        holder.titleNews.text = currentItem.titleNews
        holder.imgNews.setImageResource(currentItem.imgNews)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleNews: TextView = itemView.findViewById(R.id.tvTitleNews)
        val imgNews: ImageView = itemView.findViewById(R.id.imgNews)

    }
}