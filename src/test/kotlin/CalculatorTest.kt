import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class CalculatorTest {

    @BeforeEach
    fun setUp(){

    }
    @Test
    fun calcSum() {
        val cal = Calculator()
        val sum = cal.calcSum(this::class.java.getResource("numbers.txt").getPath())
        assertEquals(sum, 10)

    }
}