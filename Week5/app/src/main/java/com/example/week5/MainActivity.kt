package com.example.week5

import android.app.SearchManager
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.EditText
import android.widget.SearchView
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.week5.databinding.ActivityMainBinding
import com.example.week5.fragments.HomeFragment
import com.example.week5.fragments.LikeFragment
import com.example.week5.fragments.MeFragment
import com.example.week5.fragments.MyPageFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_home.*

class MainActivity : AppCompatActivity() {


    private lateinit var homeFragment: HomeFragment
    private lateinit var meFragment: MeFragment
    private lateinit var myPageFragment: MyPageFragment
    private lateinit var likeFragment: LikeFragment
    private lateinit var activityMainBinding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        activityMainBinding = binding
        setContentView(activityMainBinding!!.root)

        activityMainBinding?.bottomNav?.setOnNavigationItemSelectedListener(onBottomNavItemSelectedListener)

        homeFragment = HomeFragment.newInstance()
        supportFragmentManager.beginTransaction().add(R.id.fragments_frame, homeFragment).commit()


        val email = intent.getStringExtra("email")
        Log.d("로그", "${email}")


    }



    private val onBottomNavItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {

        when(it.itemId){
            R.id.homeFragment -> {
                homeFragment = HomeFragment.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.fragments_frame, homeFragment).commit()
            }
            R.id.meFragment -> {
                meFragment = MeFragment.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.fragments_frame, meFragment).commit()
            }
            R.id.likeFragment -> {
                likeFragment = LikeFragment.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.fragments_frame,likeFragment).commit()
            }
            R.id.myPageFragment -> {
                myPageFragment = MyPageFragment.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.fragments_frame, myPageFragment).commit()
            }
        }

        true
    }
}