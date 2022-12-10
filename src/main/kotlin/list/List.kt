package list

import builder.UserTypeBuilder
import java.io.*

class List : IList {
    private var head: Node? = null
    private var tail: Node? = null
    private var length = 0
    override fun add(data: Any?) {
        if (head == null) {
            head = Node(data)
            tail = head
            length++
            return
        }
        val newTail: Node = Node(data)
        newTail.prev = tail
        tail!!.next = newTail
        tail = newTail
        length++
    }

    override fun toString(): String {
        var str = ""
        if(head == null)
            return str
        var tmp: Node = head!!
        for (i in 0 until length) {
            str += tmp.data
            str += "\n"
            if(tmp.next != null)
                tmp = tmp.next!!
        }
        return str
    }

    override operator fun get(index: Int): Any? {
        return getNode(index)!!.data
    }

    override fun remove(i: Int) {
        val x = getNode(i)
        if (x === head) {
            x!!.next!!.prev = null
            head = x.next
        } else if (x === tail) {
            x!!.prev!!.next = null
            tail = x.prev
        } else {
            x!!.prev!!.next = x.next
            x.next!!.prev = x.prev
        }
        length--
    }

    override fun size(): Int {
        return length
    }

    override fun add(data: Any, index: Int) {
        val tmp = getNode(index)
        val newNode: Node = Node(data)
        if (tmp !== head) {
            tmp!!.prev!!.next = newNode
            newNode.prev = tmp.prev
        } else {
            head = newNode
        }
        newNode.next = tmp
        tmp!!.prev = newNode
        length++
    }

    override fun forEach(action : Action) {
        var tmp: Node? = head
        for (i in 0 until length) {
            action.toDo(tmp?.data)
            tmp = tmp?.next
        }
    }

    override fun sort(comparator: comparator.Comparator) {
        head = mergeSort(head, comparator)
        var tmp = head
        while (tmp!!.next != null) tmp = tmp.next
        tail = tmp
    }

    override fun saveToFile(filename: String, list: IList, builder: UserTypeBuilder) {
        val writer = BufferedWriter(FileWriter(filename))
        writer.write(builder.typeName() + "\n")
        this.forEach(object : Action {
            override fun toDo(obj: Any?) {
                val x = obj
                writer.write(
                    """
                        ${builder.toString(x!!)}
                        
                        """.trimIndent()
                )
            }
        })
        if (writer != null) writer.close()
    }

    override fun loadFromFile(filename: String, list: IList, builder: UserTypeBuilder): IList {
        BufferedReader(FileReader(filename)).use { br ->
            var line: String?
            line = br.readLine()
            if (!builder.typeName().equals(line)) {
                throw Exception("Wrong file structure")
            }
            while (br.readLine().also { line = it } != null) {
                list.add(builder.createFromString(line)!!)
            }
            return list
        }
    }

    private fun mergeSort(h: Node?, comparator: comparator.Comparator): Node? {
        if (h?.next == null) {
            return h
        }
        val middle = getMiddle(h)
        val middleNext = middle!!.next
        middle.next = null
        val left = mergeSort(h, comparator)
        val right = mergeSort(middleNext, comparator)
        return merge(left, right, comparator)
    }

    private fun merge(head11: Node?, head22: Node?, comparator: comparator.Comparator): Node? {
        var left = head11
        var right = head22
        val merged: Node = Node(null)
        var temp: Node? = merged
        while (left != null && right != null) {
            if ((left.data?.let { right!!.data?.let { it1 -> comparator.compare(it, it1) } } as Double) < 0) {
                temp!!.next = left
                left.prev = temp
                left = left.next
            } else {
                temp!!.next = right
                right.prev = temp
                right = right.next
            }
            temp = temp.next
        }
        while (left != null) {
            temp!!.next = left
            left.prev = temp
            left = left.next
            temp = temp.next
        }
        while (right != null) {
            temp!!.next = right
            right.prev = temp
            right = right.next
            temp = temp.next
            tail = temp
        }
        return merged.next
    }

    private fun getMiddle(h: Node?): Node? {
        if (h == null) return null
        var fast = h.next
        var slow = h
        while (fast != null) {
            fast = fast.next
            if (fast != null) {
                slow = slow!!.next
                fast = fast.next
            }
        }
        return slow
    }

    private fun getNode(index: Int): Node? {
        if (index < 0 || index >= length) throw IndexOutOfBoundsException()
        var tmp = head
        for (i in 0 until index) {
            tmp = tmp!!.next
        }
        return tmp
    }

    private inner class Node(var data: Any?) {
        var next: Node?
        var prev: Node? = null

        init {
            next = prev
        }
    }
}