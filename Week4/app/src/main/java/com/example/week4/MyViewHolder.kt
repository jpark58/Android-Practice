package com.example.week4

import android.view.View
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_recycler_item.view.*

class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    val textView = itemView.ranking_text

    fun bind(model: MyModel){
        textView.text = model.score
    }
}