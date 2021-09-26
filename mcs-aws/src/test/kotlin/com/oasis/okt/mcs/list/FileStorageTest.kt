package com.oasis.okt.mcs.list

import com.oasis.okt.mcs.aws.storage.AwsStorageDriver
import com.oasis.okt.mcs.fundamentals.storage.FileContent
import com.oasis.okt.mcs.fundamentals.storage.FileStorage
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import java.io.File
import java.nio.charset.Charset


class FileStorageTest {
//    @Test
//    fun fileStorageTest(){
//        val fileStorage = FileStorage(AwsStorageDriver(profileName = "ons-kt-s3", bucket = "oas-img-upload"))
//        runBlocking {
//            val fileContent = FileContent {
//                loadFromPath("/Users/lizuguang/Desktop/test.png")
//            }
//            fileStorage.upload("https://img.oasgames.com/uploads/test123.txt",fileContent)
//            val fileObject = fileStorage.read("https://img.oasgames.com/uploads/test.png")
//            println(fileObject.readAsString())
//        }
//    }

}