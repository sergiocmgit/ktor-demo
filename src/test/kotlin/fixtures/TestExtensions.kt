package fixtures

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import org.assertj.core.api.ObjectAssert

fun <L, R> ObjectAssert<Either<L, R>>.isRightWith(expected: R): ObjectAssert<Either<L, R>> = isEqualTo(expected.right())

fun <L, R> ObjectAssert<Either<L, R>>.isLeftWith(expected: L): ObjectAssert<Either<L, R>> = isEqualTo(expected.left())
