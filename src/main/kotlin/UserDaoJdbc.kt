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
        //check: $id에 ?를 썻을때는?
        return jdbcTemplate.query("select * from users where id = '$id'",mapper)[0]
    }

    override fun getAll(): List<User> {
        return jdbcTemplate.query("select * from users", mapper)
    }

    override fun deleteAll(){

            jdbcTemplate.update("delete from users")

    }

    override fun getCount(): Int?{
         return jdbcTemplate.queryForObject("select count(*) from users", Int::class.java)
    }

    override fun update(user: User) {
        jdbcTemplate.update("update users set name = ?, password = ?, level = ?, login = ?, recommend = ?, email = ? where id = ?",
        user.name, user.password, user.level.level, user.login, user.recommend, user.email, user.id)
    }
}

class MockUserDao(val users: List<User>) : UserDao {
    //check: updated가 null을 가질수 없게 선언하면...
    var updated: MutableList<User?> = mutableListOf()


    /**
     * Get
     *
     * @param id id로 User를 조회
     * @return id에 해당하는 user
     */
    override fun get(id: String): User? {
        TODO("Not yet implemented")
    }

    /**
     * Add
     *
     * @param user
     */
    override fun add(user: User) {
        TODO("Not yet implemented")
    }

    override fun getAll(): List<User> {
        return users
    }

    override fun deleteAll() {
        TODO("Not yet implemented")
    }

    override fun getCount(): Int? {
        TODO("Not yet implemented")
    }

    override fun update(user: User) {
            updated.add(user)

    }

}
