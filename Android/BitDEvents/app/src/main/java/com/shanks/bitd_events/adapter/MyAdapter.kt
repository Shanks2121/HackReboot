package com.shanks.bitd_events.adapter




import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.shanks.bitd_events.R
import com.shanks.bitd_events.models.Event

class MyAdapter(
    private val List : ArrayList<Event>,
    private val context : Context,
    private val listener : OnClickEvent)
    : RecyclerView.Adapter<MyAdapter.ViewHolder>()
{


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.event_card, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val gdsc : Event = List[position]

        holder.title.text = gdsc.title
        holder.description.text = gdsc.description
        holder.date.text = gdsc.date


        Glide.with(context)
            .load(gdsc.imageUrl)
            .fitCenter()
            .into(holder.image)



    }

    override fun getItemCount(): Int {
        return List.size
    }

    interface OnClickEvent{
        fun onItemClick(position: Int)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val title : TextView = itemView.findViewById(R.id.tvEventTitle)
        val description : TextView = itemView.findViewById(R.id.tvEventDescription)
        val image : ImageView = itemView.findViewById(R.id.ivEventCard)
        val date : TextView = itemView.findViewById(R.id.tvEventDate)
        val openBtn : Button = itemView.findViewById(R.id.btnEventOpen)

        init {
            openBtn.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION){
                listener.onItemClick(position)

            }
        }

    }


    }


