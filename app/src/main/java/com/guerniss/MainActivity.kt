package com.guerniss

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.guerniss.DB.DatabaseHelper
import com.guerniss.adapter.HomeAdapter
import com.guerniss.adapter.LocalDataHomeAdapter
import com.guerniss.adapter.SlidingImage_Adapter
import com.guerniss.model.AllProducts
import com.guerniss.model.ImageModel
import com.guerniss.model.Product
import com.guerniss.model.SingleCategoryProduct
import com.guerniss.utils.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jsoup.Jsoup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private var context: Context? = null
    private val TAG = "tag"
    private var toolbar: Toolbar? = null
    private var drawerLayout: DrawerLayout? = null
    private var recyclerView1: RecyclerView? = null
    private var allProductlist = ArrayList<AllProducts>()
    private var mAdapter: HomeAdapter? = null
    private var eyeLayout : LinearLayout? = null
    private var faceLayout : LinearLayout? = null
    private var lipsLayout : LinearLayout? = null
    private var makeupLayout : LinearLayout? = null
    private var nailLayout : LinearLayout? = null
    private var skincareLayout : LinearLayout? = null
    private var serumLayout : LinearLayout? = null
    private var haircareLayout : LinearLayout? = null
    private var accessoriesLayout : LinearLayout? = null
    var currentVersion: String? = null
    private var imageModelArrayList: java.util.ArrayList<ImageModel>? = null

    private val myImageList = intArrayOf(R.drawable.covera, R.drawable.cover,R.drawable.coverb, R.drawable.coverc)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        context=this
        imageModelArrayList = java.util.ArrayList()
        imageModelArrayList = populateList()
        initUi()
        appUpdate()
    }

    private fun appUpdate() {
        try {
            currentVersion = packageManager.getPackageInfo(packageName, 0).versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        CheckIsUpdateReady(
            "https://play.google.com/store/apps/details?id=$packageName&hl=en",
            object : UrlResponce() {
                override fun onReceived(resposeStr: String?) {
                    //Toast.makeText(getBaseContext(),resposeStr,Toast.LENGTH_SHORT).show();

                    //resposeStr = "6.0.0";
                    if (!currentVersion.equals(
                            resposeStr,
                            ignoreCase = true
                        ) && null != resposeStr
                    ) {
                        //show dialog
                        //Toast.makeText(this, "Update App", Toast.LENGTH_SHORT).show();
                        val builder = AlertDialog.Builder(
                            context!!
                        )
                        builder.setTitle(R.string.app_update_available)
                            .setMessage(R.string.app_update_text)
                            .setPositiveButton(android.R.string.yes,
                                DialogInterface.OnClickListener { dialog, which -> // continue to play store
                                    val appPackageName =
                                        context!!.packageName // getPackageName() from Context or Activity object
                                    try {
                                        context!!.startActivity(
                                            Intent(
                                                Intent.ACTION_VIEW,
                                                Uri.parse("market://details?id=$appPackageName")
                                            )
                                        )
                                    } catch (anfe: ActivityNotFoundException) {
                                        context!!.startActivity(
                                            Intent(
                                                Intent.ACTION_VIEW,
                                                Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                                            )
                                        )
                                    }
                                })
                            .setNegativeButton(android.R.string.no,
                                DialogInterface.OnClickListener { dialog, which ->
                                    // do nothing
                                })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show()
                    }
                }
            }).execute()

    }


    abstract class UrlResponce {
        abstract fun onReceived(resposeStr: String?)
    }

     class CheckIsUpdateReady(appURL: String, callback: UrlResponce) :
        AsyncTask<Void?, String?, String?>() {
        var appURL = ""
        private val mUrlResponce: UrlResponce
         override fun doInBackground(vararg params: Void?): String? {
            var newVersion: String? = null
            try {
                val document = Jsoup.connect(appURL)
                    .timeout(20000)
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com")
                    .get()
                if (document != null) {
                    val element = document.getElementsContainingOwnText("Current Version")
                    for (ele in element) {
                        if (ele.siblingElements() != null) {
                            val sibElemets = ele.siblingElements()
                            for (sibElemet in sibElemets) {
                                newVersion = sibElemet.text()
                            }
                        }
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return newVersion
        }

        override fun onPostExecute(onlineVersion: String?) {
            super.onPostExecute(onlineVersion)
            if (onlineVersion != null && !onlineVersion.isEmpty()) {
                mUrlResponce.onReceived(onlineVersion)
            }
            Log.d("update", " playstore App version $onlineVersion")
        }

        init {
            this.appURL = appURL
            mUrlResponce = callback
        }


    }

    override fun onResume() {
        super.onResume()

        //Log.e("imgurl","uyy "+PersistData.getLoginData(context!!).user.image)
        if (PersistData.isLogged(context!!)){
            profile_image.visibility = View.VISIBLE
            Glide.with(context!!)
                .load(PersistData.getLoginData(context!!).imageUrl+"/"+PersistData.getLoginData(context!!).user.image)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(profile_image)


        }else{
            profile_image.visibility = View.INVISIBLE
        }

        val nav_Menu = navigationView.menu
        if (PersistData.isLogged(context!!)){
            nav_Menu.findItem(R.id.nav_logout).isVisible = true
        }else{
            nav_Menu.findItem(R.id.nav_logout).isVisible = false
        }

    }
    private fun populateList(): java.util.ArrayList<ImageModel> {

        val list = java.util.ArrayList<ImageModel>()

        for (i in 0..3) {
            val imageModel = ImageModel()
            imageModel.setImage_drawables(myImageList[i])
            list.add(imageModel)
        }

        return list
    }
    @SuppressLint("NewApi", "WrongConstant")
    private fun initUi() {

        mPager = findViewById(R.id.quizeViewpager) as ViewPager
        mPager!!.adapter = SlidingImage_Adapter(this, this.imageModelArrayList!!)

        NUM_PAGES = imageModelArrayList!!.size

        // Auto start of viewpager
        val handler = Handler()
        val Update = Runnable {
            if (currentPage == NUM_PAGES) {
                currentPage = 0
            }
            mPager!!.setCurrentItem(currentPage++, true)
        }
        val swipeTimer = Timer()
        swipeTimer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(Update)
            }
        }, 3000, 3000)

        if (PersistData.isLogged(context!!)){
            profile_image.visibility = View.VISIBLE
        }else{
            profile_image.visibility = View.INVISIBLE
        }

        profile_image.setOnClickListener {
            startActivity(Intent(context, ProfileActivity::class.java))
        }
        cart.setOnClickListener {
            if (AppConstant.cartList.size > 0) {
                startActivity(Intent(context, CartActivity::class.java))
             } else {
                Toast.makeText(context, "No cart available", Toast.LENGTH_SHORT).show()
             }
        }
         eyeLayout = findViewById(R.id.eyeLayout) as LinearLayout
        faceLayout = findViewById(R.id.faceLayout) as LinearLayout
        lipsLayout = findViewById(R.id.lipsLayout) as LinearLayout
        makeupLayout = findViewById(R.id.makeupLayout) as LinearLayout
        nailLayout = findViewById(R.id.nailLayout) as LinearLayout
        skincareLayout = findViewById(R.id.skincareLayout) as LinearLayout
        serumLayout = findViewById(R.id.serumLayout) as LinearLayout
        haircareLayout = findViewById(R.id.haircareLayout) as LinearLayout
        accessoriesLayout = findViewById(R.id.accessoriesLayout) as LinearLayout

        eyeLayout!!.setOnClickListener {
            Toast.makeText(context,"Eye",Toast.LENGTH_SHORT).show()
            startActivity(Intent(context, EyeActivity::class.java))
        }
        faceLayout!!.setOnClickListener {
            Toast.makeText(context,"Face",Toast.LENGTH_SHORT).show()
            startActivity(Intent(context, FaceActivity::class.java))
        }
        lipsLayout!!.setOnClickListener {
            Toast.makeText(context,"Lip",Toast.LENGTH_SHORT).show()
            startActivity(Intent(context, LipActivity::class.java))
        }
        makeupLayout!!.setOnClickListener {
            Toast.makeText(context,"Makeup",Toast.LENGTH_SHORT).show()
            startActivity(Intent(context, MakeupActivity::class.java))
        }
        nailLayout!!.setOnClickListener {
            Toast.makeText(context,"Nail",Toast.LENGTH_SHORT).show()
            startActivity(Intent(context,NailActivity ::class.java))
        }
        skincareLayout!!.setOnClickListener {
            Toast.makeText(context,"Skin Care",Toast.LENGTH_SHORT).show()
            startActivity(Intent(context, SkincareActivity::class.java))
        }
        serumLayout!!.setOnClickListener {
            Toast.makeText(context,"Serum",Toast.LENGTH_SHORT).show()
            startActivity(Intent(context, SerumActivity::class.java))
        }
        haircareLayout!!.setOnClickListener {
            Toast.makeText(context,"Hair Care",Toast.LENGTH_SHORT).show()
            startActivity(Intent(context, HaircareActivity::class.java))
        }
        accessoriesLayout!!.setOnClickListener {
            Toast.makeText(context,"Accessories",Toast.LENGTH_SHORT).show()
            startActivity(Intent(context, AccessoriesActivity::class.java))
        }


        toolbar = findViewById(R.id.toolbar)as Toolbar
        drawerLayout = findViewById(R.id.drawerLayout)as DrawerLayout
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        var drawerToggle = object : ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        ) {
            override fun onDrawerClosed(view: View) {
                super.onDrawerClosed(view)
                setTitle(R.string.app_name)
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                setTitle(R.string.app_name)
            }
        }


        drawerToggle.isDrawerIndicatorEnabled = true
        drawerLayout!!.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        // val view = navigationView.getHeaderView(0)
        //view!!.tvName.text = context?.let { PersistData.getLoginData(it).name }

        navigationView.setNavigationItemSelectedListener{
            when (it.itemId){
                R.id.nav_participant_list -> {
                    // Toast.makeText(context,"toast",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(context, LoginActivity::class.java))
                    drawerLayout!!.closeDrawer(GravityCompat.START)
                }

                R.id.nav_onusuchi_report -> {
                    //  Toast.makeText(context,"toast",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(context, CategoriesActivity::class.java))
                    drawerLayout!!.closeDrawer(GravityCompat.START)
                }


                R.id.nav_order_list -> {
                    if (PersistData.isLogged(context!!)){
                        startActivity(Intent(context,OrderListActivity::class.java))
                    }else{
                        Toast.makeText(context, "Please login first", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(context,LoginActivity::class.java))
                    }
//                   drawerLayout!!.closeDrawer(GravityCompat.START)
                }

                R.id.nav_logout -> {
                    PersistData.logOut(context!!)

                    if (PersistData.isLogged(context!!)){
                        profile_image.visibility = View.VISIBLE
                    }else{
                        profile_image.visibility = View.INVISIBLE
                    }

                    val nav_Menu = navigationView.menu
                    if (PersistData.isLogged(context!!)){
                        nav_Menu.findItem(R.id.nav_logout).isVisible = true
                    }else{
                        nav_Menu.findItem(R.id.nav_logout).isVisible = false
                    }

                    drawerLayout!!.closeDrawer(GravityCompat.START)
                }


            }
            // Close the drawer
            true
        }

        val nav_Menu = navigationView.menu
        if (PersistData.isLogged(context!!)){
            nav_Menu.findItem(R.id.nav_logout).isVisible = true
        }else{
            nav_Menu.findItem(R.id.nav_logout).isVisible = false
        }



//        btnScan = findViewById(R.id.btnScan)as Button
//        btnScan!!.setOnClickListener {
//
//            startActivity(Intent(context, ScanActivity::class.java))
//        }

        recyclerView1 = findViewById(R.id.recyclerViewHome) as RecyclerView


        if (!NetInfo.isOnline(context!!)){
            //var databaseHelper = DatabaseHelper(context)

           // val localProdlist = databaseHelper.localDbProduct()
//            Log.e("name", "nam: " + databaseHelper.localDbProduct()[0].prod_name)
            //imgHomeGal.setImageBitmap(localProdlist[0].image)

//            val gridLayoutManager = GridLayoutManager(applicationContext, 2)
//            var mAdapterLocal = LocalDataHomeAdapter(
//                context!!,
//                this,
//                localProdlist
//            )
//            recyclerView1!!.layoutManager = gridLayoutManager
//            recyclerView1!!.itemAnimator = DefaultItemAnimator()
//            recyclerView1!!.adapter = mAdapterLocal

        }else{
            getHome_all_products()
        }

    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu to use in the action bar
//        val inflater = menuInflater
//        inflater.inflate(R.menu.nav_menu, menu)
//        return super.onCreateOptionsMenu(menu)
//    }
//        override fun onOptionsItemSelected(item: MenuItem?): Boolean {
//        when (item!!.itemId) {
//            R.id.search -> {
//                Toast.makeText(context, "toast", Toast.LENGTH_SHORT).show()
//                //startActivity(Intent(context,CategoriesActivity::class.java))
//                return true
//            }
//            R.id.setting -> {
//                //Toast.makeText(context,"toast",Toast.LENGTH_SHORT).show()
//                if (AppConstant.cartList.size > 0) {
//                    startActivity(Intent(context, CartActivity::class.java))
//                } else {
//                    Toast.makeText(context, "No cart available", Toast.LENGTH_SHORT).show()
//                }
//
//                return true
//            }
//
//        }
//        return super.onOptionsItemSelected(item)
//    }
    private fun prepareMoviesData() {

        var bmp1: Bitmap = BitmapFactory.decodeResource(
            resources,
            R.drawable.guerniss_logo_without_bg
        )
        //var bmp2: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.gliter_eyeliner)
        val products = ArrayList<AllProducts>()
        products.add(AllProducts(bmp1))
       // products.add(AllProducts(bmp2))
        mAdapter!!.notifyDataSetChanged()
    }


//    private fun checkAndRequestPermissions(): Boolean {
//
//        val camerapermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
//        val writepermission = ContextCompat.checkSelfPermission(
//            this,
//            Manifest.permission.WRITE_EXTERNAL_STORAGE
//        )
//        val permissionLocation = ContextCompat.checkSelfPermission(
//            this,
//            Manifest.permission.ACCESS_FINE_LOCATION
//        )
//        val permissionRecordAudio = ContextCompat.checkSelfPermission(
//            this,
//            Manifest.permission.RECORD_AUDIO
//        )
//
//
//        val listPermissionsNeeded = ArrayList<String>()
//
//        if (camerapermission != PackageManager.PERMISSION_GRANTED) {
//            listPermissionsNeeded.add(Manifest.permission.CAMERA)
//        }
//
//        if (writepermission != PackageManager.PERMISSION_GRANTED) {
//            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//        }
//        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
//            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION)
//        }
//        if (permissionRecordAudio != PackageManager.PERMISSION_GRANTED) {
//            listPermissionsNeeded.add(Manifest.permission.RECORD_AUDIO)
//        }
//        if (!listPermissionsNeeded.isEmpty()) {
//            ActivityCompat.requestPermissions(
//                this, listPermissionsNeeded.toTypedArray(),
//                MainActivity.REQUEST_ID_MULTIPLE_PERMISSIONS
//            )
//            return false
//        }
//        return true
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>, grantResults: IntArray
//    ) {
//        Log.d(TAG, "Permission callback called-------")
//        when (requestCode) {
//            REQUEST_ID_MULTIPLE_PERMISSIONS -> {
//
//                val perms = HashMap<String, Int>()
//                // Initialize the map with both permissions
//                perms[Manifest.permission.CAMERA] = PackageManager.PERMISSION_GRANTED
//                perms[Manifest.permission.WRITE_EXTERNAL_STORAGE] =
//                    PackageManager.PERMISSION_GRANTED
//                perms[Manifest.permission.ACCESS_FINE_LOCATION] = PackageManager.PERMISSION_GRANTED
//                perms[Manifest.permission.RECORD_AUDIO] = PackageManager.PERMISSION_GRANTED
//                // Fill with actual results from user
//                if (grantResults.size > 0) {
//                    for (i in permissions.indices)
//                        perms[permissions[i]] = grantResults[i]
//                    // Check for both permissions
//                    if (perms[Manifest.permission.CAMERA] == PackageManager.PERMISSION_GRANTED
//                        && perms[Manifest.permission.WRITE_EXTERNAL_STORAGE] == PackageManager.PERMISSION_GRANTED
//                        && perms[Manifest.permission.ACCESS_FINE_LOCATION] == PackageManager.PERMISSION_GRANTED
//                        && perms[Manifest.permission.RECORD_AUDIO] == PackageManager.PERMISSION_GRANTED
//                    ) {
//                        Log.d(TAG, "sms & location services permission granted")
//                        // process the normal flow
////                        val i = Intent(this@MainActivity, WelcomeActivity::class.java)
////                        startActivity(i)
////                        finish()
//                        //else any one or both the permissions are not granted
//                    } else {
//                        Log.d(TAG, "Some permissions are not granted ask again ")
//                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                        //                        // shouldShowRequestPermissionRationale will return true
//                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
//                        if (ActivityCompat.shouldShowRequestPermissionRationale(
//                                this,
//                                Manifest.permission.CAMERA
//                            )
//                            || ActivityCompat.shouldShowRequestPermissionRationale(
//                                this,
//                                Manifest.permission.WRITE_EXTERNAL_STORAGE
//                            )
//                            || ActivityCompat.shouldShowRequestPermissionRationale(
//                                this,
//                                Manifest.permission.ACCESS_FINE_LOCATION
//                            )
//                            || ActivityCompat.shouldShowRequestPermissionRationale(
//                                this,
//                                Manifest.permission.RECORD_AUDIO
//                            )
//                        ) {
//                            showDialogOK("Service Permissions are required for this app",
//                                DialogInterface.OnClickListener { dialog, which ->
//                                    when (which) {
//                                        DialogInterface.BUTTON_POSITIVE -> checkAndRequestPermissions()
//                                        DialogInterface.BUTTON_NEGATIVE ->
//                                            // proceed with logic by disabling the related features or quit the app.
//                                            finish()
//                                    }
//                                })
//                        } else {
//                            //explain("You need to give some mandatory permissions to continue. Do you want to go to app settings?")
//                            //                            //proceed with logic by disabling the related features or quit the app.
//                        }//permission is denied (and never ask again is  checked)
//                        //shouldShowRequestPermissionRationale will return false
//                    }
//                }
//            }
//        }
//
//    }

    private fun showDialogOK(message: String, okListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("OK", okListener)
            .setNegativeButton("Cancel", okListener)
            .create()
            .show()
    }

    private fun explain(msg: String) {
        val dialog = AlertDialog.Builder(this)
        dialog.setMessage(msg)
            .setPositiveButton("Yes") { paramDialogInterface, paramInt ->
                //  permissionsclass.requestPermission(type,code);
                startActivity(
                    Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:com.example.parsaniahardik.kotlindrawerbasic")
                    )
                )
            }
            .setNegativeButton("Cancel") { paramDialogInterface, paramInt -> finish() }
        dialog.show()
    }

    companion object {

        val REQUEST_ID_MULTIPLE_PERMISSIONS = 1
        private val SPLASH_TIME_OUT = 2000
        private var mPager: ViewPager? = null
        private var currentPage = 0
        private var NUM_PAGES = 0
    }


    fun getHome_all_products() {


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

        val call: Call<SingleCategoryProduct> = api.home_all_products()

        call.enqueue(object : Callback<SingleCategoryProduct?> {

            override fun onResponse(
                call: Call<SingleCategoryProduct?>,
                response: Response<SingleCategoryProduct?>
            ) {
                pd.dismiss()
                val result = response.body()
                //Log.e("Response",""+result)
                if (result != null) {
                   // var dbhelper = DatabaseHelper(context)
                   // val db: SQLiteDatabase = dbhelper.getWritableDatabase() // helper is object extends SQLiteOpenHelper
                   // dbhelper.delete(db)

                    AppConstant.productListForColor.clear()
                    for ((index, value) in result.data.withIndex()) {
                        if (!TextUtils.isEmpty(value.hex_color_code) || value.hex_color_code != null) {
                            AppConstant.productListForColor.add(value)
                        }
                    }

                    val gridLayoutManager = GridLayoutManager(applicationContext, 2)
                    mAdapter = HomeAdapter(
                        context!!,
                        result.data as ArrayList<Product>,
                        result.imageUrl
                    )
                    recyclerView1!!.layoutManager = gridLayoutManager
                    recyclerView1!!.itemAnimator = DefaultItemAnimator()
                    recyclerView1!!.adapter = mAdapter
                }

            }

            override fun onFailure(call: Call<SingleCategoryProduct?>, t: Throwable) {
                pd.dismiss()
            }
        })

    }

    fun logout() {

        //var url = "api/customer/logout?phone="+PersistData.&password=12345678"

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

        val call: Call<String> = api.logout("")

        call.enqueue(object : Callback<String?> {

            override fun onResponse(call: Call<String?>, response: Response<String?>) {
                pd.dismiss()
                val result = response.body()
                //Log.e("Response",""+result)
                if (response.code() == 200) {
                    Toast.makeText(context, "Logout success", Toast.LENGTH_SHORT).show()
                    PersistData.logOut(context!!)
                }

            }

            override fun onFailure(call: Call<String?>, t: Throwable) {
                pd.dismiss()
            }
        })

    }


}

