fun main() {
    val totalSeconds = System.currentTimeMillis() / 1000 // dont change this line
    // enter your code
    val seconds = totalSeconds % 60
    val minutes = (totalSeconds % 3600) / 60
    val hours = totalSeconds / 3600 % 24
    println("%02d:%02d:%02d".format(hours, minutes, seconds))



}