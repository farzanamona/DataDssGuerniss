//package com.guerniss.adapter
//
//
//import android.content.Context
//import android.content.Intent
//import android.graphics.Color
//import android.text.TextUtils
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import androidx.cardview.widget.CardView
//import androidx.recyclerview.widget.RecyclerView
//import com.guerniss.R
//import com.guerniss.model.Product
//import kotlinx.android.synthetic.main.item_color.view.*
//
//
//class ColorAdapter(private val context :Context, private val allProductlist: ArrayList<Product>) : RecyclerView.Adapter<ColorAdapter.MyViewHolder>() {
//
//    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        var card = view.color1
//
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_color, parent, false)
//
//
//        return MyViewHolder(itemView)
//
//    }
//
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        val product = allProductlist[position]
//        if(!TextUtils.isEmpty(product.hex_color_code) || product.hex_color_code!=null){
//            holder.card.setBackgroundColor(Color.parseColor(product.hex_color_code))
//        }
//
//    }
//
//    override fun getItemCount(): Int {
//        return allProductlist.size
//    }
//
//
//}
//
//
//
//
