package com.vjapp.catalogshowcase

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.vjapp.catalogshowcase.databinding.ActivityDetailBinding

//prova modifica per vedere se jenkins parte
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        (supportActionBar as ActionBar).setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        fun newInstance(context: Context):Intent {
            return Intent(context, DetailActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
    }

}
