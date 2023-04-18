package xyz.looorielovbb.playground.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import xyz.looorielovbb.playground.R
import xyz.looorielovbb.playground.databinding.FragmentFavBinding
import xyz.looorielovbb.playground.ext.binding

class FavFragment : Fragment(R.layout.fragment_fav) {

    private val binding by binding(FragmentFavBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

        }
        lifecycle.addObserver(object : DefaultLifecycleObserver {

        })
    }
}