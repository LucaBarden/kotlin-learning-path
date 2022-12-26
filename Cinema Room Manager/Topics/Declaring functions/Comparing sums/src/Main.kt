// write your function here

fun main() {
    val number1 = readln().toInt()
    val number2 = readln().toInt()
    val number3 = readln().toInt()
    val number4 = readln().toInt()

    println(isGreater(number1, number2, number3, number4))
}

fun isGreater(num1: Int, num2: Int, num3: Int, num4: Int): Boolean {
    return num1+num2 > num3+num4
}