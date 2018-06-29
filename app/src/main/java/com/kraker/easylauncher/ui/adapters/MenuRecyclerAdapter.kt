package com.kraker.easylauncher.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kraker.easylauncher.R
import com.kraker.easylauncher.domain.data.MenuObject
import kotlinx.android.synthetic.main.menu_cell.view.*

class MenuRecyclerAdapter(private var menuObjectList: List<MenuObject>) : RecyclerView.Adapter<MenuRecyclerAdapter.MenuViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.menu_cell, parent, false)
        return MenuViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val component = menuObjectList[position]
        holder.setUpAppComponent(component)
    }

    override fun getItemCount() : Int{
        return menuObjectList.size
    }

    class MenuViewHolder(var view: View) : RecyclerView.ViewHolder(view){

        fun setUpAppComponent(menuItem : MenuObject){
            view.itemName.text = menuItem.itemName
            view.itemLogo.setImageDrawable(menuItem.icon)
        }
    }

    fun updateOwnerList(menuObjectList: List<MenuObject>){
        this.menuObjectList = menuObjectList
        notifyDataSetChanged()
    }

}