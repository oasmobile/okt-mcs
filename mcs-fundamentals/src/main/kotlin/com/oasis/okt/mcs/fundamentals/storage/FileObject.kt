package com.oasis.okt.mcs.fundamentals.storage

import java.io.File

class FileObject(
    val path:String,
    val content:FileContent,
)

fun FileContent(config:FileContent.()->Unit):FileContent{
    return FileContent().apply(config)
}

class FileContent{
    private lateinit var content:ByteArray
    var metaData:Map<String,String> = mapOf()

    fun asString():String{
        return String(content)
    }

    fun asByteArray():ByteArray{
        return content
    }

    fun loadFromString(str:String){
        this.content = str.encodeToByteArray()
    }

    fun loadFromByteArray(bytes:ByteArray){
        this.content = bytes
    }

    fun loadFromPath(path:String){
        this.content = File(path).readBytes()
    }
}