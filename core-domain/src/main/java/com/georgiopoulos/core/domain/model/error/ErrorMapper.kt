package com.georgiopoulos.core.domain.model.error

interface ErrorMapper {

    fun mapError(exception: Throwable?): ErrorModel
}
