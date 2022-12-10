package ui

import builder.UserTypeBuilder
import factory.UserFactory

import list.*
import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.io.FileNotFoundException
import javax.swing.*
import javax.xml.stream.XMLStreamException

class UI: JPanel() {
    var userFactory: UserFactory
    var myList: IList
    var userType: UserTypeBuilder
    private val delBtn: JButton
    private val insertByIdBtn: JButton
    private val insertBtn: JButton
    private val sortBtn: JButton
    private val saveBtn: JButton
    private val loadBtn: JButton
    private val clearBtn: JButton

    private val outTextField: JTextArea
    private val delByIdField: JTextField
    private val insertByIdField: JTextField

    private val delLabel : JLabel
    private val typeLabel : JLabel
    private val insertLabel : JLabel
    private val createLabel : JLabel
    private val sortLabel : JLabel

    private val factoryListItems: JComboBox<*>
    private var defaultType = "Double"
    val filename = "file.dat"

    fun showMenu() {
        val frame = JFrame("MainMenu")
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.contentPane.add(UI())
        frame.pack()
        frame.isVisible = true
    }

    init {
        userFactory = UserFactory()
        userType = userFactory.getBuilderByName("Double")
        myList = list.List()

        //construct preComponent
        val typeNameList = userFactory.getTypeNameList()
        val factoryListItemsItems = arrayOfNulls<String>(typeNameList.size)
        for (i in typeNameList.indices) {
            factoryListItemsItems[i] = typeNameList[i]
        }

        //construct components
        delBtn = JButton("Удалить")
        delBtn.background = Color.LIGHT_GRAY
        insertByIdBtn = JButton("Вставить")
        insertByIdBtn.background = Color.LIGHT_GRAY
        insertBtn = JButton("Сгенерировать")
        insertBtn.background = Color.LIGHT_GRAY
        sortBtn = JButton("Сортировать")
        sortBtn.background = Color.LIGHT_GRAY
        saveBtn = JButton("Сохранить")
        saveBtn.background = Color.LIGHT_GRAY
        loadBtn = JButton("Загрузить")
        loadBtn.background = Color.LIGHT_GRAY
        clearBtn = JButton("Очистить")
        clearBtn.background = Color.LIGHT_GRAY
        delLabel = JLabel("Удалить по индексу")
        typeLabel = JLabel("Тип данных")
        insertLabel = JLabel("Вставка по индексу")
        createLabel = JLabel("Сгенерировать данные")
        sortLabel = JLabel("Сортировка")
        outTextField = JTextArea(5, 5)
        delByIdField = JTextField(5)
        insertByIdField = JTextField(5)
        factoryListItems = JComboBox<Any?>(factoryListItemsItems)
        factoryListItems.background = Color.LIGHT_GRAY

        //adjust size and set layout
        preferredSize = Dimension(600, 500)
        layout = null

        //add components

        //Добавление компонентов
        add(delBtn)
        add(insertByIdBtn)
        add(outTextField)
        add(factoryListItems)
        add(delByIdField)
        add(delLabel)
        add(typeLabel)
        add(insertLabel)
        add(insertByIdField)
        add(insertBtn)
        add(createLabel)
        add(sortLabel)
        add(sortBtn)
        add(saveBtn)
        add(loadBtn)
        add(clearBtn)

        //set component bounds (only needed by Absolute Positioning)

        //регулировка положения компонентов
        delBtn.setBounds(25, 220, 150, 25)
        insertByIdBtn.setBounds(25, 315, 150, 25)
        outTextField.setBounds(190, 20, 345, 300)
        factoryListItems.setBounds(25, 120, 150, 25)
        delByIdField.setBounds(25, 190, 150, 25)
        delLabel.setBounds(25, 165, 150, 25)
        typeLabel.setBounds(25, 100, 150, 25)
        insertLabel.setBounds(25, 255, 150, 25)
        insertByIdField.setBounds(25, 280, 150, 25)
        insertBtn.setBounds(300, 355, 150, 20)
        createLabel.setBounds(300, 325, 145, 25)
        sortLabel.setBounds(25, 350, 100, 25)
        sortBtn.setBounds(25, 375, 150, 25)
        saveBtn.setBounds(300, 385, 150, 20)
        loadBtn.setBounds(300, 410, 150, 20)
        clearBtn.setBounds(300, 440, 150, 25)


        //Добавление действий на кнопки
        delBtn.addActionListener { deleteNodeById() }
        insertByIdBtn.addActionListener { addNodeById() }
        insertBtn.addActionListener { addNode() }
        sortBtn.addActionListener { sortList() }
        saveBtn.addActionListener { saveList() }
        loadBtn.addActionListener {
            try {
                loadList()
            } catch (ex: XMLStreamException) {
                throw RuntimeException(ex)
            } catch (ex: FileNotFoundException) {
                throw RuntimeException(ex)
            }
        }
        clearBtn.addActionListener { clearOutTextField() }
        factoryListItems.addActionListener(ActionListener { e: ActionEvent? -> selectTypeList(e) })
        outTextField.isEnabled = true
        outTextField.font = Font("Arial", Font.BOLD, 14)
    }

    private fun selectTypeList(actionEvent: ActionEvent?) {
        val box = actionEvent?.source as JComboBox<*>
        val item = box.selectedItem as String
        if (defaultType !== item) {
            defaultType = item
            userType = userFactory.getBuilderByName(defaultType)
            myList = List()
            setTextOnOutTextField()
        }

    }

    private fun clearOutTextField() {
        myList = List()
        setTextOnOutTextField()
    }

    private fun loadList() {
        myList.loadFromFile(filename, myList, userType)
        JOptionPane.showMessageDialog(null, "Список успешно загружен!")
        setTextOnOutTextField()

    }

    private fun saveList() {
        myList.saveToFile(filename, myList, userType)
        JOptionPane.showMessageDialog(null, "Список успешно сохранен!")

    }

    private fun sortList() {
        userType.getComparator()?.let { myList.sort(it) }
        setTextOnOutTextField()
    }

    private fun addNode() {
        myList.add(userType.create())
        setTextOnOutTextField()
    }

    private fun addNodeById() {
        if (insertByIdField.text.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Пустое поле! Введите значение!")
            return
        }
        userType.create()?.let { myList.add(it, insertByIdField.text.toInt()) }
        setTextOnOutTextField()
    }

    private fun deleteNodeById() {
        if (delByIdField.text.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Пустое поле! Введите значение!")
            return
        }
        myList.remove(delByIdField.text.toInt())
        setTextOnOutTextField()
    }

    private fun setTextOnOutTextField() {
        outTextField.text = myList.toString()
    }

}