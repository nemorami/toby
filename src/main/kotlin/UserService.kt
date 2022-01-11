/**
 * User service
 *
 * @property userDao
 * @constructor Create empty User service
 */
class UserService (val userDao: UserDao){

    /**
     * Upgrade levels
     *
     */
    fun upgradeLevels(): Unit {
        userDao.getAll()?.forEach {
            if(canUpgradeLevel(it))
                upgradeLevel(it)
//            var changed = false
//            when(it.level) {
//                Level.BASIC -> {
//                    if(it.login >= 50) it.level = Level.SILVER
//                    changed = true
//                }
//                Level.SILVER -> {
//                    if(it.recommend >= 30) it.level = Level.GOLD
//                    changed = true
//                }
//            }
//            if(changed) {userDao.update(it)}
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
       user.level.inc()
       userDao.update(user)
    }
}