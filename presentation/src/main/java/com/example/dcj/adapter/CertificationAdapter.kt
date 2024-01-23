package com.example.dcj.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dcj.R
import com.example.dcj.base.Challenge

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

    private val textTitle: TextView = certificationItemView.findViewById(R.id.textTitle)
    fun setChallenge(chellenge: Challenge){
        textTitle.text = chellenge.title
    }
}
