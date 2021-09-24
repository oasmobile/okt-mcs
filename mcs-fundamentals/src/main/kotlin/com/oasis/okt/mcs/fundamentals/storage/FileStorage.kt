package com.oasis.okt.mcs.fundamentals.storage

class FileStorage(
    private val driver: AbstractStorageDriver,
    private val basePath:String
):StorageDriverInterface{
    override suspend fun put(path: String, content: String) {
        driver.put(basePath+path,content)
    }

    override suspend fun read(path: String): FileObject {
        return driver.read(basePath+path)
    }

    override suspend fun delete(path: String) {
        driver.delete(basePath+path)
    }

}
