package com.example.timeline

data class User(
    val name: String,
    val age: Int
) {
    companion object {
        val userList = listOf(
            User("Random0", 0),
            User("Random1", 1),
            User("Random2", 2),
            User("Random3", 3),
            User("Random4", 4),
            User("Random5", 5),
            User("Random6", 6),
            User("Random7", 7),
            User("Random8", 8),
            User("Random9", 9),
            User("Random10", 10),
            User("Random10", 10),
            User("Random11", 11),
            User("Random12", 12),
            User("Random13", 13),
            User("Random14", 14),
            User("Random15", 15),
        )
    }
}