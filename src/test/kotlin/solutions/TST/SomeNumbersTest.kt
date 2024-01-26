package solutions.TST

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SomeNumbersTest {
    @Test
    fun one() {
        Assertions.assertEquals(1, One.invoke())
    }
}