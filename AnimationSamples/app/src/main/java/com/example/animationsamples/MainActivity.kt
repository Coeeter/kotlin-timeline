package com.example.animationsamples

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.interpolator.view.animation.FastOutSlowInInterpolator

class MainActivity : AppCompatActivity() {
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<ImageView>(R.id.image).setOnClickListener {
            if (count != 15) {
                animateImage(it as ImageView)
                count += 1
                findViewById<TextView>(R.id.count).text = "Count: $count"
                return@setOnClickListener
            }
            Intent(this, SecondActivity::class.java).run {
                val options = ActivityOptions.makeSceneTransitionAnimation(
                    this@MainActivity,
                    it,
                    "image"
                )
                startActivity(this, options.toBundle())
            }
        }
    }

    private fun animateImage(imageView: ImageView) {
        val animation = AnimationUtils.loadAnimation(this, R.anim.raise)
        animation.interpolator = FastOutSlowInInterpolator()
        imageView.startAnimation(animation)
    }
}