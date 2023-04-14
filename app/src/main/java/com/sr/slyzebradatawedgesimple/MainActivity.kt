package com.sr.slyzebradatawedgesimple

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.sr.slyzebradatawedgesimple.ui.theme.SlyZebraDataWedgeSimpleTheme
import com.sr.slyzebradatawedgesimple.zebra.utils_zebra.ZBroadcastReceiverHandler
import com.sr.slyzebradatawedgesimple.zebra.view.ZScreenSimpleResult
import com.sr.slyzebradatawedgesimple.zebra.viewmodels.ZScanViewModel

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: ZScanViewModel
    private lateinit var broadcastReceiverHandler: ZBroadcastReceiverHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(ZScanViewModel::class.java)
        broadcastReceiverHandler = ZBroadcastReceiverHandler(this, viewModel)

        setContent {
            SlyZebraDataWedgeSimpleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ZScreenSimpleResult(viewModel)
                }
            }
        }

        broadcastReceiverHandler.register()
    }

    override fun onDestroy() {
        super.onDestroy()
        broadcastReceiverHandler.unregister()
    }

}

