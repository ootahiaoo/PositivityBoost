package com.example.android.positivityboost.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.positivityboost.model.DogItem
import com.example.android.positivityboost.repository.DogRepository
import com.example.android.positivityboost.repository.QuoteRepository
import kotlinx.coroutines.launch
import org.koin.core.component.get

class MainViewModel(
    private val quoteRepository: QuoteRepository,
    private val dogRepository: DogRepository
) : ViewModel() {

    private var _quoteItem = MediatorLiveData<String>()
    val quoteItem: LiveData<String> = _quoteItem

    fun getQuote() = viewModelScope.launch {
        _quoteItem.addSource(quoteRepository.getQuote(this), _quoteItem::setValue)
        _dogItem.addSource(dogRepository.getDog(this), _dogItem::setValue)
    }

    private var _dogItem = MediatorLiveData<DogItem>()
    val dogItem: LiveData<DogItem> = _dogItem

    fun getDog() = viewModelScope.launch {
        _dogItem.addSource(dogRepository.getDog(this), _dogItem::setValue)
    }

    fun refresh() {
        getQuote()
        getDog()
    }
}