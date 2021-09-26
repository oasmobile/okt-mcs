package com.oasis.okt.mcs.fundamentals.storage

class FileStorage(
    private val driver: AbstractStorageDriver
){
    suspend fun read(path:String):FileObject{
        return driver.read(path)
    }
    suspend fun delete(fileObject: FileObject){
        driver.delete(fileObject.path)
    }
    suspend fun put(path: String, content: FileContent){
        //driver.upload(fileObject)
    }
    suspend fun update(fileObject: FileObject){
        driver.upload(fileObject)
    }
}
