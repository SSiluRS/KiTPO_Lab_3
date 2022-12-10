package builder
import comparator.Comparator
interface UserTypeBuilder {
    fun typeName(): String?
    fun create(): Any?
    fun getComparator(): Comparator?

    fun createFromString(s: String?): Any?
    fun toString(`object`: Any?): String?
}