package com.example.myapplication.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MainActivity
import com.example.myapplication.model.AffairsModel

class AffairsAdapter (activity: MainActivity) :
    RecyclerView.Adapter<AffairsAdapter.ViewHolder>() {
    private var affairsList: List<AffairsModel>? = null
    private val activity: MainActivity

    init {
        this.activity = activity
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(com.example.myapplication.R.layout.design, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = affairsList!![position]
        holder.affairs.id = item.id
        holder.affairs.text = item.affairs
        holder.affairs.isChecked = item.status
        holder.affairs.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                holder.affairs.paintFlags = holder.affairs.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                holder.affairs.paintFlags = holder.affairs.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }
    }

    override fun getItemCount(): Int {
        return affairsList!!.size
    }

    class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        var affairs: CheckBox

        init {
            affairs = view.findViewById<CheckBox>(com.example.myapplication.R.id.checkBox)
        }
    }
    fun setList(list: List<AffairsModel>){
        affairsList=list
        notifyDataSetChanged()
    }
}