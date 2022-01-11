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
            var changed = false
            when(it.level) {
                Level.BASIC -> {
                    if(it.login >= 50) it.level = Level.SILVER
                    changed = true
                }
                Level.SILVER -> {
                    if(it.recommend >= 30) it.level = Level.GOLD
                    changed = true
                }
            }
            if(changed) {userDao.update(it)}
        }
    }


}