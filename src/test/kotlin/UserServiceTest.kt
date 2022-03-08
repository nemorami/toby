import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig


/**
 * User service test
 *
 * @constructor Create empty User service test
 */
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

    /**
     * Upgrade levels
     *
     */
    @Test
   fun upgradeLevels() {
//        userDao.deleteAll()
//        users.forEach {
//            userService.add(it)
//        }
//        userService.upgradeLevels()
//        assertEquals(userDao.get("bumjin")?.level, Level.BASIC)
//        assertEquals(userDao.get("joytouch")?.level, Level.SILVER)
//        assertEquals(userDao.get("erwins")?.level, Level.SILVER)
//        assertEquals(userDao.get("madnite1")?.level, Level.GOLD)
//        assertEquals(userDao.get("green")?.level, Level.GOLD)
        var userServiceImpl = UserServiceImpl()
        var mockUserDao = MockUserDao(users)
        userServiceImpl.userDao = mockUserDao

        userServiceImpl.upgradeLevels()

        with(mockUserDao.updated){
            assertEquals(this.size, 2)
            assertEquals(this[0]?.level, Level.SILVER )
            assertEquals(this[1]?.level, Level.GOLD )
        }
    }

    @Test
    fun add() {
       userDao.deleteAll()
       users.forEach {
           userService.add(it)
       }
    }

    @Test
    fun mockUpgradeLevels() {
        val userServiceImpl = UserServiceImpl()
        val mockUserDao = mock<UserDao>{
            on {getAll()} doReturn users
        }

        userServiceImpl.userDao = mockUserDao

        userServiceImpl.upgradeLevels()
        verify(mockUserDao, times(2)).update(any<User>())
    }
}


