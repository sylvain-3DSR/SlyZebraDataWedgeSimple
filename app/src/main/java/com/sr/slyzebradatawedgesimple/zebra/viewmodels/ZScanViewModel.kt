package com.sr.slyzebradatawedgesimple.zebra.viewmodels

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.sr.slyzebradatawedgesimple.R
import com.sr.slyzebradatawedgesimple.zebra.model.ZScanResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ZScanViewModel: ViewModel() {

    private val _scanResult = MutableStateFlow(ZScanResult("", "", ""))
    val scanResult: StateFlow<ZScanResult> = _scanResult

    val myBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            val extras = intent.extras

            if (action == context.getString(R.string.activity_intent_filter_action)) {
                _scanResult.value = ZScanResult(
                    source = extras?.getString(context.getString(R.string.datawedge_intent_key_source)) ?: "",
                    data = extras?.getString(context.getString(R.string.datawedge_intent_key_data)) ?: "",
                    labelType = extras?.getString(context.getString(R.string.datawedge_intent_key_label_type)) ?: ""
                )
            }
        }
    }
}