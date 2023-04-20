package fr.tahia910.android.positivityboost.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.tahia910.android.positivityboost.model.AnimalItem
import fr.tahia910.android.positivityboost.model.Result
import fr.tahia910.android.positivityboost.repository.AnimalRepository
import fr.tahia910.android.positivityboost.repository.QuoteRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(
    private val quoteRepository: QuoteRepository,
    private val animalRepository: AnimalRepository
) : ViewModel() {

    private val _currentAnimalType = MutableLiveData(AnimalType.CAT)

    private var _quoteItem = MediatorLiveData<Result<String>>()
    val quoteItem: LiveData<Result<String>> = _quoteItem

    private var _animalItem = MediatorLiveData<Result<AnimalItem>>()
    val animalItem: LiveData<Result<AnimalItem>> = _animalItem

    fun refresh() {
        _quoteItem.value = Result.loading(null)
        _animalItem.value = Result.loading(null)
        getQuote()
        getRandomAnimal()
    }

    private fun getQuote() {
        viewModelScope.launch {
            quoteRepository.getFlowQuote()
                .catch {
                    _quoteItem.value = Result.error()
                }.collect { result ->
                    _quoteItem.value = Result.success(result.affirmation)
                }
        }
    }

    private fun getRandomAnimal() {
        if (_currentAnimalType.value == AnimalType.DOG) {
            getCat()
            _currentAnimalType.value = AnimalType.CAT
        } else {
            getDog()
            _currentAnimalType.value = AnimalType.DOG
        }
    }

    private fun getDog() {
        viewModelScope.launch {
            animalRepository.getRandomDog()
                .catch {
                    _animalItem.value = Result.error()
                }.collect { result ->
                    _animalItem.value = Result.success(result[0])
                }
        }
    }

    private fun getCat() {
        viewModelScope.launch {
            animalRepository.getRandomCat()
                .catch {
                    _animalItem.value = Result.error()
                }.collect { result ->
                    _animalItem.value = Result.success(result[0])
                }
        }
    }
}

enum class AnimalType { DOG, CAT }
