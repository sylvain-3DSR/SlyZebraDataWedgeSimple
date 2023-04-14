package com.sr.slyzebradatawedgesimple.zebra.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sr.slyzebradatawedgesimple.zebra.model.ZScanResult
import com.sr.slyzebradatawedgesimple.zebra.utils_zebra.ZPrinterHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ZPrintViewModel: ViewModel() {

    private val zebraPrinterHelper = ZPrinterHelper()

    // print only the Scanned result Data
    fun printScanResult(scanResult: ZScanResult) {
        viewModelScope.launch {

            //Printer Ip / Port
            val printerAddress = "010.005.59.042"
            val port = 9100

            // Use withContext(Dispatchers.IO) to run the network operation on a background thread
            val printer = withContext(Dispatchers.IO) { zebraPrinterHelper.connectToPrinter(printerAddress, port) }

            if (printer != null) {
                val zpl = zebraPrinterHelper.createZplFromScanResult(scanResult)
                zebraPrinterHelper.printZpl(printer, zpl)
            }
        }
    }
}