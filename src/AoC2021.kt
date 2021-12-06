import kotlin.system.measureNanoTime

fun main() {

	fun profileTime (block: () -> Unit) {
		val time = measureNanoTime(block)
		println("${time}ns, ${time / 1_000_000.0}ms, ${time / 1_000_000_000.0}s")
	}
	// Get errors out of the way
	readInputOnline(2021, 0)
	profileTime { day01() }
	profileTime { day02() }
	profileTime { day03() }
	profileTime { day04() }
	profileTime { day05() }
	profileTime { day06() }
}