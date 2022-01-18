import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import java.sql.*
import javax.sql.DataSource

interface UserDao {
    /**
     * Add
     *
     * @param user
     */
    fun add(user: User)

    /**
     * Get
     *
     * @param id id로 User를 조회
     * @return id에 해당하는 user
     */
    fun get(id: String): User?
    fun getAll(): List<User>
    fun deleteAll()
    fun getCount(): Int?
    fun update(user: User)

}

class UserDaoJdbc(val dataSource: DataSource) : UserDao {

    val jdbcTemplate = JdbcTemplate(dataSource)

    val mapper = RowMapper<User> {rs, rowId ->
        User(
            rs.getString("id"),
            rs.getString("name"),
            rs.getString("password"),
            rs.getInt("level").toLevel(),
            rs.getInt("login"),
            rs.getInt("recommend"),
            rs.getString("email")
        )
    }
    /**
     * [query]를 실행한다.
     *
     * @param query 실행할 쿼리
     * @return 쿼리 실행 결과를 반환합니다.     */
    private fun runQuery(query: String) : ResultSet? {
        runCatching {
            (dataSource.connection.prepareStatement(query)).use {
                if (it.execute())
                    return it.executeQuery()
            }
        }.onFailure {
            //예외처리
        }
        return null
    }

    /**
     * Run query
     *
     * @param stmt PreparedStatement
     * @receiver
     * @return ᅟResultSet
     */
    private fun runQuery(stmt: (Connection)->PreparedStatement): ResultSet? {
        stmt(dataSource.connection).use {
            if (it.execute())
                return it.executeQuery()
        }
        return null
    }
    /**
     * Add
     *
     * @param user
     */
    override fun add(user: User) {
        //   runQuery("insert into users(id, name, password) values('${user.id}', '${user.name}', '${user.password}')")
//        runQuery {
//            with(it.prepareStatement("insert into users(id, name, password) values(?, ?, ?)")) {
//                setString(1, user.id)
//                setString(2, user.name)
//                setString(3, user.password)
//                this
//            }
//        }
        jdbcTemplate.update(
            "insert into users(id, name, password, level, login, recommend, email) values(?, ?, ?, ?, ?, ?, ?)",
            user.id,
            user.name,
            user.password,
            user.level.level,
            user.login,
            user.recommend,
            user.email
        )

    }

    /**
     * Get
     *
     * @param id id로 User를 조회
     * @return id에 해당하는 user
     */
    override fun get(id: String): User? {
        return runQuery{
            with(it.prepareStatement("select * from users where id = ?")) {
                setString(1, id)
                this
            }
        }?.run{
            next()
            User(getString("id"), getString("name"), getString("password"),
                getInt("level").toLevel() ?: Level.BASIC, getInt("login"),
                getInt("recommend"), getString("email"))
        }
    }

    override fun getAll(): List<User> {
        return jdbcTemplate.query("select * from users", mapper)
    }

    override fun deleteAll(){
        runQuery {
            it.prepareStatement("delete from users")
        }
    }

    override fun getCount(): Int?{
         return runQuery{it.prepareStatement("select count(*) from users")}?.run {
            next()
            getInt(1)
        }
    }

    override fun update(user: User) {
        jdbcTemplate.update("update users set name = ?, password = ?, level = ?, login = ?, recommend = ?, email = ? where id = ?",
        user.name, user.password, user.level.level, user.login, user.recommend, user.email, user.id)
    }
}
