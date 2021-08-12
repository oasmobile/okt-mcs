package com.oasis.okt.mcs.fundamentals.queue

interface QueueClientProvider<T> {
    val client: T
}