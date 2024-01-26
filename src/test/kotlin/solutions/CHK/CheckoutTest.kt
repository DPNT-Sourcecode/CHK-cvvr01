package solutions.CHK

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CheckoutTest {

    @Test
    fun `invalid SKUs returns -1`() {
        assertEquals(-1, CheckoutSolution.checkout("123"))
    }

    @Test
    fun `non existing items return -1`() {
        assertEquals(-1, CheckoutSolution.checkout("Z"))
        assertEquals(-1, CheckoutSolution.checkout("AxA"))
    }

    @Test
    fun `empty SKU list returns 0 price`() {
        assertEquals(0, CheckoutSolution.checkout(""))
    }

    @Test
    fun `single SKUs return expected price`() {
        assertEquals(50, CheckoutSolution.checkout("A"))
        assertEquals(30, CheckoutSolution.checkout("B"))
        assertEquals(20, CheckoutSolution.checkout("C"))
        assertEquals(15, CheckoutSolution.checkout("D"))
    }

    @Test
    fun `multiple single SKUs return expected price`() {
        assertEquals(115, CheckoutSolution.checkout("ABCD"))
    }

    @Test
    fun `special offers return expected price`() {
        assertEquals(130, CheckoutSolution.checkout("AAA"))
        assertEquals(45, CheckoutSolution.checkout("BB"))
    }

    @Test
    fun `lowercase SKUs return -1`() {
        assertEquals(-1, CheckoutSolution.checkout("abcd"))
    }

    @Test
    fun `mixed lower and upper case SKUs return -1`() {
        assertEquals(-1, CheckoutSolution.checkout("ABCa"))
    }

    @Test
    fun `revers order of SKUs still return expected price`() {
        assertEquals(115, CheckoutSolution.checkout("DCBA"))
    }

    @Test
    fun `multiple special offer price items still return expected price`() {
        assertEquals(180, CheckoutSolution.checkout("AAAA"))
        assertEquals(230, CheckoutSolution.checkout("AAAAA"))
        assertEquals(260, CheckoutSolution.checkout("AAAAAA"))
    }
}