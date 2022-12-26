fun main() {
    // write your code here
    val input = readln().toInt()
    val range1 = (-14..12)
    val range2 = (15..16)
    val range3 = (19..Integer.MAX_VALUE)
    if (input in range1 || input in range2 || input in range3)
        println("True")
    else
        println("False")
}