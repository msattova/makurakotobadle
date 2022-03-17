import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class MainKtTest {

    @Test
    fun `test valid compare green gray`() {
        val result = when{
            Valid.GREEN > Valid.GRAY -> true
            else -> false
        }
        assertEquals(true, result)
    }
    @Test
    fun `test valid compare yellow gray`() {
        val result = when{
            Valid.YELLOW > Valid.GRAY -> true
            else -> false
        }
        assertEquals(true, result)
    }
    @Test
    fun `test valid compare gray gray`() {
        val result = when{
            Valid.GRAY > Valid.GRAY -> true
            else -> false
        }
        assertEquals(false, result)
    }
}