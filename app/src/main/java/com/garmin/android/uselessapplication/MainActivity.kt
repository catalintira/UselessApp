package com.garmin.android.uselessapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mToolbar: Toolbar
    private lateinit var mDrawerLayout: DrawerLayout
    private lateinit var mNavView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initDrawer()


    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Toast.makeText(this, "AI SELECTAT CEVA", Toast.LENGTH_SHORT).show()
        mDrawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun initDrawer() {
        mToolbar = findViewById(R.id.toolbar)
        mDrawerLayout = findViewById(R.id.drawer_layout)
        mNavView = findViewById(R.id.navigationView)

        setSupportActionBar(mToolbar)

        val toogle = ActionBarDrawerToggle(
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
}
