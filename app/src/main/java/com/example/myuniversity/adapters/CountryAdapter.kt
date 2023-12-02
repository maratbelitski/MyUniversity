package com.example.myuniversity.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.myuniversity.R

/**
 * @author Belitski Marat
 * @date  21.11.2023
 * @project MyUniversity
 */
class CountryAdapter : BaseAdapter() {

    var listCountry: MutableList<String> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getCount(): Int {
        return listCountry.size
    }

    override fun getItem(position: Int): Any {
        return listCountry[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        @SuppressLint("ViewHolder")
        val view: View =
            LayoutInflater.from(parent?.context).inflate(R.layout.item_spinner, parent, false)

        val textSpinner: TextView = view.findViewById(R.id.itemSpinner)
        textSpinner.text = listCountry[position]
        return view
    }

    @SuppressLint("ResourceAsColor")
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view: View =
            LayoutInflater.from(parent?.context).inflate(R.layout.spinner_dropdown, parent, false)
        val textSpinner: TextView = view.findViewById(R.id.spinnerDropdown)

        textSpinner.text = listCountry[position]
        if (position==0){
            textSpinner.setBackgroundResource(R.color.white)
            textSpinner.setTextColor(Color.BLACK)
        }
        return view
    }
}