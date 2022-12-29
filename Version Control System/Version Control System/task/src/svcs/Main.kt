package svcs

import java.io.File

val SEPERATOR: String = File.separator

const val LOG_FILE_NAME = "log.txt"
const val LAST_COMMIT_FILE_NAME = "lastCommit.txt"
const val CONFIG_FILE_NAME = "config.txt"
const val INDEX_FILE_NAME = "index.txt"

val CURRENT_WORKING_DIR = System.getProperty("user.dir")


val VCS_FOLDER_PATH = "vcs$SEPERATOR"
val COMMIT_FOLDER_PATH = "${VCS_FOLDER_PATH}commits$SEPERATOR"
val INDEX_FILE_PATH = "${VCS_FOLDER_PATH}$INDEX_FILE_NAME"
val CONFIG_FILE_PATH = "${VCS_FOLDER_PATH}$CONFIG_FILE_NAME"
val LAST_COMMIT_FILE_PATH = "${VCS_FOLDER_PATH}$LAST_COMMIT_FILE_NAME"
val LOG_FILE_PATH = "${VCS_FOLDER_PATH}$LOG_FILE_NAME"


fun main(args: Array<String>) {
    initVcsDirectories()
    if (args.isEmpty()) {
        printHelp()
        return
    }
    when (args[0]) {
        "--help" -> printHelp()
        "config" -> configCommand(args)
        "add" -> addCommand(args)
        "commit" -> commitCommand(args)
        "log" -> logCommand()
        "checkout" -> checkoutCommand(args)
        else -> invalidCommand(args[0])
    }
}

fun initVcsDirectories() {
    val vcsFolder = File(VCS_FOLDER_PATH)
    val commitsFolder = File(COMMIT_FOLDER_PATH)

    if (!vcsFolder.exists())
        vcsFolder.mkdir()
    if (!commitsFolder.exists())
        commitsFolder.mkdir()

    File(LAST_COMMIT_FILE_PATH).createNewFile()
    File(LOG_FILE_PATH).createNewFile()
    File(INDEX_FILE_PATH).createNewFile()
    File(CONFIG_FILE_PATH).createNewFile()

}

fun invalidCommand(enteredCommand: String) {
    println("'$enteredCommand' is not a SVCS command.")
}

fun checkoutCommand(args: Array<String>) {
    if (args.size < 2) {
        println("Commit id was not passed.")
        return
    }
    val givenCommitHash = args[1]
    val pathToLookFor = COMMIT_FOLDER_PATH + givenCommitHash
    val hashFolder = File(pathToLookFor)

    if (!hashFolder.exists()) {
        println("Commit does not exist.")
        return
    }
    val committedFiles = hashFolder.listFiles().toMutableList()
    for (committedFile in committedFiles) {
        val restoredFilePath = CURRENT_WORKING_DIR + SEPERATOR + committedFile.name
        committedFile.copyTo(File(restoredFilePath), overwrite = true)
    }

    println("Switched to commit $givenCommitHash.")

}

fun logCommand() {
    val logFile = File(LOG_FILE_PATH)
    if (logFile.length() == 0L) {
        println("No commits yet.")
        return
    }
    logFile.readLines().forEach(::println)
}


fun commitCommand(args: Array<String>) {
    if (args.size < 2) {
        println("Message was not passed.")
        return
    }
    val commitMessage = args[1]
    val trackFile = File(INDEX_FILE_PATH)
    if (trackFile.length() == 0L) {
        println("Nothing to commit.")
        return
    }

    var fileContents: String = ""
    for (fileName in trackFile.readLines()) {
        fileContents += File(fileName).readText()
    }
    val currentCommitHash = fileContents.hashCode().toString(16)
    val lastCommitFile = File(LAST_COMMIT_FILE_PATH)
    val lastCommitHash = lastCommitFile.readText()

    if (currentCommitHash == lastCommitHash) {
        println("Nothing to commit.")
        return
    }

    val currentCommitDir = File(COMMIT_FOLDER_PATH + SEPERATOR + currentCommitHash + SEPERATOR)
    currentCommitDir.mkdir()
    for (fileName in trackFile.readLines()) {
        File(fileName).copyTo(File(currentCommitDir.path + SEPERATOR + fileName))
    }

    saveCommitLog(commitMessage, currentCommitHash)
    lastCommitFile.writeText(currentCommitHash)
    println("Changes are committed.")


}

fun saveCommitLog(commitMessage: String, currentCommitHash: String) {
    val username = File(CONFIG_FILE_PATH).readText()
    val logFile = File(LOG_FILE_PATH)
    val logContent = logFile.readText()
    val newCommitLog = "commit $currentCommitHash\nAuthor: $username\n$commitMessage"
    logFile.writeText(newCommitLog + "\n" + logContent)
}


fun addCommand(args: Array<String>) {
    if (args.size < 2) {
        outputTrackedFiles()
    } else {
        val fileNameToAdd = args[1]
        val fileToAdd = File(fileNameToAdd)
        if (!fileToAdd.exists()) {
            println("Can't find '$fileNameToAdd'.")
            return
        }
        addFile(fileToAdd)
    }
}

fun addFile(fileToAdd: File) {
    val trackFile = File(INDEX_FILE_PATH)
    if (trackFile.canWrite()) {
        val fileName = fileToAdd.name
        trackFile.appendText(fileName + '\n')
        println("The file '$fileName' is tracked.")
    } else
        println("Could not write to index file")
}

fun outputTrackedFiles() {
    val trackFile = File(INDEX_FILE_PATH)
    if (trackFile.exists() && trackFile.length() != 0L) {
        println("Tracked files:")
        for (file in trackFile.readLines())
            println(file)
    } else {
        println("Add a file to the index.")
    }
}

fun configCommand(args: Array<String>) {
    val configFile = File(CONFIG_FILE_PATH)
    if (args.size == 1 && configFile.length() == 0L)
        println("Please, tell me who you are.")
    else if (args.size == 1) {
        if (configFile.canRead()) {
            val username = configFile.readText()
            println("The username is $username.")
        } else {
            println("Could not read username from config file")
        }

    } else if (args.size == 2) {
        val username = args[1]
        configFile.createNewFile()
        if (configFile.canWrite()) {
            configFile.writeText(username)
            println("The username is $username.")
        } else
            println("Could not open config file")
    }
}

fun printHelp() {
    println("These are SVCS commands:")
    println("config     Get and set a username.")
    println("add        Add a file to the index.")
    println("log        Show commit logs.")
    println("commit     Save changes.")
    println("checkout   Restore a file.")
}