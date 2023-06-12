package com.example.eggcareapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = HomeFragment()
        val profileFragment = ProfileFragment()
        val statsFragment = StatsFragment()
        val guideFragment = GuideFragment()
        val bottomNavigationView: BottomNavigationView

        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        SetCurrentFragment(homeFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener{
            when(it.itemId){
                R.id.miHome -> SetCurrentFragment(homeFragment)
                R.id.miProfile -> SetCurrentFragment(profileFragment)
                R.id.miStats -> SetCurrentFragment(statsFragment)
                R.id.miGuide -> SetCurrentFragment(guideFragment)
            }
            true
        }
    }

    private fun SetCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
}