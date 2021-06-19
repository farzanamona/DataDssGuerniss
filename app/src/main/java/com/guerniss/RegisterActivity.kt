package com.guerniss

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.guerniss.model.LoginResponse
import com.guerniss.utils.*
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class RegisterActivity : AppCompatActivity() {
    var login: TextView? = null
    var context:Context? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        context = this
        login = findViewById<TextView>(R.id.logintv)
        login!!.setOnClickListener{
            startActivity(Intent(context,LoginActivity::class.java))
        }
        btnSignUp.setOnClickListener {

            var usrename = etUserName.text.toString()
            var phone = etPhone.text.toString()
            var email = etEmail.text.toString()
            var pass = etPass.text.toString()
            var confirmpass = etConformPass.text.toString()

            if(TextUtils.isEmpty(usrename)){
                Toast.makeText(context, "Input Username", Toast.LENGTH_SHORT).show()
            }else if(TextUtils.isEmpty(phone)){
                Toast.makeText(context, "Input Phone", Toast.LENGTH_SHORT).show()
            }else if(TextUtils.isEmpty(email)){
                Toast.makeText(context, "Input Email address", Toast.LENGTH_SHORT).show()
            }else if(!isValidEmail(email)){
                Toast.makeText(context, "Input valid Email address", Toast.LENGTH_SHORT).show()
            }else if(TextUtils.isEmpty(pass)){
                Toast.makeText(context, "Input Password", Toast.LENGTH_SHORT).show()
            }else if(TextUtils.isEmpty(confirmpass)){
                Toast.makeText(context, "Input Confirm Password", Toast.LENGTH_SHORT).show()
            }else if(!pass.equals(confirmpass)){
                Toast.makeText(context, "Confirm Password and Password doesn't match", Toast.LENGTH_SHORT).show()
            }else{
                register(usrename,email,phone,pass)
            }
        }
    }

    fun register( name:String, email:String,phone:String, pass:String) {

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

        val call: Call<LoginResponse> = api.register(name,phone,email,pass)

        call.enqueue(object : Callback<LoginResponse?> {

            override fun onResponse(call: Call<LoginResponse?>, response: Response<LoginResponse?>) {
                pd.dismiss()
                val result = response.body()
                Log.e("Response",""+result)
                if(result!=null){
                    PersistData.saveLoginData(context!!,result)
                    finish()
                }

            }

            override fun onFailure(call: Call<LoginResponse?>, t: Throwable) {
                pd.dismiss()
            }
        })

    }


    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

}
