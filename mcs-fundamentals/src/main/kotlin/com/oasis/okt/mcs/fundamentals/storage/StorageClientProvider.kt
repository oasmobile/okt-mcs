package com.oasis.okt.mcs.fundamentals.storage

interface StorageClientProvider<T> {
    val client:T
}