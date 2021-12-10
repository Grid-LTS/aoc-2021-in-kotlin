import java.lang.Math.abs

fun main() {

    fun makePair(inp: String): Pair<Int, Int> {
        var coord = inp.split(",").map { it.toInt() }
        return Pair(coord[0], coord[1])
    }

    fun part1(input: List<String>, len: Int): Int {
        var sum = 0
        var coordSys = Array(len) { IntArray(len) { 0 } }
        for (i in 0..input.size - 1) {
            var coords = input[i].split(" -> ").map {
                makePair(it)
            }
            if ((coords[0].first != coords[1].first) &&
                    (coords[0].second != coords[1].second)) {
                continue
            }
            val spanx = coords[1].first - coords[0].first
            val spany = coords[1].second - coords[0].second
            var seqx: IntProgression = if (spanx >= 0) (coords[0].first..coords[1].first)
            else (coords[0].first downTo coords[1].first)
            var seqy: IntProgression = if (spany >= 0) (coords[0].second..coords[1].second) else
                (coords[0].second downTo coords[1].second)

            for (k in seqx) {
                for (l in seqy) {
                    if (coordSys[k][l] == 1) {
                        sum += 1
                    }
                    coordSys[k][l]++
                }
            }
        }
        return sum
    }

    fun part2(input: List<String>, len: Int): Int {
        var sum = 0
        var coordSys = Array(len) { IntArray(len) { 0 } }
        for (i in 0..input.size - 1) {
            var coords = input[i].split(" -> ").map {
                makePair(it)
            }
            val spanx = coords[1].first - coords[0].first
            val spany = coords[1].second - coords[0].second
            var seqx: Sequence<Int>
            var seqy: Sequence<Int>
            if (spanx != 0) {
                seqx = (if (spanx > 0) (coords[0].first..coords[1].first)
                else (coords[0].first downTo coords[1].first)).asSequence()
            } else {
                seqx = IntArray(abs(spany) + 1) { coords[1].first }.asSequence()
            }
            if (spany != 0) {
                seqy = (if (spany > 0) (coords[0].second..coords[1].second) else
                    (coords[0].second downTo coords[1].second)).asSequence()
            } else {
                seqy = IntArray(abs(spanx) + 1) { coords[1].second }.asSequence()
            }
            seqx.zip(seqy).forEach { pair ->
                if (coordSys[pair.first][pair.second] == 1) {
                    sum += 1
                }
                coordSys[pair.first][pair.second]++
            }
        }
        return sum
    }

    // test sample
    val testInput = readInput("test_resources/Day05_test")

    check(part1(testInput, 10) == 5)
    check(part2(testInput, 10) == 12)

    var input = readInput("resources/Day05_vents")
    println(part1(input, 1000))
    println(part2(input, 1000))
}
