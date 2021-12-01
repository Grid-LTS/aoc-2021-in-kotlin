fun main() {
    fun part1(input: List<String>): Int {
        var increases = 0;
        for (i in 1..input.size-1) {
            if (input[i].toInt() > input[i-1].toInt()){
                increases++
            }
        }
        return increases
    }

    fun partSlidingWindow(input: List<String>): Int {
        var increases = 0;
        var sumA = 0
        var start = 1
        for (j in -1..1) {
            sumA += input[start+j].toInt()
        }
        start++
        var sumB = 0
        for (i in start..input.size-2) {
            for (j in -1..1) {
                sumB += input[i + j].toInt()
            }
            if (sumB > sumA){
                increases++
            }
            sumA = sumB
            sumB = 0
        }
        return increases
    }

    // test sample
    val testInput = readInput("test_resources/Day01_test")
    check(part1(testInput) == 7)
    check(partSlidingWindow(testInput) == 5)

    var input = readInput("resources/Day01_sonar")
    println(part1(input))
    println(partSlidingWindow(input))
}
