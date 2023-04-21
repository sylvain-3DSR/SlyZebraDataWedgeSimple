package com.sr.slyzebradatawedgesimple.zebra.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sr.slyzebradatawedgesimple.zebra.model.ZScanResult
import com.sr.slyzebradatawedgesimple.zebra.utils_zebra.ZPrinterConstants.PRINTER_ADDRESS
import com.sr.slyzebradatawedgesimple.zebra.utils_zebra.ZPrinterConstants.PRINTER_PORT
import com.sr.slyzebradatawedgesimple.zebra.utils_zebra.ZPrinterHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ZPrintViewModel: ViewModel() {

    private val zebraPrinterHelper = ZPrinterHelper()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage


    // print only the Scanned result Data
    fun printScanResult(scanResult: ZScanResult) {
        viewModelScope.launch {

            val printer = zebraPrinterHelper.connectToPrinter(PRINTER_ADDRESS, PRINTER_PORT)
            if (printer != null) {
                val zpl = zebraPrinterHelper.createZplFromScanResult(scanResult)
                zebraPrinterHelper.printZpl(printer, zpl)
            } else {
                _errorMessage.value = "Failed to connect to printer"
            }
        }
    }

    // print A Bitmap
    fun printBitmap(bitmap: Bitmap) {
        viewModelScope.launch {

            val printer = zebraPrinterHelper.connectToPrinter(PRINTER_ADDRESS, PRINTER_PORT)
            if (printer != null) {
                //zebraPrinterHelper.printBitmap(printer, bitmap)
                val result = zebraPrinterHelper.printBitmap(printer, bitmap)
                if (result.isFailure) {
                    _errorMessage.value = "Failed to print bitmap"
                }
            } else {
                _errorMessage.value = "Failed to connect to printer"
            }
        }
    }


}