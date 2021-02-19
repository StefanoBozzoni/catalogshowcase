package com.vjapp.catalogshowcase.domain

interface IRepository {
    suspend fun httpBinGetDemo(): String

}