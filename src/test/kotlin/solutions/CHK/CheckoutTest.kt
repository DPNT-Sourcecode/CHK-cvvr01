package solutions.CHK

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CheckoutTest {

    @Test
    fun invalidSkusThrowError() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            CheckoutSolution.checkout("123")
        }
    }
}