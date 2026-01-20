package com.singsin.studio.array


data class Animals(val name: String, val no: Int)

class ArrayListSample {

    private var animalsCache: MutableList<Animals> = mutableListOf()

    fun setAnimals(animals: List<Animals>) {
        val finaleAddToCache = arrayListOf<Animals>()
        finaleAddToCache.addAll(animalsCache)
        animals.forEach { e ->
            val checkAlreadyAddAnimals: Boolean = checkAlreadyAddAnimals(e)
            if (!checkAlreadyAddAnimals) {
                finaleAddToCache.add(e)
            } else {
                println("动物名为「${e.name}」将不能进入...")
            }
        }
        this.animalsCache = finaleAddToCache
    }

    private fun checkAlreadyAddAnimals(animal: Animals): Boolean {
        var isFind = false
        for ((index, animalByCache) in animalsCache.withIndex()) {
            println("当前检查索引位置：${index},Animal.Name=${animalByCache.name},Animal.No=${animalByCache.no}")
            if (animalByCache.name.equals(animal.name, true)) {
                isFind = true;
                println("我在检查索引位置=${index} 的时候找到了相同的动物名称${animalByCache.name}")
            }
            if (animalByCache.no == animal.no) {
                println("我在检查索引位置=${index} 的时候找到了相同的动物编号${animalByCache.no}")
                isFind = true;
            }
        }
        return isFind
    }

    fun getAnimals(): List<Animals> {
        return this.animalsCache
    }

    fun getAnimalByName(name: String): Result<Animals> {
        var findAnimal: Animals? = null
        var isFind = false
        for ((index, animal) in animalsCache.withIndex()) {
            if (animal.name.equals(name, ignoreCase = true)) {
                findAnimal = animal;
                println("我在INDEX=${index} 的位置找到了动物${animal.name}")
                isFind = true
            }
            if (isFind) {
                break
            }
        }
        if (isFind) {
            return Result.success(findAnimal!!)
        }
        return Result.failure(Exception("「${name}」现在不在动物园，可能外出了..."))
    }

}

fun main() {
    val animalsSample = ArrayListSample()
    try {
        val animalByNameResult = animalsSample.getAnimalByName("Panda")
        if (animalByNameResult.isSuccess) {
            println("Result:${animalByNameResult.isSuccess} Name:${animalByNameResult.getOrNull()?.name}")
        }
    } catch (e: Exception) {
        println(e.message)
    }
    Thread.sleep(1000)
    animalsSample.setAnimals(
        arrayListOf(
            Animals("功夫熊猫", 1),
            Animals("小猪", 2),
            Animals("大象", 3),
        )
    )
    animalsSample.setAnimals(
        arrayListOf(
            Animals("大象", 3)
        )
    )
    try {
        val animalByNameResult = animalsSample.getAnimalByName("大象")
        if (animalByNameResult.isSuccess) {
            println("Result:${animalByNameResult.isSuccess} Name:${animalByNameResult.getOrNull()?.name}")
        }
    } catch (e: Exception) {
        println(e.message)
    }
    animalsSample.getAnimals().forEach(::println)
}