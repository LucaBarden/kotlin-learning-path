fun main() {
    // put your code here
    val size = readln().toInt()
    val numbers = MutableList(size) { readln().toInt() }
    var max = -1
    for(num in numbers)
        if(num % 4 == 0 && num > max)
            max = num
    println(max)
}