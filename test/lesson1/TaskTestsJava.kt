package lesson1

import org.junit.jupiter.api.Tag
import java.lang.IllegalArgumentException
import kotlin.test.Test
import org.junit.jupiter.api.assertThrows


class TaskTestsJava : AbstractTaskTests() {

    @Test
    @Tag("3")
    fun testSortTimesJava() {
        sortTimes { inputName, outputName -> JavaTasks.sortTimes(inputName, outputName) }
    }

    @Test
    @Tag("4")
    fun testSortAddressesJava() {
        sortAddresses { inputName, outputName -> JavaTasks.sortAddresses(inputName, outputName) }
        assertThrows<NotImplementedError> { JavaTasks.sortTimes("input/newAddr_in1.txt", "output.txt") }
        assertThrows<NotImplementedError> { JavaTasks.sortTimes("input/newAddr_in2.txt", "output.txt") }
        assertThrows<NotImplementedError> { JavaTasks.sortTimes("input/newAddr_in3.txt", "output.txt") }
    }

    @Test
    @Tag("4")
    fun testSortTemperaturesJava() {
        sortTemperatures { inputName, outputName -> JavaTasks.sortTemperatures(inputName, outputName) }
        assertThrows<NotImplementedError> { JavaTasks.sortTimes("input/newTemp_in1.txt", "output.txt") }
        assertThrows<NotImplementedError> { JavaTasks.sortTimes("input/newAddr_in2.txt", "output.txt") }
    }

    @Test
    @Tag("4")
    fun testSortSequenceJava() {
        sortSequence { inputName, outputName -> JavaTasks.sortSequence(inputName, outputName) }
    }

    @Test
    @Tag("2")
    fun testMergeArraysJava() {
        mergeArrays { first, second -> JavaTasks.mergeArrays<Int?>(first, second) }
    }
}
