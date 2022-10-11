package com.example.timeline

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(
    private val userList: List<User>
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val topLine: View = view.findViewById(R.id.topLine)
        val bottomLine: View = view.findViewById(R.id.bottomLine)
        val ageView: TextView = view.findViewById(R.id.age)
        val nameView: TextView = view.findViewById(R.id.name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UserViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.recycler_user_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.topLine.visibility = View.VISIBLE
        holder.bottomLine.visibility = View.VISIBLE
        if (position == 0) {
            holder.topLine.visibility = View.INVISIBLE
        }
        if (position == userList.lastIndex) {
            holder.bottomLine.visibility = View.INVISIBLE
        }
        holder.nameView.text = userList[position].name
        holder.ageView.text = userList[position].age.toString()
    }

    override fun getItemCount() = userList.size

}