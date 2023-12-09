fun main() {
    fun extrapolededValue(numbers: List<Long>): Long {
        if (numbers.all { it == 0L })
            return 0L

        val differences = numbers.zipWithNext { a, b -> b - a }
        return numbers.last() + extrapolededValue(differences)
    }

    fun extrapolededValueBackwards(numbers: List<Long>): Long {
        if (numbers.all { it == 0L })
            return 0L

        val differences = numbers.zipWithNext { a, b -> b - a }
        return numbers.first() - extrapolededValueBackwards(differences)
    }

    fun part1(input: List<String>): Long {
        val numbers = input.map { it.split(" ").map { it.toLong() } }

        val sum = numbers.sumOf {
            extrapolededValue(it)
        }
        return sum
    }

    fun part2(input: List<String>): Long {
        val numbers = input.map { it.split(" ").map { it.toLong() } }

        val sum = numbers.sumOf {
            extrapolededValueBackwards(it)
        }
        return sum
    }

    readInput("Day09_test").let {
        println("Checking part 1...")
        check(part1(it) == 114L).also { println("OK") }
        println("Checking part 2...")
        check(part2(it) == 2L).also { println("OK") }
    }

    readInput("Day09").let {
        println(part1(it))
        println(part2(it))
    }
}
