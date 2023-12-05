import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.math.pow
import kotlin.time.measureTime


fun  List<List<Pair<LongRange, Long>>>.createSeedPath(seed: Long): MutableList<Long> {
    return this.foldIndexed(mutableListOf<Long>()) { index, acc, pairs ->
        val lookupIndex = if (index == 0) seed else acc[index - 1]

        val value = (pairs.map { range ->
            range.first.indexOf(lookupIndex).takeIf { it > -1 }.let {
                if (it != null) {
                    range.second + it
                } else {
                    lookupIndex
                }
            }
        }.find { it != lookupIndex } ?: lookupIndex)
        acc.add(value)
        acc
    }
}
fun main() {
    fun part1(input: List<String>): Long {
        val (seedsText) = input
        val mapsText = input.drop(1)
        val seeds = seedsText.split(": ")[1].split(" ").map { it.toLong() }
        val maps = mapsText.map { s ->
            s.split("\\r\\n|\\n|\\r".toRegex()).drop(1).map {
               val (destStart, sourceStart, length) =  it.split(" ").map { it.toLong() }
                sourceStart..<sourceStart + length to destStart
            }
        }

        seeds.println()
        maps.println()
        val paths = seeds.associateWith {
            maps.createSeedPath(it)
        }

        paths.println()
        return paths.values.minOf { it.last() }
    }

    fun part2(input: List<String>): Long {
        return 0
    }

    readInputSplitBy("Day05_test", "\\r\\n\\r\\n|\\n\\n|\\r\\r".toRegex()).let {
        measureTime {
            check(part1(it) == 35L)
        }.println()
        measureTime {
//            check(part2(it) == 30L)
        }.println()
    }



    readInputSplitBy("Day05", "\\r\\n\\r\\n|\\n\\n|\\r\\r".toRegex()).let {
//        measureTime { part1(it).println() }.println()
//        measureTime { part2(it).println() }.println()
    }
}
