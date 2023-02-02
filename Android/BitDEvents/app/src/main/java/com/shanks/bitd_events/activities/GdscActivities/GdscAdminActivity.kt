package com.shanks.bitd_events.activities.GdscActivities

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
import kotlinx.android.synthetic.main.activity_hack_club_admin.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class GdscAdminActivity : AppCompatActivity() {

    lateinit var auth : FirebaseAuth
    private val gdscCollectionRef = Firebase.firestore.collection("gdsc")
    lateinit var storageRef : StorageReference
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private var imageUri : Uri? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_gdsc)



        auth = FirebaseAuth.getInstance()
        storageRef = FirebaseStorage.getInstance().reference.child("Images")

        //App bar
        setUpViews()

        btnSelect.setOnClickListener {
            resultLauncher.launch("image/*")

        }

//        btnRegister.setOnClickListener {
//
//            val rDialogView = LayoutInflater.from(this).inflate(R.layout.link_dialog,null)
//
//            val rBuilder = AlertDialog.Builder(this).setView(rDialogView).setTitle("Registration Link & Date")
//
//            val rAlertDialog = rBuilder.show()
//
//            rDialogView.dbConfirm.setOnClickListener {
//
//                val register = etRegisterLink.editText?.text.toString()
//                val date = etDate.editText?.text.toString()
//
//
//
//                rAlertDialog.dismiss()
//
//
//            }





        //Database backend
        btnGdscSubmit.setOnClickListener {

        val title = etTitle.editText?.text.toString()
        val description = etDescription.editText?.text.toString()
        val lnk = etLink.editText?.text.toString()
        val date = etDate.editText?.text.toString()
        upload(title, description, lnk, date)

        }



    }




    private val resultLauncher = registerForActivityResult(

        ActivityResultContracts.GetContent()){
        imageUri = it
        ivSelect.setImageURI(it)
    }

    private fun upload(title : String, des : String, lnk : String, date : String){

        storageRef = storageRef.child(System.currentTimeMillis().toString())
        imageUri?.let {
            storageRef.putFile(it).addOnCompleteListener { task ->
                if (task.isSuccessful){
                    storageRef.downloadUrl.addOnSuccessListener { uri ->

                        val url = uri.toString()

                        val gdsc = Event(title, des, url, lnk, date)

                        save(gdsc)

                    }

                }
                else{
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }



    private fun save(gdsc: Event) =
            CoroutineScope(Dispatchers.IO).launch { 

                try {
                    gdscCollectionRef.add(gdsc).await()
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@GdscAdminActivity, "Succesfully added", Toast.LENGTH_SHORT).show()
                        ivSelect.setImageResource(R.drawable.ic_baseline_image_24)
                        etTitle.editText?.text?.clear()
                        etDescription.editText?.text?.clear()
                        etLink.editText?.text?.clear()
                        etDate.editText?.text?.clear()
                    }

                    
                } catch (e:Exception){
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@GdscAdminActivity, e.message, Toast.LENGTH_SHORT).show()
                    }

                }
                




    }

    fun setUpViews(){
        setSupportActionBar(abAdminBar)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, AdminDrawer,
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