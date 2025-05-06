package com.shashank.expense.tracker.domain.repository

import com.shashank.expense.tracker.presentation.common.Result
import kotlinx.coroutines.flow.Flow

interface BaseRepository<T> {
    fun getAll(): Flow<Result<List<T>>>
    fun getById(id: Long): Flow<Result<T>>
    suspend fun insert(item: T): Result<Unit>
    suspend fun update(item: T): Result<Unit>
    suspend fun delete(id: Long): Result<Unit>
} 