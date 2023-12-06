fun main() {
    fun part1(input: List<String>): Long {
        val digitsRegex = "\\d+".toRegex()
        val (times, record) = input.map { digitsRegex.findAll(it.split(":")[1]).map { it.value.toLong() }.toList() }
        times.println()
        record.println()

        val map = mutableMapOf<Long, Long>()
        times.forEachIndexed { index, time ->
            map[time] = record[index]
        }
        return map.entries.fold(1L) { acc, (time, record) ->
            var beatCount = 0

            for (i in 0..time) {
                val speed = i
                if (speed * (time - i) > record) {
                    beatCount++
                }
            }
            acc * beatCount
        }
    }

    fun part2(input: List<String>): Long {
        val (time, record) = input.map { it.split(":")[1].replace(" ", "").toLong() }

        var beatCount = 0L

        for (i in 0..time) {
            val speed = i
            if (speed * (time - i) > record) {
                beatCount++
            }
        }
        return beatCount
    }

    readInput("Day06_test").let {
        check(part1(it) == 288L)
        check(part2(it) == 71503L)
    }

    readInput("Day06").let {
        part1(it).println()
        part2(it).println()
    }
}
