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
import com.guerniss.adapter.AccessoriesAdapter
import com.guerniss.adapter.EyeAdapter
import com.guerniss.fragment.accessories.FragmentAccessoriesBlender
import com.guerniss.fragment.accessories.FragmentAccessoriesBrush
import com.guerniss.fragment.accessories.FragmentAccessoriesPuff
import com.guerniss.model.AccessoriesModel
import com.guerniss.model.EyeModel
import kotlinx.android.synthetic.main.activity_accessories.*
import kotlinx.android.synthetic.main.activity_categories.*
import java.util.ArrayList

class AccessoriesActivity : AppCompatActivity() {
    private var context: Context? = null
    private val TAG = "tag"
    private var toolbar: Toolbar? = null
    private var drawerLayout: DrawerLayout? = null
    private var recyclerViewAccessories: RecyclerView? = null
    private var accessoriesProductlist = ArrayList<AccessoriesModel>()
    private var accessoriesAdapter: AccessoriesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accessories)
        context=this

        initUi()
    }
    @SuppressLint("NewApi", "WrongConstant")
    private fun initUi() {

        toolbar = findViewById(R.id.toolbar)as Toolbar
        drawerLayout = findViewById(R.id.drawerLayout)as DrawerLayout
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

       replaceFragment(FragmentAccessoriesBrush())

        accessoriesBrush.setOnClickListener {
            replaceFragment(FragmentAccessoriesBrush())
        }

        accessoriesPuff.setOnClickListener {
            replaceFragment(FragmentAccessoriesPuff())
        }
        accessoriesBlender.setOnClickListener {
            replaceFragment(FragmentAccessoriesBlender())
        }



    }

    fun AppCompatActivity.replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.add(R.id.fragementView, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
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

       // var bmp: Bitmap = BitmapFactory.decodeResource(resources,R.drawable.gliter_eyeliner)


       // var products = AccessoriesModel(bmp)
//        accessoriesProductlist.add(products)
//        for (i in 1..11) {
//            accessoriesProductlist.add(products)
//        }
//
//        accessoriesAdapter!!.notifyDataSetChanged()
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
}
