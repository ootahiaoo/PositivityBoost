package fr.tahia910.android.positivityboost.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.tahia910.android.positivityboost.model.AnimalType
import fr.tahia910.android.positivityboost.model.SettingsLanguage
import fr.tahia910.android.positivityboost.repository.SettingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val settingsUiState: StateFlow<SettingsUiState> =
        settingsRepository.userSettingsStream
            .map { settings ->
                val editableSettings = EditableSettings(
                    animalImageFilters = settings.first,
                    preferredLanguage = settings.second
                )
                SettingsUiState.Success(settings = editableSettings)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = SettingsUiState.Loading
            )

    fun updateAnimalImageFilters(filterList: List<AnimalType>) {
        viewModelScope.launch {
            settingsRepository.setAnimalImageFilters(filterList)
        }
    }

    fun updatePreferredLanguage(language: SettingsLanguage) {
        viewModelScope.launch {
            settingsRepository.setPreferredLanguage(language)
        }
    }
}

data class EditableSettings(
    val animalImageFilters: List<AnimalType>,
    val preferredLanguage: SettingsLanguage
)

sealed interface SettingsUiState {
    object Loading : SettingsUiState
    data class Success(val settings: EditableSettings) : SettingsUiState
}
