package com.example.week5.recylerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.week5.KakaoApplication
import com.example.week5.R
import com.example.week5.model.Movie
import kotlinx.android.synthetic.main.layout_movie_item.view.*

class MovieViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val poster = itemView.rv_poster
    private val title = itemView.rv_title
    private val year = itemView.rv_year

    fun bindWithView(item: Movie){
        title.text = item.title
        year.text = item.year

        Glide.with(KakaoApplication.instance)
            .load(item.poster)
            .placeholder(R.drawable.ic_baseline_insert_photo_24)
            .into(poster)

    }
}