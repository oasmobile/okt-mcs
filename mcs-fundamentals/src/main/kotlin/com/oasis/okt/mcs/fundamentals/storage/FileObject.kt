package com.oasis.okt.mcs.fundamentals.storage

import java.io.File
import java.nio.file.Files
import kotlin.io.path.Path

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
    var mimeType:String = ""
        private set
    fun asString():String{
        return String(content)
    }

    fun asByteArray():ByteArray{
        return content
    }

    fun loadFromString(str:String){
        this.content = str.encodeToByteArray()
        mimeType = "text/plain"
    }

    fun loadFromByteArray(bytes:ByteArray,mimeType:String){
        this.content = bytes
        this.mimeType=mimeType
    }

    fun loadFromLocalPath(path:String){
        this.content = File(path).readBytes()
        mimeType = Files.probeContentType(Path(path))
    }
}