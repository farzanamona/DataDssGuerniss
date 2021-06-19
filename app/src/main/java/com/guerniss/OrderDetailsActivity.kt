package com.guerniss

import android.annotation.SuppressLint
import android.app.ActionBar
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Window
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.guerniss.adapter.CartAdapter
import com.guerniss.adapter.OrderListAdapter
import com.guerniss.model.LoginResponse
import com.guerniss.model.OrderSumitResponse
import com.guerniss.utils.*
import kotlinx.android.synthetic.main.activity_billing.*
import kotlinx.android.synthetic.main.activity_order_details.*
import kotlinx.android.synthetic.main.activity_payment.*
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class OrderDetailsActivity : AppCompatActivity() {
    private var context: Context? = null

    var pay_op_description = ""
    var order_ship_name = ""
    var order_email = ""
    var order_phone = ""
    var order_ship_address = ""
    var order_net_amount = ""
    var order_discount = ""
    var order_total_amount = ""
    var order_cust_id = ""
    var jsonArray = JSONArray()
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)

        context = this

        val layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        var cartAdapter = OrderListAdapter(context!!, AppConstant.cartList)
        recyclerViewOrder!!.layoutManager = layoutManager
        recyclerViewOrder!!.itemAnimator = DefaultItemAnimator()
        recyclerViewOrder.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL))
        recyclerViewOrder!!.adapter = cartAdapter



        var totalPrice = 0
        for ((index, value) in AppConstant.cartList.withIndex()) {
            var price = value.totalPrice*value.quentity
            totalPrice = totalPrice+price
            var jsonObject = JSONObject()
            jsonObject.put("od_prod_id",value.prod_id.toString())
            jsonObject.put("od_prod_qty",value.quentity.toString())
            jsonObject.put("od_prod_unit_price",value.unit_price.toString())
            jsonArray.put(jsonObject)
        }


        order_net_amount = totalPrice.toString()
        totalItemPrice.text = totalPrice.toString()

        var count = custMemberShipdiscount.text.toString().toInt()

        //var discount = (10 / totalPrice) * 100
        var discount = (count * totalPrice) / 100
        order_discount = discount.toString()
        discountAmount.text = discount.toString()

        totalAmount.text = (totalPrice - discount).toString()
        order_total_amount = totalAmount.text.toString()

        order_cust_id = PersistData.getLoginData(context!!).user.id.toString()

        btnOrderNow.setOnClickListener {
            showDialogcustomerInfo()
        }

    }

    private fun showDialogcustomerInfo() {

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.activity_billing)

        var etName = dialog.etName
        var etEmail = dialog.etEmail
        var etPhoneNumber = dialog.etPhoneNumber
        var etAddress = dialog.etAddress

        etName.setText(PersistData.getLoginData(context!!).user.name)
        etEmail.setText(PersistData.getLoginData(context!!).user.email)
        etPhoneNumber.setText(PersistData.getLoginData(context!!).user.phone)
        etAddress.setText(PersistData.getLoginData(context!!).user.address)


        val btnPay = dialog.btnPay

        btnPay.setOnClickListener {
             order_ship_name = etName.text.toString()
             order_email = etEmail.text.toString()
             order_phone = etPhoneNumber.text.toString()
             order_ship_address = etAddress.text.toString()

            if (TextUtils.isEmpty(order_ship_name)){
                Toast.makeText(context,"Input Name",Toast.LENGTH_SHORT).show()
            }else if (TextUtils.isEmpty(order_email)){
                Toast.makeText(context,"Input email",Toast.LENGTH_SHORT).show()
            }else if (TextUtils.isEmpty(order_phone)){
                Toast.makeText(context,"Input phone number",Toast.LENGTH_SHORT).show()
            }else if (TextUtils.isEmpty(order_ship_address)){
                Toast.makeText(context,"Input Address",Toast.LENGTH_SHORT).show()
            }else{
                dialog.dismiss()
                showDialogPaymentMethod()
            }


        }

        dialog.show()
        val window: Window = dialog.getWindow()!!
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT)
    }

    private fun showDialogPaymentMethod() {

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.activity_payment)

//        val etName = dialog.etName
//        val etEmail = dialog.etEmail
//        val etPhoneNumber = dialog.etPhoneNumber
//        val etAddress = dialog.etAddress
        val lincash = dialog.lincash
        val linBkash = dialog.linBkash
        val linNogod = dialog.linNogod
        val linRocket = dialog.linRocket
        val lincard = dialog.lincard

        lincash.setOnClickListener {
            pay_op_description = "COD"
            dialog.dismiss()
            orderSubmit()
        }

        linBkash.setOnClickListener {
            pay_op_description = "Bkash"
            dialog.dismiss()
            orderSubmit()
        }

        linNogod.setOnClickListener {
            pay_op_description = "Nagad"
            dialog.dismiss()
            orderSubmit()
        }

        linRocket.setOnClickListener {
            pay_op_description = "Rocket"
            dialog.dismiss()
            orderSubmit()
        }

        lincard.setOnClickListener {
            pay_op_description = "Debit/Credit card"
            dialog.dismiss()
            orderSubmit()

        }

        dialog.show()
        val window: Window = dialog.getWindow()!!
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT)
    }

    fun orderSubmit() {
        Log.e("jsonArray",""+jsonArray.toString())

        Log.e("customerid",""+order_cust_id)
        Log.e("pay_op_description",""+pay_op_description)
        Log.e("order_ship_name",""+order_ship_name)
        Log.e("order_email",""+order_email)
        Log.e("order_discount",""+order_discount)
        Log.e("order_net_amount",""+order_net_amount)
        Log.e("order_ship_address",""+order_ship_address)
        Log.e("order_total_amount",""+order_total_amount)


        if (!this!!.context?.let { NetInfo.isOnline(it) }!!)
        {
            context?.let { AlertMessage.showMessage(it, "Alert!", "No internet connection!") }
        }

        val pd = ProgressDialog(context)
        pd.setMessage("Loading....")
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        pd.show()
        var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(ApiJava.BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var api = retrofit.create(Api::class.java)

        val call: Call<OrderSumitResponse> = api.orderCheckOut(pay_op_description.trim(),order_ship_name.trim(),order_email.trim(),order_phone.trim(),
        order_ship_address.trim(),order_net_amount,order_discount,order_total_amount,order_cust_id,
            jsonArray
        )

        call.enqueue(object : Callback<OrderSumitResponse?> {

            override fun onResponse(call: Call<OrderSumitResponse?>, response: Response<OrderSumitResponse?>) {
                pd.dismiss()
                val result = response.body()
                Log.e("Response",""+result)
                if(result!=null){
                    //Toast.makeText(context,""+result.message,Toast.LENGTH_SHORT).show()
                    startActivity(Intent(context,OrderListActivity::class.java))
                    finish()
                }

            }

            override fun onFailure(call: Call<OrderSumitResponse?>, t: Throwable) {
                pd.dismiss()
            }
        })

    }



}
