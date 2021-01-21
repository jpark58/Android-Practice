package com.example.mynote

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.mynote.database.NotesDatabase
import kotlinx.android.synthetic.main.activity_write.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class WriteActivity : AppCompatActivity() {
    var currentDate: String? = null
    private var notesId = -1
    private var tag = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)
        Log.d("로그", "WriteActivity - onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.d("로그", "WriteActivity - onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("로그", "WriteActivity - onResume")
        notesId = intent.getIntExtra("notesId",-1)

        if(notesId != -1){
            MainScope().launch {
                this?.let {
                    var notes = NotesDatabase.getDatabase(this@WriteActivity).noteDao().getSpecificNote(notesId)
                    etNoteTitle.setText(notes.title)
                    etNoteSubTitle.setText(notes.subTitle)
                    etNoteDesc.setText(notes.noteText)
                    imgDelete.visibility = View.VISIBLE
                }
            }
        }


        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        currentDate = sdf.format(Date())

        tvDateTime.text = currentDate

        imgDone.setOnClickListener {
            if (notesId != -1){
                updateNote()
            }else{
                saveNote()
            }
            finish()
        }

        imgDelete.setOnClickListener {
            deleteNote(notesId)
            finish()
        }

        imgBack.setOnClickListener {
            finish()
        }

        entertainment.setOnClickListener{
            if(tag == ""){
                tag = "entertainment"
                entertainment.setTextColor(Color.WHITE)
                entertainment.setBackgroundResource(R.drawable.entertainment_clicked)
            }else if(tag=="entertainment"){
                tag = ""
                entertainment.setTextColor(Color.BLACK)
                entertainment.setBackgroundResource(R.drawable.button_color)
            }
        }

        food.setOnClickListener{
            if(tag == ""){
                tag = "food"
                food.setTextColor(Color.WHITE)
                food.setBackgroundResource(R.drawable.food_clicked)
            }else if(tag =="food"){
                tag = ""
                food.setTextColor(Color.BLACK)
                food.setBackgroundResource(R.drawable.button_color)
            }
        }

        movie.setOnClickListener{
            if(tag == ""){
                tag = "movie"
                movie.setTextColor(Color.WHITE)
                movie.setBackgroundResource(R.drawable.movie_clicked)
            }else if(tag == "movie"){
                tag = ""
                movie.setTextColor(Color.BLACK)
                movie.setBackgroundResource(R.drawable.button_color)
            }
        }

        travel.setOnClickListener{
            if(tag == ""){
                tag = "travel"
                travel.setTextColor(Color.WHITE)
                travel.setBackgroundResource(R.drawable.travel_clicked)
            }else if(tag == "travel"){
                tag = ""
                travel.setTextColor(Color.BLACK)
                travel.setBackgroundResource(R.drawable.button_color)
            }
        }

        family.setOnClickListener{
            if(tag == ""){
                tag = "family"
                family.setTextColor(Color.WHITE)
                family.setBackgroundResource(R.drawable.family_clicked)
            }else if(tag =="family"){
                tag = ""
                family.setTextColor(Color.BLACK)
                family.setBackgroundResource(R.drawable.button_color)
            }
        }
    }

    private fun deleteNote(id: Int){
        MainScope().launch {
            this?.let{
                com.example.mynote.database.NotesDatabase.getDatabase(this@WriteActivity).noteDao().deleteSpecificNote(id)
                etNoteTitle.setText("")
                etNoteSubTitle.setText("")
                etNoteDesc.setText("")
            }
        }
    }

    private fun updateNote(){
        MainScope().launch {

            this?.let {
                var notes = NotesDatabase.getDatabase(this@WriteActivity).noteDao().getSpecificNote(notesId)

                notes.title = etNoteTitle.text.toString()
                notes.subTitle = etNoteSubTitle.text.toString()
                notes.noteText = etNoteDesc.text.toString()
                notes.dateTime = currentDate
                notes.tag = tag

                NotesDatabase.getDatabase(this@WriteActivity).noteDao().updateNote(notes)
                etNoteTitle.setText("")
                etNoteSubTitle.setText("")
                etNoteDesc.setText("")
                tag = ""

            }
        }
    }

    private fun saveNote(){
        if(etNoteTitle.text.isNullOrEmpty()){
            Toast.makeText(this, "Title Required", Toast.LENGTH_SHORT).show()
        }
        else if(etNoteSubTitle.text.isNullOrEmpty()){
            Toast.makeText(this, "Sub Title Required", Toast.LENGTH_SHORT).show()
        }
        else if(etNoteDesc.text.isNullOrEmpty()){
            Toast.makeText(this, "Description Required", Toast.LENGTH_SHORT).show()
        }
        else {
            MainScope().launch {
                var notes = com.example.mynote.entities.Notes()
                notes.title = etNoteTitle.text.toString()
                notes.subTitle = etNoteSubTitle.text.toString()
                notes.noteText = etNoteDesc.text.toString()
                notes.dateTime = currentDate
                notes.tag = tag
                notes.like = "0"
                this?.let{
                    com.example.mynote.database.NotesDatabase.getDatabase(this@WriteActivity).noteDao().insertNotes(notes)
                    etNoteTitle.setText("")
                    etNoteSubTitle.setText("")
                    etNoteDesc.setText("")
                    tag = ""
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d("로그", "WriteActivity - onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("로그", "WriteActivity - onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("로그", "WriteActivity - onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("로그", "WriteActivity - onDestroy")
    }

}