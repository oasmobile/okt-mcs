package com.oasis.okt.mcs.list

import com.oasis.okt.mcs.aws.storage.AwsStorageDriver
import com.oasis.okt.mcs.fundamentals.storage.FileStorage
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test


class FileStorageTest {
    @Test
    fun fileStorageTest(){
        val fileStorage = FileStorage(AwsStorageDriver(profileName = "ons-kt-s3", bucket = "oas-img-upload"),"https://img.oasgames.com/uploads/")
        runBlocking {
            fileStorage.put("test123.txt","123")
            val fileObject = fileStorage.read("test123.txt")
            println(fileObject)
        }
    }
}