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
class UserServiceImpl (val userDao: UserDao) : UserService{

    //@Autowired
    //lateinit var dataSource: DataSource
    @Autowired
    lateinit var transactionManager: PlatformTransactionManager

    override fun add(user: User) {
        TODO("Not yet implemented")
    }

    /**
     * Upgrade levels
     *
     */
    override fun upgradeLevels(): Unit {
//        TransactionSynchronizationManager.initSynchronization();
//        val c = DataSourceUtils.getConnection(dataSource)
//        c.autoCommit = false
//        kotlin.runCatching {
//            userDao.getAll().forEach {
//                if(canUpgradeLevel(it))
//                    upgradeLevel(it)
//            }
//            c.commit()
//        }.onSuccess {
//
//        }.onFailure {
//            c.rollback()
//            throw it
//        }.also {
//            DataSourceUtils.releaseConnection(c, dataSource)
//            TransactionSynchronizationManager.unbindResource(dataSource)
//            TransactionSynchronizationManager.clearSynchronization()
//        }
        //val transactionManager = DataSourceTransactionManager(dataSource)
        val status = transactionManager.getTransaction(DefaultTransactionDefinition())
        kotlin.runCatching {
            userDao.getAll().forEach {
                if(canUpgradeLevel(it))
                    upgradeLevel(it)
            }
            transactionManager.commit(status)
        }.onSuccess {

        }.onFailure {
            transactionManager.rollback(status)
            throw it
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