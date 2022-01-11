data class User(var id: String, var name: String, var password: String,
                var level: Level, var login: Int, var recommend: Int)

enum class Level(val level: Int) {
    BASIC(1), SILVER(2), GOLD(3);

}

fun Int.toLevel(): Level {
    when(this%4) {
        1 ->  return Level.BASIC
        2 -> return Level.SILVER
        3 -> return Level.GOLD
        else -> return Level.BASIC
    }
}