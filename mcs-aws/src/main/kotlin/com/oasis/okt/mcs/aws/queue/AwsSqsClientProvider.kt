package com.oasis.okt.mcs.aws.queue

import com.oasis.okt.mcs.fundamentals.queue.QueueClientProvider
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sqs.SqsClient

class AwsSqsClientProvider(
    profileName: String,
    region: Region = Region.AP_NORTHEAST_1,
) : QueueClientProvider<SqsClient> {
    override val client: SqsClient = profileName.let {
        val credential = ProfileCredentialsProvider.builder().profileName(it).build()
        SqsClient.builder().credentialsProvider(credential).region(region).build()
    }

}