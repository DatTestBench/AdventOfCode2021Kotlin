class Board(rawLines: List<List<String>>) {

	private val internalBoard = List(rawLines.size) {
		rawLines[it]
			.map { field -> Pair(field.toInt(), false) }
			.toMutableList()
	}

	val boardSize = Vec2i(internalBoard.first().size, internalBoard.size)
	var isCompleted = false
	private var lastInput = 0

	fun applyInput(input: Int) {

		if (isCompleted) return

		val flatIndex = internalBoard
			.flatten()
			.map { it.first }
			.indexOf(input)

		if (flatIndex != -1) {
			internalBoard[flatIndex / boardSize.x][flatIndex % boardSize.x] = Pair(input, true)
			lastInput = input
		}

		// Completion Test
		// Rows
		for (row in internalBoard) {
			if (row.filter { it.second }.size == boardSize.x) {
				isCompleted = true
				return
			}
		}

		// Column
		for (col in 0 until boardSize.y) {
			if (internalBoard
					.map { it[col] }
					.filter { it.second }.size == boardSize.y
			) {
				isCompleted = true
				return
			}
		}
	}

	fun calculateScore(): Int {
		return internalBoard
			.flatten()
			.sumOf { if (!it.second) it.first else 0 } * lastInput
	}
}

fun day04() {

	fun part1(gameNumbers: List<Int>, boards: List<Board>): Int {
		for (number in gameNumbers) {
			for (board in boards) {
				board.applyInput(number)
				if (board.isCompleted) {
					return board.calculateScore()
				}
			}
		}

		return 0
	}

	fun part2(gameNumbers: List<Int>, boards: List<Board>): Int {

		val sortableBoards = boards.toMutableList()
		for (number in gameNumbers) {
			for (board in sortableBoards) {
				board.applyInput(number)
			}
			if (sortableBoards.none { !it.isCompleted }) break
			sortableBoards.sortBy { it.isCompleted }
		}

		return sortableBoards
			.find { it.isCompleted }
			?.calculateScore() ?: 0
	}

	val day = dayHeader(object {}, "Giant Squid")

	val test = """
		7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1

		22 13 17 11  0
		 8  2 23  4 24
		21  9 14 16  7
		 6 10  3 18  5
		 1 12 20 15 19
		
		 3 15  0  2 22
		 9 18 13 17  5
		19  8  7 25 23
		20 11 10 24  4
		14 21 16 12  6
		
		14 21 17 24  4
		10 16 15  9 19
		18  8 23 26 20
		22 11 13  6  5
		 2  0 12  3  7
		"""
		.trimIndent()
		.split("\n")


	val input = readInputOnline(2021, day, false)

	fun extractBoards(rawBoards: List<List<String>>): List<Board> {

		val frontEmptyLines = rawBoards.takeWhile { it.isEmpty() }.size
		val backEmptyLines = rawBoards.takeLastWhile { it.isEmpty() }.size

		val sanitizedBoards = rawBoards.slice(frontEmptyLines until rawBoards.size - backEmptyLines)

		if (sanitizedBoards.isEmpty()) return listOf()

		val newBoard = Board(sanitizedBoards.takeWhile { it.isNotEmpty() })

		val recursiveFollowingBoards = extractBoards(sanitizedBoards.slice(newBoard.boardSize.y until sanitizedBoards.size))

		return if (recursiveFollowingBoards.isEmpty()) listOf(newBoard) else listOf(newBoard) + recursiveFollowingBoards
	}

	fun extractGameNumbers(input: List<String>): List<Int> {
		return input
			.first()
			.split(",")
			.map { it.toInt() }
	}

	fun extractBoardLines(input: List<String>): List<List<String>> {
		return input
			.slice(1 until input.size)
			.map { it.split(" ") }
			.map { it.filter { boardField -> boardField.isNotBlank() } }
	}

	val testNumbers = extractGameNumbers(test)
	val testBoards = extractBoards(extractBoardLines(test))

	val inputNumbers = extractGameNumbers(input)
	val inputBoards = extractBoards(extractBoardLines(input))

	outputTestResult(1, part1(testNumbers, testBoards), 4512)
	outputPuzzleResult(1, part1(inputNumbers, inputBoards))

	outputTestResult(2, part2(testNumbers, testBoards), 1924)
	outputPuzzleResult(2, part2(inputNumbers, inputBoards))
}