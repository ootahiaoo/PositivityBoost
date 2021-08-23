package fr.tahia910.android.positivityboost.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.tahia910.android.positivityboost.model.DogItem
import fr.tahia910.android.positivityboost.model.Result
import fr.tahia910.android.positivityboost.repository.DogRepository
import fr.tahia910.android.positivityboost.repository.QuoteRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(
    private val quoteRepository: QuoteRepository,
    private val dogRepository: DogRepository
) : ViewModel() {

    private var _quoteItem = MediatorLiveData<Result<String>>()
    val quoteItem: LiveData<Result<String>> = _quoteItem

    private var _dogItem = MediatorLiveData<Result<DogItem>>()
    val dogItem: LiveData<Result<DogItem>> = _dogItem

    fun getQuote() {
        viewModelScope.launch {
            quoteRepository.getFlowQuote()
                .catch {
                    _quoteItem.value = Result.error()
                }.collect { result ->
                    _quoteItem.value = Result.success(result.affirmation)
                }
        }
    }

    fun getDog() {
        viewModelScope.launch {
            dogRepository.getRandomDog()
                .catch {
                    _dogItem.value = Result.error()
                }.collect { result ->
                    _dogItem.value = Result.success(result[0])
                }
        }
    }

    fun refresh() {
        getQuote()
        getDog()
    }
}