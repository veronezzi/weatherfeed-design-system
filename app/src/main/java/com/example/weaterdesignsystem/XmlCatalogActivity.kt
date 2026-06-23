package com.example.weaterdesignsystem

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weaterdesignsystem.databinding.ActivityXmlCatalogBinding

class XmlCatalogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityXmlCatalogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnBack.setOnClickListener { finish() }
    }
}
