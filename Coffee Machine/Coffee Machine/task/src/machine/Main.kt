package machine

var money = 550
var water = 400
var milk = 540
var beans = 120
var cups = 9
fun main() {
    while(true) {
        when (getAction()) {
            "buy" -> buyAction()
            "fill" -> fillAction()
            "take" -> takeAction()
            "remaining" -> printState()
            "exit" -> break
        }
    }


}

fun takeAction() {
    println("I gave you $$money")
    money = 0
}

fun fillAction() {
    println("Write how many ml of water you want to add:")
    water += readln().toInt()
    println("Write how many ml of milk you want to add:")
    milk += readln().toInt()
    println("Write how many grams of coffee beans you want to add:")
    beans += readln().toInt()
    println("Write how many disposable cups you want to add:")
    cups += readln().toInt()
}

fun buyAction() {
    println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:")
    val option = readln()
    if (option == "back")
        return

    when (option.toInt()) {
        1 -> buyEspresso()
        2 -> buyLatte()
        3 -> buyCappuccino()
    }
}

fun enoughResources() {
    println("I have enough resources, making you a coffee!")
}

fun buyCappuccino() {
    if (water < 200 || milk < 100 || beans < 12 || cups < 1)
        println("Error")
    else {
        enoughResources()
        water -= 200
        milk -= 100
        beans -= 12
        money += 6
        cups--
    }
}

fun buyLatte() {
    if (water < 350 || milk < 75 || beans < 20 || cups < 1)
        println("Error")
    else {
        enoughResources()
        water -= 350
        milk -= 75
        beans -= 20
        money += 7
        cups--
    }
}

fun buyEspresso() {
    if (water < 250 || beans < 16 || cups < 1)
        println("Error")
    else {
        enoughResources()
        water -= 250
        beans -= 16
        money += 4
        cups--
    }
}

fun getAction(): String {
    println("Write action (buy, fill, take):")
    return readln()
}

fun printState() {
    println("The coffee machine has:")
    println("$water ml of water")
    println("$milk ml of milk")
    println("$beans g of coffee beans")
    println("$cups disposable cups")
    println("$$money of money")
}

