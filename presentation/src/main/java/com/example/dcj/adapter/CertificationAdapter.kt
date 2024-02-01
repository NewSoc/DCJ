package com.example.dcj.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dcj.R
import com.example.dcj.base.Challenge
import com.example.dcj.view.DetailActivity

class CertificationAdapter : RecyclerView.Adapter<CertificationHolder>(){

    var listData = mutableListOf<Challenge>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CertificationHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.certification_item, parent, false)

        return CertificationHolder(itemView)

    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: CertificationHolder, position: Int) {
        val challenge = listData.get(position)
        holder.setChallenge(challenge)
    }
}

class CertificationHolder(certificationItemView: View) : RecyclerView.ViewHolder(certificationItemView){

    private val userName: TextView = certificationItemView.findViewById(R.id.userName)
    private val title: TextView = certificationItemView.findViewById(R.id.challengeTitle)
    private val category : TextView = certificationItemView.findViewById(R.id.category)
    private val timeStart : TextView = certificationItemView.findViewById(R.id.timeStart)
    fun setChallenge(chellenge: Challenge){

        itemView.setOnClickListener {
            val intent = Intent(itemView.context, DetailActivity::class.java)
            itemView.context.startActivity(intent)
        }

        userName.text = chellenge.username
        title.text = chellenge.title
        category.text = chellenge.category
        timeStart.text = chellenge.timestart
    }
}
