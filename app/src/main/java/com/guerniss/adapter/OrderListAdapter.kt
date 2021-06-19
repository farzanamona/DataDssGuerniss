package com.guerniss.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.guerniss.R
import com.guerniss.model.Product
import com.guerniss.utils.AppConstant
import kotlinx.android.synthetic.main.order_item_listview.view.*

class OrderListAdapter(private val context: Context, private val cartProductList: ArrayList<Product>) : RecyclerView.Adapter<OrderListAdapter.MyViewHolder>()  {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var prodName = view.prodName
        var prodQty = view.prodQty
        var prodPrice = view.prodPrice

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.order_item_listview, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movies = cartProductList[position]

        holder.prodName.text = movies.prod_name
        holder.prodQty.text = movies.quentity.toString()
        holder.prodPrice.text = (movies.unit_price.toInt()*movies.quentity).toString()
        AppConstant.cartList[position].totalPrice = (movies.unit_price.toInt()*movies.quentity)

    }

    override fun getItemCount(): Int {
        return cartProductList.size
    }
}