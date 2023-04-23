package fr.tahia910.android.positivityboost.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.tahia910.android.positivityboost.R
import fr.tahia910.android.positivityboost.model.AnimalType
import fr.tahia910.android.positivityboost.model.SettingsLanguage
import fr.tahia910.android.positivityboost.ui.component.LoadingAnimation
import fr.tahia910.android.positivityboost.ui.theme.LightAmber
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen(viewModel: SettingsViewModel = koinViewModel()) {
    val settingsUiState by viewModel.settingsUiState.collectAsStateWithLifecycle()

    when (settingsUiState) {
        SettingsUiState.Loading -> {
            LoadingAnimation(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 44.dp)
            )
        }

        is SettingsUiState.Success -> {
            val settings = (settingsUiState as SettingsUiState.Success).settings

            Column(
                Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                // Animal image filter option
                OptionGroupTitle(title = stringResource(id = R.string.settings_filter_title))
                AnimalImageFilterOptionGroup(
                    selectedOptionList = settings.animalImageFilters,
                    onOptionSelected = {
                        val updatedList = settings.animalImageFilters.toMutableList()
                        if (updatedList.contains(it)) updatedList.remove(it) else updatedList.add(it)
                        viewModel.updateAnimalImageFilters(updatedList)
                    }
                )

                Divider(
                    modifier = Modifier.padding(top = 32.dp, bottom = 16.dp),
                    color = LightAmber
                )

                // Language option
                OptionGroupTitle(title = stringResource(id = R.string.settings_language_title))
                LanguageOptionGroup(
                    selectedOption = settings.preferredLanguage,
                    onOptionSelected = { viewModel.updatePreferredLanguage(it) }
                )
            }
        }
    }
}

@Composable
fun OptionGroupTitle(title: String) {
    Text(
        text = title,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        style = MaterialTheme.typography.h1
    )
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterialApi::class)
@Composable
fun AnimalImageFilterOptionGroup(
    animalOptionList: Array<AnimalType> = AnimalType.values(),
    selectedOptionList: List<AnimalType>,
    onOptionSelected: (AnimalType) -> Unit,
) {
    FlowRow {
        animalOptionList.forEach { animalOption ->

            val isSelected = selectedOptionList.contains(animalOption)
            FilterChip(
                selected = isSelected,
                onClick = {
                    if (isSelected && selectedOptionList.size == 1) {
                        return@FilterChip
                    }
                    onOptionSelected(animalOption)
                },
                modifier = Modifier.padding(start = 16.dp),
                border = if (!isSelected) {
                    BorderStroke(width = 1.dp, color = LightAmber)
                } else {
                    null
                },
                colors = ChipDefaults.outlinedFilterChipColors(
                    backgroundColor = Color.Transparent,
                    selectedBackgroundColor = LightAmber,
                    selectedContentColor = MaterialTheme.colors.onPrimary,
                    selectedLeadingIconColor = MaterialTheme.colors.onPrimary
                )
            ) {
                if (isSelected) {
                    Icon(
                        imageVector = Icons.Filled.Done,
                        contentDescription = null
                    )
                }
                Text(
                    text = stringResource(id = animalOption.filterLabelStringId),
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun LanguageOptionGroup(
    languageOptionList: Array<SettingsLanguage> = SettingsLanguage.values(),
    selectedOption: SettingsLanguage,
    onOptionSelected: (SettingsLanguage) -> Unit
) {
    Column(Modifier.selectableGroup()) {
        languageOptionList.forEach { languageOption ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (languageOption == selectedOption),
                        onClick = { onOptionSelected(languageOption) },
                        role = Role.RadioButton
                    )
                    .padding(16.dp),
            ) {
                RadioButton(
                    selected = (languageOption == selectedOption),
                    onClick = null,
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colors.primaryVariant
                    )
                )
                Text(
                    text = stringResource(id = languageOption.stringId),
                    modifier = Modifier.padding(start = 8.dp),
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}
