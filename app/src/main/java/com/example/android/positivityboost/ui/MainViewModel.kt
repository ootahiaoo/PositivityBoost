package com.example.android.positivityboost.ui

import androidx.lifecycle.*
import com.example.android.positivityboost.model.QuoteItem
import com.example.android.positivityboost.repository.QuoteRepository
import kotlinx.coroutines.launch

class MainViewModel(private val quoteRepository: QuoteRepository) : ViewModel() {

    private var _quoteItem = MediatorLiveData<QuoteItem>()
    val quoteItem: LiveData<QuoteItem> = _quoteItem

    fun getQuote() = viewModelScope.launch {
        _quoteItem.addSource(quoteRepository.getQuote(this), _quoteItem::setValue)
    }
}