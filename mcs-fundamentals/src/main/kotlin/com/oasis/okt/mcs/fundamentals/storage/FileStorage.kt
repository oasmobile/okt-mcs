package com.oasis.okt.mcs.fundamentals.storage

class FileStorage(
    private val driver: AbstractStorageDriver
){
    suspend fun read(path:String):FileObject{
        return driver.read(path)
    }
    suspend fun delete(path:String){
        driver.delete(path)
    }
    suspend fun put(path: String, content: FileContent){
        driver.put(path,content)
    }
    suspend fun update(fileObject: FileObject){
        driver.put(fileObject.path,fileObject.content)
    }
}
