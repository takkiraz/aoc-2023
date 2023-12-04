import kotlin.math.pow

fun main() {
    fun part1(input: List<String>): Long {
        return input.sumOf {
            val (winning, numbers) = it.split(": ")[1].split(" | ")
                .map { it.chunked(3).map { it.replace(" ", "").toLong() } }
            winning.println()
            numbers.println()
            winning.sumOf { numbers.groupingBy { it }.eachCount().get(it) ?: 0 }.let {
                if (it > 1) (2.toDouble().pow(it - 1) * 1).toLong() else it.toLong()
            }
        }
    }

    fun part2(input: List<String>): Long {
        val copies = List(input.size) { index -> index to 1 }.toMap().toMutableMap()

        input.forEachIndexed { index, card ->
            val (winning, numbers) = card.split(": ")[1].split(" | ")
                .map { it.chunked(3).map { it.replace(" ", "").toLong() } }
            val matchesCount = winning.sumOf { numbers.groupingBy { it }.eachCount().get(it) ?: 0 }

            for (i in 1..matchesCount) {
                if (index + i < input.size)
                    copies.merge(index + i, copies[index]!!, Int::plus)
            }

            index to matchesCount.let {
                if (it > 1) (2.toDouble().pow(it - 1) * 1).toLong() else it.toLong()
            } * copies[index]!!
        }

        return copies.values.sum().toLong();
    }

    readInput("Day04_test").let {
        check(part1(it) == 13L)
        check(part2(it) == 30L)
    }

    readInput("Day04").let {
        part1(it).println()
        part2(it).println()
    }
}
