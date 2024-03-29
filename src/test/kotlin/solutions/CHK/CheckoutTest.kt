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
        assertEquals(-1, CheckoutSolution.checkout("Ö"))
        assertEquals(-1, CheckoutSolution.checkout("Ä"))
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
        assertEquals(50, CheckoutSolution.checkout("R"))
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
        assertEquals(200, CheckoutSolution.checkout("AAAAA"))
        assertEquals(250, CheckoutSolution.checkout("AAAAAA"))
        assertEquals(30, CheckoutSolution.checkout("B"))
        assertEquals(45, CheckoutSolution.checkout("BB"))
        assertEquals(75, CheckoutSolution.checkout("BBB"))
        assertEquals(90, CheckoutSolution.checkout("BBBB"))
        assertEquals(120, CheckoutSolution.checkout("BBBBB"))
    }

    @Test
    fun `two special offers are being used`() {
        // 5 times offer and 3 times offer
        assertEquals(330, CheckoutSolution.checkout("AAAAAAAA"))
    }

    @Test
    fun `free item special offer is being used`() {
        assertEquals(80, CheckoutSolution.checkout("EEB"))
    }

    @Test
    fun `free item special offer is not causing an error when no free item in checkout`() {
        assertEquals(80, CheckoutSolution.checkout("EE"))
    }

    @Test
    fun `multiple free item special offers are being used`() {
        assertEquals(160, CheckoutSolution.checkout("EEEEBB"))
        assertEquals(160, CheckoutSolution.checkout("EEBBEE"))
        assertEquals(160, CheckoutSolution.checkout("BBEEEE"))
        assertEquals(160, CheckoutSolution.checkout("BEBEEE"))
        assertEquals(160, CheckoutSolution.checkout("EBEBEE"))
    }

    @Test
    fun `multiple free item special offers are being used for all free items in checkout`() {
        assertEquals(160, CheckoutSolution.checkout("EEEEB"))
        assertEquals(160, CheckoutSolution.checkout("EEEBE"))
        assertEquals(160, CheckoutSolution.checkout("EEBEE"))
        assertEquals(160, CheckoutSolution.checkout("EBEEE"))
        assertEquals(160, CheckoutSolution.checkout("BEEEE"))
    }

    @Test
    fun `multiple free item special offers are combined with multiple special offers`() {
        assertEquals(335, CheckoutSolution.checkout("EEEEBBAAABB"))
    }

    @Test
    fun `buy two get 3 offer`() {
        assertEquals(10, CheckoutSolution.checkout("F"))
        assertEquals(20, CheckoutSolution.checkout("FF"))
        assertEquals(20, CheckoutSolution.checkout("FFF"))
        assertEquals(30, CheckoutSolution.checkout("FFFF"))
        assertEquals(40, CheckoutSolution.checkout("FFFFF"))
        assertEquals(40, CheckoutSolution.checkout("FFFFFF"))
        assertEquals(50, CheckoutSolution.checkout("FFFFFFF"))
        assertEquals(40, CheckoutSolution.checkout("U"))
        assertEquals(80, CheckoutSolution.checkout("UU"))
        assertEquals(120, CheckoutSolution.checkout("UUU"))
        assertEquals(70, CheckoutSolution.checkout("K"))
        assertEquals(120, CheckoutSolution.checkout("KK"))
        assertEquals(190, CheckoutSolution.checkout("KKK"))
        assertEquals(240, CheckoutSolution.checkout("KKKK"))
    }

    @Test
    fun `buy two get 3 offer mixed with other special offers`() {
        assertEquals(195, CheckoutSolution.checkout("FFFAAABB"))
        assertEquals(260, CheckoutSolution.checkout("FFFAAABBEE"))
    }

    @Test
    fun `buy one item each`() {
        assertEquals(837, CheckoutSolution.checkout("ABCDEFGHIJKLMNOPQRSTUVWXYZ"))
        assertEquals(837, CheckoutSolution.checkout("ZYXWVUTSRQPONMLKJIHGFEDCBA"))
    }

    @Test
    fun `use one bundle`() {
        assertEquals(45, CheckoutSolution.checkout("STX"))
    }

    @Test
    fun `use bundle with most expensive items`() {
        assertEquals(82, CheckoutSolution.checkout("STXYZ"))
        assertEquals(79, CheckoutSolution.checkout("ZZZXX"))
    }
}
