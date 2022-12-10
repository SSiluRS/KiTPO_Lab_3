package ui

import javax.swing.DefaultListModel
import javax.swing.JFileChooser
import javax.swing.JFrame

interface ListActionListener {
    fun onAdd(text: String?)

    fun onInsert(text: String?, index: Int)

    fun onRemove(index: Int)

    fun onSort()

    fun onSave(fileChooser: JFileChooser?, parentFrame: JFrame?)

    fun onLoad(fileChooser: JFileChooser?, parentFrame: JFrame?)

    fun getListModel(): DefaultListModel<Any?>?

    fun onSelectType(type: String?)
}