package com.vjapp.catalogshowcase

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
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
