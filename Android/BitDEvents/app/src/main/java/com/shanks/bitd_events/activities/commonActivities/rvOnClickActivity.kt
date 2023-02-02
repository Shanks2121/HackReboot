package com.shanks.bitd_events.activities.commonActivities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.shanks.bitd_events.R
import kotlinx.android.synthetic.main.activity_rv_on_click.*


class rvOnClickActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rv_on_click)



        tvDetail.text = intent.getStringExtra("TITLE")
        tvDetailDes.text = intent.getStringExtra("DES")
        val url = intent.getStringExtra("URL")
        val lnk = intent.getStringExtra("LNK")
        Glide.with(this)
             .load(url)
             .fitCenter()
             .into(ivDetail)

        val i = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(lnk))

        btnDetailRegister.setOnClickListener {

            startActivity(i)
        }













    }
}