package list

import builder.UserTypeBuilder
import comparator.Comparator
interface IList {
    fun add(data: Any?)
    operator fun get(index: Int): Any?
    fun remove(index: Int)
    fun size(): Int
    fun add(data: Any, index: Int)
    fun forEach(action : Action)
    fun sort(comparator: Comparator)
    fun saveToFile(filename: String, list: IList, builder:UserTypeBuilder)
    fun loadFromFile(filename: String, list: IList, builder:UserTypeBuilder) : IList
}