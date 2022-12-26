// write your code here

/* Do not change code below */
fun main() {
    val a = readLine()!!.toInt()
    println(getLastDigit(a))
}

fun getLastDigit(number : Int) : Int {
    return kotlin.math.abs(number % 10)
}