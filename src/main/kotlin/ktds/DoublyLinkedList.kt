package ktds

 /**
  * A doubly linked list.
  *
  * @param T the type of a member in this list
  * @constructor Creates an empty list
  */
class DoublyLinkedList<T> {
    private var head : Node<T>? = null
    private var tail : Node<T>? = null
    private var size : Int = 0

    /**
     * A node capable of recursively linking to other nodes.
     *
     * @param T the type of a member in this node
     * @property value the value of this node
     * @property prev the previous node linked to this node
     * @property next the next node linked to this node
     */
    private class Node<T>(
            value : T? = null,
            prev  : Node<T>? = null,
            next  : Node<T>? = null) {
        public var value : T?
        public var prev : Node<T>?
        public var next : Node<T>?

        init {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    fun size() : Int {
        return this.size
    }

    fun isEmpty() : Boolean {
        return this.size == 0
    }

    fun clear() {
        this.head = null
        this.tail = null
        this.size = 0
    }

    override fun toString() : String {
        var head : Node<T>? = this.head
        var res : String = ""
        while (head != null) {
            res += head.value.toString() + " "
            head = head.next
        }
        return res
    }

    fun push(value : T?) {
        var node : Node<T> = Node<T>(value=value)
        if (this.head == null) {
            this.head = node
            this.tail = node
        } else {
            node.prev = this.tail
            node.next = null;
            this.tail?.next = node
            this.tail = node
        }
        this.size++
    }

    fun pop() : T? {
        return this.remove(this.size-1);
    }

    fun unshift() : T? {
        return this.remove(0);
    }

    fun shift(value : T?) {
        var node : Node<T> = Node<T>(value=value)
        if (this.tail == null) {
            this.head = node
            this.tail = node
        } else {
            node.prev = null;
            node.next = this.head;
            this.head?.prev = node
            this.head = node
        }
        this.size++
    }

    fun insert(pos : Int, value : T?) {
        if (pos < 0 || pos > this.size) {
            throw IndexOutOfBoundsException()
        }
        if (pos == 0) this.shift(value)
        else if (pos == this.size) this.push(value)
        else {
            var node : Node<T> = this.getNodeAt(pos)
            var pred : Node<T>? = node.prev
            var newNode : Node<T> = Node<T>(value=value, prev=pred, next=node)
            node.prev = newNode
            pred?.next = newNode
            this.size++
        }
    }

    fun remove(pos : Int) : T? {
        // Check for valid indices
        if (pos < 0 || pos >= this.size) {
            throw IndexOutOfBoundsException()
        }
        val node : Node<T>  = this.getNodeAt(pos)
        val pred : Node<T>? = node.prev;
        val succ : Node<T>? = node.next;

        if (pred == null) {
            this.head = succ;
        } else {
            pred.next = succ;
        }

        if (succ == null) {
            this.tail = pred;
        } else {
            succ.prev = pred;
        }

        this.size--
        return node.value
    }

    operator fun get(key : Int) : T? {
        if (key < 0 || key >= this.size) {
            throw IndexOutOfBoundsException()
        }
        return this.getNodeAt(key).value;
    }

    operator fun set(key : Int, value : T?) {
        if (key < 0 || key >= this.size) {
            throw IndexOutOfBoundsException()
        }
        this.getNodeAt(key).value = value
    }

    private fun getNodeAt(pos : Int) : Node<T> {
        var cnt  : Int
        var node : Node<T>?

        // start from head
        if (pos < this.size / 2) {
            cnt = pos
            node = this.head
            while (cnt > 0) {
                node = node?.next
                cnt--
            }
        }
        else {
            cnt = this.size - 1 - pos;
            node = this.tail
            while (cnt > 0) {
                node = node?.prev
                cnt--
            }
        }

        // cast to unsafe (it can't be null)
        var resNode : Node<T> = node as Node<T>
        return resNode
    }

    operator fun contains(value : T) : Boolean {
        var head : Node<T>? = this.head
        while (head != null) {
            if (head.value == value) {
                return true
            }
            head = head.next
        }
        return false
    }

    fun equals(that : DoublyLinkedList<T>) : Boolean {
        if (this === that) {
            return true
        }
        else if (this.size != that.size) {
            return false
        }
        else {
            var thisNode : Node<T>? = this.head
            var thatNode : Node<T>? = that.head
            while (thisNode != null && thatNode != null) {
                if (thisNode.value != thatNode.value) {
                    return false
                }
                thisNode = thisNode.next
                thatNode = thatNode.next
            }
            return true
        }
    }

    fun clone() : DoublyLinkedList<T> {
        var newList = DoublyLinkedList<T>()
        var head : Node<T>? = this.head
        while (head != null) {
            newList.push(head.value)
            head = head.next
        }
        return newList
    }

    fun append(other : DoublyLinkedList<T>) {
        var that = other.clone()
        this.tail?.next = that.head
        that.head?.prev = this.tail
        this.tail = that.tail
        this.size += that.size
    }

    operator fun plus(other : DoublyLinkedList<T>) : DoublyLinkedList<T> {
        // have to clone so original list does not get modified
        var res = this.clone()
        res.append(other)
        return res
    }

    operator fun times(n : Int) : DoublyLinkedList<T> {
        if (n < 1) {
            return DoublyLinkedList<T>()
        }
        var res = this.clone()
        val numIter : Int = n - 1
        for (i in 1..numIter) {
            res.append(this)
        }
        return res
    }

}
