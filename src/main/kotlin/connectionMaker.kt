import java.sql.Connection
import java.sql.DriverManager

interface ConnectionMaker {
    fun makeConnection(): Connection
}

class DconnectionMaker : ConnectionMaker {

    override fun makeConnection(): Connection {
        //
        Class.forName("org.postgresql.Driver")
        return DriverManager.getConnection("jdbc:postgresql://localhost/nemorami",
                "nemorami",
                "j5nfants")

    }
}