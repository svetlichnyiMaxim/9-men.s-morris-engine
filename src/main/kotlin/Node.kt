package main.kotlin

class Node(var value: PositionWithPlaceAble) {
    var parent: Node? = null

    var children: MutableList<Node> = mutableListOf()

    fun add(node: Node) {
        children.add(node)
        node.parent = this
    }

    // it might be used for displaying a winning move sequence
    fun print2() {
        fun print1(node: Node, index: Int) {
            println("printedContent $index")
            node.value.display()
            if (node.children.isNotEmpty()) {
                node.children.forEach {
                    print1(it, index + 1)
                }
            }
        }

        print1(this, 0)
    }

    override fun toString(): String {
        var s = "${value}"
        if (!children.isEmpty()) {
            s += " {" + children.map { it.toString() } + " }"
        }
        return s
    }
}