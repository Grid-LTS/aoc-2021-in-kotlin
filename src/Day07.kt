import java.lang.Math.abs
import kotlin.math.roundToInt

fun main() {

    fun calcFuel(crabs: List<Int>, guess: Int): Int {
        var fuel = 0
        for (i in 0..crabs.size-1) {
            fuel += abs(crabs[i]-guess)
        }
        return fuel
    }

    fun calcFuel2(crabs: List<Int>, guess: Int): Int {
        var fuel = 0
        for (i in 0..crabs.size-1) {
            val dist = abs(crabs[i]-guess)
            fuel += dist * (dist +1) /2
        }
        return fuel
    }

    fun part1(input: List<String>): Int {
        val crabs = input[0].split(",").asSequence().map { it.trim().toInt() }.toMutableList()
        val n = crabs.size
        var sum = 0
        for (i in 0..crabs.size -1) {
            sum += crabs[i]
        }
        var guess = sum/n
        // always calculate fuel for three adjacent guess and go along the decrease until you hit
        // minimum
        var minFuel = calcFuel(crabs, guess)
        var optLeft = calcFuel(crabs, guess-1)
        var optRight = calcFuel(crabs, guess +1)
        while (minFuel > optLeft || minFuel > optRight)  {
            if (minFuel > optLeft) {
                guess--
                optRight = minFuel
                minFuel = optLeft
                optLeft = calcFuel(crabs, guess-1)
            }
            if (minFuel > optRight) {
                guess++
                optLeft = minFuel
                minFuel = optRight
                optRight = calcFuel(crabs, guess+1)
            }
        }
        return minFuel
    }

    fun part2(input: List<String>): Int {
        val crabs = input[0].split(",").asSequence().map { it.trim().toInt() }.toMutableList()
        val n = crabs.size
        var sum = 0
        for (i in 0..crabs.size -1) {
            sum += crabs[i]
        }
        var guess = sum/n
        var minFuel = calcFuel2(crabs, guess)
        var optLeft = calcFuel2(crabs, guess-1)
        var optRight = calcFuel2(crabs, guess +1)
        while (minFuel > optLeft || minFuel > optRight)  {
            if (minFuel > optLeft) {
                guess--
                optRight = minFuel
                minFuel = optLeft
                optLeft = calcFuel2(crabs, guess-1)
            }
            if (minFuel > optRight) {
                guess++
                optLeft = minFuel
                minFuel = optRight
                optRight = calcFuel2(crabs, guess+1)
            }
        }
        return minFuel
    }

    // test sample
    val testInput = readInput("test_resources/Day07_test")

    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    var input = readInput("resources/Day07_crab_position")
    println(part1(input))
    println(part2(input))
}
