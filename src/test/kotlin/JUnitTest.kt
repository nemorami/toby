import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse


class JUnitTest {
    companion object {
        var testObject = mutableListOf<JUnitTest>()
    }
    @Test
    fun test1() {
        assertFalse(testObject.contains(this) )
        testObject.add(this)
    }
    @Test
    fun test2() {
        assertFalse(testObject.contains(this) )
        testObject.add(this)
    }
    @Test
    fun test3() {
        assertFalse(testObject.contains(this) )
        testObject.add(this)
    }
}
