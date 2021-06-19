package com.guerniss.utils;






import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface ApiJava {

    //String BASE_URL = "http://192.168.0.102/survey/";
    String BASE_URL = "https://www.guerniss.com/";




//    @GET("api/designation")
//    public Call<DesignationResponse> designation();
//
//



//    @Headers("Content-Type: application/json")
//    @POST("api/save-question-answer")
//    Call<String> save_question_answer(
//            @Body String data
//    );



//    @FormUrlEncoded
//    @POST("api/login")
//    Call<LoginResponse> login(
//            @Field("email") String email,
//            @Field("password") String password
//    );


//
//    @FormUrlEncoded
//        @POST("api/requisitionapplicationstore")
//        Call<CommonResponse> requisitionapplicationstore(
//            @Field("auth_data") String auth_data,
//            @Field("data") String data
//    );
//
//
//    @FormUrlEncoded
//        @POST("api/requisitionapprovedquantity")
//        Call<CommonResponse> requisitionapprovedquantity(
//            @Field("auth_data") String auth_data,
//            @Field("data") String data
//    );
//
//
//    @FormUrlEncoded
//    @POST("api/updateworkdone")
//    Call<ResponseBody> updateworkDone(
//            @Field("application_no") String application_no,
//            @Field("work_done_date") String work_done_date,
//            @Field("comment") String comment
//    );
//
//
//
//    @FormUrlEncoded
//    @POST("api/public-employee")
//    Call<EmployResponse> public_employee(
//            @Field("region_id") String region_id,
//            @Field("unit_id") String unit_id,
//            @Field("zone_id") String zone_id,
//            @Field("designation_id") String designation_id,
//            @Field("like") String like
//    );
//
//
//    @FormUrlEncoded
//    @POST("api/assigncomplain")
//    Call<AssigncomplainResponse> assigncomplain(
//            @Field("auth_data") String auth_data,
//            @Field("application_no") String complain_application_id
//    );
//
//    @FormUrlEncoded
//    @POST("api/get-filter-all-data")
//    Call<String> planAlldata_filter(
//            @Field("division_id") String division_id,
//            @Field("district_id") String district_id
//    );
//
//     @FormUrlEncoded
//        @POST("api/user")
//        Call<String> planlogin(
//             @Field("email") String email,
//             @Field("password") String password
//     );
//    @FormUrlEncoded
//    @POST("api/get-all-data")
//    Call<String> planAlldata(
//            @Field("division_id") String division_id,
//            @Field("district_id") String district_id
//    );

//    @GET
//    public Call<SebaAbedonServiceInfoResponse> serviceapplyData(@Url String url);

//
//
//    @GET
//    public Call<RequsitionApproveSinglaDataResponse> getSingeDataReqIssue(@Url String url);
//
//    @GET
//    public Call<StockTransferApproveSinglaDataResponse> getSingeDataStockTransferApprove(@Url String url);
//
//
//     @GET
//    public Call<StockTransferIssueSinglaDataResponse> getSingleDataStockTransferIssue(@Url String url);
//
//
//
//
// @GET
//    public Call<StockTransferApproveSinglaDataResponse> getSingeDataStockTransferRecommandApprove(@Url String url);
//
//
//        @GET
//            public Call<LoanRecommendReqApproveSinglaDataResponse> getSingeDataLoanRecommandApprove(@Url String url);
//
//
//    @GET
//    public Call<LoanRecommendReqApproveSinglaDataResponse> getSingeDataLoanStatus(@Url String url);
//
//
//
//
//
//     @GET
//    public Call<CurrentStockResponse> currentStockReportFilter(@Url String url);
//
//    @GET
//        public Call<PurchaseReportResponse> purchaseReportFilter(@Url String url);
//
//        @GET
//        public Call<DirectionsResponse> getRout(@Url String url);

//
//
//
////    @Multipart
////    @POST("uploadPhoto")
////    Call<LoinResponse> postImage(@Part MultipartBody.Part images, @Part("user_id") String user_id);
////
////    @Multipart
////    @POST("uploadPhoto")
////    Call<LoinResponse> uploadImage(@Part("images") RequestBody file, @Part("user_id") RequestBody desc);
//
//
//    @GET("ApiController/next/CurrentStockReport/credential")
//    Call<ReportCommonDataResponse> getAllRecords();
//
//    @GET("ApiController/next/PurchaseReport/credential")
//    Call<ReportCommonDataResponse> getAllRecordsPurchaseReport();
//



//    @GET("api/get-occupation")
//    Call<List<ScRelation>> getOccupation(
//
//    );

//    @GET("ApiController/next/ProductRequisitionApproved/edit/")
//    Call<Info_FuelGenResponse> getFuel(
//            @Query("password") String password
//
//    );


//    @GET("get_technology_names")
//    Call<Info_GetTechnologyNames> getTechnologyName(
//            @Query("email") String email,
//            @Query("password") String password
//
//    );
//
//    @GET("technology_wise_generation_report")
//    Call<Info_TechWiseGenReportResponse> getTechnologyNameReport(
//            @Query("email") String email,
//            @Query("password") String password
//
//    );





}
