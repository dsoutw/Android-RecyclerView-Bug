/**
 * Copyright 2023 Dyi-Shing Ou
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see <https://www.gnu.org/licenses/>.
 */

package com.dsou.recyclerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dsou.recyclerview.databinding.FragmentMainBinding
import com.dsou.recyclerview.list.MyListFragment
import com.google.android.material.tabs.TabLayoutMediator


class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = initBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PageAdapter(this)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = adapter.getTitle(position)
        }.attach()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMainBinding {
        return FragmentMainBinding.inflate(inflater, container, false)
    }

    private interface Page {
        val title: String
        fun createFragment(): Fragment

        companion object {

            val first = object : Page {
                override val title: String = "Page 1"
                override fun createFragment(): Fragment =
                    MyListFragment()
            }

            val second = object : Page {
                override val title: String = "Page 2"
                override fun createFragment(): Fragment =
                    Fragment()
            }

        }
    }

    private class PageAdapter(
        parentFragment: Fragment,
    ) : FragmentStateAdapter(parentFragment) {

        private val fragmentList: List<Page> =
            listOfNotNull(Page.first, Page.second)

        override fun getItemCount(): Int =
            fragmentList.size

        override fun createFragment(position: Int): Fragment =
            fragmentList[position].createFragment()

        fun getTitle(position: Int): String =
            fragmentList[position].title
    }
}