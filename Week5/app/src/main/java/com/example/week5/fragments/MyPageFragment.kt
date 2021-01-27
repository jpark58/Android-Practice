package com.example.week5.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.week5.databinding.FragmentHomeBinding
import com.example.week5.databinding.FragmentMeBinding
import com.example.week5.databinding.FragmentMyPageBinding

class MyPageFragment: Fragment() {

    private var fragmentMYPageBinding: FragmentMyPageBinding? = null

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
        fragmentMYPageBinding = binding

        return fragmentMYPageBinding?.root
    }

    override fun onDestroyView() {
        fragmentMYPageBinding = null
        super.onDestroyView()
    }
}