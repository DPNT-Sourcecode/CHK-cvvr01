package solutions.CHK

object CheckoutSolution {
    fun checkout(skus: String): Int {
        val items = setupItems()
        var skuList = skus.split("")
        validateSkus(skuList)
        skuList = toUpperCase(skuList)

    }

    private fun toUpperCase(skuList: List<String>): List<String> {
        return skuList.map { it.uppercase() }
    }

    private fun validateSkus(skuList: List<String>) {
        skuList.forEach {
            if (!it.matches(Regex("[A-Z]"))) {
                throw IllegalArgumentException("Invalid SKU: $it")
            }
        }
    }

    private fun setupItems(): List<Item> {
        return listOf(
            Item('A', 50, listOf(SpecialOffer(3, 130))),
            Item('B', 30, listOf(SpecialOffer(2, 45))),
            Item('C', 20),
            Item('D', 15)
        )
    }
}

data class Item(val sku: Char, val price: Int, val specialOffers: List<SpecialOffer> = emptyList())

data class SpecialOffer(val quantity: Int, val price: Int)