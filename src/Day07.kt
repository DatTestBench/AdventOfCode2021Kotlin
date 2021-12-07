import kotlin.math.abs

fun day07() {
	fun part1(input: List<Int>): Int {

		val max = input.maxOf { it }
		val min = input.minOf { it }

		var cheapestFuelCost = Int.MAX_VALUE
		for (pos in min .. max) {
			var fuelForPos = 0
			for (crab in input) {
				fuelForPos += abs(crab - pos)
			}

			if (fuelForPos < cheapestFuelCost) {
				cheapestFuelCost = fuelForPos
			}
		}
		return cheapestFuelCost
	}

	fun part2(input: List<Int>): Int {

		fun getFuelCost(distance: Int): Int {
			return distance * (distance + 1) / 2
		}

		val max = input.maxOf { it }
		val min = input.minOf { it }

		var cheapestFuelCost = Int.MAX_VALUE
		for (pos in min .. max) {
			var fuelForPos = 0
			for (crab in input) {
				fuelForPos += getFuelCost(abs(crab - pos))
			}

			if (fuelForPos < cheapestFuelCost) {
				cheapestFuelCost = fuelForPos
			}
		}
		return cheapestFuelCost
	}

	val day = dayHeader(object {}, "The Treachery of Whales")

	val test = """16,1,2,0,4,2,7,1,2,14"""
		.trimIndent()
		.split(",")
		.toInt()

	val input = readInputOnline(2021, day)
		.first()
		.split(",")
		.toInt()

	outputTestResult(1, part1(test), 37)
	outputPuzzleResult(1, part1(input))

	outputTestResult(2, part2(test), 168)
	outputPuzzleResult(2, part2(input))
}