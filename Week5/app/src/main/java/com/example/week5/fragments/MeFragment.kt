package com.example.week5.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.week5.databinding.FragmentMeBinding


class MeFragment: Fragment() {

    private var fragmentMeBinding: FragmentMeBinding? = null

    companion object {

        fun newInstance() :  MeFragment {
            return  MeFragment()
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMeBinding.inflate(inflater, container, false)
        fragmentMeBinding = binding
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        return fragmentMeBinding?.root
    }

    override fun onDestroyView() {
        fragmentMeBinding = null
        super.onDestroyView()
    }
}