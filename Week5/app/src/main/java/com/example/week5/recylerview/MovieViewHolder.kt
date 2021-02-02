package com.example.week5.recylerview

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.week5.KakaoApplication
import com.example.week5.R
import com.example.week5.kakaoretrofit.PapagoManager
import com.example.week5.model.Movie
import com.example.week5.utils.RESPONSE_STATE
import kotlinx.android.synthetic.main.layout_movie_item.view.*

class MovieViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val poster = itemView.rv_poster
    private val title = itemView.rv_title
    private val year = itemView.rv_year
    private var title_translated = itemView.rv_title_translated

    fun bindWithView(item: Movie){
        title.text = item.title
        year.text = item.year

        PapagoManager.instance.translate(src="en", target="ko", text = item.title, completion = {
            responseState, translatedText->
            when(responseState){
                RESPONSE_STATE.OKAY -> {
                    Log.d("파파고", "파파고 성공! ${translatedText}")
                    title_translated.text = translatedText
                }
                else -> {
                    Log.d("파파고", "파파고 패!")
                }
            }
        })

        Glide.with(KakaoApplication.instance)
            .load(item.poster)
            .placeholder(R.drawable.ic_baseline_insert_photo_24)
            .into(poster)

    }
}