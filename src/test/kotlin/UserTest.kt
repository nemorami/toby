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
            println(user)
            if(user.level != Level.GOLD)
                assertEquals(user.upgradeLevel().level, it.next())

        }

    }
}