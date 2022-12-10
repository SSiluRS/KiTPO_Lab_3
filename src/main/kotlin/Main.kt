

import factory.UserFactory
import builder.UserTypeBuilder
import list.Action
import ui.ListActionListener
import ui.UI
import list.IList
import java.io.FileNotFoundException
import java.util.*

object Main {
    private fun test(builder: UserTypeBuilder) {
        var i = 1
        while (i < 2000) {
            val n = i * 1000
            println("N = $n")
            val mylist = list.List()
            for (j in 0 until n) builder.create()?.let { mylist.add(it) }
            val start = System.nanoTime()
            try {
                builder.getComparator()?.let { mylist.sort(it) }
            } catch (ignored: StackOverflowError) {
                System.err.println("Stack error")
                return
            }
            val end = System.nanoTime()
            println("Millis elapsed " + (end - start) * 1.0 / 1000000)
            i *= 2
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val userTypeList = Arrays.asList("PolarVector", "Double")
        val ui = UI()
        val uf = UserFactory()
        ui.showMenu()
        for (s in userTypeList) {
            println("$s: ")
            val builder: UserTypeBuilder = uf.getBuilderByName(s)
            test(builder)
            val mylist = list.List()
            for (j in 0..9) builder.create()?.let { mylist.add(it) }
            println("initial")
            mylist.forEach(object : Action {
                override fun toDo(obj: Any?) {
                    val x = obj
                    println(x)
                }
            })
            builder.getComparator()?.let { mylist.sort(it) }
            println("\nafter sort")
            mylist.forEach(object : Action {
                override fun toDo(obj: Any?) {
                    val x = obj
                    println(x)
                }
            })
            mylist.remove(1)
            println("\nafter remove from 1 index")
            mylist.forEach(object : Action {
                override fun toDo(obj: Any?) {
                    val x = obj
                    println(x)
                }
            })
            mylist.remove(0)
            println("\nafter remove from 0 index")
            mylist.forEach(object : Action {
                override fun toDo(obj: Any?) {
                    val x = obj
                    println(x)
                }
            })
            builder.create()?.let { mylist.add(it, 1) }
            println("\nafter add to 1 index")
            mylist.forEach(object : Action {
                override fun toDo(obj: Any?) {
                    val x = obj
                    println(x)
                }
            })
            builder.create()?.let { mylist.add(it, 0) }
            println("\nafter add to 0 index")
            mylist.forEach(object : Action {
                override fun toDo(obj: Any?) {
                    val x = obj
                    println(x)
                }
            })
            try {
                mylist.saveToFile("file.dat", mylist, builder)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
            val list1 = list.List()
            try {
                mylist.loadFromFile("file.dat", list1, builder)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            println("\nSaved List:")
            mylist.forEach(object : Action {
                override fun toDo(obj: Any?) {
                    val x = obj
                    println(x)
                }
            })
            println("\n\n")
            println("Loaded List:")
            list1.forEach(object : Action {
                override fun toDo(obj: Any?) {
                    val x = obj
                    println(x)
                }
            })
        }
    }
}