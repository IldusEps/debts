package com.ildus.debts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class DeptsRecyclerAdapter(private val items: List<Map<String, Any>>) :
    RecyclerView.Adapter<DeptsRecyclerAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val view : ConstraintLayout = itemView.findViewById(R.id.debt)
        val count: TextView = itemView.findViewById(R.id.count)
        val whoDept: TextView = itemView.findViewById(R.id.whoDept)

        var index: Int = 999
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_debt, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (items[position]["whoDept"].toString() != "") {
            holder.count.text = items[position]["count"].toString()
            holder.whoDept.text = items[position]["whoDept"].toString()
//            holder.index = items[position]["position"].toString()
        } else {
            holder.view.visibility = View.GONE
        }
    }

    override fun getItemCount() = items.size
}