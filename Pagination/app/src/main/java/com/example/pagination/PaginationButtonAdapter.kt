package com.example.pagination

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PaginationButtonAdapter(
    private val listener: Listener
) : RecyclerView.Adapter<PaginationButtonAdapter.ViewHolder>() {
    var buttonList: List<String> = listOf()
    var selected: String = "1"
    var nextButtonDisabled = false
    var prevButtonDisabled = true

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val text: TextView = view.findViewById(R.id.pageButton)
        val bg: View = view.findViewById(R.id.selected)
    }

    interface Listener {
        fun nextPage()
        fun prevPage()
        fun setCurrentPage(nextPage: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.recycler_pagination_button_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.text.text = buttonList[position]
        holder.bg.visibility = View.INVISIBLE
        if (selected == buttonList[position]) {
            holder.bg.visibility = View.VISIBLE
        }
        holder.text.setOnClickListener {
            when(buttonList[position]) {
                "..." -> Unit
                ">" -> listener.nextPage()
                "<" -> listener.prevPage()
                else -> listener.setCurrentPage(buttonList[position].toInt())
            }
        }
        (holder.text.parent as View).visibility = View.VISIBLE
        if (prevButtonDisabled && buttonList[position] == "<") {
            (holder.text.parent as View).visibility = View.GONE
        }
        if (nextButtonDisabled && buttonList[position] == ">") {
            (holder.text.parent as View).visibility = View.GONE
        }
    }

    override fun getItemCount() = buttonList.size

}