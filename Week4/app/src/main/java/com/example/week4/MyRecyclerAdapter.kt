package com.example.week4

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MyRecyclerAdapter: RecyclerView.Adapter<MyViewHolder>() {

    private var modelList = ArrayList<MyModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_recycler_item, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(this.modelList[position])
    }

    override fun getItemCount(): Int {
        return 5
    }

    fun submitList(modelList: ArrayList<MyModel>){
        this.modelList = modelList
    }
}