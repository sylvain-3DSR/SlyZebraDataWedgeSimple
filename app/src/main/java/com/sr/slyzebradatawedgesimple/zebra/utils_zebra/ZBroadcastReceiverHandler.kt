package com.sr.slyzebradatawedgesimple.zebra.utils_zebra

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.sr.slyzebradatawedgesimple.R
import com.sr.slyzebradatawedgesimple.zebra.viewmodels.ZScanViewModel

class ZBroadcastReceiverHandler(
    private val context: Context,
    private val viewModel: ZScanViewModel
) {

    private val filter = IntentFilter().apply {
        addCategory(Intent.CATEGORY_DEFAULT)
        addAction(context.getString(R.string.activity_intent_filter_action))
    }

    fun register() {
        context.registerReceiver(viewModel.myBroadcastReceiver, filter)
    }

    fun unregister() {
        context.unregisterReceiver(viewModel.myBroadcastReceiver)
    }
}