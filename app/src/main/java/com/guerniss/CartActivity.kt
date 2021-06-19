package com.guerniss

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.guerniss.adapter.CartAdapter
import com.guerniss.model.cartModel
import com.guerniss.utils.AppConstant
import com.guerniss.utils.PersistData
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_categories.toolbar
import java.util.ArrayList

class CartActivity : AppCompatActivity() {
    private var context: Context? = null
    private val TAG = "tag"
    private var recyclerViewcart: RecyclerView? = null
    private var accessoriesProductlist = ArrayList<cartModel>()
    private var cartAdapter: CartAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
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
        actionBar!!.title = getString(R.string.cart)
        // set categories Layout
        recyclerViewcart = findViewById(R.id.recyclerViewcart) as RecyclerView
        val layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)

        cartAdapter = CartAdapter(context!!,AppConstant.cartList)
        recyclerViewcart!!.layoutManager = layoutManager
        recyclerViewcart!!.itemAnimator = DefaultItemAnimator()
        recyclerViewcart!!.adapter = cartAdapter

        btnChekOut.setOnClickListener {
            if (PersistData.isLogged(context!!)){
                startActivity(Intent(context,OrderDetailsActivity::class.java))
                finish()
            }else{
                startActivity(Intent(context,LoginActivity::class.java))
            }
        }

    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
