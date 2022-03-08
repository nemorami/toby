import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.jdbc.datasource.SimpleDriverDataSource
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
open class DaoFactory {
    // comment 내 마크다운 렌더링
    /**
     * https://www.baeldung.com/spring-jdbc-jdbctemplate에서는
     * DriverManagerDataSource사용
     *
     * ````java
     * DriverManagerDataSource dataSource = new DriverManagerDataSource();
     * dataSource.setDriverClassName("com.mysql.jdbc.Driver");
     * dataSource.setUrl("jdbc:mysql://localhost:3306/springjdbc");
     * dataSource.setUsername("guest_user");
     * dataSource.setPassword("guest_password");
     * ````
     *
     */
    @Bean
    open fun dataSource(): DataSource {
        return SimpleDriverDataSource(org.postgresql.Driver(),
            "jdbc:postgresql://localhost/nemorami",
            "nemorami",
            "j5nfants")
    }

    @Bean
    open fun graphDataSource(): DataSource {
        return SimpleDriverDataSource(org.postgresql.Driver(),
            "jdbc:postgresql://172.17.0.2/agens",
            "agens",
            "agens")
    }

    @Bean
    open fun graphDao(): GraphDao{
        return GraphDao(graphDataSource())
    }

    @Bean
    open fun userDao(): UserDao {
        return UserDaoJdbc(dataSource())
    }

    @Bean
    open fun connectionMaker(): ConnectionMaker{
        return DconnectionMaker()
    }

    @Bean
    open fun userService(): UserServiceImpl {
        return UserServiceImpl()
    }

    @Bean
    open fun transactionManager(): PlatformTransactionManager {
        return DataSourceTransactionManager(dataSource())
    }
}