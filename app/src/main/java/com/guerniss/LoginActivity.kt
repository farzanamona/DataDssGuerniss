package com.guerniss

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.guerniss.adapter.EyeAdapter
import com.guerniss.model.LoginResponse
import com.guerniss.model.Product
import com.guerniss.model.SingleCategoryProduct
import com.guerniss.utils.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_kazol.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class LoginActivity : AppCompatActivity() {
    var register:TextView? = null
    var context:Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        context = this
       register = findViewById<TextView>(R.id.registertv)
        register!!.setOnClickListener{
            startActivity(Intent(context,RegisterActivity::class.java))
        }


        btnSignin.setOnClickListener {
            var phone = etPhone.text.toString()
            var pass = etPass.text.toString()

            if (TextUtils.isEmpty(phone)){
                Toast.makeText(context,"Input mobile number",Toast.LENGTH_SHORT).show()
                etPhone.requestFocus()
            }else if (TextUtils.isEmpty(pass)){
                Toast.makeText(context,"Input password",Toast.LENGTH_SHORT).show()
            }else{
                login(phone,pass)
            }
        }
    }

    fun login( phone:String, pass:String) {

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

        val call: Call<LoginResponse> = api.login(phone,pass)

        call.enqueue(object : Callback<LoginResponse?> {

            override fun onResponse(call: Call<LoginResponse?>, response: Response<LoginResponse?>) {
                pd.dismiss()
                val result = response.body()
                Log.e("Response",""+result)
                if(result!=null){
                    PersistData.saveLoginData(context!!,result)
                    PersistData.setLogin(context!!)
                    finish()
                }

            }

            override fun onFailure(call: Call<LoginResponse?>, t: Throwable) {
                pd.dismiss()
            }
        })

    }
}
