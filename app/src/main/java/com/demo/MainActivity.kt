package com.demo

import android.os.Bundle
import android.view.View
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.demo.common.SharedViewModel
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        val fab: FloatingActionButton = findViewById(R.id.fab)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val sharedViewModel= ViewModelProvider(this).get(SharedViewModel::class.java)
        sharedViewModel.exception.observe(this, Observer { t ->
            t?.let {
                val bundle=Bundle()
                bundle.putSerializable(Exception::class.java.simpleName,  it)
                navController.navigate(R.id.nav_error,bundle)
            }
        })

        navController.addOnDestinationChangedListener { _, destination, _ ->
            fab.visibility=View.GONE
            when(destination.id){
                R.id.nav_home->{
                    fab.visibility=View.VISIBLE
                    fab.setImageResource(android.R.drawable.ic_dialog_email)
                    fab.setOnClickListener { view ->
                        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show()
                    }
                }
                R.id.nav_slideshow->{
                    fab.visibility=View.VISIBLE
                    fab.setImageResource(android.R.drawable.ic_media_next)
                    fab.setOnClickListener { view ->
                        val bundle=Bundle()
                        bundle.putBoolean(Boolean::class.java.simpleName,true)
                        navController.navigate(R.id.action_nav_slideshow_self,bundle)
                    }
                }
            }
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
