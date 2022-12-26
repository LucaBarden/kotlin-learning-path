package cinema

fun main() {
    println("Enter the number of rows:")
    val rowsOfRoom = Integer.parseInt(readln())
    println("Enter the number of seats in each row: ")
    val seatsPerRow = Integer.parseInt(readln())

    val cinema = Array(seatsPerRow) { Array(rowsOfRoom) { "S" } }
    var income = 0
    while (true) {
        val option = getUserOption()
        if (option == 0)
            break
        else if (option == 1)
            printSeating(cinema)
        else if (option == 2) {
            var rowNumber: Int
            var seatNumber: Int
            while (true) {
                println("Enter a row number:")
                rowNumber = Integer.parseInt(readln())
                println()
                println("Enter a seat number in that row:")
                seatNumber = Integer.parseInt(readln())
                println()

                if (rowNumber > rowsOfRoom || seatNumber > seatsPerRow) {
                    println("Wrong input")
                    continue
                }

                if (!cinema[seatNumber - 1][rowNumber - 1].equals("B"))
                    break
                else
                    println("That ticket has already been purchased!")
            }
            val costOfTicket = if ((rowsOfRoom * seatsPerRow) <= 60)
                10
            else
                if (rowNumber <= rowsOfRoom / 2)
                    10
                else
                    8
            println("Ticket price: $$costOfTicket")
            income += costOfTicket
            cinema[seatNumber - 1][rowNumber - 1] = "B"
        } else
            printStats(cinema, income)
    }
}

fun printStats(cinema: Array<Array<String>>, income: Int) {
    val purchasedTickets = cinema.flatten().stream().filter { a -> a.equals("B") }.count()
    val seats = cinema.size * cinema[0].size
    val percentage = (purchasedTickets.toDouble() / seats.toDouble()) * 100
    val formattedPercentage = "%.2f".format(percentage)
    val totalIncome = if (seats <= 60)
        seats * 10
    else
        (cinema[0].size / 2) * cinema.size * 10 + (cinema[0].size - (cinema[0].size / 2)) * cinema.size * 8
    println("Number of purchased tickets: $purchasedTickets")
    println("Percentage: $formattedPercentage%")
    println("Current income: $$income")
    println("Total income: $$totalIncome")

}

private fun getUserOption(): Int {
    println("1. Show the seats")
    println("2. Buy a ticket")
    println("3. Statistics")
    println("0. Exit")
    val option = Integer.parseInt(readln())
    return option
}

fun printSeating(cinema: Array<Array<String>>) {
    println("Cinema:")
    print("  ")
    repeat(cinema.size) {
        val currentSeat = it + 1
        print("$currentSeat ")
    }
    println()
    repeat(cinema[0].size) { it ->
        val row = it + 1
        print("$row ")
        repeat(cinema.size) { it2 ->
            print(cinema[it2][it] + " ")
        }
        println()
    }
    println()
}