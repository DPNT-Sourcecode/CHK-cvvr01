package solutions.SUM

object SumSolution {
    fun sum(x: Int, y: Int): Int {
        validateInputNumbers(x, y)
        return x + y
    }

    fun validateInputNumbers(x: Int, y: Int) {
        if (x < 0) {
            throw IllegalArgumentException("x should be positive")
        }
        if (x > 100) {
            throw IllegalArgumentException("x should be max 100")
        }
        if (y < 0) {
            throw IllegalArgumentException("y should be positive")
        }
        if (y > 100) {
            throw IllegalArgumentException("y should be max 100")
        }
    }
}

