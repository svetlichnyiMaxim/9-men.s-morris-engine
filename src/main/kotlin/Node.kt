class Node(var value: PositionWithPlaceAble) {
    private var parent: Node? = null

    private var children: MutableList<Node> = mutableListOf()

    fun add(node: Node) {
        children.add(node)
        node.parent = this
    }

    override fun toString(): String {
        var s = "$value"
        if (children.isNotEmpty()) {
            s += " {" + children.map { it.toString() } + " }"
        }
        return s
    }
}