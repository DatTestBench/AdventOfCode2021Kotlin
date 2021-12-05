fun List<String>.digitToInt(): List<List<Int>> {
	return this.map {
		it.map { digit -> digit.digitToInt() }
	}
}

fun day03() {
	fun part1(input: List<List<Int>>): Int {

		val accumulationList = mutableListOf<Int>()

		for (diagnostic in input) {
			diagnostic.forEachIndexed { index, i ->
				if (accumulationList.size <= index) accumulationList.add(i)
				else accumulationList[index] += i
			}
		}

		val iterator = accumulationList.listIterator()
		while (iterator.hasNext()) {
			val currentDigit = iterator.next()
			iterator.set(currentDigit
				.compareTo(input.size / 2)
				.coerceIn(0..1))
		}

		val gamma = accumulationList
			.joinToString("")
			.toInt(2)

		val epsilon = accumulationList
			.map { it xor 1 }
			.joinToString("")
			.toInt(2)

		return gamma * epsilon
	}

	/**
	 * Both the oxygen generator rating and the CO2 scrubber rating are values that can be found in your diagnostic report - finding them is the tricky part.
	 * Both values are located using a similar process that involves filtering out values until only one remains. Before searching for either rating value, start with the full list of binary numbers from your diagnostic report and **consider just the first bit** of those numbers. Then:
	 * - Keep only numbers selected by the bit criteria for the type of rating value for which you are searching. Discard numbers which do not match the **bit criteria**.
	 * - If you only have one number left, stop; this is the rating value for which you are searching.
	 * - Otherwise, repeat the process, considering the next bit to the right.
	 *
	 * The **bit criteria** depends on which type of rating value you want to find:
	 *
	 * - To find **oxygen generator rating**, determine the most **common value** (0 or 1) in the current bit position, and keep only numbers with that bit in that position. If 0 and 1 are equally common, keep values with a 1 in the position being considered.
	 * - To find **CO2 scrubber rating**, determine the **least common** value (0 or 1) in the current bit position, and keep only numbers with that bit in that position. If 0 and 1 are equally common, keep values with a 0 in the position being considered.
	 *
	 * For example, to determine the **oxygen generator rating** value using the same example diagnostic report from above:
	 *
	 * - Start with all 12 numbers and consider only the first bit of each number. There are more 1 bits (7) than 0 bits (5), so keep only the 7 numbers with a 1 in the first position: 11110, 10110, 10111, 10101, 11100, 10000, and 11001.
	 * - Then, consider the second bit of the 7 remaining numbers: there are more 0 bits (4) than 1 bits (3), so keep only the 4 numbers with a 0 in the second position: 10110, 10111, 10101, and 10000.
	 * - In the third position, three of the four numbers have a 1, so keep those three: 10110, 10111, and 10101.
	 * - In the fourth position, two of the three numbers have a 1, so keep those two: 10110 and 10111.
	 * - In the fifth position, there are an equal number of 0 bits and 1 bits (one each). So, to find the **oxygen generator rating**, keep the number with a 1 in that position: 10111.
	 * - As there is only one number left, stop; the **oxygen generator rating** is 10111, or **23** in decimal.
	 *
	 * Then, to determine the **CO2 scrubber rating** value from the same example above:
	 *
	 * - Start again with all 12 numbers and consider only the first bit of each number. There are fewer 0 bits (5) than 1 bits (7), so keep only the 5 numbers with a 0 in the first position: 00100, 01111, 00111, 00010, and 01010.
	 * - Then, consider the second bit of the 5 remaining numbers: there are fewer 1 bits (2) than 0 bits (3), so keep only the 2 numbers with a 1 in the second position: 01111 and 01010.
	 * - In the third position, there are an equal number of 0 bits and 1 bits (one each). So, to find the **CO2 scrubber rating**, keep the number with a 0 in that position: 01010.
	 * - As there is only one number left, stop; the **CO2 scrubber rating** is 01010, or 10 in decimal.
	 *
	 * Finally, to find the life support rating, multiply the oxygen generator rating (23) by the CO2 scrubber rating (10) to get **230**.
	 */
	fun part2(input: List<List<Int>>): Int {

		fun findOxygenRating(diagnostics: List<List<Int>>, filterIndex: Int = 0): Int {
			val minimumThreshold = if (diagnostics.size.mod(2) == 0) diagnostics.size / 2 else (diagnostics.size / 2) + 1

			// (if > minimumThreshold) 1 most common else 0 most common
			// 1 has precedence over 0, so >=
			val filterValue = if (diagnostics.sumOf { it[filterIndex] } >= minimumThreshold) 1 else 0

			val filteredResults = diagnostics.filter { it[filterIndex] == filterValue }
			return if (filteredResults.size > 1) {
				findOxygenRating(filteredResults, filterIndex + 1)
			} else {
				filteredResults
					.first()
					.joinToString("")
					.toInt(2)
			}
		}

		fun findCO2Rating(diagnostics: List<List<Int>>, filterIndex: Int = 0): Int {
			val minimumThreshold = if (diagnostics.size.mod(2) == 0) diagnostics.size / 2 else (diagnostics.size / 2) + 1

			// (if > minimumThreshold) 0 least common else 1 least common
			// 0 has precedence over 1, so >=
			val filterValue = if (diagnostics.sumOf { it[filterIndex] } >= minimumThreshold) 0 else 1

			val filteredResults = diagnostics.filter { it[filterIndex] == filterValue }
			return if (filteredResults.size > 1) {
				findCO2Rating(filteredResults, filterIndex + 1)
			} else {
				filteredResults
					.first()
					.joinToString("")
					.toInt(2)
			}
		}

		return findOxygenRating(input) * findCO2Rating(input)
	}

	val day = dayHeader(object {}, "Binary Diagnostic")

	val test = """
		00100
		11110
		10110
		10111
		10101
		01111
		00111
		11100
		10000
		11001
		00010
		01010
		"""
		.trimIndent()
		.split("\n")
		.digitToInt()

	val input = readInputOnline(2021, day)
	val numericInput = input.map { it.map { digit -> digit.digitToInt() } }

	outputTestResult(1, part1(test), 198)
	outputPuzzleResult(1, part1(numericInput))

	outputTestResult(2, part2(test), 230)
	outputPuzzleResult(2, part2(numericInput))
}