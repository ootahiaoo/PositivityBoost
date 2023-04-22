package fr.tahia910.android.positivityboost.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import fr.tahia910.android.positivityboost.R
import fr.tahia910.android.positivityboost.model.AnimalType
import fr.tahia910.android.positivityboost.model.SettingsLanguage
import fr.tahia910.android.positivityboost.ui.theme.LightAmber

@Composable
fun SettingsScreen() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        // TODO: save to pref
        // Animal image filter option
        val animalOptionList = AnimalType.values()
        val selectedAnimalOptionList: MutableState<List<AnimalType>> = remember {
            // TODO: change default
            mutableStateOf(listOf(AnimalType.DOG))
        }
        OptionGroupTitle(title = stringResource(id = R.string.settings_filter_title))
        AnimalImageFilterOptionGroup(
            animalOptionList = animalOptionList,
            selectedOptionList = selectedAnimalOptionList.value,
            onOptionSelected = {
                val previousList = selectedAnimalOptionList.value.toMutableList()

                if (previousList.contains(it)) previousList.remove(it) else previousList.add(it)
                selectedAnimalOptionList.value = previousList
            }
        )

        Divider(
            modifier = Modifier.padding(top = 32.dp, bottom = 16.dp),
            color = LightAmber
        )

        // TODO: save to pref
        // Language option
        val languageOptionList = SettingsLanguage.values()
        val (selectedLanguageOption, onLanguageOptionSelected) = remember {
            mutableStateOf(languageOptionList[0])
        }
        OptionGroupTitle(title = stringResource(id = R.string.settings_language_title))
        LanguageOptionGroup(
            languageOptionList = languageOptionList,
            selectedOption = selectedLanguageOption,
            onOptionSelected = onLanguageOptionSelected
        )
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
    animalOptionList: Array<AnimalType>,
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
    languageOptionList: Array<SettingsLanguage>,
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
