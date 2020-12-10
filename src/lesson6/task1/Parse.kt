@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

import lesson4.task1.roman

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun amain() {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}


fun daysInMonth(month: Int, year: Int): Int = when (month) {
    in listOf<Int>(1, 3, 5, 7, 8, 10, 12) -> 31
    in listOf<Int>(4, 6, 9, 11) -> 30
    2 -> if ((year % 4 == 0) && (year % 100 != 0)) 29 else if ((year % 100 == 0) && (year % 400 == 0)) 29 else 28
    else -> -1
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
fun dateStrToDigit(str: String): String {
    val months = listOf(
        "января",
        "февраля",
        "марта",
        "апреля",
        "мая",
        "июня",
        "июля",
        "августа",
        "сентября",
        "октября",
        "ноября",
        "декабря"
    )
    val date = str.split(' ').map { it.trim().toLowerCase() }

    try {
        if (date.size != 3) throw Exception("Неверные входные данные: $str")
        val month = months.indexOf(date[1]) + 1
        val day = date[0].toInt()
        val year = date[2].toInt()

        if (
            ((month < 1) || (month > 12)) ||
            (year < 1) ||
            ((day < 1) || (day > daysInMonth(month, year)))
        ) {
            throw Exception("Неверный формат данных. day='$day', month='$month', year='$year'")
        }

        return String.format("%02d.%02d.%d", day, month, year)

    } catch (e: Exception) {
        //e.printStackTrace()
        return ""
    }


}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String): String {
    val months = listOf(
        "января",
        "февраля",
        "марта",
        "апреля",
        "мая",
        "июня",
        "июля",
        "августа",
        "сентября",
        "октября",
        "ноября",
        "декабря"
    )
    val splitString = digital.split('.').map { it.trim() }
    try {
        if (splitString.size != 3) throw Exception("Неверные входные данные: $digital")
        val day = splitString[0].toInt()
        val month = splitString[1].toInt()
        val year = splitString[2].toInt()

        if (
            ((month < 1) || (month > 12) || month > months.size) ||
            (year < 1) ||
            ((day < 1) || (day > daysInMonth(month, year)))
        ) {
            throw Exception("Неверный формат данных. day='$day', month='$month', year='$year'")
        }

        return String.format("%d %s %d", day, months[month - 1], year)
    } catch (e: Exception) {
        //e.printStackTrace()
        return ""
    }

}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -89 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку.
 *
 * PS: Дополнительные примеры работы функции можно посмотреть в соответствующих тестах.
 */
fun flattenPhoneNumber(phone: String): String {
    if (("+" in phone) && (!phone.startsWith("+"))) return ""
    if (("()" in phone) || (")(" in phone)) return ""
    val symList = listOf('+', '-', '(', ')', ' ')
    val phoneSym = phone.filter { !it.isDigit() }
    if (!phoneSym.all { it in symList }) return ""

    val phoneNumber = phone.filter { it.isDigit() }

    return "${if (phone.startsWith("+")) "+" else ""}$phoneNumber"
}


/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    val symList = listOf("-", "%")
    if (!jumps.split(" ").filter { it.toIntOrNull() == null }.all { it in symList }) return -1
    return jumps.split(" ").filter { it.toIntOrNull() != null }.map { it.toInt() }.max() ?: -1
}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки, а также в случае отсутствия удачных попыток,
 * вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    val sym = listOf("+", "%", "-")
    val height = jumps.split(' ').filter { it.toIntOrNull() != null }.map { it.toInt() }
    val attempt = jumps.split(' ').filter { it.toIntOrNull() == null }
    var max = -1
    val pairs = height.zip(attempt)
    if (pairs.size != height.size || pairs.size != attempt.size) return -1
    pairs.forEach { (h, a) ->
        if (!a.all { it.toString() in sym }) return -1
        if (a.startsWith('+') && (h > max)) {
            max = h
        }
    }
    return max
}

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    val validSym = listOf("+", "-")
    var result: Int = 0
    var prevSym: String? = null
    for (sym in expression.split(" ")) {
//        println("$result, $prevSym, $sym")
        when {
            (sym in validSym) -> {
                if ((prevSym == null) || (prevSym in validSym)) {
                    throw IllegalArgumentException()
                }
                prevSym = sym
            }
            ((sym.toInt() >= 0) && (sym.first().toString().toIntOrNull() != null)) -> {
                if (prevSym == null) {
                    result = sym.toInt()
                    prevSym = sym
                } else {
                    if (prevSym in validSym) {
                        when (prevSym) {
                            "+" -> result += sym.toInt()
                            "-" -> result -= sym.toInt()
                        }
                    } else {
                        throw IllegalArgumentException()
                    }
                }
                prevSym = ""
            }
            else -> throw IllegalArgumentException()
        }
    }
//    println("$expression -> $result")
    return result

}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {

    val strSplit = str.toLowerCase().split(" ")
    if (strSplit.isEmpty() || strSplit.size < 2) return -1

    var word: String? = null
    var prevStr = strSplit.first()

    for (curStr in strSplit.subList(1, strSplit.size - 1)) {
        if (prevStr == curStr) {
            word = curStr
            break
        }
        prevStr = curStr
    }
    if (word == null) return -1

    return str.toLowerCase().indexOf("$word $word")

}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше либо равны нуля.
 */
fun mostExpensive(description: String): String {
    val items = description.split(";").map { it.trim() }
    val costs = mutableMapOf<String, Double>()
    try {
        items.forEach {
            val (name, coast) = it.split(" ")
            costs[name] = coast.toDouble()
        }
    } catch (e: Exception) {
        return ""
    }
    return costs.maxBy { it.value }?.key ?: ""
}

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */

fun fromRoman(roman: String): Int {
    var result = 0
    val tree = mapOf(
        'I' to mapOf('_' to 1, 'V' to 4, 'X' to 9),
        'V' to mapOf('_' to 5),
        'X' to mapOf('_' to 10, 'L' to 40, 'C' to 90),
        'L' to mapOf('_' to 50),
        'C' to mapOf('_' to 100, 'D' to 400, 'M' to 900),
        'D' to mapOf('_' to 500),
        'M' to mapOf('_' to 1000)
    )
    if (roman.isEmpty) return -1
    var subTree: Map<Char, Int>
    var curSym: Char
    var nextSym: Char

    var i = 0
    val lastIndex = roman.length - 1
    while (i <= lastIndex) {
        curSym = roman[i]
        subTree = tree[curSym] ?: return -1
        if (i == lastIndex) {
            result += subTree['_'] ?: return -1
            break
        } else {
            nextSym = roman[i + 1]
            if (nextSym in subTree) {
                i += 2
                result += subTree[nextSym] ?: return -1
            } else {
                i++
                result += subTree['_'] ?: return -1
            }
        }
    }
    return result
}

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> = TODO()
