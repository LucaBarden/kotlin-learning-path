import kotlin.math.pow

fun main() {
    // write your code here
    val radius = readln().toDouble()
    val pi = 3.1415
    val area = pi * radius.pow(2)
    println(area)
}