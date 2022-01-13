/**
 * User
 *
 * @property id
 * @property name
 * @property password
 * @property level
 * @property login
 * @property recommend
 * @constructor Create empty User
 */
data class User(var id: String, var name: String, var password: String,
                var level: Level, var login: Int, var recommend: Int) {

    /**
     * Upgrade level
     * @return this 자신을 리턴한다. method chain을 사용할 수 있게...
     * 사용자의 level을 한단계 상향시킨다.
     * 이미 최고 level일 경우 IllegalStateException에러를 던진다.
     */
    fun upgradeLevel() : User{
        println(level)
        if(level == Level.GOLD)
            throw IllegalStateException("${level}은 업그레이드가 불가능합니다.")
        else
            level.inc()
        println("after inc: $level")
        return this
    }

}

/**
 * Level
 *
 * @property level
 * @constructor Create empty Level
 */
enum class Level(var level: Int) {

    BASIC(1),
    SILVER(2),
    GOLD(3);

//    //todo getter로 변경
    fun next(): Level{
        var l = level + 1
        if( l < 0 || l > 3)
            throw IllegalStateException("${l.toLevel()} doesn't have next level.")
        return l.toLevel()
    }
    /**
     * Inc
     *
     */
    fun inc() {
        level = level + 1
        if (level > 3) level = 3
    }

}

/**
 * To level
 *
 * @return
 */
fun Int.toLevel(): Level {
    when(this%4) {
        1 ->  return Level.BASIC
        2 -> return Level.SILVER
        3 -> return Level.GOLD
        else -> return Level.BASIC
    }
}



