
import solutions.ARRS.ArraySumSolution
import solutions.CHK.CheckoutSolution
import solutions.CHL.CheckliteSolution
import solutions.FIZ.FizzBuzzSolution
import solutions.HLO.HelloSolution
import solutions.IRNG.IntRangeSolution
import solutions.SUM.SumSolution.sum

import com.google.gson.JsonElement

class EntryPointMapping {

    fun sum(p: List<JsonElement>): Any {
        return sum(p[0].asInt, p[1].asInt)
    }

    fun hello(p: List<JsonElement>): Any {
        return HelloSolution.hello(p[0].asString)
    }

    fun arraySum(p: List<JsonElement>): Any {
        val intArray: ArrayList<Int> = ArrayList()
        for (jsonElement in p[0].asJsonArray) {
            intArray.add(jsonElement.asInt)
        }
        return ArraySumSolution.sum(intArray)
    }

    fun intRange(p: List<JsonElement>): Any {
        return IntRangeSolution.generate(p[0].asInt, p[1].asInt)
    }

    fun fizzBuzz(p: List<JsonElement>): Any {
        return FizzBuzzSolution.fizzBuzz(p[0].asInt)
    }

    fun checkout(p: List<JsonElement>): Any {
        return CheckoutSolution.checkout(p[0].asString)
    }

    fun checklite(p: List<JsonElement>): Any {
        return CheckliteSolution.checklite(p[0].asString)
    }
}
