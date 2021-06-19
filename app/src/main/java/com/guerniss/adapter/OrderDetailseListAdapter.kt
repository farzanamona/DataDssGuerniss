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
import com.guerniss.model.DataDetils
import com.guerniss.model.Product
import com.guerniss.utils.AppConstant
import kotlinx.android.synthetic.main.order_item_listview.view.*

class OrderDetailseListAdapter(private val context: Context, private val cartProductList: ArrayList<DataDetils>) : RecyclerView.Adapter<OrderDetailseListAdapter.MyViewHolder>()  {
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

        holder.prodName.text = movies.product.prod_name
        holder.prodQty.text = movies.od_prod_qty
        holder.prodPrice.text = movies.od_net_prod_price
//        AppConstant.cartList[position].totalPrice = (movies.product.unit_price.toInt()*movies.product.quentity)

    }

    override fun getItemCount(): Int {
        return cartProductList.size
    }
}