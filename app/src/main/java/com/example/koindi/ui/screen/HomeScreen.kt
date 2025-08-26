package com.example.koindi.ui.screen

import android.Manifest
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.koindi.ui.spacing
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.shouldShowRationale

@Composable
fun HomeScreen() {
    var selectedTab by rememberSaveable(
        stateSaver = Saver(
            save = { it.name },
            restore = { HomeTab.valueOf(it) }
        )
    ) {
        mutableStateOf(HomeTab.List)
    }
    val scrollState = rememberScrollState()

    Column {
        Row(
            modifier = Modifier
                .horizontalScroll(scrollState)
                .padding(MaterialTheme.spacing.small)
        ) {
            Button(onClick = { selectedTab = HomeTab.List }) { Text("List") }
            Spacer(modifier = Modifier.size(MaterialTheme.spacing.small))
            Button(onClick = { selectedTab = HomeTab.Timer }) { Text("Timer") }
            Spacer(modifier = Modifier.size(MaterialTheme.spacing.small))
            Button(onClick = { selectedTab = HomeTab.ProgressBar }) { Text("Progress") }
            Spacer(modifier = Modifier.size(MaterialTheme.spacing.small))
            Button(onClick = { selectedTab = HomeTab.Constraint }) { Text("Constraint") }
            Spacer(modifier = Modifier.size(MaterialTheme.spacing.small))
            Button(onClick = { selectedTab = HomeTab.TextFields }) { Text("TextFields") }
            Spacer(modifier = Modifier.size(MaterialTheme.spacing.small))
            Button(onClick = { selectedTab = HomeTab.Permissions }) { Text("Permissions") }
        }
        when (selectedTab) {
            HomeTab.List -> Lists()
            HomeTab.Constraint -> ConstraintLayoutExample()
            HomeTab.Timer -> Timer(
                totalTime = 20L * 1000L,
                handleColor = Color.Green,
                inActiveBarColor = Color.DarkGray,
                activeBarColor = Color.Green,
                modifier = Modifier.size(200.dp)
            )

            HomeTab.ProgressBar -> CircularProgressBar(
                percentage = 0.8f,
                number = 100
            )
            HomeTab.TextFields -> TextFieldsButtons()
            HomeTab.Permissions -> PermissionsScreen()
        }
    }
}

enum class HomeTab {
    List,
    Constraint,
    Timer,
    ProgressBar,
    TextFields,
    Permissions
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionsScreen() {
    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
        )
    )

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(
        key1 = lifecycleOwner,
        effect = {
            val observer = LifecycleEventObserver { _, event ->
                if(event == Lifecycle.Event.ON_START) {
                    permissionState.launchMultiplePermissionRequest()
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)
            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(MaterialTheme.spacing.medium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        permissionState.permissions.forEach { perm ->
            when (perm.permission) {
                Manifest.permission.CAMERA -> {
                    when {
                        perm.status.isGranted -> {
                            Text(
                                text = "Camera permission accepted",
                                color = Color.Green
                            )
                        }
                        perm.status.shouldShowRationale -> {
                            Text(
                                text = "Camera permission is needed to access the camera"
                            )
                            Button(onClick = { permissionState.launchMultiplePermissionRequest() }) {
                                Text(text = "Request permission")
                            }
                        }
                        !perm.status.isGranted && !perm.status.shouldShowRationale -> {
                            Text(
                                text = "Camera permission was permanently denied. You can enable it in the app settings.",
                                color = Color.Red
                            )
                        }
                        else -> {
                            Button(onClick = { perm.launchPermissionRequest() }) {
                                Text("Grant Camera permission")
                            }
                        }
                    }
                }
                Manifest.permission.RECORD_AUDIO -> {
                    when {
                        perm.status.isGranted -> {
                            Text(
                                text = "Record Audio permission accepted",
                                color = Color.Green
                            )
                        }
                        perm.status.shouldShowRationale -> {
                            Text(
                                text = "Record Audio permission is needed to access the microphone"
                            )
                            Button(onClick = { permissionState.launchMultiplePermissionRequest() }) {
                                Text(text = "Request permission")
                            }
                        }
                        perm.isPermanentDenied() -> {
                            Text(
                                text = "Record Audio permission was permanently denied. You can enable it in the app settings.",
                                color = Color.Red
                            )
                        }
                        else -> {
                            Button(onClick = { perm.launchPermissionRequest() }) {
                                Text("Grant Record Audio permission")
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
fun PermissionState.isPermanentDenied(): Boolean {
    return !status.shouldShowRationale && !status.isGranted
}

@Composable
fun CircularProgressBar(
    percentage: Float,
    number: Int,
    fontSize: TextUnit = 28.sp,
    radius: Dp = 50.dp,
    color: Color = Color.Green,
    strokeWidth: Dp = 8.dp,
    animDuration: Int = 1000,
    animaDelay: Int = 0,
) {
    var animationPlayed by remember {
        mutableStateOf(false)
    }

    val curPercentage = animateFloatAsState(
        targetValue = if(animationPlayed) percentage else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = animaDelay
        ),
        label = "",
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(radius * 2f)
        ) {
            Canvas(modifier = Modifier.size(radius * 2f)) {
                drawArc(
                    color = color,
                    startAngle = -90f,
                    sweepAngle = curPercentage.value * 360f,
                    useCenter = false,
                    style = Stroke(
                        width = strokeWidth.toPx(),
                        cap = StrokeCap.Round
                    )
                )
            }
            Text(
                text = "${(curPercentage.value * number).toInt()}",
                color = Color.Black,
                fontSize = fontSize,
                fontWeight = FontWeight.Bold
            )
        }
    }
}