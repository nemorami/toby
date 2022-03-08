import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.DefaultTransactionDefinition
//import org.springframework.mail.javamail.JavaMailSenderImpl

/**
 * User service
 *
 * @property userDao
 * @constructor Create empty User service
 */
class UserServiceImpl  : UserService{

    //@Autowired
    //lateinit var dataSource: DataSource
    @Autowired
    lateinit var transactionManager: PlatformTransactionManager
    @Autowired
    lateinit var userDao: UserDao

    override fun add(user: User) {
        userDao.add(user)
    }

    /**
     * Upgrade levels
     *
     */
    override fun upgradeLevels(): Unit {
        userDao.getAll().forEach {
            if (canUpgradeLevel(it))
                upgradeLevel(it)
        }

    }

    fun canUpgradeLevel(user: User) =
        when(user.level) {
            Level.BASIC ->  user.login >= 50
            Level.SILVER -> user.recommend >= 30
            Level.GOLD -> false
            else -> throw IllegalArgumentException("Unknown Level: ${user.level}")
        }

    fun upgradeLevel(user: User) {
       user.upgradeLevel()
       userDao.update(user)
     //  sendUpgradeEmail(user)
    }
//    private fun sendUpgradeEmail(user: User){
//        val mailSender = JavaMailSenderImpl()
//        mailSender
//        val props = Properties()
//        props.put("", "")
//        val s = Session.getInstance(props, null)
//    }
}