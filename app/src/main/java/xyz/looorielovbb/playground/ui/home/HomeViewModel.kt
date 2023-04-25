package xyz.looorielovbb.playground.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import xyz.looorielovbb.playground.data.remote.WanRepository
import xyz.looorielovbb.playground.pojo.Article
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val wanRepository: WanRepository) : ViewModel() {


    private val _listData: MutableLiveData<PagingData<Article>> = MutableLiveData()

    val listData: LiveData<PagingData<Article>>
        get() = _listData

    init {
        forceUpdate()
    }

    fun forceUpdate() {
        viewModelScope.launch {
            wanRepository.fetchArticles().collect {
                _listData.value = it
            }
        }
    }

//    val flowData = wanRepository.fetchArticles().cachedIn(viewModelScope)
}