package com.oasis.okt.mcs.fundamentals.storage

data class FileObject(
    val metaData:Map<String,String> = mapOf(),
    val content:String,
    val path:String
)