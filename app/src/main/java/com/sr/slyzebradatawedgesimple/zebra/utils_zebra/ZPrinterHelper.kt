package com.sr.slyzebradatawedgesimple.zebra.utils_zebra

import com.sr.slyzebradatawedgesimple.zebra.model.ZScanResult
import com.zebra.sdk.comm.Connection
import com.zebra.sdk.comm.TcpConnection
import com.zebra.sdk.printer.ZebraPrinter
import com.zebra.sdk.printer.ZebraPrinterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ZPrinterHelper {

    suspend fun connectToPrinter(printerAddress: String,port: Int): ZebraPrinter? {
        var connection: Connection? = null
        var printer: ZebraPrinter? = null

        try {
            connection = TcpConnection(printerAddress, port)
            connection.open()

            printer = ZebraPrinterFactory.getInstance(connection)
        } catch (e: Exception) {
            e.printStackTrace()
            connection?.close()
        }

        return printer
    }

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