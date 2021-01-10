package com.example.mynote.adapter


import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mynote.R
import com.example.mynote.entities.Notes
import kotlinx.android.synthetic.main.item_rv_notes.view.*
import java.security.AccessController.getContext

class NotesAdapter(): RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {
    var arrList = ArrayList<Notes>()
    var listener: OnItemClickListener? = null

    fun setData(arrNotesList: List<Notes>){
        arrList = arrNotesList as ArrayList<Notes>
        Log.d("로그", "${arrList.size}")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_rv_notes, parent, false)
        )
    }

    fun setOnClickListener(listener1: OnItemClickListener){
        listener = listener1
    }


    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {

        holder.itemView.tvTitle.text = arrList[position].title
        holder.itemView.tvDesc.text = arrList[position].noteText
        holder.itemView.tvDateTime.text = arrList[position].dateTime

        var temp = arrList[position].tag
        if(temp =="food"){
            holder.itemView.imgTag.setBackgroundResource(R.drawable.ic_food)
        }
        else if(temp =="family"){
            holder.itemView.imgTag.setBackgroundResource(R.drawable.ic_family)
        }
        else if(temp =="entertainment"){
            holder.itemView.imgTag.setBackgroundResource(R.drawable.ic_entertainment)
        }
        else if(temp =="movie"){
            holder.itemView.imgTag.setBackgroundResource(R.drawable.ic_movie)
        }
        else if(temp =="travel"){
            holder.itemView.imgTag.setBackgroundResource(R.drawable.ic_travel)
        }else{
            holder.itemView.imgTag.setBackgroundResource(R.drawable.ic_face)
        }

        holder.itemView.cardView.setOnClickListener {
            listener!!.onClicked(arrList[position].id!!)
        }
    }

    override fun getItemCount(): Int {
        return arrList.size
    }

    class NotesViewHolder(view: View): RecyclerView.ViewHolder(view){

    }

    interface OnItemClickListener{
        fun onClicked(notesId:Int)
    }
}