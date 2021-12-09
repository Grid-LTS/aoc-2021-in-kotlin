fun main() {

    fun readNumbers(input: List<String>): List<Int> {
        return input[0].split(',').map { it.toInt() }
    }

    fun readMatrices(input: List<String>): List<Array<IntArray>> {
        val value = input.subList(0, input.size)
        val matrices = arrayListOf<Array<IntArray>>()
        var i = 1
        while (i < value.size && value[i] == "") {
            i++
            val matrix = Array(5) { IntArray(5) { 0 } }
            for (j in 0..4) {
                matrix[j] = value[i].split(" ").map { it.trim() }.filter { !it.isEmpty() }
                        .map { it.toInt() }.toIntArray()
                i++
            }
            matrices.add(matrix)
        }
        return matrices
    }

    fun findAndReplaceNumberInMatrix(matrix: Array<IntArray>, number: Int): Pair<Int, Int>? {
        for (i in 0..matrix.size - 1) {
            for (j in 0..matrix[i].size - 1) {
                if (matrix[i][j] == number) {
                    matrix[i][j] = -1
                    return Pair(i, j)
                }
            }
        }
        return null
    }

    fun sumMatrix(matrix: Array<IntArray>): Int {
        var sum = 0
        for (i in 0..matrix.size - 1) {
            for (j in 0..matrix[i].size - 1) {
                if (matrix[i][j] >= 0) {
                    sum += matrix[i][j]
                }
            }
        }
        return sum
    }

    fun part1(input: List<String>): Int {
        val numbers = readNumbers(input)
        val matrices = readMatrices(input)
        val lists = Array(matrices.size) { Array(2) { IntArray(5) { 0 } } }
        var matrixIndex = -1
        var number = -1
        for (it in 0..numbers.size - 1) {
            number = numbers[it]
            for (z in 0..matrices.size - 1) {
                val mat = matrices[z]
                val coord = findAndReplaceNumberInMatrix(mat, number)
                if (coord != null) {
                    lists[z][0][coord.first]++
                    if (lists[z][0][coord.first] == 5) {
                        matrixIndex = z
                        break
                    }
                    lists[z][1][coord.second]++
                    if (lists[z][1][coord.second] == 5) {
                        matrixIndex = z
                        break
                    }
                }
            }
            if (matrixIndex >= 0) {
                break
            }
        }
        return sumMatrix(matrices[matrixIndex]) * number
    }

    fun part2(input: List<String>): Int {
        val numbers = readNumbers(input)
        val matrices = readMatrices(input)
        val lists = Array(matrices.size) { Array(2) { IntArray(5) { 0 } } }
        var matrixIndex = -1
        var number = -1
        var boardsLeft = mutableListOf<Int>()
        (0..matrices.size - 1).asSequence().toCollection(boardsLeft)
        for (it in 0..numbers.size - 1) {
            number = numbers[it]
            val boardsLeftMod = boardsLeft.toMutableList()
            for (z in boardsLeft) {
                val mat = matrices[z]
                val coord = findAndReplaceNumberInMatrix(mat, number)
                if (coord != null) {
                    lists[z][0][coord.first]++
                    lists[z][1][coord.second]++
                    if (lists[z][0][coord.first] == 5 || lists[z][1][coord.second] == 5) {
                        if (boardsLeftMod.size == 1) {
                            matrixIndex = z
                            break
                        }
                        boardsLeftMod.remove(z)
                    }
                }
            }
            boardsLeft = boardsLeftMod.toMutableList()

            if (matrixIndex >= 0) {
                break
            }
        }
        return sumMatrix(matrices[matrixIndex]) * number
    }

    // test sample
    val testInput = readInput("test_resources/Day04_test")

    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    var input = readInput("resources/Day04_bingo")
    println(part1(input))
    println(part2(input))
}
