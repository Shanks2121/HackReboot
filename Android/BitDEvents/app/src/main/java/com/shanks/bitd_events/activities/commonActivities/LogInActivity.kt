package com.shanks.bitd_events.activities.commonActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.shanks.bitd_events.R
import com.shanks.bitd_events.activities.MainActivity
import kotlinx.android.synthetic.main.activity_log_in.*

class LogInActivity : AppCompatActivity() {

    lateinit var loginAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        loginAuth = FirebaseAuth.getInstance()
        btnLogin.setOnClickListener {
            logInUser()
        }

        tvSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)

        }

        btnToAdminLogin.setOnClickListener {
            val intent = Intent(this@LogInActivity, AdminLoginActivity::class.java)
            startActivity(intent)

        }

    }

    private fun logInUser() {
        val email: String = etEmail.editText?.text.toString().lowercase()
        val password: String = etPassword.editText?.text.toString()

        if (email.isBlank() || password.isBlank()) {

            when {
                email.isBlank() and password.isBlank() -> Toast.makeText(
                    this,
                    "Enter your email and password",
                     Toast.LENGTH_SHORT)
                    .show()

                email.isBlank() -> Toast.makeText(
                    this,
                    "Please enter your email",
                    Toast.LENGTH_SHORT
                ).show()
                password.isBlank() -> Toast.makeText(
                    this,
                    "Please enter your password",
                    Toast.LENGTH_SHORT
                ).show()
                else -> Toast.makeText(
                    this,
                    "email and password can't be empty",
                    Toast.LENGTH_SHORT
                ).show()

            }
            return

        }

        loginAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->

            if (task.isSuccessful) {
                Toast.makeText(this
                    , "LOG IN SUCCESSFUL"
                    , Toast.LENGTH_LONG)
                    .show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()

            } else {
                Toast.makeText(this, "INVALID CREDENTIALS", Toast.LENGTH_SHORT).show()
            }
        }

    }
}