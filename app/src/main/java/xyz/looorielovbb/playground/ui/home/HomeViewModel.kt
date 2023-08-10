package xyz.looorielovbb.playground.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import xyz.looorielovbb.playground.data.remote.WanRepository
import xyz.looorielovbb.playground.pojo.BannerData
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val wanRepository: WanRepository) : ViewModel() {


    val articlesFlow = wanRepository.fetchArticles().flowOn(Dispatchers.IO).cachedIn(viewModelScope)

    val bannerData: MutableStateFlow<HomeState> = MutableStateFlow(HomeState.Loading)

    fun fetchBannerData() = viewModelScope.launch {
        bannerData.value = HomeState.Loading
        wanRepository.fetchBanner()
            .catch {
                bannerData.value = HomeState.Failure(it)
            }
            .mapNotNull {
                it
            }
            .collect {
                bannerData.value = HomeState.Success(it)
            }
    }

}

sealed class HomeState {
    data object Loading : HomeState()
    data class Failure(val e: Throwable) : HomeState()
    data class Success(val data: List<BannerData>) : HomeState()
}
