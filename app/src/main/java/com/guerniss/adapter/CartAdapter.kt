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
import kotlinx.android.synthetic.main.cart_itemlist.view.*

class CartAdapter(private val context: Context, private val cartProductList: ArrayList<Product>) : RecyclerView.Adapter<CartAdapter.MyViewHolder>()  {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var image: ImageView = view.findViewById<ImageView>(R.id.image_view)
        var prod_name :TextView = view.findViewById<TextView>(R.id.prod_nametv)
        var priceTv : TextView = view.findViewById<TextView>(R.id.priceTv)
        var cartDeleteBtn = view.cartDeleteBtn
        var plusBtn = view.plusBtn
        var minusBtn = view.minusBtn
        var qentityEt = view.qentityEt
        var tvColorCode = view.tvColorCode

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cart_itemlist, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movies = cartProductList[position]
        //holder.image.setImageBitmap(Movies.image)

//        Log.e("AppConstant.imgUrl",""+AppConstant.imgUrl)
//        Log.e("AppConstant.image",""+AppConstant.productinfo!!.image)
        holder.tvColorCode.text = "Color code : "+movies.color_code
        Glide.with(context)
            .load(AppConstant.imgUrl+"/"+ movies.image)
            .into(holder.image)

        holder.prod_name.setText(movies.prod_name)
        holder.priceTv.text = (movies.unit_price.toInt()*movies.quentity).toString()

        holder.cartDeleteBtn.setOnClickListener {
            cartProductList.removeAt(position)
            notifyDataSetChanged()
        }

        holder.qentityEt.setText(movies.quentity.toString())

        movies.totalPrice = movies.unit_price.toInt()
        holder.qentityEt.setText(movies.quentity.toString())
        holder.plusBtn.setOnClickListener {
            movies.quentity = movies.quentity+1
            holder.qentityEt.setText(movies.quentity.toString())

            movies.totalPrice = movies.unit_price.toInt()*movies.quentity
            AppConstant.cartList[position].totalPrice = movies.totalPrice
            holder.priceTv.text = movies.totalPrice.toString()
        }

        holder.minusBtn.setOnClickListener {
            if(movies.totalPrice > 0){
                movies.quentity = movies.quentity-1
                holder.qentityEt.setText(movies.quentity.toString())

                movies.totalPrice = movies.totalPrice - movies.unit_price.toInt()
                holder.priceTv.text = movies.totalPrice.toString()
            }

        }

    }

    override fun getItemCount(): Int {
        return cartProductList.size
    }
}