package com.guerniss

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.guerniss.model.Product
import com.guerniss.utils.AppConstant
import kotlinx.android.synthetic.main.activity_product_details.*
import kotlinx.android.synthetic.main.item_color.view.*

class ProductDetailsActivity : AppCompatActivity() {
    private var context: Context? = null
    private var btn: Button? = null

    var productList = arrayListOf<Product>()
    private var mAdapter: ColorAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)
        context = this
        btn = findViewById<Button>(R.id.backBtn)
        btn!!.setOnClickListener{
            onBackPressed()
        }

        productList.clear()
        for ((index, value) in AppConstant.productListForColor.withIndex()) {
            if(value.prod_name.equals(AppConstant.productinfo.prod_name)){
                productList.add(value)
            }
        }
        Log.e("colorsiz","color: "+productList.size)

        val gridLayoutManager = GridLayoutManager(applicationContext, 5)
        mAdapter = ColorAdapter(
            context!!,
            productList
        )
        recyclerColor!!.layoutManager = gridLayoutManager
        recyclerColor!!.itemAnimator = DefaultItemAnimator()
        recyclerColor!!.adapter = mAdapter

        cartBtn.setOnClickListener {


                if(AppConstant.productinfo.color_code==null){
                    if (AppConstant.cartList.size>0){

                        try {
                            for ((index, value) in AppConstant.cartList.withIndex()) {

                                //throw ConcurrentModificationException()

                                if (AppConstant.productinfo.prod_name.equals(AppConstant.cartList[index].prod_name)){
                                    Log.e("appProd","appProd  "+AppConstant.productinfo.prod_name)
                                    Log.e("prod","prod  "+value.prod_name)

                                    var count = value.quentity
                                    var count2 = AppConstant.productinfo.quentity

                                    //AppConstant.cartList[index].image = AppConstant.productinfo.image

                                    var totalcount = count+1
                                    AppConstant.cartList[index].quentity = totalcount

                                    //var price = value.totalPrice
                                    var price2 = AppConstant.productinfo.unit_price.toInt()

                                    var totalprice = price2*totalcount
                                    AppConstant.cartList[index].totalPrice = totalprice

                                    //   throw ConcurrentModificationException()
                                    break
                                    // throw ConcurrentModificationException()

                                }else{
                                    AppConstant.productinfo.quentity = 1
                                    AppConstant.productinfo.totalPrice = AppConstant.productinfo.unit_price.toInt()
                                    AppConstant.cartList.add(AppConstant.productinfo)
                                }
                            }
                        } catch ( exception: ConcurrentModificationException) {

                        } catch ( throwable : Throwable) {

                        }
                    }else{
                        AppConstant.productinfo.quentity = 1
                        AppConstant.productinfo.totalPrice = AppConstant.productinfo.unit_price.toInt()
                        AppConstant.cartList.add(AppConstant.productinfo)
                    }
                }


                if(AppConstant.productinfo.color_code!=null){
                    if (AppConstant.cartList.size>0){
                        try {
                            for ((index, value) in AppConstant.cartList.withIndex()) {

                                // throw ConcurrentModificationException()

                                if (AppConstant.productinfo.color_code.equals(AppConstant.cartList[index].color_code) &&
                                    AppConstant.productinfo.prod_name.equals(AppConstant.cartList[index].prod_name)){
                                    Log.e("appProd","appProd  "+AppConstant.productinfo.prod_name)
                                    Log.e("prod","prod  "+value.prod_name)

                                    var count = value.quentity
                                    var count2 = AppConstant.productinfo.quentity

                                    var totalcount = count+1
                                    AppConstant.cartList[index].quentity = totalcount
                                    //AppConstant.cartList[index].image = AppConstant.productinfo.image

                                    //var price = value.totalPrice
                                    var price2 = AppConstant.productinfo.unit_price.toInt()

                                    var totalprice = price2*totalcount
                                    AppConstant.cartList[index].totalPrice = totalprice

//                            throw ConcurrentModificationException()
                                    break
                                    //throw ConcurrentModificationException()
                                }else{
                                    AppConstant.productinfo.quentity = 1
                                    AppConstant.productinfo.totalPrice = AppConstant.productinfo.unit_price.toInt()
                                    AppConstant.cartList.add(AppConstant.productinfo)
                                }
                            }
                        } catch ( exception: ConcurrentModificationException) {

                        } catch ( throwable : Throwable) {

                        }
                    }else{
                        AppConstant.productinfo.quentity = 1
                        AppConstant.productinfo.totalPrice = AppConstant.productinfo.unit_price.toInt()
                        AppConstant.cartList.add(AppConstant.productinfo)
                    }
                }

            Toast.makeText(context,"Cart Inserted Successfully",Toast.LENGTH_SHORT).show()
           // startActivity(Intent(context, CartActivity::class.java))
           // finish()
        }

        dataSet()

    }

    private fun dataSet() {

        tvProductName.text = AppConstant.productinfo!!.prod_name
        tvPrice.text = AppConstant.productinfo!!.unit_price
        tvdetails.text = AppConstant.productinfo!!.prod_short_desc
        Glide.with(context as ProductDetailsActivity)
            .load(AppConstant.imgUrl+"/"+AppConstant.productinfo!!.image)
            .into(prod_details_image)
    }


    inner class ColorAdapter(private val context :Context, private val allProductlist: ArrayList<Product>) : RecyclerView.Adapter<ColorAdapter.MyViewHolder>() {

        inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            var card = view.color1

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_color, parent, false)


            return MyViewHolder(itemView)

        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val product = allProductlist[position]
            if(!TextUtils.isEmpty(product.hex_color_code) || product.hex_color_code!=null){
                holder.card.setBackgroundColor(Color.parseColor(product.hex_color_code))
            }

            holder.card.setOnClickListener {
                AppConstant.productinfo = product
                dataSet()
            }

        }

        override fun getItemCount(): Int {
            return allProductlist.size
        }


    }

}
