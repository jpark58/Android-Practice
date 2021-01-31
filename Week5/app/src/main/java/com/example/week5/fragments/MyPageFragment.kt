package com.example.week5.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.week5.KakaoApplication
import com.example.week5.MainActivity
import com.example.week5.R
import com.example.week5.databinding.FragmentHomeBinding
import com.example.week5.databinding.FragmentMeBinding
import com.example.week5.databinding.FragmentMyPageBinding

class MyPageFragment: Fragment() {

    private var fragmentMyPageBinding: FragmentMyPageBinding? = null

    companion object {
        const val TAG : String = "로그"

        fun newInstance() : MyPageFragment {
            return MyPageFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMyPageBinding.inflate(inflater, container, false)
        fragmentMyPageBinding = binding

        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        return fragmentMyPageBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var email = (activity as MainActivity).intent.getStringExtra("email")
        var image = (activity as MainActivity).intent.getStringExtra("image")
        var name = (activity as MainActivity).intent.getStringExtra("nickname")

        fragmentMyPageBinding?.kakaoEmail?.text = email
        fragmentMyPageBinding?.kakaoName?.text = name

        Glide.with(KakaoApplication.instance)
                .load(image)
                .placeholder(R.drawable.ic_baseline_insert_photo_24)
                .into(fragmentMyPageBinding!!.kakaoImage)
    }




    override fun onDestroyView() {
        fragmentMyPageBinding = null
        super.onDestroyView()
    }
}