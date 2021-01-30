package com.example.week5.recylerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.week5.KakaoApplication
import com.example.week5.R
import com.example.week5.model.Movie

class MovieRecyclerAdapter: RecyclerView.Adapter<MovieViewHolder>() {

    private var movieList = ArrayList<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_movie_item, parent, false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {

        holder.bindWithView(this.movieList[position])
    }

    override fun getItemCount(): Int {
        return this.movieList.size
    }

    fun submitList(movieList: ArrayList<Movie>){
        this.movieList = movieList
    }
}