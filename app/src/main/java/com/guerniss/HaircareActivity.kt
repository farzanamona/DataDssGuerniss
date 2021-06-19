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
import com.guerniss.adapter.HaircareAdapter
import com.guerniss.adapter.LipAdapter
import com.guerniss.fragment.lip.FragmentHairCareConditioner
import com.guerniss.fragment.lip.FragmentHairCareShampo
import com.guerniss.model.HaircareModel
import com.guerniss.model.LipModel
import kotlinx.android.synthetic.main.activity_haircare.*
import java.util.ArrayList

class HaircareActivity : AppCompatActivity() {
    private var context: Context? = null
    private val TAG = "tag"
    private var toolbar: Toolbar? = null
    private var drawerLayout: DrawerLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_haircare)
        context=this

        initUi()
    }
    @SuppressLint("NewApi", "WrongConstant")
    private fun initUi() {

        toolbar = findViewById(R.id.toolbar)as Toolbar
        drawerLayout = findViewById(R.id.drawerLayout)as DrawerLayout
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        replaceFragment(FragmentHairCareShampo())

        haircareShampoo.setOnClickListener {
            replaceFragment(FragmentHairCareShampo())
        }
        haircareConditioner.setOnClickListener {
            replaceFragment(FragmentHairCareConditioner())
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
