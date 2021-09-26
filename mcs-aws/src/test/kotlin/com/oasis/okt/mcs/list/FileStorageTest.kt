package com.oasis.okt.mcs.list

import com.oasis.okt.mcs.aws.storage.AwsStorageDriver
import com.oasis.okt.mcs.fundamentals.storage.FileContent
import com.oasis.okt.mcs.fundamentals.storage.FileObject
import com.oasis.okt.mcs.fundamentals.storage.FileStorage
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test


class FileStorageTest {
    @Test
    fun fileStorageTest(){
//        AwsStorageDriver(profileName = "ons-kt-s3", bucket = "oas-dev-common").createDir("ons")
        val fileStorage = FileStorage(AwsStorageDriver(profileName = "ons-kt-s3", bucket = "oas-dev-common"))
        runBlocking {
//            val path = "ons/234dfsgsfdg.png"
//            val fileContent =  FileContent {
//                metaData = mapOf("test" to "test")
//                loadFromLocalPath("/Users/lizuguang/Desktop/test.png")
//            }
//            fileStorage.put(path,fileContent)
            //https://img.oasgames.com/uploads/234dfsgsfdg.png
            val fileObject2 = fileStorage.read("ons/234dfsgsfdg.png")
            println(fileObject2.content.asByteArray().count())
        }
    }
}