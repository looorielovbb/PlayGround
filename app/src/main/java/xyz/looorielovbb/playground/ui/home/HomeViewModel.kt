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
import xyz.looorielovbb.playground.data.remote.ApiState
import xyz.looorielovbb.playground.data.remote.WanRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val wanRepository: WanRepository) : ViewModel() {

    val articlesFlow = wanRepository.fetchArticles().flowOn(Dispatchers.IO).cachedIn(viewModelScope)


    val bannerData: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Empty)

    fun fetchBannerData() = viewModelScope.launch {
        bannerData.value = ApiState.Loading
        wanRepository.fetchBanner()
            .catch {
                bannerData.value = ApiState.Failure(it)
            }
            .mapNotNull {
                it
            }
            .collect {
                bannerData.value = ApiState.Success(it)
            }
    }

}