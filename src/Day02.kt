const val UP = "up"
const val DOWN = "down"
fun main() {
    fun part1(input: List<String>): Int {
        var forwardInt = 0
        var verticalInt = 0
        for (i in 0..input.size-1) {
            val command = input[i].split(" ").map { it -> it.trim() }
            if (UP.equals(command[0])) {
                verticalInt -= command[1].toInt()
                continue
            }
            if (DOWN.equals(command[0])) {
                verticalInt += command[1].toInt()
                continue
            }
            forwardInt += command[1].toInt()
        }
        return forwardInt * verticalInt
    }

    fun part2(input: List<String>): Int {
        var forwardInt = 0
        var verticalInt = 0
        var aimInt = 0
        for (i in 0..input.size-1) {
            val command = input[i].split(" ").map { it -> it.trim() }
            if (UP.equals(command[0])) {
                aimInt -= command[1].toInt()
                continue
            }
            if (DOWN.equals(command[0])) {
                aimInt += command[1].toInt()
                continue
            }
            val step = command[1].toInt()
            forwardInt += step
            verticalInt += aimInt*step
        }
        return forwardInt * verticalInt
    }


    // test sample
    val testInput = readInput("test_resources/Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    var input = readInput("resources/Day02_route")
    println(part1(input))
    println(part2(input))
}
