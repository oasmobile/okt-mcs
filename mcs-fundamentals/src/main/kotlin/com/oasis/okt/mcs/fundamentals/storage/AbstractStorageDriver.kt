package com.oasis.okt.mcs.fundamentals.storage

import java.io.File
import java.io.FileInputStream
import java.io.IOException

internal interface StorageDriverInterface {
    suspend fun put(path: String, content: String)
    suspend fun read(path:String):FileObject
    suspend fun delete(path:String)
}

abstract class AbstractStorageDriver:StorageDriverInterface{
    protected fun getObjectFile(filePath: String): ByteArray? {
        var fileInputStream: FileInputStream? = null
        var bytesArray: ByteArray? = null
        try {
            val file = File(filePath)
            bytesArray = ByteArray(file.length().toInt())
            fileInputStream = FileInputStream(file)
            fileInputStream.read(bytesArray)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return bytesArray
    }
}