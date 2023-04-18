package xyz.looorielovbb.playground.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import xyz.looorielovbb.playground.R
import xyz.looorielovbb.playground.databinding.FragmentFavBinding

class FavFragment : Fragment() {

    private lateinit var viewBinding: FragmentFavBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fav, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentFavBinding.bind(view)
        with(viewBinding) {

        }
        lifecycle.addObserver(object : DefaultLifecycleObserver {

        })
    }
}