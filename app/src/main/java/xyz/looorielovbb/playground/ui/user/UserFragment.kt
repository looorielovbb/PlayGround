package xyz.looorielovbb.playground.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import xyz.looorielovbb.playground.R
import xyz.looorielovbb.playground.databinding.FragmentUserBinding

class UserFragment : Fragment() {
    private lateinit var viewBinding: FragmentUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentUserBinding.bind(view)
        with(viewBinding) {

        }
        lifecycle.addObserver(object : DefaultLifecycleObserver {

        })
    }
}