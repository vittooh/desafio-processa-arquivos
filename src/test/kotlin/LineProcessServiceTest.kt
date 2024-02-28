import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LineProcessServiceTest {

    @Test
    fun `test processLines with valid input`() {

        val lines = listOf(
            "001ç1234567891234çPedroç50000",
            "002ç2345675434544345çJose da SilvaçRural",
            "003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro"
        )
        val result = LinesProcessService().processLines(lines)

        assertEquals(1, result.salesman.size)
        assertEquals(1, result.client.size)
        assertEquals(1, result.sales.size)
        assertEquals(10, result.mostHigherSale.saleId)
        assertEquals("Pedro", result.bestSalesman.salesman)
        assertEquals("", result.worstSalesman.salesman)
        assertEquals(10.0 * 100 + 30.0 * 2.50 + 40.0 * 3.10, result.mostHigherSale.totalSale)
        assertEquals(0, result.worstSale.saleId)
    }

    @Test
    fun `test processLines with empty input`() {
        val lines = emptyList<String>()
        Assertions.assertThrows(
            EmptyDataSetException::class.java
        ) { LinesProcessService().processLines(lines) }
    }
}