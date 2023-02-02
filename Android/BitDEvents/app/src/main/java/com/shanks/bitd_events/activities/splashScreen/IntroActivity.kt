package com.shanks.bitd_events.activities.splashScreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.shanks.bitd_events.R
import com.shanks.bitd_events.activities.commonActivities.LogInActivity
import com.shanks.bitd_events.activities.MainActivity
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : AppCompatActivity() {

    lateinit var introAuth : FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)



        introAuth = FirebaseAuth.getInstance()
        if (introAuth.currentUser != null){
            Toast.makeText(this, "User is already logged in!!!", Toast.LENGTH_SHORT).show()
            redirect("MAIN")
        }



        btnGetStarted.setOnClickListener {
                 redirect("LOGIN")
        }

    }

    private fun redirect(name : String){
        val intent = when(name) {
            "LOGIN" -> Intent(this, LogInActivity::class.java)
            "MAIN" -> Intent(this, MainActivity::class.java)
            else -> throw Exception("no path exists")

        }
        startActivity(intent)
        finish()

    }




}