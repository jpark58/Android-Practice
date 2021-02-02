package com.example.week5.fragments

import android.app.SearchManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat.invalidateOptionsMenu
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.week5.KakaoApplication
import com.example.week5.R
import com.example.week5.databinding.FragmentHomeBinding
import com.example.week5.kakaoretrofit.PapagoManager
import com.example.week5.model.Movie
import com.example.week5.recylerview.MovieRecyclerAdapter
import com.example.week5.retrofit.RetrofitManager
import com.example.week5.utils.RESPONSE_STATE
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment: Fragment(), SearchView.OnQueryTextListener {

//test
    private var fragmentHomeBinding : FragmentHomeBinding? = null
    private lateinit var movieAdapter: MovieRecyclerAdapter
    private var movieList = ArrayList<Movie>()

    private lateinit var mySearchView: SearchView
    private lateinit var mySearchViewEditText: EditText

    private lateinit var myMenu: Menu

    companion object {

        fun newInstance() : HomeFragment {
            return HomeFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        fragmentHomeBinding = binding
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()

        setHasOptionsMenu(true)



        RetrofitManager.instance.searchMovies(title = "galaxy", completion = {
                responseState, responseBody ->
            when(responseState) {
                RESPONSE_STATE.OKAY -> {
                    Log.d("로그", "호출 성공: ${responseBody?.size}")
                    this.movieList.addAll(responseBody as ArrayList<Movie>)
                    this.movieAdapter.notifyDataSetChanged()
                }
                RESPONSE_STATE.FAIL -> {
                    Log.d("로그", "Error")
                }
            }

        })



        return fragmentHomeBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.movieAdapter = MovieRecyclerAdapter()
        this.movieAdapter.submitList(this.movieList)
        my_recycler_view.layoutManager = LinearLayoutManager(KakaoApplication.instance, LinearLayoutManager.VERTICAL, false)
        my_recycler_view.adapter = this.movieAdapter
        Log.d("로그그2", "onViewCreated: ${movieList.size}")

        //top_app_bar.menu.clear()


    }

    override fun onDestroyView() {
        fragmentHomeBinding = null
        super.onDestroyView()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {

        Log.d("로그", "Query submitted")
        this.movieList.clear()

        RetrofitManager.instance.searchMovies(title = query!!, completion = {
            responseState, responseBody ->
            when(responseState) {
                RESPONSE_STATE.OKAY -> {
                    Log.d("로그", "호출 성공: ${responseBody?.size}")
                    this.movieList.addAll(responseBody as ArrayList<Movie>)
                    this.movieAdapter.notifyDataSetChanged()
                }
                RESPONSE_STATE.FAIL -> {
                    Log.d("로그", "Error")
                }
            }

        })

        myMenu.findItem(R.id.search_menu).collapseActionView()
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {

        val userInput = newText ?: ""

        if(userInput.count() > 30){
            Toast.makeText(KakaoApplication.instance, "30자 초과", Toast.LENGTH_SHORT).show()
        }

        return true
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {


        Log.d("로그2", "OnCraeteOptionMEnu")

        inflater.inflate(R.menu.top_app_bar_menu, menu)


        this.myMenu = menu
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager

        this.mySearchView = menu?.findItem(R.id.search_menu)?.actionView as SearchView
        this.mySearchView.apply{
            this.queryHint = "영화 제목을 입력해주세요"

            this.setOnQueryTextListener(this@HomeFragment)

            this.setOnQueryTextFocusChangeListener { _, hasFocus ->
                when(hasFocus){
                    true -> {
                        Log.d("로그", "Search view opened")
                    }
                    false ->{
                        Log.d("로그", "Search view closed")
                    }
                }
            }

            mySearchViewEditText = this.findViewById(androidx.appcompat.R.id.search_src_text)
        }

        this.mySearchViewEditText.apply{
            this.setTextColor(Color.WHITE)
            this.setHintTextColor(Color.WHITE)
        }

        super.onCreateOptionsMenu(menu, inflater)
    }


}