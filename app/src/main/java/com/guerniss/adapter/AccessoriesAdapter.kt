package com.guerniss.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.guerniss.ProductDetailsActivity
import com.guerniss.R
import com.guerniss.model.AccessoriesModel

class AccessoriesAdapter (private val context : Context, private val accessoriesProductList: ArrayList<AccessoriesModel>) : RecyclerView.Adapter<AccessoriesAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var image: ImageView = view.findViewById<ImageView>(R.id.image_view)
        var card: CardView = view.findViewById<CardView>(R.id.cardview)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.home_card_itemlist, parent, false)


        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val Movies = accessoriesProductList[position]
        holder.image.setImageBitmap(Movies.image)
        holder.card.setOnClickListener{
            context.startActivity(Intent(context, ProductDetailsActivity::class.java))

        }


    }

    override fun getItemCount(): Int {
        return accessoriesProductList.size
    }
}