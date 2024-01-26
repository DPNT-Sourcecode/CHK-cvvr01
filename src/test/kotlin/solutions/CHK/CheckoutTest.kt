package solutions.CHK

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CheckoutTest {

    @Test
    fun `invalid input returns -1`() {
        assertEquals(-1, CheckoutSolution.checkout("1"))
    }
}