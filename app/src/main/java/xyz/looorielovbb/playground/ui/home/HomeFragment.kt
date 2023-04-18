package xyz.looorielovbb.playground.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import xyz.looorielovbb.playground.R
import xyz.looorielovbb.playground.databinding.FragmentHomeBinding
import xyz.looorielovbb.playground.ext.binding

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by binding(FragmentHomeBinding::bind)

    companion object {
        const val TAG = "HomeFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

        }

        lifecycle.addObserver(object : DefaultLifecycleObserver {

        })
    }
}