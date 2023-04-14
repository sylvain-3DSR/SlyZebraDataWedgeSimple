package com.sr.slyzebradatawedgesimple.zebra.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sr.slyzebradatawedgesimple.zebra.model.ZScanResult
import com.sr.slyzebradatawedgesimple.zebra.viewmodels.ZPrintViewModel
import com.sr.slyzebradatawedgesimple.zebra.viewmodels.ZScanViewModel


@Composable
fun ZScreenSimpleResult(
    zScanViewModel: ZScanViewModel = viewModel(),
    printViewModel: ZPrintViewModel = viewModel()
) {
    val scanResult = zScanViewModel.scanResult.collectAsState(initial = ZScanResult("", "", ""))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
            .border(BorderStroke(1.dp, color = Color.Black)),

    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp).border(BorderStroke(1.dp, color = Color.Black)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Scan Source: ${scanResult.value.source}")
            Text("Scan Data: ${scanResult.value.data}")
            Text("Scan Label Type: ${scanResult.value.labelType}")


            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { printViewModel.printScanResult(scanResult.value) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Print Scan Result")
            }
        }
    }
}