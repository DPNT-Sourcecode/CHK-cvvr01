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
        val remainingCheckoutItemsMap = removeFreeItems(checkoutItemsMap, items)
        return remainingCheckoutItemsMap.entries.sumOf { (sku, quantity) ->
            val item = items.find { it.sku == sku }
            if (item == null) {
                return -1
            } else {
                val sortedSpecialOffers = item.specialOffers.sortedByDescending { it.quantity }

                var totalCost = 0
                var remainingQuantity = quantity

                for (specialOffer in sortedSpecialOffers) {
                    while (remainingQuantity >= specialOffer.quantity) {
                        if (specialOffer.price != null) {
                            remainingQuantity -= specialOffer.quantity
                            totalCost += specialOffer.price
                        } else if (specialOffer.freeSku != null) {
                            remainingQuantity -= specialOffer.quantity
                            totalCost += item.price * specialOffer.quantity
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

    private fun removeFreeItems(checkoutItemsMap: Map<Char, Int>, items: List<Item>): Map<Char, Int> {
        val updatedCheckoutItemsMap = checkoutItemsMap.toMutableMap()

        checkoutItemsMap.forEach { (sku, quantity) ->
            val item = items.find { it.sku == sku }

            item?.let {
                val sortedSpecialOffers = it.specialOffers.sortedByDescending { offer -> offer.quantity }

                for (specialOffer in sortedSpecialOffers) {
                    var applicableTimes = quantity / specialOffer.quantity

                    while (applicableTimes > 0 && specialOffer.freeSku != null) {
                        val freeItemQuantity = updatedCheckoutItemsMap[specialOffer.freeSku]
                        if (freeItemQuantity != null && freeItemQuantity > 0) {
                            updatedCheckoutItemsMap[specialOffer.freeSku] = freeItemQuantity - 1
                            applicableTimes--
                        } else {
                            break
                        }
                    }
                }
            }
        }

        return updatedCheckoutItemsMap.filter { it.value > 0 }
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