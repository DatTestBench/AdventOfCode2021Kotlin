import kotlin.math.abs
import kotlin.math.max
import kotlin.math.sign

typealias Line = Pair<Vec2i, Vec2i>
typealias LineList = List<Line>

fun LineList.bounds(): Vec2i {
	return Vec2i(this.maxOf { max(it.first.x, it.second.x) }, this.maxOf { max(it.first.y, it.second.y) })
}

data class AbstractPoint(val position: Vec2i = Vec2i(), var traversalCount: Int = 1)

class AbstractBoard(private val bounds: Vec2i) {

	// Significant speed increase over using a list
	private val boardPoints = mutableMapOf<Int, AbstractPoint>()

	private fun traversePoint(point: Vec2i) {
		val index = point.y * bounds.x + point.x
		if (boardPoints.contains(index)) {
			boardPoints[index]!!.traversalCount += 1
		} else {
			boardPoints[index] = AbstractPoint(point)
		}
	}

	fun traverseWithLine(line: Line, considerDiagonal: Boolean) {
		val (start, end) = line

		val x = (end.x - start.x).sign
		val y = (end.y - start.y).sign

		val movementDirection = if (considerDiagonal) {
			Vec2i((end.x - start.x).sign, (end.y - start.y).sign)
		} else if (abs(x) + abs(y) <= 1) {
			Vec2i((end.x - start.x).sign, (end.y - start.y).sign)
		} else {
			return
		}

		var intermediatePoint = start

		while (intermediatePoint != end) {
			traversePoint(intermediatePoint)
			intermediatePoint += movementDirection
		}
		traversePoint(end)
	}

	fun getDangerousPointCount(dangerThreshold: Int): Int {
		return boardPoints.values.sumOf {
			@Suppress("USELESS_CAST") // Overload resolution fails without explicit mention of return types
			(if (it.traversalCount >= dangerThreshold) 1 else 0) as Int
		}
	}

}

fun day05() {

	fun stripToLineList(input: List<String>): LineList {
		return input.map { line ->
			line
				.split(" -> ")
				.map { point ->
					val (x, y) = point
						.split(",")
						.zipWithNext()
						.first()
					Vec2i(x.toInt(), y.toInt())
				}
				.zipWithNext()
				.first()
		}
	}

	fun part1(input: LineList): Int {

		val board = AbstractBoard(input.bounds())

		for (line in input) {
			board.traverseWithLine(line, false)
		}
		return board.getDangerousPointCount(2)
	}

	fun part2(input: LineList): Int {

		val board = AbstractBoard(input.bounds())

		for (line in input) {
			board.traverseWithLine(line, true)
		}
		return board.getDangerousPointCount(2)
	}

	val day = dayHeader(object {}, "Hydrothermal Venture")

	val test = """
		0,9 -> 5,9
		8,0 -> 0,8
		9,4 -> 3,4
		2,2 -> 2,1
		7,0 -> 7,4
		6,4 -> 2,0
		0,9 -> 2,9
		3,4 -> 1,4
		0,0 -> 8,8
		5,5 -> 8,2
		"""
		.trimIndent()
		.split("\n")

	val input = readInputOnline(2021, day)

	val testLines = stripToLineList(test)
	val inputLines = stripToLineList(input)


	outputTestResult(1, part1(testLines), 5)
	outputPuzzleResult(1, part1(inputLines))

	outputTestResult(2, part2(testLines), 12)
	outputPuzzleResult(2, part2(inputLines))
}