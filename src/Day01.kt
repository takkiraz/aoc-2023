enum class NUMBERS {
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE;
}


fun toNumberChar(input: String): Char {
    if (input.first().isDigit()) return input.first()

    return (NUMBERS.valueOf(input.uppercase()).ordinal + 1).digitToChar()
}


fun main() {
    fun part1(input: List<String>): Long {
        var sum = 0L

        input.forEach {
            sum += (it.first { c -> c.isDigit() }.toString() +  it.last(Char::isDigit)).toLong()
        }

        return sum
    }

    fun part2(input: List<String>): Long {
        var sum = 0L
        val numbersRegex = "one|two|three|four|five|six|seven|eight|nine"
        val firstRegex = Regex("(\\d)|$numbersRegex")
        // IDK how to reverse this regex :D
        val lastRegex = Regex("(\\d)|" + numbersRegex.reversed())

        input.forEach {
            val first = toNumberChar(firstRegex.find(it)!!.value)
            val last = toNumberChar(lastRegex.find(it.reversed())!!.value.reversed())
            sum += "$first$last".toLong()
        }
        return sum
    }

    val testInputPart1 = readInput("Day01_test_1")
    val testInputPart2 = readInput("Day01_test_2")

    check(part1(testInputPart1) == 142L)
    check(part2(testInputPart2) == 281L)

    val input1 = readInput("Day01_1")
    val input2 = readInput("Day01_2")
    part1(input1).println()
    part2(input2).println()
}
