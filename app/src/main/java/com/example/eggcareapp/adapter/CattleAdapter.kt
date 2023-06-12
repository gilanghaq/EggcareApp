package com.example.eggcareapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eggcareapp.model.CattleModel
import com.example.eggcareapp.R

class CattleAdapter(private val cattleList: ArrayList<CattleModel>) :
    RecyclerView.Adapter<CattleAdapter.ViewHolder>() {

    // Add click listener on item
    private lateinit var mListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentCattle = cattleList[position]
        holder.tvName.text = currentCattle.fillName
        val tvAmount = "${currentCattle.fillAmount} Chicken"
        holder.tvAmount.text = tvAmount
        val tvAge = "${currentCattle.fillAge} Week"
        holder.tvAge.text = tvAge
        holder.tvType.text = currentCattle.cageType
        val tvFeedTime = "Every ${currentCattle.feedTime}"
        holder.feedtime.text = tvFeedTime
        val tvCleanTime = "Every ${currentCattle.cleanTime}"
        holder.cleantime.text = tvCleanTime

        // Load image using Glide
        Glide.with(holder.itemView)
            .load(currentCattle.imageUrl)
            .into(holder.img)
    }

    override fun getItemCount(): Int {
        return cattleList.size
    }

    inner class ViewHolder(itemView: View, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvAmount: TextView = itemView.findViewById(R.id.tvAmount)
        val tvAge: TextView = itemView.findViewById(R.id.tvAge)
        val img: ImageView = itemView.findViewById(R.id.img)
        val tvType: TextView = itemView.findViewById(R.id.tvType)
        val feedtime: TextView = itemView.findViewById(R.id.feedTime)
        val cleantime: TextView = itemView.findViewById(R.id.cleanTime)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position)
                }
            }
        }
    }
}
