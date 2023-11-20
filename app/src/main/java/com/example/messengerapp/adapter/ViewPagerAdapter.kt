package com.example.messengerapp.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(
    private val context: Context,
    fm: FragmentManager?,
    val list: ArrayList<Fragment>
): FragmentPagerAdapter(fm!!) {
    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        return list[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return Tab_Titles[position]
    }

    companion object{
        val Tab_Titles = arrayOf("Chats","Status","Call")
    }

}