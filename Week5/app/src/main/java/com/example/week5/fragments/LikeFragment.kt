package com.example.week5.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.week5.databinding.FragmentLikeBinding
import com.example.week5.databinding.FragmentMeBinding


class LikeFragment: Fragment() {

    private var fragmentLikeBinding: FragmentLikeBinding? = null

    companion object {

        fun newInstance() :  LikeFragment {
            return  LikeFragment()
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLikeBinding.inflate(inflater, container, false)
        fragmentLikeBinding = binding
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        return fragmentLikeBinding?.root
    }

    override fun onDestroyView() {
        fragmentLikeBinding = null
        super.onDestroyView()
    }
}