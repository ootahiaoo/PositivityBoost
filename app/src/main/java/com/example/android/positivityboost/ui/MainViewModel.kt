package com.example.android.positivityboost.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.positivityboost.repository.QuoteRepository
import kotlinx.coroutines.launch

class MainViewModel(private val quoteRepository: QuoteRepository) : ViewModel() {

    private var _quoteItem = MediatorLiveData<String>()
    val quoteItem: LiveData<String> = _quoteItem

    fun getQuote() = viewModelScope.launch {
        _quoteItem.addSource(quoteRepository.getQuote(this), _quoteItem::setValue)
    }
}