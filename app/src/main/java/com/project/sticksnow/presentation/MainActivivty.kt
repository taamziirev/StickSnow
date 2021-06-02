package com.project.sticksnow.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.project.sticksnow.presentation.bottomnavigation.active
import com.project.sticksnow.presentation.bottomnavigation.switchFragment
import com.project.sticksnow.R
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.project.sticksnow.databinding.ActivityMainBinding
import com.project.sticksnow.presentation.bottomnavigation.BottomNavigationPosition
import com.project.sticksnow.presentation.bottomnavigation.createFragment
import com.project.sticksnow.presentation.bottomnavigation.findNavigationPositionById
import com.project.sticksnow.presentation.bottomnavigation.getTag
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(){

    private var navPosition: BottomNavigationPosition = BottomNavigationPosition.CATEGORIES

    private lateinit var mNavController: NavController

    private lateinit var mAppBarConfiguration: AppBarConfiguration

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        restoreSavedInstanceState(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.toolbar.apply {
            setSupportActionBar(this)
        }

        binding.bottomNavigation.apply {
            active(navPosition.position)
            setOnNavigationItemSelectedListener { item ->
                navPosition = findNavigationPositionById(item.itemId)
                switchFragment(navPosition)
            }
        }

        // Add the home fragment
        savedInstanceState ?: switchFragment(BottomNavigationPosition.CATEGORIES)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        // Store the current navigation position.
        outState.putInt(KEY_POSITION, navPosition.id)
        super.onSaveInstanceState(outState)
    }

    private fun restoreSavedInstanceState(savedInstanceState: Bundle?) {
        // Restore the current navigation position.
        savedInstanceState?.getInt(KEY_POSITION, BottomNavigationPosition.CATEGORIES.id)?.also {
            navPosition = findNavigationPositionById(it)
        }
    }

    private fun switchFragment(navPosition: BottomNavigationPosition): Boolean {
        return findFragment(navPosition).let {
            supportFragmentManager.switchFragment(it, navPosition.getTag()) // Extension function
        }
    }

    private fun findFragment(position: BottomNavigationPosition): Fragment {
        return supportFragmentManager.findFragmentByTag(position.getTag())
            ?: position.createFragment()
    }

    companion object {
        const val KEY_POSITION = "keyPosition"

    }

}