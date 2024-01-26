package solutions.CHK

object CheckoutSolution {
    fun checkout(skus: String): Int {
        val items = setupItems()
        val skuList: List<Char> = skus.toList()
        if (containsInvalidSkus(skuList)) {
            return -1
        }
        skuList = toUpperCase(skuList)

        val itemQuantities = getItemQuantities(skuList)

        return skuList.sumOf { sku ->
            val item = items.find { it.sku == sku[0] }
            item?.price ?: return -1
        }
    }

    private fun getItemQuantities(skuList: List<Char>): Map<Char, Int> {
        return skuList
            .groupingBy { it }
            .eachCount()
    }

    private fun toUpperCase(skuList: List<Char>): List<String> {
        return skuList.map { it.uppercase() }
    }

    private fun containsInvalidSkus(skuList: List<Char>): Boolean {
        skuList.forEach {
            if (!it.matches(Regex("[A-Z]"))) {
                return true
            }
        }
        return false
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



