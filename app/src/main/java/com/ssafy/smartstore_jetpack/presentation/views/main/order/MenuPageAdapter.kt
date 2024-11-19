package com.ssafy.smartstore_jetpack.presentation.views.main.order

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MenuPageAdapter(fm: FragmentActivity) : FragmentStateAdapter(fm) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val itemId = getItemId(position)

        return MenuPageFragment.newInstance(itemId)
    }

    override fun getItemId(position: Int): Long = (position - START_POSITION).toLong()

    override fun containsItem(itemId: Long): Boolean =
        ((itemId >= START_POSITION) && (itemId < 2))

    companion object {

        const val START_POSITION = 0
    }
}