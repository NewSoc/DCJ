package com.example.dcj.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dcj.R
import com.example.dcj.databinding.FragmentLoginBinding
import com.example.mylibrary.usecase.GetChallengeRecent
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {


    lateinit var binding : FragmentLoginBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener{
            val intent = Intent(this.getActivity(), LoginActivity::class.java)
            startActivity(intent)
        }
    }
}