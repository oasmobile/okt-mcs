package com.oasis.okt.mcs.fundamentals.queue

interface QueueService {
    suspend fun sendMessage(message: String, delay: Int = 0): SendMessage
    suspend fun sendMessages(messages: List<String>, delay: Int = 0): List<SendMessage>

    suspend fun receiveMessage(waitSeconds: Int = 0): ReceiveMessage?
    suspend fun receiveMessages(batchSize: Int, waitSeconds: Int = 0): List<ReceiveMessage>

    suspend fun deleteMessage(message: ReceiveMessage): DeleteMessage
    suspend fun deleteMessages(messages: List<ReceiveMessage>): List<DeleteMessage>
}

