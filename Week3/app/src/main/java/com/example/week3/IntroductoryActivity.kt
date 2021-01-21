package com.example.week3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_introductory.*

class IntroductoryActivity : AppCompatActivity() {
    private lateinit var viewPager:ViewPager
    private lateinit var pagerAdapter: FragmentStatePagerAdapter
    private lateinit var anim:Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_introductory)

        viewPager = pager
        pagerAdapter = ScreenSlidePagerAdapter(supportFragmentManager)
        viewPager.adapter = pagerAdapter

        anim = AnimationUtils.loadAnimation(this, R.anim.o_b_anim)
        viewPager.startAnimation(anim)

        logo.animate().translationY(-1600f).setDuration(1000).startDelay = 5000
        app_name.animate().translationY(1400f).setDuration(1000).startDelay = 5000
        image.animate().translationY(1400f).setDuration(1000).startDelay = 5000
        lottie.animate().translationY(1400f).setDuration(1000).startDelay = 5000
    }

    class ScreenSlidePagerAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm) {
        private val NUM_PAGES = 3

        override fun getCount(): Int {
            return NUM_PAGES
        }

        override fun getItem(position: Int): Fragment {

            return when(position)
            {
                0 -> {
                    OnBoardingFragment1.newInstance()
                }

                1-> {
                    OnBoardingFragment2.newInstance()
                }

                2-> {
                    OnBoardingFragment3.newInstance()
                }
                else -> OnBoardingFragment1.newInstance()
            }
        }

    }
}