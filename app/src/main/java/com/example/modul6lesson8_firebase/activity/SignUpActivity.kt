package com.example.modul6lesson8_firebase.activity

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.modul6lesson8_firebase.R
import com.example.modul6lesson8_firebase.helper.Extensions.toast
import com.example.modul6lesson8_firebase.manager.AuthHandler
import com.example.modul6lesson8_firebase.manager.AuthManager
import java.lang.Exception

class SignUpActivity : BaseActivity() {

    val TAG = SignUpActivity::class.java.toString()
    lateinit var fullnameEt : EditText
    lateinit var emailEt : EditText
    lateinit var passwordEt : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        initViews()
    }

    private fun initViews(){
        fullnameEt = findViewById(R.id.et_fullname)
        emailEt = findViewById(R.id.et_email)
        passwordEt = findViewById(R.id.et_password)

        val b_signup = findViewById<Button>(R.id.b_signup)
        b_signup.setOnClickListener {
            if(emailEt.text.isEmpty() || passwordEt.text.isEmpty()){
                toast("Please fill all fields")
            }else{
                val email = emailEt.text.toString().trim()
                val password = passwordEt.text.toString().trim()

                if(isInvalidEmail(email) && isInvalidPassword(password)){
                    firebaseSignUp(email, password)
                }
            }
        }

        val tv_signin = findViewById<TextView>(R.id.tv_signin)
        tv_signin.setOnClickListener {
            callSignInActivity(this)
        }
    }

    fun firebaseSignUp(email : String, password : String){
        showLoading(this)
        AuthManager.signUp(email, password, object  : AuthHandler {
            override fun onSuccess() {
                toast("Sign up successfully")
                dismissLoading()
                callMainActivity(context)
            }

            override fun onError(exception: Exception?) {
                dismissLoading()
                Log.d("@@@", exception.toString())
                toast("Sign up failed")
            }
        })
    }
}