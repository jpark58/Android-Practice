package com.example.mynote

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.BitmapFactory
import android.icu.lang.UCharacter
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.example.mynote.adapter.NotesAdapter
import com.example.mynote.database.NotesDatabase
import com.example.mynote.entities.Notes
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_write.*
import kotlinx.android.synthetic.main.activity_write.view.*
import kotlinx.android.synthetic.main.item_rv_notes.*
import kotlinx.android.synthetic.main.item_rv_notes.view.*
import kotlinx.coroutines.*
import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope by MainScope(), MyCustomDialogInterface {
    var arrNotes = ArrayList<Notes>()
    var notesAdapter: NotesAdapter = NotesAdapter()
    private lateinit var job: Job
    var username: String? =null
    var favoriteList = ArrayList<Int>()
    var tag = ""
    var ascending = true

    var options = listOf("Fun", "Entertainment", "Food", "Family", "Movie")

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("로그", "MainActivity - onCreate")

        job = Job()
        setContentView(R.layout.activity_main)

        val swipeHelperCallback = SwipeHelperCallback()
        val itemTouchHelper = ItemTouchHelper(swipeHelperCallback)
        itemTouchHelper.attachToRecyclerView(recycler_view)


        fabBtnCreateNote.setOnClickListener {
            var intent = Intent(this, WriteActivity::class.java)
            startActivityForResult(intent,1)
        }

        username = intent.getStringExtra("username")

        Log.d("username", "$username")
        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)

    }

    fun onDialogBtnClicked(view: View){
        val myCustomDialog = MyCustomDialog(this, this)
        myCustomDialog.show()
    }

    fun onLikeSetBtnClicked(view: View){
        var tempArr = ArrayList<Notes>()

        for (arr in arrNotes){
            for(e in favoriteList){
                if ( e == arr.id){
                    tempArr.add(arr)
                }
            }
        }

        notesAdapter.setData(tempArr)
        notesAdapter.notifyDataSetChanged()
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

                for(arr in arrNotes){
                    if(arr.like == "1" && arr.id !in favoriteList){
                        favoriteList.add(arr.id!!)
                    }
                }
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

        override fun onLikeClicked(view: View, notesId: Int) {
            Log.d("로그", "Like Btn Clicked!")

            if(notesId !in favoriteList){
                favoriteList.add(notesId)
            }else{
                favoriteList.remove(notesId)
            }

            launch {

                this?.let {
                    var notes = NotesDatabase.getDatabase(this@MainActivity).noteDao().getSpecificNote(notesId)
                    var temp = notes.like


                    if(temp == "0"){
                        Log.d("로그", "Clicked: $notesId")
                        view.like.setImageResource(R.drawable.ic_star_clicked)
                        notes.like = "1"
                        Log.d("로그", "temp: ${notes.like}")
                        NotesDatabase.getDatabase(this@MainActivity).noteDao().updateNote(notes)
                    }else{
                        Log.d("로그", "UnClicked: $notesId")
                        view.like.setImageResource(R.drawable.ic_star)
                        notes.like = "0"
                        Log.d("로그", "temp: ${notes.like}")
                        NotesDatabase.getDatabase(this@MainActivity).noteDao().updateNote(notes)
                    }

                    notesAdapter.notifyItemChanged(notesId)
                }
            }

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

    override fun onApplyBtnClicked(ascending: Boolean, tag: String) {

        var tempArr = ArrayList<Notes>()

        for (arr in arrNotes){
            if (arr.tag!!.toLowerCase(Locale.getDefault()).contains(tag)){
                tempArr.add(arr)
            }
        }

       if(tag == ""){
           tempArr.clear()
           tempArr.addAll(arrNotes)
       }

        if(ascending){
            notesAdapter.setData(tempArr)
            notesAdapter.notifyDataSetChanged()
        }else{
            if(tempArr.size > 1){
                var finallist = tempArr.reversed()
                Log.d("로그", "reversed!")
                notesAdapter.setData(finallist)
                notesAdapter.notifyDataSetChanged()
            }else{
                notesAdapter.setData(tempArr)
                notesAdapter.notifyDataSetChanged()
            }

        }

    }

    override fun onCancelBtnClicked() {
    }

    inner class SwipeHelperCallback : ItemTouchHelper.Callback() {

        override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
        ): Int {
            // Drag와 Swipe 방향을 결정 Drag는 사용하지 않아 0, Swipe의 경우는 LEFT, RIGHT 모두 사용가능하도록 설정
            return makeMovementFlags(0, UCharacter.IndicPositionalCategory.LEFT or UCharacter.IndicPositionalCategory.RIGHT)
        }

        override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
        ) = false

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            launch {
                this?.let{
                    Log.d("로그", "$position")
                    NotesDatabase.getDatabase(this@MainActivity).noteDao().deleteSpecificNote(arrNotes[position].id!!)
                    arrNotes.removeAt(position)
                    notesAdapter.notifyDataSetChanged()
                }
            }
        }
    }
}