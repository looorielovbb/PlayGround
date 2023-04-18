package xyz.looorielovbb.playground.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import xyz.looorielovbb.playground.R
import xyz.looorielovbb.playground.databinding.ActivityMainBinding
import xyz.looorielovbb.playground.ui.favorite.FavFragment
import xyz.looorielovbb.playground.ui.home.HomeFragment
import xyz.looorielovbb.playground.ui.user.UserFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var fragments: List<Fragment>

    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fragments = arrayListOf(HomeFragment(), FavFragment(), UserFragment())
        with(binding) {
            Log.d(TAG, "onCreate: ")
            viewpager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            viewpager2.adapter = object : FragmentStateAdapter(this@MainActivity) {
                override fun getItemCount(): Int {
                    return fragments.size
                }

                override fun createFragment(position: Int): Fragment {
                    return fragments[position]
                }

            }
            viewpager2.offscreenPageLimit = 3
            viewpager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    when (position) {
                        0 -> bottomNavi.selectedItemId = R.id.home
                        1 -> bottomNavi.selectedItemId = R.id.fav
                        2 -> bottomNavi.selectedItemId = R.id.user
                    }
                }
            })
            viewpager2.currentItem = 0
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