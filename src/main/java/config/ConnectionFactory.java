package config;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;

/**
 * Fábrica centralizada de conexões JDBC baseada em DataSource.
 */
public final class ConnectionFactory {

    private static final String DEFAULT_URL =
            "jdbc:mysql://localhost:3306/unibiblio?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String DEFAULT_USER = "root";
    private static final String DEFAULT_PASSWORD = "";

    private static volatile DataSource dataSource;

    private ConnectionFactory() {
    }

    /**
     * Retorna o {@link DataSource} configurado para a aplicação.
     */
    public static DataSource getDataSource() {
        if (dataSource == null) {
            synchronized (ConnectionFactory.class) {
                if (dataSource == null) {
                    dataSource = criarDataSource();
                }
            }
        }
        return dataSource;
    }

    /**
     * Permite injetar um {@link DataSource} customizado (ex.: em testes).
     */
    public static void setDataSource(DataSource customDataSource) {
        synchronized (ConnectionFactory.class) {
            dataSource = customDataSource;
        }
    }

    /**
     * Obtém uma nova conexão JDBC a partir do {@link DataSource}.
     */
    public static Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }

    private static DataSource criarDataSource() {
        Properties props = carregarProperties();

        String url = primeiroNaoVazio(
                props.getProperty("db.url"),
                props.getProperty("url"),
                System.getenv("DB_URL"),
                DEFAULT_URL);
        String user = primeiroNaoVazio(
                props.getProperty("db.user"),
                props.getProperty("user"),
                System.getenv("DB_USER"),
                DEFAULT_USER);
        String password = primeiroNaoNull(
                props.getProperty("db.password"),
                props.getProperty("password"),
                System.getenv("DB_PASSWORD"),
                DEFAULT_PASSWORD);

        MysqlDataSource mysqlDs = new MysqlDataSource();
        mysqlDs.setURL(url);
        mysqlDs.setUser(user);
        mysqlDs.setPassword(password);
        return mysqlDs;
    }

    private static Properties carregarProperties() {
        Properties props = new Properties();
        try (InputStream is = ConnectionFactory.class.getResourceAsStream("/db.properties")) {
            if (is != null) {
                props.load(is);
            }
        } catch (IOException ignored) {
            // Mantém fallback para variáveis de ambiente e valores padrão.
        }
        return props;
    }

    private static String primeiroNaoVazio(String... valores) {
        for (String valor : valores) {
            if (valor != null && !valor.isBlank()) {
                return valor;
            }
        }
        return "";
    }

    private static String primeiroNaoNull(String... valores) {
        for (String valor : valores) {
            if (valor != null) {
                return valor;
            }
        }
        return "";
    }
}

