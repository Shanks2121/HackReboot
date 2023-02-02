package com.shanks.bitd_events.activities.HackClubActivities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.shanks.bitd_events.R
import com.shanks.bitd_events.activities.commonActivities.LogInActivity
import com.shanks.bitd_events.activities.commonActivities.ProfileActivity
import com.shanks.bitd_events.models.Event
import kotlinx.android.synthetic.main.activity_admin_gdsc.*
import kotlinx.android.synthetic.main.activity_admin_gdsc.ivSelect
import kotlinx.android.synthetic.main.activity_hack_club_admin.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class HackClubAdminActivity : AppCompatActivity() {

    lateinit var auth : FirebaseAuth
    private val CollectionRef = Firebase.firestore.collection("hack")
    lateinit var storageRef : StorageReference
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private var imageUri : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hack_club_admin)

        auth = FirebaseAuth.getInstance()
        storageRef = FirebaseStorage.getInstance().reference.child("Images")

        //App bar
        setUpViews()

        btnSelectHack.setOnClickListener {
            resultLauncher.launch("image/*")

        }



        //Database backend
        btnHackSubmit.setOnClickListener {

            val title = etTitleHack.editText?.text.toString()
            val description = etDescriptionHack.editText?.text.toString()
            val lnk = etLinkHack.editText?.text.toString()
            val date = etDateHack.editText?.text.toString()
            upload(title, description, lnk, date)

        }

    }

    private val resultLauncher = registerForActivityResult(

        ActivityResultContracts.GetContent()){
        imageUri = it
        ivSelectHack.setImageURI(it)
    }

    private fun upload(title : String, des : String, lnk : String, date : String){

        storageRef = storageRef.child(System.currentTimeMillis().toString())
        imageUri?.let {
            storageRef.putFile(it).addOnCompleteListener { task ->
                if (task.isSuccessful){
                    storageRef.downloadUrl.addOnSuccessListener { uri ->

                        val url = uri.toString()

                        val event = Event(title, des, url, lnk, date)

                        save(event)

                    }

                }
                else{
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }



    private fun save(event: Event) =
        CoroutineScope(Dispatchers.IO).launch {

            try {
                CollectionRef.add(event).await()
                withContext(Dispatchers.Main){
                    Toast.makeText(this@HackClubAdminActivity, "Succesfully added", Toast.LENGTH_SHORT).show()
                    ivSelectHack.setImageResource(R.drawable.ic_baseline_image_24)
                    etTitleHack.editText?.text?.clear()
                    etDescriptionHack.editText?.text?.clear()
                    etLinkHack.editText?.text?.clear()
                    etDateHack.editText?.text?.clear()

                }


            } catch (e:Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(this@HackClubAdminActivity, e.message, Toast.LENGTH_SHORT).show()
                }

            }





        }

    fun setUpViews(){
        setSupportActionBar(abAdminBarHack)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, AdminDrawerHack,
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
        startActivity(intent)
        finish()

    }

}