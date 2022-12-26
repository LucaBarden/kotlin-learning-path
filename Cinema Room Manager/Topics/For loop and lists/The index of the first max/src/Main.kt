fun main() {
    // write your code here
    val size = readln().toInt()
    val numbers = MutableList(size) { readln().toInt() }
    println(numbers.indexOf(numbers.maxOrNull()))
}