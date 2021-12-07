import java.math.BigInteger

fun day06() {

	fun processFish(startingFish: List<Int>, days: Int): BigInteger {

		val workingList = MutableList(9) { 0.toBigInt() }

		for (fish in startingFish) {
			workingList[fish]++
		}

		for (day in 0 until days) {
			val fishToSpawnOrReset = workingList[0]
			workingList[0] = 0.toBigInt()
			for (fishTimer in 1 .. 8) {
				workingList[fishTimer - 1] = workingList[fishTimer]
				workingList[fishTimer] = 0.toBigInt()
			}
			workingList[8] += fishToSpawnOrReset
			workingList[6] += fishToSpawnOrReset
		}

		return workingList.sumOf { it }
	}
	fun part1(input: List<Int>): BigInteger {
		return processFish(input, 80)
	}

	fun part2(input: List<Int>): BigInteger {
		return processFish(input, 256)
	}

	val day = dayHeader(object {}, "Lanternfish ")

	val test = """3,4,3,1,2"""
		.trimIndent()
		.split(",")
		.toInt()

	val input = readInputOnline(2021, day)
		.first()
		.split(",")
		.toInt()

	outputTestResult(1, part1(test), 5934.toBigInt())
	outputPuzzleResult(1, part1(input))

	outputTestResult(2, part2(test), 26984457539.toBigInt())
	outputPuzzleResult(2, part2(input))
}