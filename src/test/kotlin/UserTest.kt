import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class UserTest {
    var user = User("bumjin", "박범진", "p1", Level.BASIC, 49, 0)
    @Test
    fun upgradeLevel() {
        val levels = Level.values()

        levels.forEach {
            user.level = it
            println(it)
            kotlin.runCatching {
                println(user.level.next())
            }.onFailure {
                println("gold는 다음 level이 없습니다.")
            }


        }

    }
}