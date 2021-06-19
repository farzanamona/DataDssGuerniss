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
import com.guerniss.SingleOderDeatilsActivity
import com.guerniss.model.Data
import com.guerniss.model.LipModel
import com.guerniss.model.OrderModel
import com.guerniss.utils.AppConstant
import kotlinx.android.synthetic.main.item_orde_list.view.*

class OrderAdapter (private val context : Context, private val orderlistitem: ArrayList<Data>) : RecyclerView.Adapter<OrderAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var orderNo: TextView = view.findViewById<TextView>(R.id.orderNo)
        var total_amount: TextView = view.findViewById<TextView>(R.id.total_amount)
        var payment_status: TextView = view.findViewById<TextView>(R.id.payment_status)
        var view = view.view


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_orde_list, parent, false)


        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val Movies = orderlistitem[position]
        holder.orderNo.setText(Movies.order_no)
        holder.total_amount.setText(Movies.order_total_amount)
        if (Movies.order_state.equals("0")){
            holder.payment_status.setText("Not Approve")
        }else if (Movies.order_state.equals("1")){
            holder.payment_status.setText("Approve")
        }

        holder.view.setOnClickListener {
            context.startActivity(Intent(context,SingleOderDeatilsActivity::class.java))
            AppConstant.orderData = Movies
        }
        //holder.paymentType.setText(Movies.order_payment_status)


    }

    override fun getItemCount(): Int {
        return orderlistitem.size
    }
}