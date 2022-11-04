package com.example.soundlightrgb.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.soundlightrgb.R
import com.example.soundlightrgb.databinding.ActivitySetupBinding

class SetupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySetupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetupBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}