fun main() {

    fun part1(depthValues: List<Int>): Int {
        var depthIncreaseCount = 0

        for (depthIndex in 1..depthValues.count()) {

            if (depthValues[depthIndex] > depthValues[depthIndex - 1])
                ++depthIncreaseCount
        }

        return depthIncreaseCount
    }

    fun part2(input: List<Int>): Int {
        val sumWidth = 3
        var depthIncreaseCount = 0

        for (i in 0 until input.count() - sumWidth) {
            val depthSum = input.slice(i until i + sumWidth).sum()
            val nextDepthSum = input.slice( i + 1..i + sumWidth).sum()

            if (depthSum < nextDepthSum)
                ++depthIncreaseCount
        }

        return depthIncreaseCount
    }

    val test =
        """
        199
        200
        208
        210
        200
        207
        240
        269
        260
        263
        """
        .trimIndent()
        .split("\n")
        .mapNotNull { it.toIntOrNull() }

    val input = readInputOnline(2021, 1)
    val depthValues = input.mapNotNull { it.toIntOrNull() }

    outputTestResult(1, part1(test))
    outputPuzzleResult(1, part1(depthValues))

    outputTestResult(2, part2(test))
    outputPuzzleResult(2, part2(depthValues))
}
