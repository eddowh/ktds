import kotlin.test.*
import org.junit.*
import ktds.DoublyLinkedList

class DoublyLinkedListTest {
    private var list : DoublyLinkedList<Int> = DoublyLinkedList<Int>()

    @Before
    fun setUp() {
        var list = DoublyLinkedList<Int>()
        for (i in 0..9) {
            list.push(i)
        }
        this.list = list
    }

    @After
    fun tearDown() {
    }

    @Test
    fun get() {
        var list = DoublyLinkedList<Int>()
        fun func(i : Int) : Int = (i+1)*10
        for (i in 0..10) {
            list.push(func(i))
        }
        for (i in 0..10) {
            assertEquals(list.get(i), func(i))
        }
    }

    @Test
    fun contains() {
        for (i in 0..9) {
            assertTrue(this.list.contains(i))
        }
        assertFalse(this.list.contains(99))
    }

}
