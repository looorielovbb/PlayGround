package xyz.looorielovbb.playground.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import xyz.looorielovbb.playground.data.remote.WanRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val wanRepository: WanRepository) : ViewModel() {
    val flowData = wanRepository.fetchArticles().cachedIn(viewModelScope)

}