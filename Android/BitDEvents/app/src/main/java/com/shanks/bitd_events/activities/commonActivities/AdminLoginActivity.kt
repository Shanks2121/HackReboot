package com.shanks.bitd_events.activities.commonActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.shanks.bitd_events.R
import com.shanks.bitd_events.activities.GdscActivities.GdscAdminActivity
import com.shanks.bitd_events.activities.HackClubActivities.HackClubActivity
import com.shanks.bitd_events.activities.HackClubActivities.HackClubAdminActivity
import kotlinx.android.synthetic.main.activity_login_admin.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AdminLoginActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_admin)

        auth = FirebaseAuth.getInstance()

        btnLoginAdmin.setOnClickListener {
            adminLogIn()
        }


    }

    private fun adminLogIn() {
        val email: String = etEmailAdmin.editText?.text.toString()
        val password: String = etPasswordAdmin.editText?.text.toString()

        if (email.isBlank() || password.isBlank()) {

            when {
                email.isBlank() and password.isBlank() -> Toast.makeText(
                    this,
                    "Enter your email and password",
                    Toast.LENGTH_SHORT
                )
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
        CoroutineScope(Dispatchers.IO).launch {
            try {
                auth.signInWithEmailAndPassword(email, password).await()
                withContext(Dispatchers.Main) {
                    checkLogInState(email, password)
                }


            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@AdminLoginActivity, e.message, Toast.LENGTH_LONG).show()

                }
            }
        }


    }

    fun checkLogInState(email: String, password: String) {
        if (auth.currentUser == null) {
            Toast.makeText(this@AdminLoginActivity, "Error", Toast.LENGTH_LONG).show()
        } else {

            if (email == "gdscbitd@gmail.com" && password == "gdsc1234") {

                Toast.makeText(this, "LOG IN SUCCESFUL", Toast.LENGTH_LONG).show()

                val intent = Intent(this, GdscAdminActivity::class.java)
                startActivity(intent)
                finish()

            }

            if(email== "hackedclub@gmail.com" && password == "hack1234"){
                Toast.makeText(this, "LOG IN SUCCESSFUL", Toast.LENGTH_LONG).show()

                val intent = Intent(this, HackClubAdminActivity::class.java)
                startActivity(intent)
            }


        }

    }
}