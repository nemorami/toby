import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.datasource.DataSourceUtils
import org.springframework.transaction.support.TransactionSynchronizationManager
import javax.sql.DataSource

/**
 * User service
 *
 * @property userDao
 * @constructor Create empty User service
 */
class UserService (val userDao: UserDao){

    @Autowired
    lateinit var dataSource: DataSource
    /**
     * Upgrade levels
     *
     */
    fun upgradeLevels(): Unit {
        TransactionSynchronizationManager.initSynchronization();
        val c = DataSourceUtils.getConnection(dataSource)
        c.autoCommit = false
        kotlin.runCatching {
            userDao.getAll().forEach {
                if(canUpgradeLevel(it))
                    upgradeLevel(it)
            }
            c.commit()
        }.onSuccess {

        }.onFailure {
            c.rollback()
            throw it
        }.also {
            DataSourceUtils.releaseConnection(c, dataSource)
            TransactionSynchronizationManager.unbindResource(dataSource)
            TransactionSynchronizationManager.clearSynchronization()
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
    }
}