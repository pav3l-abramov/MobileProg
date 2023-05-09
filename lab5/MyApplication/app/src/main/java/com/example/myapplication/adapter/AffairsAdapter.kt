package com.example.myapplication.adapter

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MainActivity
import com.example.myapplication.model.AffairsModel

class AffairsAdapter (activity: MainActivity) :
    RecyclerView.Adapter<AffairsAdapter.ViewHolder>() {
    private var affairsList: MutableList<AffairsModel>? = null
    private val activity: MainActivity

    init {
        this.activity = activity
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(com.example.myapplication.R.layout.design, parent, false)
        return ViewHolder(itemView)
    }
    class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        var affairs: CheckBox
        var delBtn: ImageButton
        var itemNav: TextView

        init {
            affairs = view.findViewById<CheckBox>(com.example.myapplication.R.id.checkBox)
            delBtn = view.findViewById<ImageButton>(com.example.myapplication.R.id.searchImageButton)
            itemNav = view.findViewById<TextView>(com.example.myapplication.R.id.itemNav)
        }
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
        holder.delBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                deleteItem(holder.adapterPosition)
            }
        })
        holder.itemNav.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val bundle = Bundle()
                bundle.putString("text", holder.affairs.text.toString())
                //Fragment.findNavController().navigate(R.id.action_tasksListFragment_to_detailFragment, bundle)

                     Navigation.findNavController(view!!).navigate(com.example.myapplication.R.id.detailFragment, bundle)

            }
        })
    }

    override fun getItemCount(): Int {
        return affairsList!!.size
    }

    fun setList(list: MutableList<AffairsModel>){
        affairsList=list
        notifyDataSetChanged()
    }
    fun deleteItem(position: Int) {
        affairsList!!.removeAt(position)
        notifyItemRemoved(position)
    }
}