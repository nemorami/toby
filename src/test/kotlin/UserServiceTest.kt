import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig


@SpringJUnitConfig(classes = [DaoFactory::class])
internal class UserServiceTest {
    @Autowired
    lateinit var userService: UserService
    @Autowired
    lateinit var userDao: UserDao
    var users = listOf<User>(
        User("bumjin", "박범진", "p1", Level.BASIC, 49, 0),
        User("joytouch", "강명성", "p2", Level.BASIC, 50, 0),
        User("erwins", "신승한", "p3", Level.SILVER, 60, 29),
        User("madnite1", "이상호", "p4", Level.SILVER, 60, 30),
        User("green", "오민규", "p5", Level.GOLD, 100, 100),
    )

    @Test
   fun upgradeLevels() {
        userDao.deleteAll()
        users.forEach {
            userDao.add(it)
        }
        userService.upgradeLevels()

        assertEquals(userDao.get("bumjin")?.level, Level.BASIC)
        assertEquals(userDao.get("joytouch")?.level, Level.SILVER)
        assertEquals(userDao.get("erwins")?.level, Level.SILVER)
        assertEquals(userDao.get("madnite1")?.level, Level.GOLD)
        assertEquals(userDao.get("green")?.level, Level.GOLD)

    }
}


