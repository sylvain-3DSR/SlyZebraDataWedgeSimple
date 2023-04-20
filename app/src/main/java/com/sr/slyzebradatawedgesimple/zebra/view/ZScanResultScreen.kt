package com.sr.slyzebradatawedgesimple.zebra.view

import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sr.slyzebradatawedgesimple.zebra.model.ZScanResult
import com.sr.slyzebradatawedgesimple.zebra.utils_zebra.convertMmToDots
import com.sr.slyzebradatawedgesimple.zebra.viewmodels.ZPrintViewModel
import com.sr.slyzebradatawedgesimple.zebra.viewmodels.ZScanViewModel


const val dpi = 203 // Replace with your printer's dpi
const val labelWidthMm = 105f
const val labelHeightMm = 150f

@Composable
fun ZScanResultScreen(
    zScanViewModel: ZScanViewModel = viewModel(),
    printViewModel: ZPrintViewModel = viewModel()
) {
    val scanResult = zScanViewModel.scanResult.collectAsState(initial = ZScanResult("", "", ""))
    val context = LocalContext.current

    // Set up the composable to print
    val composeView = remember {
        ComposeView(context).apply {
            setContent {
                MyComposable(
                    source = scanResult.value.source,
                    data = scanResult.value.data,
                    labelType = scanResult.value.labelType
                )
            }
        }
    }

    // Set the desired width and height for the bitmap
    val density = LocalDensity.current.density

    val widthPx = remember(density) { (convertMmToDots(labelWidthMm, dpi) * density).toInt() }
    val heightPx = remember(density) { (convertMmToDots(labelHeightMm, dpi) * density).toInt() }

    // Create the bitmap and canvas
    val myBitmap: Bitmap = remember {
        Bitmap.createBitmap(widthPx, heightPx, Bitmap.Config.ARGB_8888)
    }
    val myCanvas = remember { Canvas(myBitmap) }

    // UI
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                composeView.draw(myCanvas)
                val result = myBitmap
                printViewModel.printBitmap(result)
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Print Composable")
        }

        Box {
            AndroidView({ composeView })
        }
    }
}