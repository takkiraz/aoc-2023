
data class Node(val name: String, var children: List<Node>) {
    override fun toString(): String {
        return "Node(name='$name', children=${children.map { it.name }})"
    }
}

tailrec fun Node.countStepsToNode(name: String, instructions: List<Int>, steps: Long = 0): Long {
    val direction = instructions[steps.toInt() % instructions.size]
    if (name == this.name) {
        return steps
    }
    return children[direction].countStepsToNode(name, instructions, steps + 1)
}

tailrec fun Node.countStepsToNodeNameEndsWith(suffix: String, instructions: List<Int>, steps: Long = 0): Long {
    val direction = instructions[steps.toInt() % instructions.size]
    if (this.name.endsWith(suffix)) {
        return steps
    }
    return children[direction].countStepsToNodeNameEndsWith(suffix, instructions, steps + 1)
}

fun String.toInstructions(): List<Int> {
    return this.replace("L", "0").replace("R", "1").map { it.digitToInt() }
}

fun String.toNetworkMap(): Map<String, Node> {
    return this.split("\n").fold(mutableMapOf()) { networkMap, it ->
        val (name, childrenText) = it.split(" = ")
        val node = networkMap.getOrPut(name) { Node(name, emptyList()) }
        val children = childrenText.split(", ").map { childNameRaw ->
            val childName = childNameRaw.replace("(", "").replace(")", "")
            networkMap.getOrPut(childName) { Node(childName, emptyList()) }
        }
        node.children = children
        networkMap[name] = node
        networkMap
    }
}

fun main() {
    fun part1(input: List<String>): Long {
        // L = 0, R = 1
        val instructions = input[0].toInstructions()
        val networkMap = input[1].toNetworkMap()

        return networkMap["AAA"]!!.countStepsToNode("ZZZ", instructions)
    }

    fun part2(input: List<String>): Long {
        val instructions = input[0].toInstructions()
        val networkMap = input[1].toNetworkMap()

        networkMap.println()

        val startingNodes = networkMap.filter { it.value.name.endsWith("A") }.map { it.value }
        startingNodes.println()
        val stepsForEachNode = startingNodes.map { it.countStepsToNodeNameEndsWith("Z", instructions) }
        stepsForEachNode.println()

        // Brutoforce doesn't work
//        while (true) {
//            if (stepsForEachNode.all { it == stepsForEachNode[0] }) {
//                return stepsForEachNode[0]
//            }
//            stepsForEachNode = stepsForEachNode.map { it + it }
//        }

        return lcm(*stepsForEachNode.toLongArray())
    }

    readInputSplitByDoubleNewLine("Day08_test").let {
        println("Checking part 1...")
        check(part1(it) == 6L).also { println("OK") }
    }

    readInputSplitByDoubleNewLine("Day08_testp2").let {
        println("Checking part 2...")
        check(part2(it) == 6L).also { println("OK") }
    }

    readInputSplitByDoubleNewLine("Day08").let {
        println(part1(it))
        println(part2(it))
    }
}
