import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.TransactionManager
import org.springframework.transaction.support.DefaultTransactionDefinition
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

class UserServiceTx : UserService {
    lateinit var userService: UserService
    lateinit var transactionManager: PlatformTransactionManager



    override fun add(user: User) {
        userService.add(user)
    }

    override fun upgradeLevels() {
        val status = transactionManager.getTransaction(DefaultTransactionDefinition())
        kotlin.runCatching {
            userService.upgradeLevels()
            transactionManager.commit(status)
        }.onSuccess {

        }.onFailure {
            transactionManager.rollback(status)
            throw it
        }
    }
}

class TransactionHandler(val target: Any, val transactionManager: PlatformTransactionManager) : InvocationHandler {
    override fun invoke(p0: Any?, p1: Method, p2: Array<out Any>?): Any {
        val status = transactionManager.getTransaction(DefaultTransactionDefinition())
        kotlin.runCatching {
            val ret = p1.invoke(target, *(p2 ?: arrayOfNulls(0)))
            transactionManager.commit(status)
            return ret
        }.onFailure {
            transactionManager.rollback(status)
            throw it
        }
        return Unit
    }

}