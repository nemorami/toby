import org.junit.jupiter.api.Test
import java.lang.reflect.Proxy
import kotlin.test.assertEquals

internal class HelloTargetTest {

    @Test
    fun simpleProxy() {
        val hello = HelloTarget()
        assertEquals(hello.sayHello("Toby"), "Hello Toby")
        assertEquals(hello.sayHi("Toby"), "Hi Toby")
        assertEquals(hello.sayThankYou("Toby"), "Thank You Toby")
    }

    @Test
    fun dynamicProxy() {
        val proxiedHello = Proxy.newProxyInstance(Hello::class.java.classLoader, arrayOf<Class<*>>(Hello::class.java), UppercaseHandler(HelloTarget())) as Hello

        //val proxiedHello = Proxy.newProxyInstance(Hello::class.java.classLoader, arrayOf(Hello::class.java), UppercaseHandler(HelloTarget())) as Hello
        assertEquals(proxiedHello.sayHello("Toby"), "HELLO TOBY")
        assertEquals(proxiedHello.sayHi("Toby"), "HI TOBY")
        assertEquals(proxiedHello.sayThankYou("Toby"), "THANK YOU TOBY")
    }
}

