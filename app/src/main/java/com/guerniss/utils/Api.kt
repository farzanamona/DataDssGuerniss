package com.guerniss.utils

import com.guerniss.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONArray
import retrofit2.Call
import retrofit2.http.*


interface Api {



    @GET("api/all_products")
    fun home_all_products(
    ): Call<SingleCategoryProduct>



    @GET("api/eye_kajol")
    fun getEyeKajol(
    ): Call<SingleCategoryProduct>

    @GET("api/eye_mascara")
    fun getEyeMaskara(
    ): Call<SingleCategoryProduct>

    @GET("api/eye_shadow")
    fun getEyeShadow(
    ): Call<SingleCategoryProduct>


    @GET("api/eye_liner")
    fun getEyeliner(
    ): Call<SingleCategoryProduct>


    @GET("api/face_contour")
    fun getface_contour(
    ): Call<SingleCategoryProduct>

    @GET("api/face_powder")
    fun getface_Power(
    ): Call<SingleCategoryProduct>


        @GET("api/face_foundation")
            fun getface_foundation(
            ): Call<SingleCategoryProduct>

        @GET("api/face_setting_spray")
                    fun getface_settings(
                    ): Call<SingleCategoryProduct>



        @GET("api/face_bbcream")
                    fun getface_BBCream(
                    ): Call<SingleCategoryProduct>


        @GET("api/face_concealer")
                    fun getface_concealer(
                    ): Call<SingleCategoryProduct>



        @GET("api/face_highlighter")
                    fun getface_HeighLighter(
                    ): Call<SingleCategoryProduct>



        @GET("api/face_makeup_remover")
                    fun getface_MakeUpRemover(
                    ): Call<SingleCategoryProduct>


         @GET("api/face_primer")
                            fun getface_Primer(
                            ): Call<SingleCategoryProduct>



         @GET("api/lip_lipStick")
                            fun getLipProduct(
                            ): Call<SingleCategoryProduct>


         @GET("api/makeup_kit")
                            fun getMakeupProduct(
                            ): Call<SingleCategoryProduct>


         @GET("api/nail_nailpolish")
                            fun getNailpolishProduct(
                            ): Call<SingleCategoryProduct>

    @GET("api/nail_sticker")
    fun getNailStickerProduct(
    ): Call<SingleCategoryProduct>

    @GET("api/skincare_cream")
        fun getSkinCareCreamProduct(
        ): Call<SingleCategoryProduct>

    @GET("api/skincare_face_wash")
    fun getSkinCareFaceWash(
    ): Call<SingleCategoryProduct>

    @GET("api/skincare_scrub")
    fun getSkinCareScrub(
    ): Call<SingleCategoryProduct>

    @GET("api/skincare_oil")
    fun getSkinCareOil(
    ): Call<SingleCategoryProduct>

    @GET("api/skincare_lotion")
    fun getSkinCareLotion(
    ): Call<SingleCategoryProduct>


     @GET("api/serum_face")
    fun getSerumFace(
    ): Call<SingleCategoryProduct>


     @GET("api/hairecare_shampoo")
    fun getHairecare_shampoo(
    ): Call<SingleCategoryProduct>


     @GET("api/haircare_conditioner")
    fun getHairecare_conditioner(
    ): Call<SingleCategoryProduct>


     @GET("api/accessories_brush")
    fun getAccessories_brush(
    ): Call<SingleCategoryProduct>


    @GET("api/accessories_puff")
    fun getAccessories_puff(
    ): Call<SingleCategoryProduct>


    @GET("api/accessories_blender")
    fun getAccessories_blender(
    ): Call<SingleCategoryProduct>



    @GET()
    fun logout(
        @Url url: String
    ): Call<String>


    @FormUrlEncoded
    @POST("api/customer/checkout")
    fun orderCheckOut(
        @Field("pay_op_description") pay_op_description: String,
        @Field("order_ship_name") order_ship_name: String,
        @Field("order_email") order_email: String,
        @Field("order_phone") order_phone: String,
        @Field("order_ship_address") order_ship_address: String,
        @Field("order_net_amount") order_net_amount: String,
        @Field("order_discount") order_discount: String,
        @Field("order_total_amount") order_total_amount: String,
        @Field("order_cust_id") order_cust_id: String,
        @Field("carts") carts: JSONArray
    ): Call<OrderSumitResponse>




    @FormUrlEncoded
    @POST("api/reportDailySchedule")
    fun reportDailySchedule(
        @Field("year") bp_no: String,
        @Field("course") user_id: String,
        @Field("batch") course_id: String
    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST("api/customer/login")
    fun login(
        @Field("phone") phone: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("api/customer/register")
    fun register(
        @Field("name") name: String,
        @Field("phone") phone: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>



    @FormUrlEncoded
    @POST("api/customer/customer_details")
    fun getCustDetails(
        @Field("cust_id") order_cust_id:Int
    ): Call<CustomerDetaisResponse>

    @FormUrlEncoded
    @POST("api/customer/order_lists")
    fun getAllOrderList(
        @Field("order_cust_id") order_cust_id: String
    ): Call<OrderListResponse>

    @FormUrlEncoded
    @POST("api/customer/order_details")
    fun getorder_details(
        @Field("order_id") order_id: String
    ): Call<OrderDetailsRespomse>


    @FormUrlEncoded
    @POST("api/customer/customer_update")
    fun profileUpdate(
        @Field("id") id: Int,
        @Field("phone") phone: Int,
        @Field("email") email: String,
        @Field("address") address: String,
        @Field("cust_gender") cust_gender: String,
        @Field("cust_date_of_birth") cust_date_of_birth: String,
        @Field("cust_marrage_aniversary") cust_marrage_aniversary: String,
        @Field("cust_employee_statement") cust_employee_statement: String,
        @Field("name") name: String
    ): Call<LoginResponse>

    @Multipart
    @POST("api/customer/customer_update")
    fun updateProfileWithPhoto(
        @Part("id") id: RequestBody?,
        @Part("phone") phone: RequestBody?,
        @Part image: MultipartBody.Part?,
        @Part("email") email: RequestBody?,
        @Part("address") address: RequestBody?,
        @Part("cust_gender") cust_gender: RequestBody?,
        @Part("cust_date_of_birth") cust_date_of_birth: RequestBody?,
        @Part("cust_marrage_aniversary") cust_marrage_aniversary: RequestBody?,
        @Part("cust_employee_statement") cust_employee_statement: RequestBody?,
        @Part("name") name: RequestBody?
    ): Call<ResponseBody>

    @Multipart
    @POST("instant_complain/instantComplain")
    fun uploadImage(
        @Part file: MultipartBody.Part?,
        @Part("text") text:RequestBody,
        @Part("user_id") user_id:RequestBody,
        @Part("mobile_no") mobile_no:RequestBody,
        @Part("complain_channel") complain_channel:RequestBody,
        @Part("device_token") device_token:RequestBody,
        @Part("gender") gender:RequestBody

    ): Call<ResponseBody?>?


}