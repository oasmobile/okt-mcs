package com.oasis.okt.mcs.fundamentals.storage

interface StorageService {
    suspend fun put(file:FileObject):Boolean
    suspend fun read(file:FileObject):Boolean
    suspend fun delete(key:String):Boolean
    suspend fun update(file:FileObject):Boolean
}