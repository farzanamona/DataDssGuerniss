package com.guerniss

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.guerniss.adapter.OrderAdapter
import com.guerniss.adapter.OrderDetailseListAdapter
import com.guerniss.adapter.OrderListAdapter
import com.guerniss.model.Data
import com.guerniss.model.DataDetils
import com.guerniss.model.OrderDetailsRespomse
import com.guerniss.model.OrderListResponse
import com.guerniss.utils.*
import kotlinx.android.synthetic.main.activity_single_oder_deatils.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class SingleOderDeatilsActivity : AppCompatActivity() {
    private var context: Context? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_oder_deatils)
        context=this
        initUi()
    }
    @SuppressLint("WrongConstant")
    private fun initUi() {
        setSupportActionBar(toolbar)
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar()!!.setDisplayShowHomeEnabled(true);

        val actionBar = supportActionBar

        // Set toolbar title/app title
        actionBar!!.title = "Order Details"
        // set categories Layout

        totalItemPrice.text = AppConstant.orderData.order_net_amount
        discountAmount.text = AppConstant.orderData.order_discount
        totalAmount.text = AppConstant.orderData.order_total_amount

        getOrderDetails()

    }


    fun getOrderDetails() {

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

        val call: Call<OrderDetailsRespomse> = api.getorder_details(AppConstant.orderData.order_id.toString())
        // val call: Call<OrderListResponse> = api.getAllOrderList("142")

        call.enqueue(object : Callback<OrderDetailsRespomse?> {

            @SuppressLint("WrongConstant")
            override fun onResponse(call: Call<OrderDetailsRespomse?>, response: Response<OrderDetailsRespomse?>) {
                pd.dismiss()
                val result = response.body()
                Log.e("Response",""+result)
                if(result!=null){
                    val layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
                    var cartAdapter = OrderDetailseListAdapter(context!!, result.data as ArrayList<DataDetils>)
                    recyclerViewOrder!!.layoutManager = layoutManager
                    recyclerViewOrder!!.itemAnimator = DefaultItemAnimator()
                    recyclerViewOrder.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL))
                    recyclerViewOrder!!.adapter = cartAdapter
                }

            }

            override fun onFailure(call: Call<OrderDetailsRespomse?>, t: Throwable) {
                pd.dismiss()
            }
        })

    }



    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}
