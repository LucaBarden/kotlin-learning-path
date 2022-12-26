import kotlin.math.pow

fun main() {
    // put your code here
    val input = MutableList(4) { readln().toInt() }
    for(i in 0..1000)
        if (solveEquation(input, i.toDouble()) == 0)
            println(i)
}

fun solveEquation(values: MutableList<Int>, x: Double): Int {
    return (values[0] * x.pow(3) + values[1] * x.pow(2) + values[2] * x + values[3]).toInt();
}