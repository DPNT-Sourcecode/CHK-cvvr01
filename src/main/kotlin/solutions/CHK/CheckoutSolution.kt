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
        val remainingCheckoutItemsMap = removeFreeItems(checkoutItemsMap, items).toMutableMap()
        val totalBundlePrice = setupBundleOffers().sumOf {
            applyMostExpensiveBundle(remainingCheckoutItemsMap, items, it)
        }

        val remainingItemsSum = remainingCheckoutItemsMap.entries.sumOf { (sku, quantity) ->
            calculateItemCost(sku, quantity, items) ?: return -1
        }

        return totalBundlePrice + remainingItemsSum
    }

    private fun calculateItemCost(sku: Char, quantity: Int, items: List<Item>): Int? {
        val item = items.find { it.sku == sku } ?: return null
        return calculateCostWithSpecialOffers(item, quantity)
    }

    private fun calculateCostWithSpecialOffers(item: Item, quantity: Int): Int {
        val sortedSpecialOffers = item.specialOffers.sortedByDescending { it.quantity }

        var totalCost = 0
        var remainingQuantity = quantity

        for (specialOffer in sortedSpecialOffers) {
            if (specialOffer.price != null) {
                val applicableTimes = remainingQuantity / specialOffer.quantity
                totalCost += applicableTimes * specialOffer.price
                remainingQuantity -= applicableTimes * specialOffer.quantity
            } else if (specialOffer.freeSku != null) {
                remainingQuantity -= specialOffer.quantity
                totalCost += item.price * specialOffer.quantity
            }
        }

        totalCost += remainingQuantity * item.price
        return totalCost
    }

    private fun applyMostExpensiveBundle(checkoutItemsMap: MutableMap<Char, Int>, items: List<Item>, bundleOffer: BundleOffer): Int {
        var totalBundlePrice = 0

        while (canFormBundle(checkoutItemsMap, bundleOffer.skus, bundleOffer.quantity)) {
            val sortedEligibleItems = items.filter { it.sku in bundleOffer.skus && checkoutItemsMap.getOrDefault(it.sku, 0) > 0 }
                .sortedByDescending { it.price }

            val selectedItems = selectItemsForBundle(checkoutItemsMap, sortedEligibleItems, bundleOffer.quantity)
            if (selectedItems.isNotEmpty()) {
                selectedItems.forEach { sku ->
                    checkoutItemsMap[sku] = checkoutItemsMap[sku]!! - 1
                    if (checkoutItemsMap[sku]!! <= 0) {
                        checkoutItemsMap.remove(sku)
                    }
                }

                totalBundlePrice += bundleOffer.price
            } else {
                break
            }
        }

        return totalBundlePrice
    }

    private fun canFormBundle(checkoutItemsMap: Map<Char, Int>, skus: List<Char>, bundleQuantity: Int): Boolean {
        return checkoutItemsMap.filterKeys { it in skus }.values.sum() >= bundleQuantity
    }

    private fun selectItemsForBundle(checkoutItemsMap: Map<Char, Int>, sortedItems: List<Item>, bundleQuantity: Int): List<Char> {
        val selectedItems = mutableListOf<Char>()
        var count = 0

        for (item in sortedItems) {
            val availableQuantity = checkoutItemsMap.getOrDefault(item.sku, 0)
            val quantityToAdd = minOf(availableQuantity, bundleQuantity - count)
            repeat(quantityToAdd) { selectedItems.add(item.sku) }
            count += quantityToAdd

            if (count >= bundleQuantity) break
        }

        return if (count >= bundleQuantity) selectedItems else listOf()
    }

    private fun removeFreeItems(checkoutItemsMap: Map<Char, Int>, items: List<Item>): Map<Char, Int> {
        val updatedCheckoutItemsMap = checkoutItemsMap.toMutableMap()

        checkoutItemsMap.forEach { (sku, quantity) ->
            items.find { it.sku == sku }?.let { item ->
                processItemForFreeOffers(updatedCheckoutItemsMap, item, quantity)
            }
        }

        return updatedCheckoutItemsMap.filter { it.value > 0 }
    }

    private fun processItemForFreeOffers(updatedCheckoutItemsMap: MutableMap<Char, Int>, item: Item, quantity: Int) {
        item.specialOffers.sortedByDescending { it.quantity }
            .forEach { offer ->
                applySpecialOffer(updatedCheckoutItemsMap, offer, quantity)
            }
    }

    private fun applySpecialOffer(updatedCheckoutItemsMap: MutableMap<Char, Int>, offer: SpecialOffer, quantity: Int) {
        var applicableTimes = quantity / offer.quantity

        while (applicableTimes > 0 && offer.freeSku != null) {
            val freeItemQuantity = updatedCheckoutItemsMap[offer.freeSku]
            if (freeItemQuantity != null && freeItemQuantity > 0) {
                updatedCheckoutItemsMap[offer.freeSku] = freeItemQuantity - 1
                applicableTimes--
            } else {
                break
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
            Item('F', 10, listOf(SpecialOffer(3, freeSku = 'F'))),
            Item('G', 20),
            Item('H', 10, listOf(SpecialOffer(5, price = 45), SpecialOffer(10, price = 80))),
            Item('I', 35),
            Item('J', 60),
            Item('K', 70, listOf(SpecialOffer(2, price = 120))),
            Item('L', 90),
            Item('M', 15),
            Item('N', 40, listOf(SpecialOffer(3, freeSku = 'M'))),
            Item('O', 10),
            Item('P', 50, listOf(SpecialOffer(5, price = 200))),
            Item('Q', 30, listOf(SpecialOffer(3, price = 80))),
            Item('R', 50, listOf(SpecialOffer(3, freeSku = 'Q'))),
            Item('S', 20),
            Item('T', 20),
            Item('U', 40, listOf(SpecialOffer(4, freeSku = 'U'))),
            Item('V', 50, listOf(SpecialOffer(2, price = 90), SpecialOffer(3, price = 130))),
            Item('W', 20),
            Item('X', 17),
            Item('Y', 20),
            Item('Z', 21),
        )
    }

    private fun setupBundleOffers(): List<BundleOffer> {
        return listOf(
            BundleOffer(
                3,
                45,
                listOf('S', 'T', 'X', 'Y', 'Z'),
            ),
        )
    }
}

data class Item(
    val sku: Char,
    val price: Int,
    val specialOffers: List<SpecialOffer> = emptyList(),
)

data class SpecialOffer(
    val quantity: Int,
    val price: Int? = null,
    val freeSku: Char? = null
)

data class BundleOffer(
    val quantity: Int,
    val price: Int,
    val skus: List<Char> = emptyList(),
)

