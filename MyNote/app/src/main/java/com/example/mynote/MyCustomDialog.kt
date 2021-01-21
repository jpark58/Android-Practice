package com.example.mynote

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_write.*
import kotlinx.android.synthetic.main.my_dialog.*

class MyCustomDialog(context: Context, myCustomDialogInterface: MyCustomDialogInterface): Dialog(context), View.OnClickListener {

    private var myCustomDialogInterface: MyCustomDialogInterface? = null
    private var flag: String =""
    private var tag: String = ""

    init{
        this.myCustomDialogInterface = myCustomDialogInterface
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_dialog)

        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        filter_apply.setOnClickListener(this)
        filter_cancel.setOnClickListener(this)
        filter_fun.setOnClickListener(this)
        filter_family.setOnClickListener(this)
        filter_travel.setOnClickListener(this)
        filter_movie.setOnClickListener(this)
        filter_food.setOnClickListener(this)
        sort_ascending.setOnClickListener(this)
        sort_descending.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        when(view){
            filter_apply -> {
                if(flag == "" || flag == "ascending"){
                    this.myCustomDialogInterface?.onApplyBtnClicked(true, tag)
                }else if(flag == "descending"){
                    this.myCustomDialogInterface?.onApplyBtnClicked(false, tag)
                }
                dismiss()
            }
            filter_cancel -> {
                this.myCustomDialogInterface?.onCancelBtnClicked()
                cancel()
            }
            sort_ascending -> {
                if(flag == ""){
                    flag = "ascending"
                    sort_ascending.setTextColor(Color.WHITE)
                    sort_ascending.setBackgroundResource(R.drawable.entertainment_clicked)
                }else if(flag=="ascending"){
                    flag = ""
                    sort_ascending.setTextColor(Color.BLACK)
                    sort_ascending.setBackgroundResource(R.drawable.button_color)
                }
            }
            sort_descending -> {
                if(flag == ""){
                    flag = "descending"
                    sort_descending.setTextColor(Color.WHITE)
                    sort_descending.setBackgroundResource(R.drawable.entertainment_clicked)
                }else if(flag=="descending"){
                    flag = ""
                    sort_descending.setTextColor(Color.BLACK)
                    sort_descending.setBackgroundResource(R.drawable.button_color)
                }
            }
            filter_fun -> {
                if(tag == ""){
                    tag = "entertainment"
                    filter_fun.setTextColor(Color.WHITE)
                    filter_fun.setBackgroundResource(R.drawable.entertainment_clicked)
                }else if(tag=="entertainment"){
                    tag = ""
                    filter_fun.setTextColor(Color.BLACK)
                    filter_fun.setBackgroundResource(R.drawable.button_color)
                }
            }
            filter_food -> {
                if(tag == ""){
                    tag = "food"
                    filter_food.setTextColor(Color.WHITE)
                    filter_food.setBackgroundResource(R.drawable.food_clicked)
                }else if(tag =="food"){
                    tag = ""
                    filter_food.setTextColor(Color.BLACK)
                    filter_food.setBackgroundResource(R.drawable.button_color)
                }
            }
            filter_movie -> {
                if(tag == ""){
                    tag = "movie"
                    filter_movie.setTextColor(Color.WHITE)
                    filter_movie.setBackgroundResource(R.drawable.movie_clicked)
                }else if(tag == "movie"){
                    tag = ""
                    filter_movie.setTextColor(Color.BLACK)
                    filter_movie.setBackgroundResource(R.drawable.button_color)
                }
            }
            filter_travel -> {
                if(tag == ""){
                    tag = "travel"
                    filter_travel.setTextColor(Color.WHITE)
                    filter_travel.setBackgroundResource(R.drawable.travel_clicked)
                }else if(tag == "travel"){
                    tag = ""
                    filter_travel.setTextColor(Color.BLACK)
                    filter_travel.setBackgroundResource(R.drawable.button_color)
                }
            }
            filter_family -> {
                if(tag == ""){
                    tag = "family"
                    filter_family.setTextColor(Color.WHITE)
                    filter_family.setBackgroundResource(R.drawable.family_clicked)
                }else if(tag =="family"){
                    tag = ""
                    filter_family.setTextColor(Color.BLACK)
                    filter_family.setBackgroundResource(R.drawable.button_color)
                }
            }
        }
    }
}