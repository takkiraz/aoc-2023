val bag = mapOf(
    "red" to 12,
    "green" to 13,
    "blue" to 14
)

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { game ->
            val (gameIdRaw, revealedCubesRaw) = game.split(": ")
            val gameId = gameIdRaw.split(" ").last().toInt()
            val picksRaw = revealedCubesRaw.split("; ")
            val possible = picksRaw.all {
                val picks = it.split(", ").map { val (size, color) = it.split(" "); color to size.toInt() }
                picks.all { (color, size) -> bag[color] != null && bag[color]!! >= size }
            }
            if (possible) {
                return@sumOf gameId
            } else {
                return@sumOf 0
            }
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { game ->
            val (_, revealedCubesRaw) = game.split(": ")
            val picksRaw = revealedCubesRaw.split("; ")
            val picks = picksRaw.flatMap {
                it.split(", ").map { val (size, color) = it.split(" "); color to size.toInt() }
            }
            val fewestNumbersOfCubes = mutableMapOf(
                "red" to 0,
                "green" to 0,
                "blue" to 0
            )
            picks.forEach { (color, size) ->
                if (size > fewestNumbersOfCubes[color]!!)
                    fewestNumbersOfCubes[color] = size
            }
            fewestNumbersOfCubes.values.reduce { acc, i -> acc * i }
        }
    }

    readInput("Day02_test").let {
        check(part1(it) == 8)
        check(part2(it) == 2286)
    }

    readInput("Day02").let {
        part1(it).println()
        part2(it).println()
    }
}
