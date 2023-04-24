package xyz.looorielovbb.playground.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import xyz.looorielovbb.playground.R
import xyz.looorielovbb.playground.databinding.ActivityMainBinding
import xyz.looorielovbb.playground.ext.binding
import xyz.looorielovbb.playground.ui.favorite.FavFragment
import xyz.looorielovbb.playground.ui.home.HomeFragment
import xyz.looorielovbb.playground.ui.user.UserFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by binding(ActivityMainBinding::inflate)
    private val fragments: Array<Fragment> by lazy {
        arrayOf(HomeFragment(), FavFragment(), UserFragment())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            with(viewpager2) {
                orientation = ViewPager2.ORIENTATION_HORIZONTAL
                offscreenPageLimit = 3
                adapter = object : FragmentStateAdapter(this@MainActivity) {
                    override fun getItemCount() = fragments.size
                    override fun createFragment(position: Int) = fragments[position]
                }
                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) =
                        when (position) {
                            0 -> bottomNavi.selectedItemId = R.id.home
                            1 -> bottomNavi.selectedItemId = R.id.fav
                            2 -> bottomNavi.selectedItemId = R.id.user
                            else -> {}
                        }
                })
            }

            bottomNavi.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.home -> {
                        viewpager2.currentItem = 0
                        true
                    }

                    R.id.fav -> {
                        viewpager2.currentItem = 1
                        true
                    }

                    R.id.user -> {
                        viewpager2.currentItem = 2
                        true
                    }

                    else -> false
                }
            }
        }
    }

}