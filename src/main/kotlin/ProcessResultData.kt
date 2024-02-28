class ProcessResultData(
    val salesman: MutableList<Salesman> = mutableListOf(),
    val client: MutableList<Client> = mutableListOf(),
    val sales: MutableMap<String, Sale> = mutableMapOf(),
    val mostHigherSale: Sale = Sale(),
    val worstSale: Sale = Sale(totalSale = Double.MAX_VALUE),
    var worstSalesman: Group = Group(),
    var bestSalesman: Group = Group()
) {
    init {
        setSalesmanRank()
    }

    fun setSalesmanRank() {
        print("Estou agrupando os dados de melhor/pior vendedor")
        val grouped: Map<String, Group> = sales.values.groupBy { it.salesmanName }
            .mapValues { entry ->
                Group(entry.key, entry.value.sumOf { it.totalSale })
            }
        print("agrupei os dados de melhor/pior vendedor")

        bestSalesman = grouped.maxBy { it.value.sum }.value
        worstSalesman = grouped.minBy { it.value.sum }.value
        if (bestSalesman.salesman == worstSalesman.salesman) {
            worstSalesman = Group()
        }
        println("Best Salesman :: ${grouped.maxBy { it.value.sum }}")
        println("Worst Salesman :: ${grouped.minBy { it.value.sum }}")
    }
}