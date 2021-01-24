package com.example.week4

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_rank.*
import org.json.JSONArray

class RankActivity: AppCompatActivity() {

    var rankingList = ArrayList<MyModel>()
    private lateinit var myRecyclerAdapter: MyRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        setContentView(R.layout.activity_rank)

        rank_back.setOnClickListener{
            finish()
        }

        var prefs = getSharedPreferences("game", AppCompatActivity.MODE_PRIVATE)
        var str = prefs.getString("ranking", "")

        var strList = ArrayList<String>()
        if(str != null){
            var jsonArr = JSONArray(str)
            for(i in 0..(jsonArr.length()-1)){
                strList.add(jsonArr[i].toString())
            }
        }

        var i = 1
        for(s in strList){
            var temp = "Rank #${i}: ${s}"
            var model = MyModel(score = temp)
            this.rankingList.add(model)
            i++
        }

        myRecyclerAdapter = MyRecyclerAdapter()
        myRecyclerAdapter.submitList(this.rankingList)
        my_recycler_view.apply{
            layoutManager = LinearLayoutManager(this@RankActivity, LinearLayoutManager.VERTICAL, false)
            adapter = myRecyclerAdapter
        }

    }
}