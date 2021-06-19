package com.guerniss.adapter


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
import com.guerniss.ProductDetailsActivity
import com.guerniss.R
import com.guerniss.model.Product
import com.guerniss.utils.AppConstant


class HomeAdapter(private val context :Context, private val allProductlist: ArrayList<Product>, private val imgUrl :String ) : RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {

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
        //holder.image.setImageBitmap(Movies.image)
        Glide.with(context)
            .load(imgUrl+"/"+product.image)
            .into(holder.image)

       // var databaseHelper = DatabaseHelper(context)

       // databaseHelper.addEntry(product.prod_name,null)

//        Glide.with(context)
//            .asBitmap()
//            .load(imgUrl+"/"+product.image)
//            .into(object : CustomTarget<Bitmap>(){
//                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                    holder.image.setImageBitmap(resource)
////                    if(resource!=null){
////                        databaseHelper.addEntry(product.prod_name,DbBitmapUtility.getBytes(resource))
////                    }
//
//
//                }
//                override fun onLoadCleared(placeholder: Drawable?) {
//                    // this is called when imageView is cleared on lifecycle call or for
//                    // some other reason.
//                    // if you are referencing the bitmap somewhere else too other than this imageView
//                    // clear it here as you can no longer have the bitmap
//                }
//            })

        holder.card.setOnClickListener{
            AppConstant.productinfo = product
            AppConstant.imgUrl = imgUrl
            context.startActivity(Intent(context, ProductDetailsActivity::class.java))

        }
    }

    override fun getItemCount(): Int {
        return allProductlist.size
    }


}




