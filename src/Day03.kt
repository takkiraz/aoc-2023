sealed class Element

data class Number(val value: Long, val row: Long, val colRange: LongRange) : Element() {
    val expandedRowRange: LongRange = row - 1L..row + 1L
    val expandedColRange: LongRange = colRange.first - 1..colRange.last + 1
}
data class Symbol(val char: Char, val row: Long, val col: Long) : Element()

fun List<String>.parseElements() = this.flatMapIndexed { row: Int, line: String ->
    Regex("\\d+|[^.\\s]").findAll(line).map {
        if (it.value.matches("\\d+".toRegex())) {
            Number(it.value.toLong(), row.toLong(), it.range.toLongRange())
        } else{
            Symbol(it.value.single(), row.toLong(), it.range.first.toLong())
        }
    }
}.partition { it is Number }.let { Pair(it.first as List<Number>, it.second as List<Symbol>) }

fun main() {
    fun part1(input: List<String>): Long {
        val (numbers: List<Number>, symbols: List<Symbol>) = input.parseElements()

        return numbers.filter { symbols.any { symbol ->
            symbol.col in it.expandedColRange &&
                    symbol.row in it.expandedRowRange
        } }.sumOf { it.value }
    }

    fun part2(input: List<String>): Long {
        val (numbers: List<Number>, symbols: List<Symbol>) = input.parseElements()
        return symbols.associateWith { numbers.filter { number -> it.row in number.expandedRowRange && it.col in number.expandedColRange } }
            .filter { it.value.size == 2 && it.key.char == '*' }
            .mapValues { it.value.first().value * it.value.last().value }
            .values.sum()
    }

    readInput("Day03_test").let {
        check(part1(it) == 4361L)
        check(part2(it) == 467835L)
    }

    readInput("Day03").let {
        part1(it).println()
        part2(it).println()
    }
}
