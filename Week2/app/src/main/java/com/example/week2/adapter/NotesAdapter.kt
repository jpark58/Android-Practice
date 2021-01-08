package com.example.week2.adapter

import android.graphics.BitmapFactory
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.week2.R
import com.example.week2.entities.Notes
import kotlinx.android.synthetic.main.item_rv_notes.view.*

class NotesAdapter(): RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {
    var arrList = ArrayList<Notes>()
    var listener: OnItemClickListener? = null

    fun setData(arrNotesList: List<Notes>){
        arrList = arrNotesList as ArrayList<Notes>
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

        if(arrList[position].color != null){
            holder.itemView.cardView.setBackgroundColor(Color.parseColor(arrList[position].color))
        }else{
            holder.itemView.cardView.setBackgroundColor(Color.parseColor(R.color.colorLightBlack.toString()))
        }

        if (arrList[position].imgPath != null){
            holder.itemView.imgCard.setImageBitmap(BitmapFactory.decodeFile(arrList[position].imgPath))
            holder.itemView.imgCard.visibility = View.VISIBLE
            Log.d("test", "Test in true")
        }else{
            holder.itemView.imgCard.visibility = View.GONE
            Log.d("test", "Test in false")
        }

        if (arrList[position].webLink != ""){
            holder.itemView.tvWebLink.text = arrList[position].webLink
            holder.itemView.tvWebLink.visibility = View.VISIBLE
        }else{
            holder.itemView.tvWebLink.visibility = View.GONE
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
        fun onClicked(noteId:Int)
    }
}