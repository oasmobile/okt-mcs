package com.oasis.okt.mcs.fundamentals.storage

import java.io.File

class FileObject(
    val metaData:Map<String,String> = mapOf(),
    val path:String,
    private val content:ByteArray,
){
    fun readAsString():String{
        return String(content)
    }

    fun readAsLocalFile(localFilePath:String){
        File(localFilePath).writeBytes(content)
    }
}

fun FileContent(config:FileContent.()->Unit):FileContent{
    return FileContent().apply(config)
}

class FileContent{
    lateinit var content:ByteArray
        private set
    fun loadFromString(str:String){
        this.content = str.encodeToByteArray()
    }
    fun loadByteArray(bytes:ByteArray){
        this.content = bytes
    }
    fun loadFromPath(path:String){
        this.content = File(path).readBytes()
    }
}