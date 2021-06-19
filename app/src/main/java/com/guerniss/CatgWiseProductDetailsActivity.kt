package com.guerniss

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.bumptech.glide.Glide
import com.guerniss.utils.AppConstant
import com.guerniss.utils.CatgConstant
import kotlinx.android.synthetic.main.activity_product_details.*

class CatgWiseProductDetailsActivity : AppCompatActivity() {
    private var context: Context? = null
    private var btn: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catg_wise_product_details)
        btn = findViewById<Button>(R.id.backBtn)
        btn!!.setOnClickListener{
            onBackPressed()
        }
        context = this

        tvProductName.text = CatgConstant.singleProductinfo!!.prod_name
        tvPrice.text = CatgConstant.singleProductinfo!!.unit_price
        tvdetails.text = CatgConstant.singleProductinfo!!.prod_short_desc as CharSequence?
        Glide.with(context as CatgWiseProductDetailsActivity)
            .load(CatgConstant.imgUrl+"/"+ CatgConstant.singleProductinfo!!.prod_image)
            .into(prod_details_image)

    }

}
