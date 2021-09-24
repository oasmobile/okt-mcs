package com.oasis.okt.mcs.fundamentals.storage

class FileStorage(
    private val driver: AbstractStorageDriver,
    private val basePath:String
):StorageDriverInterface{
    override suspend fun putText(path: String, content: String, metaData: Map<String, String>) {
        driver.putText(basePath+path,content,metaData)
    }

    override suspend fun readText(path: String): FileObject {
        return driver.readText(basePath+path)
    }

    override suspend fun deleteFile(path: String) {
        driver.deleteFile(basePath+path)
    }

    override suspend fun updateFile(path: String, content: String) {
        driver.updateFile(basePath+path,content)
    }

    override suspend fun putFile(path: String, localPath: String, metaData: Map<String, String>) {
        driver.putFile(basePath+path,localPath,metaData)
    }
}
