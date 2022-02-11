package com.example.habit.ui.view

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.habit.R
import com.example.habit.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.nav_header_main.view.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        setPhotoInImageView(navView)
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_habits, R.id.nav_about_app
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun setPhotoInImageView(navView: NavigationView) {
        val header: View = navView.getHeaderView(0)
        val imageUser = header.findViewById<ImageView>(R.id.avatar)
        Glide.with(this)
            .load("https://sun2.48276.userapi.com/impf/IAyEyGXLV10OvKEDqgq6mnSGM0t7fqFUlDTZrA/dHlWG8GAul8.jpg?size=960x540&quality=96&sign=822a02a84b9502c6df3e3b6238d52a17&type=album")
            .into(imageUser)
    }
}