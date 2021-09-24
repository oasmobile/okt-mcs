package com.oasis.okt.mcs.fundamentals.storage

import java.io.File
import java.io.FileInputStream
import java.io.IOException

interface StorageDriverInterface {
    suspend fun putText(path: String, content: String,metaData: Map<String,String> = mapOf())
    suspend fun readText(path:String):FileObject
    suspend fun deleteFile(path:String)
    suspend fun updateFile(path: String, content: String)
    suspend fun putFile(path:String,localPath:String,metaData: Map<String, String> = mapOf())
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