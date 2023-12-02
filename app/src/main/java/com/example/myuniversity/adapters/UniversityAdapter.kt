package com.example.myuniversity.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myuniversity.R
import com.example.myuniversity.pojo.University

/**
 * @author Belitski Marat
 * @date  20.11.2023
 * @project MyUniversity
 */
class UniversityAdapter() : RecyclerView.Adapter<UniversityAdapter.MyHolderUniversity>() {

    var listUniversity: List<University> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var myClickListener: MyClickListener? = null

    class MyHolderUniversity(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textCountry: TextView = itemView.findViewById(R.id.textCountry)
        val textName: TextView = itemView.findViewById(R.id.textName)
        val textDomain: TextView = itemView.findViewById(R.id.textDomain)
        val clickConstraint: View = itemView.findViewById(R.id.constraint4)
        val clickFavoriteStar: ImageView = itemView.findViewById(R.id.favoriteStar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolderUniversity {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycle_university, parent, false)
        return MyHolderUniversity(view)
    }

    override fun getItemCount(): Int {
        return listUniversity.size
    }

    private var starOn = android.R.drawable.btn_star_big_on
    private var starOff = android.R.drawable.btn_star_big_off

    override fun onBindViewHolder(holder: MyHolderUniversity, position: Int) {
        val isFavorite = listUniversity[position].isFavorite

        if (isFavorite) {
            holder.clickFavoriteStar.setImageResource(starOn)
        } else {
            holder.clickFavoriteStar.setImageResource(starOff)
        }

        holder.textCountry.text = listUniversity[position].country
        holder.textName.text = listUniversity[position].name
        holder.textDomain.text = listUniversity[position].domains?.get(0)

        holder.clickConstraint.setOnClickListener {
            myClickListener?.myClick(listUniversity[position])
        }

        holder.clickFavoriteStar.setOnClickListener {
            val favorite = listUniversity[position].isFavorite

            myClickListener?.myClickFavorite(listUniversity[position])
            if (favorite) {
                holder.clickFavoriteStar.setImageResource(starOff)
            } else {
                holder.clickFavoriteStar.setImageResource(starOn)
            }
        }
    }

    interface MyClickListener {
        fun myClick(university: University);
        fun myClickFavorite(university: University);
    }
}