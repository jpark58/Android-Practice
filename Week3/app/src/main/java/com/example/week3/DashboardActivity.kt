package com.example.week3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        var item = ArrayList<StaticRvModel>()
        item.add(StaticRvModel(R.drawable.pizza, "Pizza"))
        item.add(StaticRvModel(R.drawable.hamburger, "Burger"))
        item.add(StaticRvModel(R.drawable.fries, "Fries"))
        item.add(StaticRvModel(R.drawable.sandwich, "Sandwich"))
        item.add(StaticRvModel(R.drawable.icecream, "Ice cream"))

        rv_1.adapter = StaticRvAdapter(item)
        rv_1.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
    }
}