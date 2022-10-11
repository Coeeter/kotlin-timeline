package com.example.slidablerecycleritem

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SlideAdapter(
    private var animals: List<String>,
) : RecyclerView.Adapter<SlideAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.text)
        val btn: TextView = view.findViewById(R.id.deleteButton)
        val bottomBorder: View = view.findViewById(R.id.bottomBorder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.recycler_item,
                parent,
                false
            )
        )

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.animate().x(0f).setDuration(200).start()
        holder.bottomBorder.visibility = View.INVISIBLE
        if (position == animals.lastIndex) {
            holder.bottomBorder.visibility = View.VISIBLE
        }
        holder.textView.text = animals[position]
        holder.textView.setOnTouchListener(
            provideOnTouchListener(holder, position)
        )
        holder.btn.setOnClickListener {
            deleteItem(holder, position)
        }
    }

    override fun getItemCount() = animals.size

    private fun deleteItem(holder: ViewHolder, position: Int) {
        holder.textView.animate()
            .x(-holder.textView.width.toFloat())
            .setDuration(200)
            .start()
        animals = animals.toMutableList().apply {
            removeAt(position)
        }
        notifyItemRemoved(position)
        notifyItemRangeChanged(0, animals.size)
    }

    private fun provideOnTouchListener(holder: ViewHolder, position: Int): (View, MotionEvent) -> Boolean {
        var startX = 0f
        var dx = 0f
        var showBtn = false
        var close = false
        var delete = false

        return { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    startX = event.rawX
                    dx = view.x - startX
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    var newX = event.rawX + dx
                    val differenceInX = event.rawX - startX
                    if (differenceInX < 0 && differenceInX < holder.btn.width.toFloat()) {
                        showBtn = true
                    }
                    if (differenceInX > 0) {
                        close = true
                    }
                    newX = newX.coerceAtLeast(-holder.btn.width.toFloat())
                    newX = newX.coerceAtMost(0f)
                    if (-differenceInX > holder.btn.width) {
                        newX = event.rawX + dx
                        delete = true
                    }
                    view.animate()
                        .x(newX)
                        .setDuration(0)
                        .start()
                    true
                }
                MotionEvent.ACTION_UP -> {
                    startX = 0f
                    dx = 0f
                    if (showBtn) {
                        view.animate()
                            .x(-holder.btn.width.toFloat())
                            .setDuration(200)
                            .start()
                    }
                    showBtn = false
                    if (close) {
                        view.animate()
                            .x(0f)
                            .setDuration(200)
                            .start()
                    }
                    close = false
                    if (delete) {
                        deleteItem(holder, position)
                    }
                    delete = false
                    true
                }
                else -> false
            }
        }
    }

}