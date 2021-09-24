package com.oasis.okt.mcs.aws.storage

import com.oasis.okt.mcs.fundamentals.storage.AbstractStorageDriver
import com.oasis.okt.mcs.fundamentals.storage.FileObject
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.*


class AwsStorageDriver(
    profileName: String,
    region: Region = Region.US_EAST_1,
    bucket: String,
):AbstractStorageDriver() {
    private val s3Client:S3Client
    private val putObRequestBuilder = PutObjectRequest.builder().bucket(bucket)
    private val getObRequestBuilder = GetObjectRequest.builder().bucket(bucket)
    private val delObRequestBuilder = DeleteObjectsRequest.builder().bucket(bucket)
    init {
        val credential = ProfileCredentialsProvider.builder().profileName(profileName).build()
        s3Client = S3Client.builder().credentialsProvider(credential).region(region).build()
    }

    override suspend fun putText(path: String, content: String, metaData: Map<String, String>) {
        val putObRequest = putObRequestBuilder.key(path).metadata(metaData).build()
        val response = s3Client.putObject(putObRequest, RequestBody.fromBytes(content.toByteArray()))
        println("eTag = " + response.eTag())
    }

    override suspend fun readText(path: String): FileObject {
        val getObjectRequest = getObRequestBuilder.key(path).build()
        val responseWithBytes = s3Client.getObjectAsBytes(getObjectRequest)

        val metaData = responseWithBytes.response().metadata()
        val body = responseWithBytes.asByteArray().toString()

        return FileObject(metaData, body, path)
    }

    override suspend fun deleteFile(path: String) {
        val toDelete = listOf(ObjectIdentifier.builder().key(path).build())
        val dor = delObRequestBuilder.delete(Delete.builder().objects(toDelete).build()).build()
        s3Client.deleteObjects(dor)
    }

    override suspend fun updateFile(path: String, content: String) {
        putText(path,content)
    }

    override suspend fun putFile(path: String, localPath: String, metaData: Map<String, String>) {
        val putObRequest = putObRequestBuilder.key(path).metadata(metaData).build()
        val response = s3Client.putObject(putObRequest, RequestBody.fromBytes(getObjectFile(localPath)))
        println(response.eTag())
    }
}