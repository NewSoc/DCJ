package com.example.dcj.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dcj.databinding.RecentChallengeReccyclerBinding
import com.example.mylibrary.model.DomainChallenge

class RecentChallengeAdapter(val listData:MutableList<DomainChallenge>,  private val listener: OnItemClickListener) : RecyclerView.Adapter<RecentChallengeAdapter.RecentChallengeViewHolder>() {

    //부모의 xml은 맨 처음 Mainactivity가 나올 수 있는 가능성이 있지 않을까? 여기넣야
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentChallengeViewHolder {
        val binding = RecentChallengeReccyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentChallengeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listData.size
    }


    // 이 때만들어지는거낙? 이 때
    override fun onBindViewHolder(holder: RecentChallengeViewHolder, position: Int) {
        val recent_challenge = listData.get(position)
        holder.setChallenge(recent_challenge)
        holder.itemView.setOnClickListener{listener.onItemClick(recent_challenge) }
        //여기넣어야하나?
    }

    class RecentChallengeViewHolder(val binding : RecentChallengeReccyclerBinding):RecyclerView.ViewHolder(binding.root){

        fun setChallenge(recent_challenge:DomainChallenge){
            with(binding){
                challengeName.text = "${recent_challenge.name}"
                challengeDetail.text="${recent_challenge.detail}"
            }
        }

    }

    interface OnItemClickListener {
        fun onItemClick(challenge: DomainChallenge)
    }

}