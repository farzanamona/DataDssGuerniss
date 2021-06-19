package com.guerniss

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.guerniss.adapter.EyeAdapter
import com.guerniss.fragment.eye.FragmentEyeLiner
import com.guerniss.fragment.eye.FragmentKajol
import com.guerniss.fragment.eye.FragmentMaskara
import com.guerniss.fragment.eye.FragmentShadow
import com.guerniss.model.EyeModel
import kotlinx.android.synthetic.main.activity_eye.*
import java.util.ArrayList

class EyeActivity : AppCompatActivity() {
    private var context: Context? = null
    private val TAG = "tag"
    private var toolbar: Toolbar? = null
    private var drawerLayout: DrawerLayout? = null
    private var recyclerViewEye: RecyclerView? = null
    private var eyeProductlist = ArrayList<EyeModel>()
    private var eyeAdapter: EyeAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eye)
        context=this

        initUi()
    }
    @SuppressLint("NewApi", "WrongConstant")
    private fun initUi() {

        toolbar = findViewById(R.id.toolbar)as Toolbar
        drawerLayout = findViewById(R.id.drawerLayout)as DrawerLayout
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //recyclerViewEye = findViewById(R.id.recyclerViewEye) as RecyclerView
//        val gridLayoutManager = GridLayoutManager(applicationContext, 2)
//        var bmp1: Bitmap = BitmapFactory.decodeResource(resources,R.drawable.mascara)
//        var bmp2: Bitmap = BitmapFactory.decodeResource(resources,R.drawable.gliter_eyeliner)
//        val products = ArrayList<EyeModel>()
//        products.add(EyeModel(bmp1))
//        products.add(EyeModel(bmp2))
//        products.add(EyeModel(bmp1))
//        products.add(EyeModel(bmp2))
//        products.add(EyeModel(bmp1))
//        products.add(EyeModel(bmp2))
//        products.add(EyeModel(bmp1))
//        products.add(EyeModel(bmp2))
//        products.add(EyeModel(bmp1))
//        products.add(EyeModel(bmp2))
//        products.add(EyeModel(bmp1))
//        products.add(EyeModel(bmp2))
//
//        eyeAdapter = EyeAdapter(context!!,products)
//        recyclerViewEye!!.layoutManager = gridLayoutManager
//        recyclerViewEye!!.itemAnimator = DefaultItemAnimator()
//        recyclerViewEye!!.adapter = eyeAdapter
        replaceFragment(FragmentKajol())
        eyeItem1.setOnClickListener {

            replaceFragment(FragmentKajol())
        }
        eyeItem2.setOnClickListener {

            replaceFragment(FragmentMaskara())
        }

        eyeItem2.setOnClickListener {

            replaceFragment(FragmentMaskara())
        }

        eyeItem4.setOnClickListener {

            replaceFragment(FragmentShadow())
        }

        eyeItem3.setOnClickListener {

                replaceFragment(FragmentEyeLiner())
            }


    }

    fun AppCompatActivity.replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.add(R.id.fragementView, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu to use in the action bar
        val inflater = menuInflater
        inflater.inflate(R.menu.nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.search -> {
                //Toast.makeText(context,"toast",Toast.LENGTH_SHORT).show()
                startActivity(Intent(context,CategoriesActivity::class.java))
                return true
            }
            R.id.setting -> {
                //Toast.makeText(context,"toast",Toast.LENGTH_SHORT).show()
                startActivity(Intent(context,CartActivity::class.java))
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }
    private fun eyeAllData() {

//        var bmp: Bitmap = BitmapFactory.decodeResource(resources,R.drawable.gliter_eyeliner)
//
//
//        var products = EyeModel(bmp)
//        eyeProductlist.add(products)
//        for (i in 1..10) {
//            eyeProductlist.add(products)
//        }
//
//        eyeAdapter!!.notifyDataSetChanged()
    }

    private fun showDialogOK(message: String, okListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("OK", okListener)
            .setNegativeButton("Cancel", okListener)
            .create()
            .show()
    }

    companion object {

        val REQUEST_ID_MULTIPLE_PERMISSIONS = 1
        private val SPLASH_TIME_OUT = 2000
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
