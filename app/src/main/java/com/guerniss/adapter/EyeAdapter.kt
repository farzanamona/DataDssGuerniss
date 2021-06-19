package com.guerniss.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.guerniss.CatgWiseProductDetailsActivity
import com.guerniss.ProductDetailsActivity
import com.guerniss.R
import com.guerniss.model.Product
import com.guerniss.utils.AppConstant
import com.guerniss.utils.CatgConstant

class EyeAdapter (private val context : Context, private val eyeProductlist: ArrayList<Product>, private val imgUrl :String) : RecyclerView.Adapter<EyeAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var image: ImageView = view.findViewById<ImageView>(R.id.image_view)
        var card: CardView = view.findViewById<CardView>(R.id.cardview)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.home_card_itemlist, parent, false)


        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val product = eyeProductlist[position]
        //holder.image.setImageBitmap(Movies.image)

        Log.e("imgUrl","url: "+imgUrl+"/"+product.image)
        Glide.with(context)
            .load(imgUrl+"/"+product.image)
            .into(holder.image)

        holder.card.setOnClickListener{
            AppConstant.productinfo = product
//            CatgConstant.singleProductinfo = product
//            CatgConstant.imgUrl = imgUrl
            AppConstant.imgUrl = imgUrl
            context.startActivity(Intent(context, ProductDetailsActivity::class.java))

        }

    }

    override fun getItemCount(): Int {
        return eyeProductlist.size
    }
}