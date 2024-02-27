private const val idSalesman = "001"

private const val idClient = "002"

private const val idSale = "003"


class Group(
    val salesman: String,
    val sum: Double
) {
    override fun toString(): String {
        return "Group(salesman='$salesman', sum=$sum)"
    }
}

fun main(args: Array<String>) {

    val inputs = listOf(
        "001ç1234567891234çPedroç50000",
        "001ç3245678865434çPauloç40000.99",
        "002ç2345675434544345çJose da SilvaçRural",
        "002ç2345675433444345çEduardo PereiraçRural",
        "003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro", //199
        "003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo", //393.50
        "003ç11ç[1-10-100,2-30-2.50,3-40-3.10]çPedro", //199
        "003ç12ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo", //393.50
        "003ç13ç[1-34-10,2-33-1.50,3-40-0.10]çDBL", //393.50
    )
    val salesman = mutableListOf<Salesman>()
    val client = mutableListOf<Client>()
    val sales = mutableMapOf<String, Sale>()
    var mostHigherSale = Sale()
    var worstSale = Sale(totalSale = Double.MAX_VALUE)


    inputs.forEach { line ->
        val splitLine = line.split("ç")
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

    //pior-venda monetarios
    println("Total Clients: ${client.size}")
    println("Total Salesman: ${salesman.size}")
    println("Most Higher Sale: $mostHigherSale")
    println("Worst Sale: $worstSale")

    val grouped: Map<String, Group> = sales.values.groupBy { it.salesmanName }
        .mapValues { entry ->
            Group(entry.key, entry.value.sumOf { it.totalSale })
        }


    println("Best Salesman :: ${grouped.maxBy { it.value.sum }}")
    println("Worst Salesman :: ${grouped.minBy { it.value.sum }}")

    print("end")
}

fun processClient(fields: List<String>): Client {
    //002ç2345675434544345çJose da SilvaçRural
    return Client(
        fields[2],
        fields[1],
        fields[3]
    )
}

fun processSalesman(fields: List<String>): Salesman {
    return Salesman(
        fields[2],
        fields[3].toDouble(),
        fields[1]
    )
}


private const val openBrackets = "["

private const val closeBrackets = "]"

fun processSales(fields: List<String>): Sale {

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