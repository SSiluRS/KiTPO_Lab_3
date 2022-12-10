package comparator

import builder.PolarVector
import java.io.Serializable

class PolarVectorComparator : Comparator, Serializable {
    override fun compare(o1: Any, o2: Any): Double {
        return (o1 as PolarVector?)!!.length - (o2 as PolarVector?)!!.length
    }
}