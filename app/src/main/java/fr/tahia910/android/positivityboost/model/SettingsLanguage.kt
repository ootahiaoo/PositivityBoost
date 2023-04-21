package fr.tahia910.android.positivityboost.model

import androidx.annotation.StringRes
import fr.tahia910.android.positivityboost.R

enum class SettingsLanguage(@StringRes val stringId: Int) {
    ENGLISH(R.string.settings_language_english),
    JAPANESE(R.string.settings_language_japanese)
}
