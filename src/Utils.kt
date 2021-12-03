import khttp.get
import java.io.File

fun readInputOnline(year: Int, day: Int): List<String> {
	val fileName = "${year}_${day}.txt"
	val file = File(fileName)
	return if (file.exists()) {
		file
			.readLines()
			.filter { it.isNotBlank() }
	} else {
		val token = System.getenv("session_token")
		val header = mapOf("Cookie" to "session=$token")
		val url = "https://adventofcode.com/$year/day/$day/input"

		val fileText = get(url, header).text
		file.writeText(fileText)

		fileText
			.lines()
			.filter { it.isNotBlank() }
	}
}

fun <T> outputTestResult(testIndex: Int, testResult: T, expectedResult: T) =
	println("[Test $testIndex: $testResult, $expectedResult, test ${if (testResult == expectedResult) "succeeded" else "failed"}]")

fun <T> outputPuzzleResult(partIndex: Int, partResult: T) = println("[Part $partIndex: $partResult]")

data class Vec2i(var x: Int = 0, var y: Int = 0) {
	constructor(x: Number, y: Number) : this(x.toInt(), y.toInt())
	constructor() : this(0, 0)

	/**
	 * Not entirely sure that this is the proper way to do operator overloads considering [x] and [y] are const
	 */
	operator fun plus(vec: Vec2i) = Vec2i(x + vec.x, y + vec.y)
	operator fun minus(vec: Vec2i) = Vec2i(x - vec.x, y - vec.y)
}

fun dayHeader(function: Any, puzzleName: String): Int {
	val day = function.javaClass.enclosingMethod.name
		.substringAfter('y')
		.toInt()
	println(" -- Day $day: $puzzleName -- ")
	return day
}