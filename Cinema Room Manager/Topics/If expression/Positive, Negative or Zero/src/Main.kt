fun main() {
    // write your code here
    val input = readln().toInt()
    if (input < 0)
        println("negative")
    else if (input > 0)
        print("positive")
    else
        print("zero")
}