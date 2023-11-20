package com.example.messengerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.messengerapp.activity.NumberActivity
import com.example.messengerapp.adapter.ViewPagerAdapter
import com.example.messengerapp.databinding.ActivityMain1Binding
import com.example.messengerapp.userInterface.CallFragment
import com.example.messengerapp.userInterface.ChatFragment
import com.example.messengerapp.userInterface.StatusFragment
import com.google.firebase.auth.FirebaseAuth

class MainActivity1 : AppCompatActivity() {
    private var binding: ActivityMain1Binding? = null
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain1Binding.inflate(layoutInflater)
        setContentView(binding!!.root)

        val fragmentArrayList = ArrayList<Fragment>()
        fragmentArrayList.add(ChatFragment())
        fragmentArrayList.add(StatusFragment())
        fragmentArrayList.add(CallFragment())

        auth = FirebaseAuth.getInstance()
        if (auth.currentUser==null){
            startActivity(Intent(this,NumberActivity::class.java))
            finish()
        }

        val adapter = ViewPagerAdapter(this,supportFragmentManager,fragmentArrayList)
        binding!!.viewPager.adapter = adapter
        binding!!.tabs.setupWithViewPager(binding!!.viewPager)
    }
}