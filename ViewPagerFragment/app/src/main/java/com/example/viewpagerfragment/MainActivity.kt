package com.example.viewpagerfragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
val fragmentList = listOf(FragmentA(), FragmentB(), FragmentC(), FragmentD())

class MainActivity : FragmentActivity() {

    private val tabnames = arrayListOf("A", "B", "C", "D")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        var adapter1 = TestAdapter(this)
        viewPager.adapter = adapter1

        TabLayoutMediator(tabLayout, viewPager){
                tab, position ->
            tab.text = tabnames[position]

        }.attach()

    }

    override fun onBackPressed(){
        if(viewPager.currentItem == 0) {
            super.onBackPressed()
        }else{
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }

    private inner class TestAdapter(fa: FragmentActivity): FragmentStateAdapter(fa){
        override fun getItemCount(): Int {
            return fragmentList.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragmentList.get(position)
        }


    }




}