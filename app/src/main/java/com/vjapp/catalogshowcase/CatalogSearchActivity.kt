package com.vjapp.catalogshowcase

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.vjapp.catalogshowcase.databinding.ActivityMainBinding
import com.vjapp.catalogshowcase.domain.model.SearchTypes
import com.vjapp.catalogshowcase.presentation.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CatalogSearchActivity : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.let {
            it.setDisplayShowHomeEnabled(true)
            it.setLogo(R.mipmap.ic_launcher)
            it.setDisplayUseLogoEnabled(true)
        }
        binding.navView.setOnNavigationItemSelectedListener(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_highest -> mainViewModel.getCatalog(SearchTypes.HIGHEST)
            R.id.navigation_lowest -> mainViewModel.getCatalog(SearchTypes.LOWEST)
            R.id.navigation_latest -> mainViewModel.getCatalog(SearchTypes.LATEST)
        }

        return true
    }

}
