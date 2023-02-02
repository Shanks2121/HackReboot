package com.shanks.bitd_events.activities.commonActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.shanks.bitd_events.R
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignUpActivity : AppCompatActivity() {

    lateinit var signUpAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        signUpAuth = FirebaseAuth.getInstance()
        btnSignUp.setOnClickListener {
            signUpNewUser()
        }

        tvLogIn.setOnClickListener {
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun signUpNewUser() {
        val email: String = etEmailSignUp.editText?.text.toString().lowercase()
        val password: String = etPasswordSignup.editText?.text.toString()
        val confirmPassword: String = etConfirmPasswordSignup.editText?.text.toString()

        if (email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            when {
                email.isBlank() -> Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show()
                password.isBlank() -> Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT)
                    .show()
                confirmPassword.isBlank() -> Toast.makeText(
                    this,
                    "confirm your password",
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

        if (password != confirmPassword) {
            Toast.makeText(this, "confirm password is invalid", Toast.LENGTH_SHORT).show()
            return
        }

        signUpAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Success please login", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, LogInActivity::class.java)
                    startActivity(intent)



                } else {
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }


    }

}