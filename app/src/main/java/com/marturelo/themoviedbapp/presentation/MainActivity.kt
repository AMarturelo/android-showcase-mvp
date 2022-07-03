package com.marturelo.themoviedbapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.marturelo.themoviedbapp.R
import kotlinx.android.synthetic.main.activity_main.nav_host_fragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHost = nav_host_fragment as NavHostFragment
        navHost.navController.setGraph(
            R.navigation.nav_graph,
        )
    }
}