package com.oasis.okt.mcs.aws.queue

import com.oasis.okt.mcs.fundamentals.queue.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import software.amazon.awssdk.services.sqs.SqsClient
import software.amazon.awssdk.services.sqs.model.DeleteMessageBatchRequestEntry
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest
import software.amazon.awssdk.services.sqs.model.SendMessageBatchRequestEntry

@Suppress("unused")
open class AwsQueueServiceImpl(
    name: String,
    queueClientProvider: AwsSqsClientProvider,
) : QueueService {
    companion object {
        const val MAX_BATCH_SIZE = 10
    }

    private val logger: Logger = LoggerFactory.getLogger("aws.sqs.service")
    private val client: SqsClient = queueClientProvider.client
    private val url: String = name.let {
        logger.info("aws queue name = $name")
        val req = GetQueueUrlRequest.builder().queueName(it).build()
        client.getQueueUrl(req).queueUrl()
    }

    override suspend fun sendMessage(message: String, delay: Int): SendMessage = withContext(Dispatchers.IO) {
        sendMessages(listOf(message), delay).first()
    }

    override suspend fun sendMessages(messages: List<String>, delay: Int): List<SendMessage> =
        withContext(Dispatchers.IO) {
            if (messages.isEmpty()) {
                throw QueueServiceException("messages is empty")
            }
            val entries = messages.map {
                SendMessageBatchRequestEntry.builder().id(it.hashCode().toString()).messageBody(it).delaySeconds(delay)
                    .build()
            }
            val res = try {
                client.sendMessageBatch {
                    it.queueUrl(url).entries(entries)
                }
            } catch (ex: Exception) {
                logger.error("send messages failed: ${ex.message}")
                throw QueueServiceException(ex.message ?: "")
            }
            val successful = res.successful().map {
                SendMessage(it.messageId(), it.md5OfMessageBody(), true)
            }
            val failed = res.failed().map {
                SendMessage(status = false, message = it.message())
            }
            logger.debug("send messages = ${successful + failed}")
            successful + failed
        }


    override suspend fun receiveMessage(waitSeconds: Int): ReceiveMessage = withContext(Dispatchers.IO) {
        val res = receiveMessages(1, waitSeconds)
        if (res.isNotEmpty()) {
            return@withContext res.first()
        }
        throw QueueServiceException("No message received")

    }

    override suspend fun receiveMessages(
        batchSize: Int,
        waitSeconds: Int,
    ): List<ReceiveMessage> = withContext(Dispatchers.IO) {
        val size = if (batchSize > MAX_BATCH_SIZE) MAX_BATCH_SIZE else batchSize
        val res = try {
            client.receiveMessage {
                it.queueUrl(url).maxNumberOfMessages(size).waitTimeSeconds(waitSeconds)
            }
        } catch (ex: Exception) {
            logger.error("receive messages failed: ${ex.message}")
            throw QueueServiceException(ex.message ?: "")
        }
        val messages = res.messages().map {
            ReceiveMessage(it.messageId(), it.md5OfBody(), it.receiptHandle(), it.body())
        }
        logger.debug("receive messages = $messages")
        messages
    }


    override suspend fun deleteMessage(message: ReceiveMessage): DeleteMessage = withContext(Dispatchers.IO) {
        val res = deleteMessages(listOf(message))
        if (res.isNotEmpty()) {
            return@withContext res.first()
        }
        throw QueueServiceException("No message deleted")
    }

    override suspend fun deleteMessages(messages: List<ReceiveMessage>): List<DeleteMessage> =
        withContext(Dispatchers.IO) {
            if (messages.isEmpty()) {
                throw QueueServiceException("messages is empty")
            }
            val entries = messages.map {
                DeleteMessageBatchRequestEntry.builder().id(it.id).receiptHandle(it.receiptHandle).build()
            }

            val res = try {
                client.deleteMessageBatch {
                    it.queueUrl(url).entries(entries)
                }
            } catch (ex: Exception) {
                logger.error("delete messages failed: ${ex.message}")
                throw QueueServiceException(ex.message ?: "")
            }
            val successful = res.successful().map {
                DeleteMessage(it.id(), true)
            }
            val failed = res.failed().map {
                DeleteMessage(it.id(), false, it.message())
            }
            logger.debug("delete messages = ${successful + failed}")
            successful + failed
        }

}
