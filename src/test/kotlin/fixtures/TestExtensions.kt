package com.example.fixtures

import arrow.core.Either
import arrow.core.right
import org.assertj.core.api.ObjectAssert

fun <L, R> ObjectAssert<Either<L, R>>.isRightWith(expected: R): ObjectAssert<Either<L, R>> =
    isEqualTo(expected.right())