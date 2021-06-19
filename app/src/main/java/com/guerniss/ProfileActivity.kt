package com.guerniss

import android.Manifest
import android.app.ActionBar
import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.content.ComponentName
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Parcelable
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.guerniss.model.CustomerDetaisResponse
import com.guerniss.model.LoginResponse
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.dialog_dender.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.guerniss.utils.*
import kotlinx.android.synthetic.main.activity_main.*


class ProfileActivity : AppCompatActivity() {

    private var id = ""
    private var firstname = ""
    private var lastname = ""
    private var email = ""
    private var phone = ""
    private var address = ""
    private var gender = ""
    private var dob = ""
    private var anivesary = ""
    var imageFile:File? = null
    var context: Context? = null

    private val TAG = "tag"

    var filePath: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        context = this

        Glide.with(context!!)
            .load(PersistData.getLoginData(context!!).imageUrl+"/"+PersistData.getLoginData(context!!).user.image)
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .into(imgProfile)

        getCustDetails()

        checkAndRequestPermissions()

        imgGallary.setOnClickListener {
            if(!checkAndRequestPermissions()){
                checkAndRequestPermissions()
            }else{
                startActivityForResult(getPickImageChooserIntent(), REQUEST_TAKE_PHOTO)
            }        }
        imgCam.setOnClickListener {
            if(!checkAndRequestPermissions()){
                checkAndRequestPermissions()
            }else{
                startActivityForResult(getPickImageChooserIntent(), REQUEST_TAKE_PHOTO)
            }
        }

        etGender.setOnClickListener {
            //etGender.visibility = View.GONE
            showDialogGender()
        }

        btnProfile.setOnClickListener {
            id = PersistData.getLoginData(context as ProfileActivity).user.id.toString()
            Log.e("id", "id" + id)
            firstname = etFiestName.text.toString()
            lastname = etlastname.text.toString()
            email = etEmail.text.toString()
            phone = etPhone.text.toString()
            address = etAddress.text.toString()
            dob = etDob.text.toString()
            anivesary = etAniversasry.text.toString()

            if(TextUtils.isEmpty(firstname)){
                Toast.makeText(context, "Input first name", Toast.LENGTH_SHORT).show()
            }else if(TextUtils.isEmpty(lastname)){
                Toast.makeText(context, "Input last name", Toast.LENGTH_SHORT).show()
            }else if(TextUtils.isEmpty(phone)){
                Toast.makeText(context, "Input Phone", Toast.LENGTH_SHORT).show()
            }else if(TextUtils.isEmpty(email)){
                Toast.makeText(context, "Input Email address", Toast.LENGTH_SHORT).show()
            }else if(!isValidEmail(email)){
                Toast.makeText(context, "Input valid Email address", Toast.LENGTH_SHORT).show()
            }else{
                //profileUpdate()
                profileUpdateWithPhoto()
            }
        }


    }





    fun profileUpdate() {

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

        val call: Call<LoginResponse> = api.profileUpdate(
            id.toInt(),
            phone.toInt(),
            email,
            address,
            gender,
            "",
            "",
            "test",
            firstname + " " + lastname
        )

        call.enqueue(object : Callback<LoginResponse?> {

            override fun onResponse(
                call: Call<LoginResponse?>,
                response: Response<LoginResponse?>
            ) {
                pd.dismiss()
                val result = response.body()
                Log.e("Response", "" + result)
                if (result != null) {
//                    PersistData.saveLoginData(context!!,result)
                    finish()
                }

            }

            override fun onFailure(call: Call<LoginResponse?>, t: Throwable) {
                pd.dismiss()
            }
        })

    }
    fun getCustDetails() {

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

        val call: Call<CustomerDetaisResponse> = api.getCustDetails(
            PersistData.getLoginData(context!!).user.id
        )

        call.enqueue(object : Callback<CustomerDetaisResponse?> {

            override fun onResponse(
                call: Call<CustomerDetaisResponse?>,
                response: Response<CustomerDetaisResponse?>
            ) {
                pd.dismiss()
                val result = response.body()
                // Log.e("Response", "" + result)
                if (result != null) {
                    Log.e("Response", "" + result.data[0].cust_address)
                    etAddress.setText(result.data[0].cust_address)
                    etAniversasry.setText(result.data[0].cust_marrage_aniversary)
                    etDob.setText(result.data[0].cust_date_of_birth)
                    etEmail.setText(result.data[0].cust_email)
                    etPhone.setText(result.data[0].cust_phone)
                    etFiestName.setText(result.data[0].cust_name)
                    etGender.setText(result.data[0].cust_gender)

                }

            }

            override fun onFailure(call: Call<CustomerDetaisResponse?>, t: Throwable) {
                pd.dismiss()
            }
        })

    }




    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    private fun showDialogGender() {

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_dender)

        var spnGender = dialog.spnGender
        var tvCancel = dialog.tvCancel

        tvCancel.setOnClickListener {
            dialog.dismiss()
        }

        var listGender = arrayListOf<String>()
        listGender.add("Select Gender")
        listGender.add("Male")
        listGender.add("Female")
        listGender.add("Third Gender")

        spnGender.adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,
            listGender
        )

        spnGender?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position>0){
                    gender = listGender[position]
                    etGender.setText(gender)
                    dialog.dismiss()
                }
            }

        }
        dialog.show()
        val window: Window = dialog.getWindow()!!
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT)
    }



    fun getPickImageChooserIntent(): Intent? {
        val outputFileUri = getCaptureImageOutputUri()
        val allIntents: MutableList<Intent> = java.util.ArrayList()
        val packageManager = packageManager
        val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val listCam = packageManager.queryIntentActivities(captureIntent, 0)
        for (res in listCam) {
            val intent = Intent(captureIntent)
            intent.component = ComponentName(res.activityInfo.packageName, res.activityInfo.name)
            intent.setPackage(res.activityInfo.packageName)
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri)
            }
            allIntents.add(intent)
        }
        val galleryIntent = Intent(Intent.ACTION_GET_CONTENT)
        //galleryIntent.type = "image/*"
        galleryIntent.type = "image/*"
        val listGallery = packageManager.queryIntentActivities(galleryIntent, 0)
        for (res in listGallery) {
            val intent = Intent(galleryIntent)
            intent.component = ComponentName(res.activityInfo.packageName, res.activityInfo.name)
            intent.setPackage(res.activityInfo.packageName)
            allIntents.add(intent)
        }



        var mainIntent: Intent? = allIntents[allIntents.size - 1]

        for (intent in allIntents) {
            if (intent.component!!.className == "com.android.documentsui.DocumentsActivity") {
                mainIntent = intent
                break
            }
        }
        allIntents.remove(mainIntent)
        val chooserIntent = Intent.createChooser(mainIntent, "Select source")
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toTypedArray<Parcelable>())
        return chooserIntent
    }

    private fun getCaptureImageOutputUri(): Uri? {
        var outputFileUri: Uri? = null
        val getImage = getExternalFilesDir("")
        if (getImage != null) {
            outputFileUri = Uri.fromFile(File(getImage.path, "profile.png"))
        }
        return outputFileUri
    }


    private fun getImageFromFilePath(data: Intent?): String? {
        val isCamera = data == null || data.data == null
        return if (isCamera) getCaptureImageOutputUri()!!.getPath() else getPathFromURI(data!!.data!!)
    }

    fun getImageFilePath(data: Intent?): String? {
        return getImageFromFilePath(data)
    }

    private fun getPathFromURI(contentUri: Uri): String? {
        val proj = arrayOf(MediaStore.Audio.Media.DATA)
        val cursor = contentResolver.query(contentUri, proj, null, null, null)
        val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(column_index)
    }


    private fun checkAndRequestPermissions(): Boolean {

        val camerapermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        val writepermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val permissionLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
//        val permissionRecordAudio = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)


        val listPermissionsNeeded = ArrayList<String>()

        if (camerapermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA)
        }

        if (writepermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
//        if (permissionRecordAudio != PackageManager.PERMISSION_GRANTED) {
//            listPermissionsNeeded.add(Manifest.permission.RECORD_AUDIO)
//        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toTypedArray(), REQUEST_ID_MULTIPLE_PERMISSIONS)
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        Log.d(TAG, "Permission callback called-------")
        when (requestCode) {
            REQUEST_ID_MULTIPLE_PERMISSIONS -> {

                val perms = HashMap<String, Int>()
                // Initialize the map with both permissions
                perms[Manifest.permission.CAMERA] = PackageManager.PERMISSION_GRANTED
                perms[Manifest.permission.WRITE_EXTERNAL_STORAGE] = PackageManager.PERMISSION_GRANTED
                perms[Manifest.permission.READ_EXTERNAL_STORAGE] = PackageManager.PERMISSION_GRANTED
//                perms[Manifest.permission.RECORD_AUDIO] = PackageManager.PERMISSION_GRANTED
                // Fill with actual results from user
                if (grantResults.size > 0) {
                    for (i in permissions.indices)
                        perms[permissions[i]] = grantResults[i]
                    // Check for both permissions
                    if (perms[
                                Manifest.permission.CAMERA] == PackageManager.PERMISSION_GRANTED
                        && perms[Manifest.permission.WRITE_EXTERNAL_STORAGE] == PackageManager.PERMISSION_GRANTED
                        && perms[Manifest.permission.READ_EXTERNAL_STORAGE] == PackageManager.PERMISSION_GRANTED
//                            && perms[Manifest.permission.RECORD_AUDIO] == PackageManager.PERMISSION_GRANTED
                    ) {
                        Log.d(TAG, "sms & location services permission granted")
                        // process the normal flow
//                        val i = Intent(this@MainActivity, WelcomeActivity::class.java)
//                        startActivity(i)
//                        finish()
                        //else any one or both the permissions are not granted
                    } else {
                        Log.d(TAG, "Some permissions are not granted ask again ")
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
                        //                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)
                            || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)
//                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)
                        ) {
                            showDialogOK("Service Permissions are required for this app",
                                DialogInterface.OnClickListener { dialog, which ->
                                    when (which) {
                                        DialogInterface.BUTTON_POSITIVE -> checkAndRequestPermissions()
                                        DialogInterface.BUTTON_NEGATIVE ->
                                            // proceed with logic by disabling the related features or quit the app.
                                            finish()
                                    }
                                })
                        } else {
                            //explain("You need to give some mandatory permissions to continue. Do you want to go to app settings?")
                            //                            //proceed with logic by disabling the related features or quit the app.
                        }//permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                    }
                }
            }
        }

    }

    private fun showDialogOK(message: String, okListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("OK", okListener)
            .setNegativeButton("Cancel", okListener)
            .create()
            .show()
    }

//    private fun explain(msg: String) {
//        val dialog = AlertDialog.Builder(this)
//        dialog.setMessage(msg)
//                .setPositiveButton("Yes") { paramDialogInterface, paramInt ->
//                    //  permissionsclass.requestPermission(type,code);
//                    startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:bd.org.blast.sromik_jigyasha")))
//                }
//                .setNegativeButton("Cancel") { paramDialogInterface, paramInt -> finish() }
//        dialog.show()
//    }

    companion object {

        val REQUEST_ID_MULTIPLE_PERMISSIONS = 1
        val REQUEST_TAKE_PHOTO  = 100
        val REQUEST_TAKE_FILE  = 7
        private val SPLASH_TIME_OUT = 2000
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int,
                                  data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO) {
                filePath = getImageFilePath(data)
                Log.e("filePath", "" + filePath)
                if (filePath != null) {
                    var selectedImage = BitmapFactory.decodeFile(filePath)
                    selectedImage = getResizedBitmap(selectedImage!!, 300, 300)
                    // userBmp = selectedImage
                    imgProfile!!.setImageBitmap(selectedImage)

                    saveImageToExternalStorage(selectedImage)
                    //saveImage(selectedImage)
                }
            }

            if (requestCode == REQUEST_TAKE_FILE) {

                val pdfPath: String? = data!!.data!!.path
                Log.e("pdfPath", "" + pdfPath)
            }


        }
    }


    // Method to save an image to external storage
    private fun saveImageToExternalStorage(bitmap: Bitmap):Uri{
        // Get the external storage directory path
        val path = Environment.getExternalStorageDirectory().toString()

        // Create a file to save the image
        val file = File(path, "${UUID.randomUUID()}.jpg")

        try {
            // Get the file output stream
            val stream: OutputStream = FileOutputStream(file)

            // Compress the bitmap
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)

            // Flush the output stream
            stream.flush()

            // Close the output stream
            stream.close()
            //toast("Image saved successful.")
        } catch (e: IOException){ // Catch the exception
            e.printStackTrace()
            //toast("Error to save image.")
        }

        // Return the saved image path to uri
        return Uri.parse(file.absolutePath)
    }

    private fun saveImage(finalBitmap: Bitmap) {
        val root: String = Environment.getExternalStorageDirectory().toString()
        val myDir = File(root)
        myDir.mkdirs()
        val curDate = Date()
        val curMillis = curDate.time
        val fname = curMillis.toString()+".jpg"
        val file = File(myDir, fname)
        if (file.exists()) file.delete()
        Log.i("LOAD", root + fname)
        try {
            val out = FileOutputStream(file)
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
            out.flush()
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }



    fun getResizedBitmap(bm: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap? {
        var bmp: Bitmap? = null
        return if (maxHeight > 0 && maxWidth > 0) {
            val width = bm.width
            val height = bm.height
            Log.e("bm.getWidth()", "" + bm.width)
            Log.e("bm.getHeight()", "" + bm.height)
            val ratioBitmap = width.toFloat() / height.toFloat()
            val ratioMax = maxWidth.toFloat() / maxHeight.toFloat()
            var finalWidth = maxWidth
            var finalHeight = maxHeight
            if (ratioMax > ratioBitmap) {
                finalWidth = (maxHeight.toFloat() * ratioBitmap).toInt()
            } else {
                finalHeight = (maxWidth.toFloat() / ratioBitmap).toInt()
            }
            bmp = Bitmap.createScaledBitmap(bm, finalWidth, finalHeight, true)
            bmp
        } else {
            bmp
        }
    }


//    private fun uploadImage() {
//        if (!this!!.context?.let { NetInfo.isOnline(it) }!!)
//        {
//            context?.let { AlertMessage.showMessage(it, "Alert!", "ইন্টারনেট সংযোগ পরীক্ষা করুন") }
//        }
//
//        val pd = ProgressDialog(context)
//        pd.setMessage("Loading....")
//        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER)
//        pd.show()
//        var retrofit: Retrofit = Retrofit.Builder()
//            .baseUrl(ServerConfig.BASE_URL)
//            //.addConverterFactory(ScalarsConverterFactory.create())
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        val file =  File(filePath)
//        // Create a request body with file and image media type
//        val fileReqBody = RequestBody.create(MediaType.parse("image/*"), file)
//        // Create MultipartBody.Part using file request-body,file name and part name
//        val part = MultipartBody.Part.createFormData("file", file.getName(), fileReqBody);
//        var descriptionString = etComplaintText.text.toString()+""
//        var description = RequestBody.create(
//            okhttp3.MultipartBody.FORM, descriptionString)
//
//
//        var user_id:RequestBody? = null
//        if (Utils.getPreference(context, Utils.userId)!=null || !(TextUtils.isEmpty(Utils.getPreference(context, Utils.userId)))){
//            user_id = RequestBody.create(
//                okhttp3.MultipartBody.FORM, Utils.getPreference(context, Utils.userId).toString())
//        }else{
//            user_id = RequestBody.create(
//                okhttp3.MultipartBody.FORM, "0")
//        }
//
//
//        var mobile = Utils.getMobileNo(context).toString()
//        var mobile_no = RequestBody.create(
//            okhttp3.MultipartBody.FORM, mobile)
//
//        var gender = Utils.getGender(context).toString()
//        var geder_id = RequestBody.create(
//            okhttp3.MultipartBody.FORM, gender)
//
//        var complaint_channel = RequestBody.create(
//            okhttp3.MultipartBody.FORM, "APP-TO-APP")
//
//        val device_token = Utils.getPreference(context, Utils.device_token)
//
//        Log.e("device_token", "" + device_token);
//
//        var token = RequestBody.create(
//            okhttp3.MultipartBody.FORM, device_token)
//
//
//        var api = retrofit.create(Api::class.java)
//
//        val call: Call<ResponseBody?>? = api.uploadImage(part, description, user_id, mobile_no, complaint_channel, token, geder_id)
//
//        call!!.enqueue(object : Callback<ResponseBody?> {
//
//            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
//                pd.dismiss()
//
//                // Log.e("ApplicationSendRres", "response" + prettyJsonString);
//                Log.e("uploadres", "onResponse: " + Gson().toJson(response.body()))
//
//                Log.e("upload", "" + "upload")
//                Toast.makeText(context, "আপনার জিজ্ঞাসা পাঠানো হয়েছে", Toast.LENGTH_SHORT).show()
//                finish();
//
//            }
//
//            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
//                pd.dismiss()
//                Toast.makeText(context, "আবার চেষ্টা করুন", Toast.LENGTH_SHORT).show()
//            }
//        })
//
//    }

    fun profileUpdateWithPhoto() {

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
            //.addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()


//
        val file =  File(filePath)
        // Create a request body with file and image media type
        val fileReqBody = RequestBody.create(MediaType.parse("image/*"), file)
        // Create MultipartBody.Part using file request-body,file name and part name
        val part = MultipartBody.Part.createFormData("cust_image", file.getName(), fileReqBody);


        var id_cust = RequestBody.create(okhttp3.MultipartBody.FORM, id)
        var cust_phone = RequestBody.create(okhttp3.MultipartBody.FORM, phone)
        var cust_email = RequestBody.create(okhttp3.MultipartBody.FORM, email)
        var cust_address = RequestBody.create(okhttp3.MultipartBody.FORM, address)
        var cust_gender = RequestBody.create(okhttp3.MultipartBody.FORM, gender)
        var cust_dob = RequestBody.create(okhttp3.MultipartBody.FORM, dob)
        var cust_anivesary = RequestBody.create(okhttp3.MultipartBody.FORM, anivesary)
        var name = RequestBody.create(okhttp3.MultipartBody.FORM, firstname + " " + lastname)



        var api = retrofit.create(Api::class.java)
        val call: Call<ResponseBody> = api.updateProfileWithPhoto(
            id_cust,
            cust_phone,
            part,
            cust_email,
            cust_address,
            cust_gender,
            cust_dob,
            cust_anivesary,
            cust_anivesary,
            name
        )
        call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
                call: Call<ResponseBody?>,
                response: Response<ResponseBody?>
            ) {
                pd.dismiss()
                val result = response.body()
                Log.e("Response", "" + result)
                if (result != null) {
                    Toast.makeText(context,"Updated",Toast.LENGTH_SHORT).show()
                    finish()
//                    PersistData.saveLoginData(context!!,result)
//                    finish()
                }

            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                pd.dismiss()
            }
        })

    }



}
