package com.guerniss.fragment.lip
import android.app.ProgressDialog
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.guerniss.R
import com.guerniss.adapter.EyeAdapter
import com.guerniss.model.*
import com.guerniss.utils.*
import kotlinx.android.synthetic.main.fragment_kazol.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


class FragmentSkinCareCream : Fragment() {
    private var eyeProductlist = ArrayList<EyeModel>()
    private var eyeAdapter: EyeAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kazol, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getProducts()
    }

    fun getProducts() {

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

        val call: Call<SingleCategoryProduct> = api.getSkinCareCreamProduct()

        call.enqueue(object : Callback<SingleCategoryProduct?> {

            override fun onResponse(call: Call<SingleCategoryProduct?>, response: Response<SingleCategoryProduct?>) {
                pd.dismiss()
                val result = response.body()
                Log.e("Response",""+result)
                if(result!=null){

                    AppConstant.productListForColor.clear()
                    for ((index, value) in result.data.withIndex()) {
                        if(!TextUtils.isEmpty(value.hex_color_code) || value.hex_color_code!=null){
                            AppConstant.productListForColor.add(value)
                        }
                    }

                    val gridLayoutManager = GridLayoutManager(activity, 2)
                    eyeAdapter = EyeAdapter(context!!, result.data as ArrayList<Product>
                    ,result.imageUrl)
                    recyclerViewEye!!.layoutManager = gridLayoutManager
                    recyclerViewEye!!.itemAnimator = DefaultItemAnimator()
                    recyclerViewEye!!.adapter = eyeAdapter
                }

            }

            override fun onFailure(call: Call<SingleCategoryProduct?>, t: Throwable) {
                pd.dismiss()
            }
        })

    }

}