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
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.guerniss.adapter.EyeAdapter
import com.guerniss.adapter.FaceAdapter
import com.guerniss.fragment.face.*
import com.guerniss.model.EyeModel
import com.guerniss.model.FaceModel
import kotlinx.android.synthetic.main.activity_face.*
import java.util.ArrayList

class FaceActivity : AppCompatActivity() {
    private var context: Context? = null
    private val TAG = "tag"
    private var toolbar: Toolbar? = null
    private var drawerLayout: DrawerLayout? = null
    private var recyclerViewFace: RecyclerView? = null
    private var faceProductlist = ArrayList<FaceModel>()
    private var faceAdapter: FaceAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_face)
        context=this

        initUi()
    }
    @SuppressLint("NewApi", "WrongConstant")
    private fun initUi() {

        toolbar = findViewById(R.id.toolbar)as Toolbar
        drawerLayout = findViewById(R.id.drawerLayout)as DrawerLayout
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        replaceFragment(FragmentContour())

        faceItem1.setOnClickListener {
            replaceFragment(FragmentContour())
        }

        faceItem2.setOnClickListener {
            replaceFragment(FragmentPower())
        }

           faceItem3.setOnClickListener {
            replaceFragment(FragmentFoundation())
        }

         faceItem4.setOnClickListener {
                    replaceFragment(FragmentSettingsSpray())
                }


         faceItem5.setOnClickListener {
                    replaceFragment(FragmentBbCream())
                }


         faceItem6.setOnClickListener {
                            replaceFragment(FragmentConcealer())
                        }

         faceItem7.setOnClickListener {
                            replaceFragment(FragmentHeighLighter())
                        }
        faceItem8.setOnClickListener {
                            replaceFragment(FragmentMakeUpRemover())
                        }

        faceItem9.setOnClickListener {
                            replaceFragment(FragmentPrimer())
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
//        var products = FaceModel(bmp)
//        faceProductlist.add(products)
//        for (i in 1..10) {
//            faceProductlist.add(products)
//        }
//
//        faceAdapter!!.notifyDataSetChanged()
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
