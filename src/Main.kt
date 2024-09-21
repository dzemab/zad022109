import kotlin.random.Random
import java.util.Collections // Import the Collections class

fun main() {
    val alphabet = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"
    val alphabetSize = alphabet.length

    println("Введите сообщение:")
    val message = readLine()!!.uppercase()

    println("Введите ключ:")
    var key = readLine()!!.uppercase()

    println("Использовать типовую таблицу (1) или случайную (2)?")
    val tableType = readLine()!!.toInt()

    val table = if (tableType == 1) {
        generateVigenereTable(alphabet)
    } else {
        generateRandomVigenereTable(alphabet)
    }

    // Повторяем ключ до длины сообщения
    key = key.repeat((message.length / key.length) + 1).substring(0, message.length)

    // Шифруем сообщение
    val encryptedMessage = encrypt(message, key, table, alphabet)

    // Выводим результаты
    println("\nИсходное сообщение: $message")
    println("Ключ:                $key")
    println("Зашифрованное сообщение: $encryptedMessage")
    printTable(table)
}

fun encrypt(message: String, key: String, table: List<String>, alphabet: String): String {
    val encryptedMessage = StringBuilder()
    for (i in message.indices) {
        val messageCharIndex = alphabet.indexOf(message[i])
        val keyCharIndex = alphabet.indexOf(key[i])
        if (messageCharIndex != -1 && keyCharIndex != -1) {
            encryptedMessage.append(table[keyCharIndex][messageCharIndex])
        } else {
            encryptedMessage.append(message[i])
        }
    }
    return encryptedMessage.toString()
}

fun generateVigenereTable(alphabet: String): List<String> {
    val table = mutableListOf<String>()
    for (i in 0 until alphabet.length) {
        table.add(alphabet.substring(i) + alphabet.substring(0, i))
    }
    return table
}

fun generateRandomVigenereTable(alphabet: String): List<String> {
    val table = mutableListOf<String>()
    val randomAlphabet = alphabet.toMutableList().shuffled()
    table.add(randomAlphabet.joinToString(""))

    val shifts = (1 until alphabet.length).shuffled()
    for (i in 1 until alphabet.length) {
        val shiftedAlphabet = randomAlphabet.toMutableList()
        Collections.rotate(shiftedAlphabet, shifts[i - 1]) // Use Collections.rotate here
        table.add(shiftedAlphabet.joinToString(""))
    }
    return table
}

fun printTable(table: List<String>) {
    println("\nШифровальная таблица:")
    for (row in table) {
        println(row)
    }
    println()
}