package com.oasis.okt.mcs.aws.storage

import com.oasis.okt.mcs.fundamentals.storage.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider
import software.amazon.awssdk.core.ResponseBytes
import software.amazon.awssdk.core.exception.SdkException
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.*
import java.io.InputStream

class AwsStorageDriver(
    profileName: String,
    region: Region = Region.AP_NORTHEAST_1,
    bucket: String,
):AbstractStorageDriver() {
    private val logger: Logger = LoggerFactory.getLogger("aws.storage")
    private val s3Client:S3Client
    private val putObRequestBuilder = PutObjectRequest.builder().bucket(bucket)
    private val getObRequestBuilder = GetObjectRequest.builder().bucket(bucket)
    private val delObRequestBuilder = DeleteObjectsRequest.builder().bucket(bucket)
    init {
        val credential = ProfileCredentialsProvider.builder().profileName(profileName).build()
        s3Client = S3Client.builder().credentialsProvider(credential).region(region).build()
    }

    override suspend fun put(path: String,fileContent: FileContent) {
        val putObRequest = putObRequestBuilder.key(path).metadata(fileContent.metaData).build()
        try {
            val requestBody = RequestBody.fromContentProvider({fileContent.asByteArray().inputStream()},fileContent.mimeType)
            val response = s3Client.putObject(putObRequest, requestBody)
            logger.info("s3 put object eTag : " + response.eTag())
        }catch (e:SdkException){
            throw StorageServiceException(e.message)
        }
    }

    override suspend fun read(path: String): FileObject {
        val getObjectRequest = getObRequestBuilder.key(path).build()

        val responseWithBytes: ResponseBytes<GetObjectResponse>
        try {
            responseWithBytes = s3Client.getObjectAsBytes(getObjectRequest)
        }catch (e:SdkException){
            throw StorageServiceException(e.message)
        }

        val metaDataMap = responseWithBytes.response().metadata()
        val body = responseWithBytes.asByteArray()
        val mimeType = responseWithBytes.response().contentType()
        return FileObject(path, FileContent {
            metaData = metaDataMap
            loadFromByteArray(body,mimeType)
        })
    }

    override suspend fun delete(path: String) {
        val toDelete = listOf(ObjectIdentifier.builder().key(path).build())
        val dor = delObRequestBuilder.delete(Delete.builder().objects(toDelete).build()).build()

        try {
            val response = s3Client.deleteObjects(dor)
            logger.debug("aws sdk delete status :${response.hasDeleted()}")
            logger.debug("aws sdk has errors :${response.hasErrors()}")
        }catch (e:SdkException){
            throw StorageServiceException(e.message)
        }
    }
}