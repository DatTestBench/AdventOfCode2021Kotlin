// Template
fun day00() {
	fun part1(input: List<String>): Int {
		return input.size
	}

	fun part2(input: List<String>): Int {
		return input.size
	}

	val day = dayHeader(object {})

	val test = """"""
		.trimIndent()
		.split("")

	val input = readInputOnline(2021, day)

	outputTestResult(1, part1(test))
	outputPuzzleResult(1, part1(input))

	outputTestResult(2, part2(test))
	outputPuzzleResult(2, part2(input))
}