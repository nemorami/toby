import org.springframework.jdbc.core.JdbcTemplate
import javax.sql.DataSource

class GraphDao(val dataSource: DataSource) {
    val jdbcTemplate = JdbcTemplate(dataSource)

//    fun <T>query(query: String): List<T> {
//        return jdbcTemplate.query
//    }


}