package com.guerniss.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.google.gson.Gson
import com.guerniss.model.LoginResponse

public class PersistData {



    companion object {
        private val PREFS_FILE_NAME = "AppPreferences"

        fun getStringData(ctx: Context, key:String):String {
            return ctx.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
                .getString(key, "")!!
        }
        fun setStringData(ctx: Context, key:String,
                          data:String) {
            val prefs = ctx.getSharedPreferences(
                    PREFS_FILE_NAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString(key, data)
            editor.commit()
        }

        fun getIntData(ctx:Context, key:String):Int {
            return ctx.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
                    .getInt(key, -1)
        }
        fun setIntData(ctx:Context, key:String,
                       value:Int) {
            val prefs = ctx.getSharedPreferences(
                    PREFS_FILE_NAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putInt(key, value)
            editor.commit()
        }

        fun getBooleanData(ctx:Context, key:String):Boolean {
            return ctx.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
                    .getBoolean(key, false)
        }
        fun setBooleanData(ctx:Context, key:String,
                           data:Boolean) {
            val prefs = ctx.getSharedPreferences(
                    PREFS_FILE_NAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putBoolean(key, data)
            editor.commit()
        }

        fun getFloatData(ctx:Context, key:String):Float {
            return ctx.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
                    .getFloat(key, 0.0f)
        }
        fun setFloatData(ctx:Context, key:String,
                         value:Float) {
            val prefs = ctx.getSharedPreferences(
                    PREFS_FILE_NAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putFloat(key, value)
            editor.commit()
        }


        fun getLongData(ctx:Context, key:String):Long {
            return ctx.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
                    .getLong(key, -1)
        }
        fun setLongData(ctx:Context, key:String,
                        value:Long) {
            val prefs = ctx.getSharedPreferences(
                    PREFS_FILE_NAME, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putLong(key, value)
            editor.commit()
        }

        fun logOut(c: Context) {
            val prefs = c.getSharedPreferences(
                PREFS_FILE_NAME,
                MODE_PRIVATE
            )
            prefs.edit().putBoolean("LOGIN", false).commit()
        }
        fun setLogin(c: Context) {
            val prefs = c.getSharedPreferences(
                PREFS_FILE_NAME,
                MODE_PRIVATE
            )
            prefs.edit().putBoolean("LOGIN", true).commit()
        }

        fun isLogged(c: Context): Boolean {
            val prefs = c.getSharedPreferences(
                PREFS_FILE_NAME,
                MODE_PRIVATE
            )
            return prefs.getBoolean("LOGIN", false)
        }

//        public static void saveLoginData(Context con, LoginData loginData) {
//            SharedPreferences mPrefs = con.getSharedPreferences("logindata",MODE_PRIVATE);
//            SharedPreferences.Editor prefsEditor = mPrefs.edit();
//            Gson gson = new Gson();
//            String json = gson.toJson(loginData);
//            prefsEditor.putString("logindata", json);
//            prefsEditor.commit();
//        }
//
        fun saveLoginData(ctx:Context,loginData:LoginResponse){
            val mPrefs = ctx.getSharedPreferences("logindata", MODE_PRIVATE)
            val prefsEditor = mPrefs.edit()
            val gson = Gson()
            val json = gson.toJson(loginData)
            prefsEditor.putString("logindata", json)
            prefsEditor.commit()
        }

        fun getLoginData(con:Context):LoginResponse {
            val mPrefs = con.getSharedPreferences("logindata", MODE_PRIVATE)
            val loginData : LoginResponse
            val gson = Gson()
            val json = mPrefs.getString("logindata", "")
            loginData = gson.fromJson(json, LoginResponse::class.java)
            return loginData
        }

    }



}