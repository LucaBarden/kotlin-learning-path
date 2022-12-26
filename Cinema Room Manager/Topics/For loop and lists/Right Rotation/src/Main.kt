import java.util.Collections

fun main() {
    // write your code here
    val size = readln().toInt()
    val numbers = MutableList(size) { readln().toInt() }
    val rot = readln().toInt() % size
    Collections.rotate(numbers, rot)
    for(num in numbers)
        print("$num ")
}
