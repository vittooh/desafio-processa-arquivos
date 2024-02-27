class Sale(
    val saleId: Long = 0,
    val salesmanName: String = "",
    val totalSale: Double = 0.0
){
    override fun toString(): String {
        return "Sale(saleId=$saleId, salesmanName='$salesmanName', totalSale=$totalSale)"
    }
}