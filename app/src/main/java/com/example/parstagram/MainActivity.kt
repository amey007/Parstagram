package com.example.parstagram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.parstagram.fragments.ComposeFragment
import com.example.parstagram.fragments.FeedFragment
import com.example.parstagram.fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


/**
* Activity to create a post by taking the photo with their camera
*/

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager: FragmentManager = supportFragmentManager
        val bottomNavigtion = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigtion.setOnItemSelectedListener() {
            item ->
            var fragmentToShow: Fragment? = null

            when (item.itemId){
                R.id.action_home ->{
                    fragmentToShow = FeedFragment()
                }
                R.id.action_profile ->{
                    fragmentToShow = ProfileFragment()
                }
                R.id.action_compose ->{
                    fragmentToShow =ComposeFragment()
                }
            }
            if(fragmentToShow != null){
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragmentToShow).commit()
            }
            // Return true to say that we handled the iteraction
            true
        }

        // Set default selection
        bottomNavigtion.selectedItemId = R.id.action_home

//        queryPosts()
    }



    companion object{
        val TAG = "MainActivity"
    }


}
