package com.oasis.okt.mcs.fundamentals.storage

class FileStorage(
    private val driver: AbstractStorageDriver,
){
    suspend fun upload(fileObject: FileObject){
        driver.upload(fileObject)
    }
    suspend fun read(path:String):FileObject{
        return driver.read(path)
    }
    suspend fun delete(path:String){
        driver.delete(path)
    }
}
