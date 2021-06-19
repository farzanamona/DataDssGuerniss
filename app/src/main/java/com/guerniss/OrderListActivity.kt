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
import com.guerniss.adapter.OrderListAdapter
import com.guerniss.model.Data
import com.guerniss.model.LoginResponse
import com.guerniss.model.OrderListResponse
import com.guerniss.utils.*
import kotlinx.android.synthetic.main.activity_order_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class OrderListActivity : AppCompatActivity() {
    private var context: Context? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_list)
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
        actionBar!!.title = "Order List"
        // set categories Layout

        getOrder()

    }



    fun getOrder() {

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

        val call: Call<OrderListResponse> = api.getAllOrderList(PersistData.getLoginData(context!!).user.id.toString())
       // val call: Call<OrderListResponse> = api.getAllOrderList("142")

        call.enqueue(object : Callback<OrderListResponse?> {

            @SuppressLint("WrongConstant")
            override fun onResponse(call: Call<OrderListResponse?>, response: Response<OrderListResponse?>) {
                pd.dismiss()
                val result = response.body()
                Log.e("Response",""+result)
                if(result!=null){
                    val layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
                    var cartAdapter = OrderAdapter(context!!, result.data as ArrayList<Data>)
                    recycalOrderList!!.layoutManager = layoutManager
                    recycalOrderList!!.itemAnimator = DefaultItemAnimator()
                    recycalOrderList.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL))
                    recycalOrderList!!.adapter = cartAdapter
                }

            }

            override fun onFailure(call: Call<OrderListResponse?>, t: Throwable) {
                pd.dismiss()
            }
        })

    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
