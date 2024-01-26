package solutions.CHK

object CheckoutSolution {
    fun checkout(skus: String): Int {
        val items = setupItems()
        var skuList = skus.split("")
        if (validateSkus(skuList)) {
            return -1
        }
        skuList = toUpperCase(skuList)

        return skuList.sumOf { sku ->
            val item = items.find { it.sku == sku[0] }
            if (item == null) {
                return -1
            } else {
                item.price
            }
        }
    }

    private fun toUpperCase(skuList: List<String>): List<String> {
        return skuList.map { it.uppercase() }
    }

    private fun validateSkus(skuList: List<String>): Boolean {
        skuList.forEach {
            if (!it.matches(Regex("[A-Z]"))) {
                return false
            }
        }
        return true
    }

    private fun setupItems(): List<Item> {
        return listOf(
            Item('A', 50, listOf(SpecialOffer(3, 130))),
            Item('B', 30, listOf(SpecialOffer(2, 45))),
            Item('C', 20),
            Item('D', 15),
        )
    }
}

data class Item(val sku: Char, val price: Int, val specialOffers: List<SpecialOffer> = emptyList())

data class SpecialOffer(val quantity: Int, val price: Int)
