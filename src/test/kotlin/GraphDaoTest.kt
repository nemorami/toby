import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.context.annotation.AnnotationConfigApplicationContext

internal class GraphDaoTest {
    val context = AnnotationConfigApplicationContext(DaoFactory::class.java)
    //    @Autowired
//    lateinit var context : ApplicationContext
    val dao = context.getBean("graphDao", GraphDao::class.java)
    @Test
    fun getJdbcTemplate() {
        val count = dao.jdbcTemplate.queryForList("select count(*) from products")
        println(count)

    }
}