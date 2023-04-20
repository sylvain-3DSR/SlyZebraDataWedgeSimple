package com.sr.slyzebradatawedgesimple.zebra.utils_zebra

import android.graphics.Bitmap
import android.util.Base64
import android.util.Log
import com.sr.slyzebradatawedgesimple.zebra.model.ZScanResult
import com.zebra.sdk.comm.Connection
import com.zebra.sdk.comm.TcpConnection
import com.zebra.sdk.graphics.ZebraImageFactory
import com.zebra.sdk.graphics.ZebraImageI
import com.zebra.sdk.graphics.internal.ZebraImageAndroid
import com.zebra.sdk.printer.GraphicsUtil
import com.zebra.sdk.printer.PrinterLanguage
import com.zebra.sdk.printer.ZebraPrinter
import com.zebra.sdk.printer.ZebraPrinterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

class ZPrinterHelper {

    // Connection to the Printer using IP/PORT
    suspend fun connectToPrinter(printerAddress: String, port: Int): ZebraPrinter? {
        return withContext(Dispatchers.IO) {
            Log.d("ZPrinterHelper", "Connecting to printer: $printerAddress:$port")
            var connection: Connection? = null
            var printer: ZebraPrinter? = null

            try {
                connection = TcpConnection(printerAddress, port)
                connection.open()

                val printerLanguage = PrinterLanguage.ZPL
                printer = ZebraPrinterFactory.getInstance(printerLanguage, connection)
            } catch (e: Exception) {
                e.printStackTrace()
                connection?.close()
            }

            Log.d("ZPrinterHelper", "Printer connected")
            printer
        }
    }

    // Convert the scanned data to a ZPL format
    fun createZplFromScanResult(scanResult: ZScanResult): String {
        val source = "Scan Source: ${scanResult.source}"
        val data = "Scan Data: ${scanResult.data}"
        val labelType = "Scan Label Type: ${scanResult.labelType}"

        return "^XA" +
                "^CF0,30" +
                "^FO50,50^FD$source^FS" +
                "^FO50,100^FD$data^FS" +
                "^FO50,150^FD$labelType^FS" +
                "^XZ"
    }

    // Print a Bitmap image
    suspend fun printBitmap(printer: ZebraPrinter, bitmap: Bitmap): Result<Unit> {
        return try {
            withContext(Dispatchers.IO) {


                val dpi = 520 // Replace with your printer's dpi
                val labelWidthMm = 105f
                val labelHeightMm = 160f

                val labelWidthDots = convertMmToDots(labelWidthMm, dpi)
                val labelHeightDots = convertMmToDots(labelHeightMm, dpi)

                // Resize the input Bitmap to fit the label size
                val resizedBitmap = Bitmap.createScaledBitmap(bitmap, labelWidthDots, labelHeightDots, true)

                val zebraImage = ZebraImageFactory.getImage(resizedBitmap)

                val x = 0
                val y = 0

                // Print the ZebraImage object directly
                printer.printImage(zebraImage, x, y, zebraImage.width, zebraImage.height, false)

                Result.success(Unit)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        } finally {
            printer.connection.close()
        }
    }


    // Print the ZPL
    suspend fun printZpl(printer: ZebraPrinter, zpl: String) {
        try {
            withContext(Dispatchers.IO) {
                printer.connection.write(zpl.toByteArray())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            printer.connection.close()
        }
    }
}

/*
    fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.NO_WRAP)
    }

}
 */