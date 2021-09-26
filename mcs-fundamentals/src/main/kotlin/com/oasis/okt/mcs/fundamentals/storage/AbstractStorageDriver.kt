package com.oasis.okt.mcs.fundamentals.storage

import java.io.File
import java.io.FileInputStream
import java.io.IOException

abstract class AbstractStorageDriver{
    abstract suspend fun put(path:String,fileContent: FileContent)
    abstract suspend fun read(path:String):FileObject
    abstract suspend fun delete(path:String)
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