package com.guerniss

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_categories.*

class CategoriesActivity : AppCompatActivity() {
    private var context: Context? = null

    private var eyeLayout : LinearLayout? = null
    private var faceLayout : LinearLayout? = null
    private var lipsLayout : LinearLayout? = null
    private var makeupLayout : LinearLayout? = null
    private var nailLayout : LinearLayout? = null
    private var skincareLayout : LinearLayout? = null
    private var serumLayout : LinearLayout? = null
    private var haircareLayout : LinearLayout? = null
    private var accessoriesLayout : LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)
        context=this
        initUi()
    }
    private fun initUi(){
        setSupportActionBar(toolbar)
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar()!!.setDisplayShowHomeEnabled(true);

        val actionBar = supportActionBar

        // Set toolbar title/app title
        actionBar!!.title = getString(R.string.category)
        // set categories Layout
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

    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
