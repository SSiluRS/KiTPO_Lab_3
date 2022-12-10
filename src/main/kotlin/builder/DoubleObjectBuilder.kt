package builder

import comparator.Comparator
import comparator.DoubleComparator
import java.util.Random

class DoubleObjectBuilder : UserTypeBuilder {
    override fun typeName(): String {
        return "Double"
    }

    override fun create(): Double {
        return Random().nextDouble()
    }

    override fun createFromString(s: String?): Any? {
        return s?.toDouble()
    }

    override fun toString(`object`: Any?): String {
        return `object`.toString()
    }

    override fun getComparator(): Comparator{
        return DoubleComparator()
    }
}