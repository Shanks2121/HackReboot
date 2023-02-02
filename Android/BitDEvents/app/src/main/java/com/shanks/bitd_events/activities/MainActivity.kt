package com.shanks.bitd_events.activities

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.firebase.auth.FirebaseAuth
import com.shanks.bitd_events.R
import com.shanks.bitd_events.activities.GdscActivities.GdscActivity
import com.shanks.bitd_events.activities.HackClubActivities.HackClubActivity
import com.shanks.bitd_events.activities.commonActivities.LogInActivity
import com.shanks.bitd_events.activities.commonActivities.ProfileActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var auth : FirebaseAuth
      lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpViews()

        auth = FirebaseAuth.getInstance()

        cvGdscMain.setOnClickListener{
            val intent = Intent(this, GdscActivity::class.java)
            startActivity(intent)

        }

        cvHackClubMain.setOnClickListener {
            val intent = Intent(this, HackClubActivity::class.java)
            startActivity(intent)
        }



//        btnSignOut.setOnClickListener{
//            Toast.makeText(this, "signed out successfully", Toast.LENGTH_SHORT).show()
//            signOutAuth.signOut().also {
//                val intent = Intent(this, IntroActivity::class.java)
//                startActivity(intent)
//                finish()
//            }
//        }




    }

    fun setUpViews(){
        setSupportActionBar(abMainBar)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, MainDrawer,
            R.string.app_name,
            R.string.app_name
        )
        actionBarDrawerToggle.syncState()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun SignOut(item: MenuItem) {

        auth.signOut().also {
            Toast.makeText(this, "signed out successfully", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
            finish()


        }


    }

    fun toProfile(item: MenuItem){
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        finish()

    }
}