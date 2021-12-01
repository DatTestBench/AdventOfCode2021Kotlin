import khttp.get
import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

fun readInputOnline(year: Int, day: Int): List<String> {
    val token = System.getenv("session_token")
    val header = mapOf("Cookie" to "session=$token")
    val url = "https://adventofcode.com/$year/day/$day/input"
    return get(url, headers = header).text.lines()
}

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

fun outputTestResult(testIndex: Int, testResult: Int) = println("Test $testIndex: $testResult")
fun outputPuzzleResult(partIndex: Int, partResult: Int) = println("Part $partIndex: $partResult")