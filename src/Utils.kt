import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readLines()

fun readInputSplitBy(name: String, split: Regex) = Path("src/$name.txt").readText().split(split)

fun readInputSplitByDoubleNewLine(name: String) = readInputSplitBy(name, "\\r\\n\\r\\n|\\n\\n|\\r\\r".toRegex())

tailrec fun gcd(a: Long, b: Long): Long {
    return if (b == 0L) a else gcd(b, a % b)
}

fun lcm(a: Long, b: Long): Long {
    return a * b / gcd(a, b)
}

fun lcm(vararg numbers: Long): Long {
    return numbers.fold(1L) { acc, i -> lcm(acc, i) }
}


/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun IntRange.toLongRange(): LongRange =
    this.first().toLong() .. this.last.toLong()
