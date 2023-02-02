package com.shanks.bitd_events.activities.HackClubActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.shanks.bitd_events.R
import com.shanks.bitd_events.activities.commonActivities.LogInActivity
import com.shanks.bitd_events.activities.commonActivities.ProfileActivity
import com.shanks.bitd_events.activities.commonActivities.rvOnClickActivity
import com.shanks.bitd_events.adapter.MyAdapter
import com.shanks.bitd_events.models.Event
import kotlinx.android.synthetic.main.activity_hack_club.*
import kotlinx.android.synthetic.main.activity_main.*

class HackClubActivity : AppCompatActivity(), MyAdapter.OnClickEvent {

    private lateinit var recyclerView: RecyclerView
    private lateinit var Adapter: MyAdapter
    private lateinit var db : FirebaseFirestore
    private lateinit var actionBarDrawerToggle : ActionBarDrawerToggle
    private lateinit var auth: FirebaseAuth
    private val arraylist : ArrayList<Event> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hack_club)

        auth = FirebaseAuth.getInstance()

        setUpViews()
        EventChangeListner()

        recyclerView = findViewById(R.id.rvHack)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        Adapter = MyAdapter(arraylist, this, this)
        recyclerView.adapter = Adapter



    }

    override fun onItemClick(position: Int) {

        val info = arraylist[position]
        val title = info.title
        val des = info.description
        val url = info.imageUrl
        val lnk = info.registrationLink

        val intent = Intent(this, rvOnClickActivity::class.java)

        intent.putExtra("TITLE",title)
        intent.putExtra("DES",des)
        intent.putExtra("URL",url)
        intent.putExtra("LNK", lnk)

        startActivity(intent)



        Toast.makeText(this, info.title, Toast.LENGTH_SHORT).show()

    }

    private fun EventChangeListner() {
        db = FirebaseFirestore.getInstance()
        db.collection("hack").orderBy("title", Query.Direction.ASCENDING)
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {

                    if(error != null){
                        Log.e("FireStore error",error.message.toString())
                        return
                    }

                    for (dc : DocumentChange in value?.documentChanges!!){

                        if(dc.type == DocumentChange.Type.ADDED){

                            arraylist.add(dc.document.toObject(Event::class.java))

                        }


                    }

                    Adapter.notifyDataSetChanged()


                }


            }




            )


    }

    fun setUpViews(){
        setSupportActionBar(abMainBarHack)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, MainDrawerHack,
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