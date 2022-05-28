package com.example.modul6lesson8_firebase.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.modul6lesson8_firebase.R
import com.example.modul6lesson8_firebase.helper.Extensions.toast
import com.example.modul6lesson8_firebase.manager.AuthHandler
import com.example.modul6lesson8_firebase.manager.AuthManager
import java.lang.Exception

class SignInActivity : BaseActivity() {

    val TAG = SignInActivity::class.java.toString()
    lateinit var emailEt : EditText
    lateinit var passwordEt : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        initViews()
    }

    private fun initViews(){
        emailEt = findViewById(R.id.et_email)
        passwordEt = findViewById(R.id.et_password)
        val b_signin = findViewById<Button>(R.id.b_signin)

        b_signin.setOnClickListener {

            val email = emailEt.text.toString().trim()
            val password = passwordEt.text.toString().trim()

            if(emailEt.text.isEmpty()){
                emailEt.error = "Please enter email"
                emailEt.requestFocus()
            } else if(passwordEt.text.isEmpty()){
                passwordEt.error = "Please enter password"
                passwordEt.requestFocus()
            }else if(!Patterns.EMAIL_ADDRESS.matcher(emailEt.text.toString()).matches()){
                emailEt.error = "Please enter valid email"
                emailEt.requestFocus()

            }else if(!isInvalidPassword(password)){
                passwordEt.error = "Please enter valid email"
                passwordEt.requestFocus()
            }else{
                firebaseSignIn(email, password)
            }
        }

        val tv_signup = findViewById<TextView>(R.id.tv_signup)
        tv_signup.setOnClickListener {
            callSignUpActivity()
        }
    }

    fun firebaseSignIn(email : String, password : String){
        showLoading(this)
        AuthManager.signIn(email, password, object  : AuthHandler{
            override fun onSuccess() {
                toast("Sign in successfully")
                dismissLoading()
                callMainActivity(context)
            }

            override fun onError(exception: Exception?) {
                dismissLoading()
                Log.d("@@@", exception.toString())
                toast("Sign in failed")
            }
        })
    }

    private fun callSignUpActivity() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
        finish()
    }
}