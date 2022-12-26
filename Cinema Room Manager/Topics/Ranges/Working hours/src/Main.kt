fun main() {
    val time = readln().toInt()
    val workTime = 9..18
    val lunchTime = 13..14
    // do not change the code above
    // put your code here
    when (time) {
        in lunchTime -> println(false)
        in workTime -> println(true)
        else -> println(false)
    }
}