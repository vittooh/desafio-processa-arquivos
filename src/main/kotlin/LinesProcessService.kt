import Constants.Companion.C_CEDILHA
import Constants.Companion.closeBrackets
import Constants.Companion.idClient
import Constants.Companion.idSale
import Constants.Companion.idSalesman
import Constants.Companion.openBrackets

class LinesProcessService {

    fun processLines(lines: List<String>): ProcessResultData {
        if (lines.isEmpty()) {
            throw EmptyDataSetException("Dataset Cannot be empty")
        }
        var mostHigherSale = Sale()
        var worstSale = Sale(totalSale = Double.MAX_VALUE)
        val salesman = mutableListOf<Salesman>()
        val client = mutableListOf<Client>()
        val sales = mutableMapOf<String, Sale>()
        lines.forEach { line ->
            val splitLine = line.split(C_CEDILHA)
            when (splitLine[0]) {
                idSalesman -> salesman.add(processSalesman(splitLine))
                idClient -> client.add(processClient(splitLine))
                idSale -> {
                    sales[splitLine[1]] = processSales(splitLine)
                    //TODO talvez remover o !!
                    if (sales[splitLine[1]]!!.totalSale > mostHigherSale.totalSale) {
                        mostHigherSale = sales[splitLine[1]]!!
                    }
                    if (sales[splitLine[1]]!!.totalSale < worstSale.totalSale) {
                        worstSale = sales[splitLine[1]]!!
                    }
                }
            }
        }
        if (worstSale.saleId == mostHigherSale.saleId){
            worstSale = Sale()
        }

        val resultProcess = ProcessResultData(
            salesman, client, sales, mostHigherSale, worstSale
        )

        return resultProcess;
    }


    private fun processSalesman(fields: List<String>): Salesman {
        return Salesman(
            fields[2],
            fields[3].toDouble(),
            fields[1]
        )
    }

    private fun processClient(fields: List<String>): Client {
        //002ç2345675434544345çJose da SilvaçRural
        return Client(
            fields[2],
            fields[1],
            fields[3]
        )
    }

    private fun processSales(fields: List<String>): Sale {

        val totalSale = fields[2]
            .removePrefix(openBrackets)
            .removeSuffix(closeBrackets)
            .split(",")
            .map {
                val saleValues = it.split("-")
                saleValues[1].toLong() * saleValues[2].toDouble()
            }.reduce { acc, value -> acc + value }

        println(totalSale)

        return Sale(
            fields[1].toLong(),
            fields[3],
            totalSale
        )
    }
}