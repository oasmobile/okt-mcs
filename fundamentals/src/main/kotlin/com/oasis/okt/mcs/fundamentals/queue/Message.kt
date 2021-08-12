package com.oasis.okt.mcs.fundamentals.queue

abstract class Message(open val id: String, open val md5OfBody: String)

data class SendMessage(
    override val id: String = "",
    override val md5OfBody: String = "",
    val status: Boolean,
    val message: String = "",
) : Message(id, md5OfBody)

data class ReceiveMessage(
    override val id: String,
    override val md5OfBody: String,
    val receiptHandle: String,
    val body: String,
) : Message(id, md5OfBody)

data class DeleteMessage(
    override val id: String = "",
    val status: Boolean,
    val message: String = "",
) : Message(id, "")