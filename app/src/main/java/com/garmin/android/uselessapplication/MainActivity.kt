package com.garmin.android.uselessapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.garmin.android.uselessapplication.fragment.WelcomeFragment
import com.google.android.material.navigation.NavigationView
import androidx.lifecycle.ViewModelProviders
import com.garmin.android.uselessapplication.fragment.SearchWordFragment
import com.garmin.android.uselessapplication.repository.InternalStorageRepository
import com.garmin.android.uselessapplication.viewmodel.StorageViewModel


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mToolbar: Toolbar
    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mNavView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initDrawer()
        setFragment(WelcomeFragment.newInstance())
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_search_word -> setFragment(SearchWordFragment.newInstance())
        }

        mDrawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun initDrawer() {
        mToolbar = findViewById(R.id.toolbar)
        mDrawerLayout = findViewById(R.id.drawer_layout)
        mNavView = findViewById(R.id.navigationView)

        setSupportActionBar(mToolbar)

        ActionBarDrawerToggle(
            this,
            mDrawerLayout,
            mToolbar,
            0,
            0
        ).let {
            mDrawerLayout.addDrawerListener(it)
            it.syncState()
            mNavView.setNavigationItemSelectedListener(this@MainActivity)
        }
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.content_frame, fragment)
            .commit()
    }
}
