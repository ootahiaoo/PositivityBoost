package fr.tahia910.android.positivityboost.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.tahia910.android.positivityboost.model.AnimalItem
import fr.tahia910.android.positivityboost.model.Status
import fr.tahia910.android.positivityboost.repository.AnimalRepository
import fr.tahia910.android.positivityboost.repository.QuoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val quoteRepository: QuoteRepository,
    private val animalRepository: AnimalRepository
) : ViewModel() {

    private val _animalImageUiState =
        MutableStateFlow<AnimalImageUiState>(AnimalImageUiState.Loading)
    val animalImageUiState: StateFlow<AnimalImageUiState> = _animalImageUiState

    private val _quoteUiState = MutableStateFlow<QuoteUiState>(QuoteUiState.Loading)
    val quoteUiState: StateFlow<QuoteUiState> = _quoteUiState

    init {
        refresh()
    }

    fun refresh() {
        getQuote()
        getAnimalImage()
    }

    private fun getQuote() {
        viewModelScope.launch {
            quoteRepository.quoteStream.collect { result ->
                val update = when (result.status) {
                    Status.ERROR -> QuoteUiState.Error
                    Status.LOADING -> QuoteUiState.Loading
                    Status.SUCCESS -> QuoteUiState.Success(quote = result.data)
                }
                _quoteUiState.emit(update)
            }
        }
    }

    private fun getAnimalImage() {
        viewModelScope.launch {
            animalRepository.getAnimal().collect { result ->
                val update = when (result.status) {
                    Status.ERROR -> AnimalImageUiState.Error
                    Status.LOADING -> AnimalImageUiState.Loading
                    Status.SUCCESS -> AnimalImageUiState.Success(image = result.data)
                }
                _animalImageUiState.emit(update)
            }
        }
    }
}

sealed interface QuoteUiState {
    object Error : QuoteUiState
    object Loading : QuoteUiState
    data class Success(val quote: String?) : QuoteUiState
}

sealed interface AnimalImageUiState {
    object Error : AnimalImageUiState
    object Loading : AnimalImageUiState
    data class Success(val image: AnimalItem?) : AnimalImageUiState
}
