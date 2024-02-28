class Group(
    val salesman: String = "", val sum: Double = Double.MAX_VALUE
) {
    override fun toString(): String {
        return "Group(salesman='$salesman', sum=$sum)"
    }
}