package com.example.week5.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.week5.KakaoApplication
import com.example.week5.databinding.FragmentHomeBinding
import com.example.week5.model.Movie
import com.example.week5.recylerview.MovieRecyclerAdapter
import com.example.week5.retrofit.RetrofitManager
import com.example.week5.utils.RESPONSE_STATE
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment: Fragment() {


    private var fragmentHomeBinding : FragmentHomeBinding? = null
    private lateinit var movieAdapter: MovieRecyclerAdapter
    private var movieList = ArrayList<Movie>()

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




//        fragmentHomeBinding!!.searchBtn.setOnClickListener {
//            RetrofitManager.instance.searchMovies(title = fragmentHomeBinding!!.searchTitle.text.toString(), completion = {
//                responseState, responseBody ->
//                when(responseState) {
//                    RESPONSE_STATE.OKAY -> {
//                        Log.d("로그", "호출 성공: ${responseBody?.size}")
//                        this.movieAdapter.submitList(responseBody!!)
//                    }
//                    RESPONSE_STATE.FAIL -> {
//                       Log.d("로그", "Error")
//                    }
//                }
//
//            })
//        }

        return fragmentHomeBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.movieAdapter = MovieRecyclerAdapter()
        this.movieAdapter.submitList(this.movieList)
        my_recycler_view.layoutManager = LinearLayoutManager(KakaoApplication.instance, LinearLayoutManager.VERTICAL, false)
        my_recycler_view.adapter = this.movieAdapter
        Log.d("로그그2", "onViewCreated: ${movieList.size}")
    }

    override fun onDestroyView() {
        fragmentHomeBinding = null
        super.onDestroyView()
    }
}