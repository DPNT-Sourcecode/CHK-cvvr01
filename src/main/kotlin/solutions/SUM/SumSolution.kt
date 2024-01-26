package solutions.SUM

object SumSolution {
    fun sum(x: Int, y: Int): Int {
        validateInputNumbers(x, y)
        return x + y
    }

    private fun validateInputNumber(value: Int, variableName: String) {
        require(value in 0..100) { "$variableName should be between 0 and 100 (inclusive)" }
    }

    private fun validateInputNumbers(x: Int, y: Int) {
        validateInputNumber(x, "x")
        validateInputNumber(y, "y")
    }
}


