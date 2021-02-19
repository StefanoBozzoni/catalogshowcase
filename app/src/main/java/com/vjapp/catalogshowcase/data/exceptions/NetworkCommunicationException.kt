package com.vjapp.catalogshowcase.data.exceptions

class NetworkCommunicationException(cause: Throwable?) : Exception(cause) {
    constructor() : this(null)
}