package lesson3

import java.util.*

interface CheckableSortedSet<T> : SortedSet<T> {
    fun checkInvariant(): Boolean
    fun height(): Int
    fun hasNext(): Boolean
    fun next(): T

    // Асимптотика равна методу remove(): O(log(N))
    // Ресурсоемкость: O(1)
    fun remove()
}