import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.dao.DataAccessException
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import kotlin.test.assertEquals
import kotlin.test.fail

@SpringJUnitConfig(classes = [DaoFactory::class])
class UserDaoTest {

    @Autowired
    lateinit var dao: UserDao
    lateinit var users: List<User>
    @Autowired
    lateinit var context: ApplicationContext
    @BeforeEach
    fun setUp() {
        //val context = AnnotationConfigApplicationContext(DaoFactory::class.java)
        //    @Autowired
        //    lateinit var context : ApplicationContext
        //dao = context.getBean("userDao", UserDao::class.java)
        users = listOf(
            User("gyumee", "박성철", "springno1", Level.BASIC, 1,0),
            User("leegw700", "이길원", "springno2", Level.SILVER, 55, 10),
            User("bumjin", "박범진", "springno3", Level.GOLD, 100,40)
        )

        println("context => $context");
        println(this)

    }

    private fun checkSameUser(user1: User, user2: User){
        assertEquals(user1.id, user2.id)
        assertEquals(user1.name, user2.name)
        assertEquals(user1.password, user2.password)
        assertEquals(user1.level, user2.level)
        assertEquals(user1.login, user2.login)
        assertEquals(user1.recommend, user2.recommend)
    }
    /**
     * users테이블을 지운후(delete) 유저를 한명씩 add하면서 getCount()가 정상적으로 작동하는지 확인하다.
     */
    @Test
    fun count() {
        // 다중사용자 환경에서는 어떻게 되지?
        dao.deleteAll()
        assertEquals(dao.getCount(), 0)
        users.forEachIndexed { index, user ->
            dao.add(user)
            assertEquals(dao.getCount(), index+1)
        }
    }

    @Test
    fun addAndGet() {
        dao.deleteAll()
        assertEquals(dao.getCount(), 0)
        var count = 0
        users.forEach {
            dao.add(it)
            count += 1
            assertEquals(dao.getCount(), count)
        }
        assertEquals(dao.getCount(), users.size)
//        users.forEach { user ->
//            assertNotNull(dao.get(user.id)) { getuser ->
//                //assertEquals(user, getuser)
//                checkSameUser(user, getuser)
//            }
//        }
    }

    @Test
    fun getAll() {
        dao.getAll()?.forEach {
            checkSameUser(dao.get(it.id)!!, it)
        }
    }

    @Test
    fun dupllicateKey(): Unit {
        dao.deleteAll()

        dao.add(users[0])
        assertThrows<DataAccessException> { dao.add(users[0]) }

    }

    @Test
    fun update() {
        dao.deleteAll()

        dao.add(users[0])

        val user1 =  User(users[0].id, "오민규", "springno6", Level.GOLD, 1000, 999)

        dao.update(user1)
        dao.get(user1.id)?.let {
            checkSameUser(user1, it)
        } ?: fail("")




    }
}