package com.shanks.bitd_events.activities.commonActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.shanks.bitd_events.R
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        firebaseAuth = FirebaseAuth.getInstance()
        txtEmail.text = firebaseAuth.currentUser?.email

        btnProfileLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
        }
    }
}