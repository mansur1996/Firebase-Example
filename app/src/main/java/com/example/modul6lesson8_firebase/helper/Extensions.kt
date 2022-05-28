package com.example.modul6lesson8_firebase.helper

import android.app.Activity
import android.widget.Toast

object Extensions {
    fun Activity.toast(msg : String){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}