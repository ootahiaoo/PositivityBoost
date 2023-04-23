package fr.tahia910.android.positivityboost.model

import androidx.annotation.StringRes
import fr.tahia910.android.positivityboost.R

enum class AnimalType(@StringRes val filterLabelStringId: Int) {
    DOG(R.string.settings_filter_dogs),
    CAT(R.string.settings_filter_cats),
//    MY_PET(R.string.settings_filter_mypet)
}
