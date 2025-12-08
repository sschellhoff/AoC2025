package utils

fun <T> List<T>.loopEachPair(action: (a: T, b: T) -> Unit) {
    this.forEachIndexed { indexA, a ->
        for (indexB in (indexA + 1) until this.size) {
            val b = this[indexB]
            action(a, b)
        }
    }
}

fun <T, R> List<T>.mapEachPair(action: (a: T, b: T) -> R): List<R> =
    this.flatMapIndexed { index, a ->
        this.drop(index + 1).map { b ->
            action(a, b)
        }
    }

fun <T, R> List<T>.mapEachPairIndexed(action: (indexA: Int, a: T, indexB: Int, b: T) -> R): List<R> =
    this.flatMapIndexed { indexA, a ->
        this.drop(indexA + 1).mapIndexed { deltaIndexB, b ->
            val indexB = deltaIndexB + indexA + 1
            action(indexA, a, indexB, b)
        }
    }