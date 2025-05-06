package com.shashank.expense.tracker.domain.usecase

import com.shashank.expense.tracker.presentation.common.Result
import kotlinx.coroutines.flow.Flow

abstract class BaseUseCase<in P, R> {
    abstract operator fun invoke(params: P): Flow<Result<R>>
}

abstract class NoParamsUseCase<R> {
    abstract operator fun invoke(): Flow<Result<R>>
}

abstract class SuspendUseCase<in P, R> {
    abstract suspend operator fun invoke(params: P): Result<R>
}

abstract class SuspendNoParamsUseCase<R> {
    abstract suspend operator fun invoke(): Result<R>
} 