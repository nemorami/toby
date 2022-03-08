import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

interface Hello {
    fun sayHello(name: String): String
    fun sayHi(name: String): String
    fun sayThankYou(name: String): String
}

class HelloTarget : Hello {
    override fun sayHello(name: String): String{
        return ("Hello " + name)
    }

    override fun sayHi(name: String): String{
        return ("Hi " + name)
    }

    override fun sayThankYou(name: String): String {
        return "Thank You " + name
    }

}

class HelloUppercase(val hello: Hello) : Hello {

    override fun sayHello(name: String): String {
        return hello.sayHello(name).uppercase()
    }

    override fun sayHi(name: String): String {
        return hello.sayHi(name).uppercase()
    }

    override fun sayThankYou(name: String): String {
        return hello.sayThankYou(name).uppercase()
    }

}

class UppercaseHandler(private val target: Any) : InvocationHandler {
    override fun invoke(p0: Any?, p1: Method?, p2: Array<out Any>?): Any {
        val ret =  p1?.invoke(target, *(p2?:arrayOfNulls(0))) as String
        return ret.uppercase()
    }

}

