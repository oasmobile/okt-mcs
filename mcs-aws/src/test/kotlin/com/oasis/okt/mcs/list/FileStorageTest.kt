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
        val fileStorage = FileStorage(AwsStorageDriver(profileName = "ons-kt-s3", bucket = "oas-img-upload"))
        runBlocking {
            val fileObject1 = FileObject("uploads/234dfsgsfdg.png", FileContent {
                metaData = mapOf("test" to "test")
                loadFromLocalPath("/Users/lizuguang/Desktop/test.png")
            })
            fileStorage.upload(fileObject1)
            //https://img.oasgames.com/uploads/234dfsgsfdg.png
//            val fileObject2 = fileStorage.read("uploads/sdafsadf234.png")
//            println(fileObject2.content.asString())
        }
    }
}