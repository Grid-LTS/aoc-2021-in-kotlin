import kotlin.streams.asSequence
import kotlin.streams.toList

fun main() {

    fun part1(input: List<String>): Int {
        var counter = 0
        for (i in 0..input.size - 1) {
            var sep = input[i].split(" | ").asSequence().toMutableList()
            var output = sep[1].split(" ")
            for (j in 0..output.size - 1) {
                if (arrayListOf(2, 3, 4, 7).contains(output[j].length)) {
                    counter++
                }
            }
        }
        return counter
    }

    fun getApos(input: List<String>): Char {
        val onePattern = input.first { it.length == 2 }
        val sevenPattern = input.first { it.length == 3 }
        return sevenPattern.chars().asSequence().minus(onePattern.chars().asSequence()).first().toChar()
    }

    fun getDGpos(input: List<String>, aPos: Char): List<Char> {
        val twoThreeFivePattern = input.filter { it.length == 5 }.map { str ->
            str.chars().filter { it.toChar() != aPos }.toList()
        }.toList()
        return twoThreeFivePattern[0].intersect(twoThreeFivePattern[1])
                .intersect(twoThreeFivePattern[2]).map { it.toChar() }.toList()
    }

    fun getTwoPattern(input: List<String>, bPos: Char, three: List<Char>): List<Char> {
        val filtered = input.filter { it.length == 5 }
                .filter { it.toCharArray().toMutableList().minus(three).isNotEmpty() }
        return filtered.filter { str -> !str.chars().anyMatch { it.toChar() == bPos } }.first().chars().toList()
                .map { it.toChar() }
    }

    fun getPattern(input: List<String>, no: Int): List<Char> {
        return input.filter { it.length == no }.first().toCharArray().toMutableList()
    }

    fun calPower(baseValue: Int, powerValue: Int): Int {
        return if (powerValue != 0) baseValue * calPower(baseValue, powerValue - 1) else 1
    }

    fun part2(input: List<String>): Int {
        var sumAll = 0
        for (i in 0..input.size - 1) {
            val wiring = Array(10) { listOf<Char>() }
            val sep = input[i].split(" | ").asSequence().toMutableList()
            val test = sep[0].split(" ")
            var apos = getApos(test)
            wiring[1] = getPattern(test, 2)
            wiring[4] = getPattern(test, 4)
            wiring[7] = getPattern(test, 3)
            wiring[8] = getPattern(test, 7)
            val dgPos: List<Char> = getDGpos(test, apos)
            if (dgPos.size != 2) {
                throw IllegalStateException("Cannot determin d g positions")
            }
            wiring[3] = dgPos.plus(wiring[7])
            val bPositions = wiring[4].plus(dgPos).distinct().plus(arrayListOf(apos))
                    .minus(wiring[3])
            if (bPositions.size != 1) {
                throw IllegalStateException("Cannot determin b positions")
            }
            val bpos = bPositions.get(0)
            wiring[9] = wiring[3].plus(arrayListOf(bpos)).distinct()
            wiring[2] = getTwoPattern(test, bpos, wiring[3])
            wiring[5] = test.filter { it.length == 5 }
                    .filter { str -> str.toCharArray().toList().minus(wiring[2]).size != 0 }
                    .filter { str -> str.toCharArray().toList().minus(wiring[3]).size != 0 }
                    .first().toList()
            val sixOrNullPatter = test.filter { it.length == 6 }
                    .filter { str -> str.toCharArray().toList().minus(wiring[9]).size != 0 }

            wiring[6] = sixOrNullPatter.filter { str -> str.chars().toList().map { it.toChar() }.intersect(dgPos).size == 2 }
                    .first().toList()
            val output = sep[1].split(" ")
            val tenPower = output.size - 1
            var sum = 0
            for (j in 0..output.size - 1) {
                val s = output[j]
                var match: List<Char> = wiring[0]
                for (x in 1..wiring.size - 1) {
                    val code = s.toCharArray().toList()
                    if (code.size != wiring[x].size) {
                        continue
                    }
                    if (wiring[x].minus(code).size == 0) {
                        match = wiring[x]
                        break
                    }
                }
                var index = wiring.indexOf(match)
                if (index < 0) {
                    index = 0
                }
                sum += index * calPower(10, tenPower - j)
            }
            sumAll += sum
        }

        return sumAll
    }

    val testInput = readInput("test_resources/Day08_test")

    check(part1(testInput) == 26)
    check(part2(testInput) == 61229)

    var input = readInput("resources/Day08_signals")
    println(part1(input))
    println(part2(input))
}
