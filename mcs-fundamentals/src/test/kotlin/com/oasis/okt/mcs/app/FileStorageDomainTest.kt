package com.oasis.okt.mcs.app

import com.oasis.okt.mcs.fundamentals.storage.FileContent
import com.oasis.okt.mcs.fundamentals.storage.FileObject
import org.junit.jupiter.api.Test


class FileStorageDomainTest {
    @Test
    fun testFileObject() {
        val fileContent = FileContent {
            loadFromString("你好")
        }
        val fileObject = FileObject(path = "",content = fileContent.content)
        println(fileObject.readAsString())
    }
}