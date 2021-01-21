package com.example.week3

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.static_rv_item.view.*

class StaticRvAdapter(private val items: ArrayList<StaticRvModel>): RecyclerView.Adapter<StaticRvAdapter.StaticRVViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaticRVViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.static_rv_item, parent, false)
        return StaticRVViewHolder(view)
    }

    override fun onBindViewHolder(holder: StaticRVViewHolder, position: Int) {
        var item = items.get(position)
        holder.setData(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class StaticRVViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun setData(item: StaticRvModel){
            itemView.rv1_image.setImageResource(item.image)
            itemView.rv1_text.text = item.text
        }
    }
}




