package solutions.CHK

object CheckoutSolution {
    fun checkout(skus: String): Int {
        val skuList = skus.map { it.toString() }
        if (containsInvalidSkus(skuList)) {
            return -1
        }

        val checkoutItemsMap = getCheckoutItemsMap(skuList)
        val items = setupItems()
        return getSumOfItems(checkoutItemsMap, items)
    }

    private fun getSumOfItems(checkoutItemsMap: Map<Char, Int>, items: List<Item>): Int {
        return checkoutItemsMap.entries.sumOf { (sku, quantity) ->
            val item = items.find { it.sku == sku }
            if (item == null) {
                return -1
            } else {
                val sortedSpecialOffers = item.specialOffers.sortedByDescending { it.quantity }

                var totalCost = 0
                var remainingQuantity = quantity

                for (specialOffer in sortedSpecialOffers) {
                    while (remainingQuantity >= specialOffer.quantity) {
                        if (specialOffer.freeSku != null) {
                            val freeItem = items.find { it.sku == specialOffer.freeSku }
                            if (freeItem != null) {
                                remainingQuantity -= specialOffer.quantity
                                totalCost += specialOffer.quantity * item.price
                                totalCost -= freeItem.price
                            }
                        } else if (specialOffer.price != null) {
                            remainingQuantity -= specialOffer.quantity
                            totalCost += specialOffer.price
                        } else {
                            return -1
                        }
                    }
                }

                totalCost += remainingQuantity * item.price

                totalCost
            }
        }
    }

    private fun getCheckoutItemsMap(skuList: List<String>): Map<Char, Int> {
        return skuList
            .flatMap { it.toList() }
            .groupingBy { it }
            .eachCount()
    }

    private fun containsInvalidSkus(skuList: List<String>): Boolean {
        return skuList.any { it.none(Char::isLetter) }
    }

    private fun setupItems(): List<Item> {
        return listOf(
            Item('A', 50, listOf(SpecialOffer(3, 130), SpecialOffer(5, 200))),
            Item('B', 30, listOf(SpecialOffer(2, 45))),
            Item('C', 20),
            Item('D', 15),
            Item('E', 40, listOf(SpecialOffer(2, freeSku = 'B'))),
        )
    }
}

data class Item(val sku: Char, val price: Int, val specialOffers: List<SpecialOffer> = emptyList())

data class SpecialOffer(val quantity: Int, val price: Int? = null, val freeSku: Char? = null)
