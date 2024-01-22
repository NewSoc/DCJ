package com.example.mylibrary.usecase

import com.example.mylibrary.model.DomainChallenge

// data에서 최근 본 챌린지 데이터를 가져오게 하는 것.
//


object GetChallengeRecent {

    fun execute() : MutableList<DomainChallenge> {
        val recent_challenge_list : MutableList<DomainChallenge> = mutableListOf()
        val input = DomainChallenge("백두산 호랑이 챌린지", "하이루 하이루" )
        recent_challenge_list.add(input)
        val input2 = DomainChallenge("버락 오바마 챌린지", "오바마는 15살의 소녀로 이른 나이에 대통령이 되엇다." )
        recent_challenge_list.add(input2)
        val input3 = DomainChallenge("고기라면치킨", "신촌에 고기는 통큰갈비가 맛있고 라면은 사실 안좋아하며 치킨은 레허반반이다." )
        recent_challenge_list.add(input3)

        return recent_challenge_list
    }
}