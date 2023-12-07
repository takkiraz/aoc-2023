val cardStrengths = listOf('2','3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A')
val cardStrengths2 = listOf('J','2', '3', '4', '5', '6', '7', '8', '9', 'T', 'Q', 'K', 'A')

data class Hand(val cards: String, val bid: Long, val useJoker: Boolean = false) {
    private val cardsGrouped = cards.groupBy { it }.mapValues { it.value.size }.let {
        if (!useJoker) it
        else it.toMutableMap().apply {
            val jokerCounts = getOrDefault('J', 0)
            if (jokerCounts in 1..4) {
                remove('J')
                maxBy { it.value }.let {
                    put(it.key, it.value + jokerCounts)
                }
            }
        }.toMap()
    }

    private val cardsStrengths = cards.map { if (useJoker) cardStrengths2.indexOf(it) else cardStrengths.indexOf(it) }

    private fun isHighCard() = (cardsGrouped.all { it.value == 1 })
    private fun isOnePair() = (cardsGrouped.count { it.value == 2 } == 1)
    private fun isTwoPair() = (cardsGrouped.count { it.value == 2 } == 2)
    private fun isThreeOfAKind() = (cardsGrouped.any { it.value == 3 } && !isFullHouse())
    private fun isFullHouse() = (cardsGrouped.any { it.value == 3 } && cardsGrouped.any { it.value == 2 })
    private fun isFourOfAKind() = cardsGrouped.any { it.value == 4 }
    private fun isFiveOfAKind() = cardsGrouped.any { it.value == 5 }

    private val strength = when {
        isFiveOfAKind() -> 60
        isFourOfAKind() -> 50
        isFullHouse() -> 40
        isThreeOfAKind() -> 30
        isTwoPair() -> 20
        isOnePair() -> 10
        isHighCard() -> 1
        else -> 0
    }

    companion object {
        fun compare(a: Hand, b: Hand): Int {
            return a.compareHands(b)
        }
    }

    fun compareHands(other: Hand): Int {
        return when {
            strength > other.strength -> 1
            strength < other.strength -> -1
            else -> {
                cardsStrengths.withIndex().forEach { (index, strength) ->
                    val otherStrength = other.cardsStrengths[index]
                    if (strength > otherStrength) return 1
                    if (strength < otherStrength) return -1
                }
                0
            }
        }
    }
}

fun main() {
    fun List<String>.parseInput(useJoker: Boolean = false): List<Hand> {
        return map { it -> it.split(" ").let { Hand(it[0], it[1].toLong(), useJoker) } }
    }

    fun part1(input: List<String>): Long {
        val hands = input.parseInput()
        val handsSorted = hands.sortedWith(Hand::compare)

        return handsSorted.withIndex().sumOf { (index, hand) -> hand.bid * (index + 1) }
    }

    fun part2(input: List<String>): Long {
        val hands = input.parseInput(useJoker = true)
        val handsSorted = hands.sortedWith(Hand::compare)

        return handsSorted.withIndex().sumOf { (index, hand) -> hand.bid * (index + 1) }
    }
    readInput("Day07_test").let {
        check(part1(it) == 6440L)
        check(part2(it) == 5905L)
    }

    readInput("Day07").let {
        part1(it).println()
        part2(it).println()
    }
}
