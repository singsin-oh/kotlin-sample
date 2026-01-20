package com.singsin.studio.file

import com.singsin.studio.array.Animals
import com.singsin.studio.array.ArrayListSample
import java.io.File

class FileReadSample {

}

fun main() {
    val file = File("/Users/singsin/space/code/kotlin/kotlin-sample/src/main/resources/animals/animals.txt")
    val emptyAnimalsArray = mutableListOf<Animals>()
    for (readLine in file.readLines(charset = Charsets.UTF_8)) {
        if (readLine.isEmpty()) {
            continue
        }
        val split = readLine.split(",")
        if (split.size == 2) {
            emptyAnimalsArray.add(Animals(split[0], split[1].toInt()))
        }
    }
    val animals = ArrayListSample()
    animals.setAnimals(emptyAnimalsArray)
    animals.setAnimals(
        arrayListOf(
            Animals("海豚", 3)
        )
    )
    animals.getAnimalByName("海豚")
}