package xyz.looorielovbb.playground.ui.user

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import xyz.looorielovbb.playground.R
import xyz.looorielovbb.playground.databinding.FragmentUserBinding
import xyz.looorielovbb.playground.ext.binding

@AndroidEntryPoint
class UserFragment : Fragment(R.layout.fragment_user) {

    private val binding by binding(FragmentUserBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

        }
    }
}