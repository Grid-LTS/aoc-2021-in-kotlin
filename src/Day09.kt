fun main() {

    fun part1(input: List<String>): Int {
        var len = input[0].length
        var elevation = Array(input.size) { IntArray(len) { 0 } }
        for (i in 0..input.size - 1) {
            elevation[i] = input[i].toCharArray().map { it.toString().toInt() }.toIntArray()
        }
        var sum = 0
        val noRows = elevation.size
        val noCols = elevation[0].size
        for (k in 0..noRows - 1) {
            outerLoop@ for (l in 0..noCols - 1) {
                loopRow@ for (rdelta in -1..1) {
                    loopCol@ for (coldelta in -1..1) {
                        if (k + rdelta < 0 || k + rdelta > noRows - 1) {
                            continue@loopRow
                        }
                        if (l + coldelta < 0 || l + coldelta > noCols - 1) {
                            continue@loopCol
                        }
                        if (coldelta == 0 && rdelta == 0) {
                            continue@loopCol
                        }
                        if (elevation[k][l] >= elevation[k + rdelta][l + coldelta]) {
                            continue@outerLoop
                        }
                    }
                }
                sum += elevation[k][l] + 1
            }
        }
        return sum
    }

    fun fillMap(map: Array<IntArray>, filler: Int, x: Int, y: Int, length: Int) {
        for (j in 0..length - 1) {
            map[x][y + j] = filler
        }
    }

    fun replaceInMap(map: Array<IntArray>, toReplace: Int, filler: Int) {
        for (i in 0..map.size - 1) {
            for (j in 0..map[0].size - 1) {
                if (map[i][j] == toReplace) {
                    map[i][j] = filler
                }
            }
        }
    }

    fun hasConnect(map: Array<IntArray>, x: Int, y: Int, length: Int): Int {
        var retVal = -1
        for (j in 0..length - 1) {
            if (map[x][y + j] > -1) {
                retVal = if (retVal > -1) retVal else map[x][y + j]
                if (map[x][y + j] != retVal) {
                    replaceInMap(map, map[x][y + j], retVal)
                }
            }
        }
        return retVal
    }

    fun evaluateBasins(basinMap: Array<IntArray>): Long {
        val basinSizes = mutableMapOf<Int, Int>()
        for (i in 0..basinMap.size - 1) {
            for (j in 0..basinMap[0].size - 1) {
                val basinIndex = basinMap[i][j]
                if (basinIndex > -1) {
                    var size = basinSizes[basinIndex]
                    if (size != null) {
                        basinSizes[basinIndex] = ++size
                    } else {
                        basinSizes[basinIndex] = 1
                    }
                }
            }
        }
        val basinSizeList = basinSizes.values.toMutableList()
        basinSizeList.sort()
        val ordered = basinSizeList.asReversed()
        return ordered[0].toLong() * ordered[1].toLong() * ordered[2]
    }

    // calculate size of basin
    fun part2(input: List<String>): Long {
        var len = input[0].length
        var start = 0
        var found: Int
        var basinMap = Array(input.size) { IntArray(len) { -1 } }
        var runningIndex = 0
        while (start < len) {
            found = input[0].indexOf('9', start)
            if (found == -1) {
                found = len
            }
            if (found > start) {
                fillMap(basinMap, runningIndex, 0, start, found - start)
                start = found + 1
                runningIndex++
            } else {
                start++
            }
        }
        for (i in 1..input.size - 1) {
            var start = 0
            var found: Int
            while (start < len) {
                found = input[i].indexOf('9', start)
                if (found == -1) {
                    found = len
                }
                if (found > start) {
                    var basinValue = hasConnect(basinMap, i - 1, start, found - start)
                    if (basinValue > -1) {
                        fillMap(basinMap, basinValue, i, start, found - start)
                    } else {
                        fillMap(basinMap, runningIndex, i, start, found - start)
                        runningIndex++
                    }
                    start = found + 1
                } else {
                    start++
                }
            }
        }
        return evaluateBasins(basinMap)
    }

    val testInput = readInput("test_resources/Day09_test")

    check(part1(testInput) == 15)
    check(part2(testInput) == 1134L)

    var input = readInput("resources/Day09_low_points")
    println(part1(input))
    println(part2(input))
}
