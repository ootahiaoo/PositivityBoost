package fr.tahia910.android.positivityboost.ui.component

import android.content.Intent
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import fr.tahia910.android.positivityboost.BuildConfig
import fr.tahia910.android.positivityboost.R
import fr.tahia910.android.positivityboost.ui.icon.Icons
import fr.tahia910.android.positivityboost.ui.theme.LightAmber

private const val TERMS_CONDITIONS_URL = "https://www.freeprivacypolicy.com/live/69d1fc81-196d-46b6-a6df-b65e2de8d575"
private const val PRIVACY_POLICY_URL =
    "https://dandelion-sandwich-d63.notion.site/Positivity-Boost-Privacy-Policy-2eebaf7fbe2544169f47cba17e15a5d9"

@Composable
fun Drawer(
    onHomeSelected: () -> Unit,
    onSettingsSelected: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(Modifier.height(56.dp))

        // Home
        DrawerMainItem(
            text = R.string.drawer_home,
            icon = Icons.Home,
            onClicked = onHomeSelected
        )

        // Settings
        DrawerMainItem(
            text = R.string.drawer_settings,
            icon = Icons.Settings,
            onClicked = onSettingsSelected
        )
        // TODO: MyPet
        // TODO: Fav?

        val context = LocalContext.current

        Divider(modifier = Modifier.padding(top = 16.dp, bottom = 24.dp), color = LightAmber)
        DrawerSubItem(text = R.string.drawer_terms_of_service) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(TERMS_CONDITIONS_URL))
            context.startActivity(intent)
        }
        DrawerSubItem(text = R.string.drawer_privacy_policy) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(PRIVACY_POLICY_URL))
            context.startActivity(intent)
        }
        DrawerSubItem(text = R.string.drawer_licenses) {
            context.startActivity(Intent(context, OssLicensesMenuActivity::class.java))
        }

        // Align the last item to the bottom of the drawer
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(id = R.string.drawer_app_version, BuildConfig.VERSION_NAME),
            modifier = Modifier
                .requiredHeight(48.dp)
                .fillMaxWidth()
                .padding(end = 24.dp),
            textAlign = TextAlign.End
        )
    }
}

@Composable
fun DrawerMainItem(
    @StringRes text: Int,
    @DrawableRes icon: Int,
    onClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(56.dp)
            .clickable(enabled = true, onClickLabel = stringResource(id = text)) { onClicked() }
            .padding(start = 24.dp, end = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null
        )
        Text(
            text = stringResource(id = text),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            style = MaterialTheme.typography.h1
        )
    }
}

@Composable
fun DrawerSubItem(
    @StringRes text: Int,
    onClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(48.dp)
            .clickable(enabled = true, onClickLabel = stringResource(id = text)) { onClicked() }
            .padding(start = 24.dp, end = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(id = text), modifier = Modifier.fillMaxWidth())
    }
}
