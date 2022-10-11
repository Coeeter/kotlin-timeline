package com.example.slidablerecycleritem

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private var animals = listOf(
        "Dog",
        "Cat",
        "Sheep",
        "Chicken",
        "Eagle",
        "Goat",
        "Snake"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<RecyclerView>(R.id.recycler).apply {
            adapter = SlideAdapter(animals)
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }
}