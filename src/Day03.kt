fun main() {
    fun part1(input: List<String>): Int {
        val len = input[0].length
        val occurences: IntArray = IntArray(len) { 0 }
        for (i in 0..input.size - 1) {
            val digits = input[i].toCharArray()
            for (j in 0..len - 1) {
                if (digits[j] == '1') {
                    occurences[j]++
                }
            }
        }
        var gammaRate = 0
        var epsilonRate = 0
        val half = input.size.div(2) + input.size.mod(2)
        for (j in 0..len - 1) {
            val powerOfTwo = ("1" + "0".repeat(len - 1 - j)).toInt(2)
            if (occurences[j] >= half) {
                gammaRate += powerOfTwo
            } else {
                epsilonRate += powerOfTwo
            }
        }
        return epsilonRate * gammaRate
    }

    fun critRating(input: List<CharArray>, occur: Int, crit: (onesOcc: Int, half: Int) -> Boolean): CharArray {
        val half = input.size.div(2) + input.size.mod(2)
        var isOne = crit(occur, half)
        var digit = if (isOne) '1' else '0'
        if (input.size == 0) {
            throw IllegalStateException("No rating found")
        }
        if (input[0].size == 1) {
            return charArrayOf(digit)
        }
        if (input.size == 1) {
            return input[0]
        }
        val len = input[0].size
        val filtered = mutableListOf<CharArray>()
        var nextOneOcc = 0
        for (i in 0..input.size - 1) {
            val digits = input[i]
            if (digits[0] == digit) {
                var nextDigits = digits.copyOfRange(1, len)
                filtered.add(nextDigits)
                if (nextDigits[0] == '1') {
                    nextOneOcc++
                }
            }
        }
        return charArrayOf(digit) + critRating(filtered, nextOneOcc, crit)
    }

    fun part2(input: List<String>): Int {
        val len = input[0].length
        val half = input.size.div(2) + input.size.mod(2)
        val filtered = mutableListOf<CharArray>()
        var nextOcc = 0
        var nextZerOcc = 0
        for (i in 0..input.size - 1) {
            val nextDigits = input[i].toCharArray()
            filtered.add(nextDigits)
            if (nextDigits[0] == '1') {
                nextOcc++
            } else {
                nextZerOcc++
            }

        }
        val oxygenRating = critRating(filtered, nextOcc) { a, b -> a >= b }.joinToString("")
        val scrubberRating = critRating(filtered, nextOcc) { a, b -> a < b }.joinToString("")
        return oxygenRating.toInt(2) * scrubberRating.toInt(2)
    }

    // test sample
    val testInput = readInput("test_resources/Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    var input = readInput("resources/Day03_binary_diagnostic")
    println(part1(input))
    println(part2(input))
}
