package com.guerniss.adapter


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.guerniss.DB.DatabaseHelper
import com.guerniss.DB.DbBitmapUtility
import com.guerniss.DB.LocalDBProduct
import com.guerniss.ProductDetailsActivity
import com.guerniss.R
import com.guerniss.model.Product
import com.guerniss.utils.AlertMessage
import com.guerniss.utils.AppConstant
import kotlinx.android.synthetic.main.activity_main.*


class LocalDataHomeAdapter(private val context :Context, val activity: Activity,private val allProductlist: ArrayList<LocalDBProduct>) : RecyclerView.Adapter<LocalDataHomeAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var image: ImageView = view.findViewById<ImageView>(R.id.image_view)
        var card: CardView = view.findViewById<CardView>(R.id.cardview)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.home_card_itemlist, parent, false)


        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val product = allProductlist[position]

        holder.image.setImageBitmap(product.image)
        holder.card.setOnClickListener{
            AlertMessage.showMessageFinishActivity(context, activity,"Alert","No internet connection!")
        }
    }

    override fun getItemCount(): Int {
        return allProductlist.size
    }


}




