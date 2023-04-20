package com.sr.slyzebradatawedgesimple.zebra.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sr.slyzebradatawedgesimple.zebra.model.ZScanResult
import com.sr.slyzebradatawedgesimple.zebra.utils_zebra.ZPrinterHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ZPrintViewModel: ViewModel() {

    private val zebraPrinterHelper = ZPrinterHelper()

    companion object {
        const val PRINTER_ADDRESS = "010.005.59.042"
        const val PRINTER_PORT = 9100
    }

    // print only the Scanned result Data
    fun printScanResult(scanResult: ZScanResult) {
        viewModelScope.launch {

            val printer = zebraPrinterHelper.connectToPrinter(PRINTER_ADDRESS, PRINTER_PORT)
            if (printer != null) {
                val zpl = zebraPrinterHelper.createZplFromScanResult(scanResult)
                zebraPrinterHelper.printZpl(printer, zpl)
            }
        }
    }

    // print A Bitmap
    fun printBitmap(bitmap: Bitmap) {
        viewModelScope.launch {

            val printer = zebraPrinterHelper.connectToPrinter(PRINTER_ADDRESS, PRINTER_PORT)
            if (printer != null) {
                zebraPrinterHelper.printBitmap(printer, bitmap)
            }
        }
    }


}