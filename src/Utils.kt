import khttp.get

fun readInputOnline(year: Int, day: Int): List<String> {
	val token = System.getenv("session_token")
	val header = mapOf("Cookie" to "session=$token")
	val url = "https://adventofcode.com/$year/day/$day/input"
	return get(url, headers = header).text
		.lines()
		.filter { it.isNotBlank() }
}

fun <T> outputTestResult(testIndex: Int, testResult: T) = println("[Test $testIndex: $testResult]")
fun <T> outputPuzzleResult(partIndex: Int, partResult: T) = println("[Part $partIndex: $partResult]")

data class Vec2i(var x: Int = 0, var y: Int = 0) {
	constructor(x: Number, y: Number) : this(x.toInt(), y.toInt())
	constructor() : this(0, 0)

	operator fun plus(vec: Vec2i) = Vec2i(x + vec.x, y + vec.y)
	operator fun minus(vec: Vec2i) = Vec2i(x - vec.x, y - vec.y)
}

fun dayHeader(function: Any): Int {
	val day = function.javaClass.enclosingMethod.name
		.substringAfter('y')
		.toInt()
	println(" -- Day $day -- ")
	return day
}