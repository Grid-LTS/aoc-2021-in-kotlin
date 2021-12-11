fun main() {

    fun part1(input: List<String>, days: Int): Int {
        var fishes = input[0].split(",").asSequence().map { it.trim().toInt() }.toMutableList()
        for (i in 1..days) {
            for (l in 0..fishes.size - 1) {
                if (fishes.get(l) == 0) {
                    fishes[l] = 6
                    fishes.add(8)
                    continue
                }
                fishes[l] = fishes.get(l) - 1
            }
        }
        return fishes.size
    }

    fun part2(input: List<String>, days: Int): Long {
        // different approach since numbers get very big
        val dist = LongArray(9) { 0 }
        val fishes = input[0].split(",").asSequence().map { it.trim().toInt() }.toMutableList()
        // populate
        for (i in 0..fishes.size - 1) {
            dist[fishes.get(i)]++
        }
        for (i in 1..days) {
            val newFishNo = dist[8]
            val spawnedFish = dist[6]
            dist[8] = dist[0]
            dist[6] = dist[0] + dist[7]
            dist[7] = newFishNo
            for (j in 0..4) {
                dist[j] = dist[j + 1]
            }
            dist[5] = spawnedFish
        }
        var sum: Long = 0
        for (i in 0..dist.size - 1) {
            sum += dist[i]
        }
        return sum
    }

    // test sample
    val testInput = readInput("test_resources/Day06_test")

    check(part2(testInput, 18) == 26L)
    check(part2(testInput, 80) == 5934L)
    check(part2(testInput, 256) == 26984457539L)

    var input = readInput("resources/Day06_lantern_fish")
    println(part1(input, 80))
    println(part2(input, 256))
}
