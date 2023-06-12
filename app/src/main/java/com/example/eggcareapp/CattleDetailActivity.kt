package com.example.eggcareapp

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.eggcareapp.databinding.ActivityCattleDetailBinding

class CattleDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCattleDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCattleDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar!!.apply {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_back)
        }
        supportActionBar!!.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(
                    this,
                    R.color.white
                )
            )
        )
        supportActionBar!!.title = intent.getStringExtra("name")

        setValuestoViews()
    }

    private fun setValuestoViews() {
        binding.tvName.text = intent.getStringExtra("name")
        binding.tvAmount.text = intent.getStringExtra("amount")
        binding.tvAge.text = intent.getStringExtra("age")
        binding.tvType.text = intent.getStringExtra("type")
        binding.tvFeedTime.text = intent.getStringExtra("feedtime")
        binding.tvCleanTime.text = intent.getStringExtra("cleantime")
        val imageUrl = intent.getStringExtra("image")
        Glide.with(this)
            .load(imageUrl)
            .into(binding.image)
    }
}