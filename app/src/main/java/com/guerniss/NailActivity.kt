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
import com.guerniss.adapter.NailAdapter
import com.guerniss.fragment.lip.FragmentNailSticker
import com.guerniss.fragment.lip.Fragmentnailpolish
import com.guerniss.model.EyeModel
import com.guerniss.model.LipModel
import com.guerniss.model.NailModel
import kotlinx.android.synthetic.main.activity_nail.*
import java.util.ArrayList

class NailActivity : AppCompatActivity() {
    private var context: Context? = null
    private val TAG = "tag"
    private var toolbar: Toolbar? = null
    private var drawerLayout: DrawerLayout? = null
    private var recyclerViewnail: RecyclerView? = null
    private var nailProductlist = ArrayList<NailModel>()
    private var nailAdapter: NailAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nail)
        context=this

        initUi()
    }
    @SuppressLint("NewApi", "WrongConstant")
    private fun initUi() {

        toolbar = findViewById(R.id.toolbar)as Toolbar
        drawerLayout = findViewById(R.id.drawerLayout)as DrawerLayout
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        replaceFragment(Fragmentnailpolish())
        accessoriesItem1.setOnClickListener {
            replaceFragment(Fragmentnailpolish())
        }

        accessoriesItem2.setOnClickListener {
            replaceFragment(FragmentNailSticker())
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

//        var bmp: Bitmap = BitmapFactory.decodeResource(resources,R.drawable.gliter_eyeliner)
//
//
//        var products = NailModel(bmp)
//        nailProductlist.add(products)
//        for (i in 1..10) {
//            nailProductlist.add(products)
//        }
//
//        nailAdapter!!.notifyDataSetChanged()
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
