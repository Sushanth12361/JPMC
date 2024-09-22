package com.example.jpmorgantest.ui.permission

import android.Manifest
import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import com.example.jpmorgantest.R
import com.example.jpmorgantest.util.extension.areLocationPermissionsAlreadyGranted
import com.example.jpmorgantest.util.extension.hasLocationPermission
import com.example.jpmorgantest.util.extension.openApplicationSettings
import kotlinx.coroutines.launch

@Composable
@SuppressWarnings("LongMethod")
fun PermissionContent(
    activity: Activity,
    permissionGranted: () -> Unit,
    launchPermission: Int?
) {
    var locationPermissionsGranted by remember { mutableStateOf(activity.areLocationPermissionsAlreadyGranted()) }
    var shouldShowPermissionRationale by remember {
        mutableStateOf(
            shouldShowRequestPermissionRationale(
                activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    var shouldDirectUserToApplicationSettings by remember {
        mutableStateOf(false)
    }

    val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            locationPermissionsGranted = permissions.values.reduce { acc, isPermissionGranted ->
                acc && isPermissionGranted
            }

            if (!locationPermissionsGranted) {
                shouldShowPermissionRationale =
                    shouldShowRequestPermissionRationale(
                        activity,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
            }
            shouldDirectUserToApplicationSettings =
                !shouldShowPermissionRationale && !locationPermissionsGranted
            if (locationPermissionsGranted) {
                permissionGranted()
            }
        }
    )

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    Box(modifier = Modifier.fillMaxSize()) {
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
    if (shouldShowPermissionRationale) {
        LaunchedEffect(Unit) {
            scope.launch {
                val userAction = snackbarHostState.showSnackbar(
                    message = activity.getString(R.string.til_authorize_permission),
                    actionLabel = activity.getString(R.string.til_approve),
                    duration = SnackbarDuration.Indefinite,
                    withDismissAction = true
                )
                when (userAction) {
                    SnackbarResult.ActionPerformed -> {
                        shouldShowPermissionRationale = false
                        locationPermissionLauncher.launch(locationPermissions)
                    }

                    SnackbarResult.Dismissed -> {
                        shouldShowPermissionRationale = false
                    }
                }
            }
        }
    }
    if (shouldDirectUserToApplicationSettings && !hasLocationPermission(activity)) {
        activity.openApplicationSettings()
    }
    LaunchedEffect(launchPermission) {
        if (launchPermission != null) {
            locationPermissionLauncher.launch(locationPermissions)
        }
    }
}
