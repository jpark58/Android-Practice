package com.example.mynote

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mynote.adapter.NotesAdapter
import com.example.mynote.database.NotesDatabase
import com.example.mynote.entities.Notes
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    var arrNotes = ArrayList<Notes>()
    var notesAdapter: NotesAdapter = NotesAdapter()
    private lateinit var job: Job
    var username: String? =null

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("로그", "MainActivity - onCreate")

        job = Job()
        setContentView(R.layout.activity_main)

        fabBtnCreateNote.setOnClickListener {
            var intent = Intent(this, WriteActivity::class.java)
            startActivityForResult(intent,1)
        }

        username = intent.getStringExtra("username")

        Log.d("username", "$username")
        tvname.text = username
        tvname.visibility = View.VISIBLE
        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)

    }

    override fun onStart() {
        super.onStart()
        Log.d("로그", "MainActivity - onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("로그", "MainActivity - onResume")

        launch{
            this@MainActivity?.let{
                var notes = NotesDatabase.getDatabase(it).noteDao().getAllNotes()
                notesAdapter!!.setData(notes)
                arrNotes = notes as ArrayList<Notes>
                recycler_view.adapter = notesAdapter
                notesAdapter.notifyDataSetChanged()
            }
        }

        recycle_container.visibility = View.VISIBLE
        tvs.visibility =View.VISIBLE
        search_view.visibility = View.VISIBLE
        fabBtnCreateNote.visibility = View.VISIBLE
        paused.visibility = View.GONE


        notesAdapter!!.setOnClickListener(onClicked)
        search_view.setOnQueryTextListener( object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {

                var tempArr = ArrayList<Notes>()

                for (arr in arrNotes){
                    if (arr.title!!.toLowerCase(Locale.getDefault()).contains(p0.toString())){
                        tempArr.add(arr)
                    }
                }

                notesAdapter.setData(tempArr)
                notesAdapter.notifyDataSetChanged()
                return true
            }

        })
    }

    private val onClicked = object :NotesAdapter.OnItemClickListener{
        override fun onClicked(notesId: Int) {
            Log.d("로그", "Clicked!")
            var intent = Intent(this@MainActivity, WriteActivity::class.java)
            intent.putExtra("notesId", notesId)
            startActivity(intent)
        }

    }

    override fun onPause() {
        super.onPause()
        Log.d("로그", "MainActivity - onPause")
        recycle_container.visibility = View.GONE
        tvs.visibility = View.GONE
        search_view.visibility = View.GONE
        fabBtnCreateNote.visibility = View.GONE
        paused.visibility = View.VISIBLE
    }

    override fun onStop() {
        super.onStop()
        Log.d("로그", "MainActivity - onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("로그", "MainActivity - onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("로그", "MainActivity - onDestroy")
        job.cancel()
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        notesAdapter.notifyDataSetChanged()
        recycle_container.visibility = View.VISIBLE
        tvs.visibility =View.VISIBLE
        search_view.visibility = View.VISIBLE
        fabBtnCreateNote.visibility = View.VISIBLE
        paused.visibility = View.GONE
    }

}