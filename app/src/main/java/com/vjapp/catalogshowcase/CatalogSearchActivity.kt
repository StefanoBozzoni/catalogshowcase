package com.vjapp.catalogshowcase

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.vjapp.catalogshowcase.presentation.MainViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class CatalogSearchActivity : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener{

    val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onSupportNavigateUp(): Boolean {
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return true
    }
}
