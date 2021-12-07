import khttp.get
import java.io.File
import java.math.BigInteger

fun readInputOnline(year: Int, day: Int, sanitize: Boolean = true): List<String> {

	val folderDir = File("inputs/")
	folderDir.mkdir()

	val file = File(folderDir, "${year}_${day}.txt")

	// Super basic caching setup
	val result = if (file.exists()) {
		file.readLines()
	} else {
		val token = System.getenv("session_token")
		val header = mapOf("Cookie" to "session=$token")
		val url = "https://adventofcode.com/$year/day/$day/input"

		val fileText = get(url, header).text
		file.writeText(fileText)

		fileText.lines()
	}

	return if (sanitize) result.filter { it.isNotBlank() } else result
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

	operator fun times(factor: Int) = Vec2i(x * factor, y * factor)
	operator fun times(vec: Vec2i) = Vec2i(x * vec.x, y * vec.y)
}

fun dayHeader(function: Any, puzzleName: String): Int {
	val day = function.javaClass.enclosingMethod.name
		.substringAfter('y')
		.toInt()
	println(" -- Day $day: $puzzleName -- ")
	return day
}

fun List<String>.digitToInt(): List<List<Int>> = this.map { it.map { digit -> digit.digitToInt() } }
fun List<String>.toInt(): List<Int> = this.map { it.toInt() }

fun Long.toBigInt(): BigInteger = BigInteger.valueOf(this)
fun Int.toBigInt(): BigInteger = BigInteger.valueOf(toLong())