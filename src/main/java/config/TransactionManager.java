package config;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Gerenciador programático de transações JDBC utilizando {@link ConnectionFactory}.
 */
public final class TransactionManager {

    private TransactionManager() {
    }

    @FunctionalInterface
    public interface TrabalhoTransacional<T> {
        T executar(Connection conn) throws SQLException;
    }

    @FunctionalInterface
    public interface TrabalhoSemRetorno {
        void executar(Connection conn) throws SQLException;
    }

    /**
     * Executa uma unidade de trabalho dentro de uma transação JDBC com retorno.
     */
    public static <T> T executarEmTransacao(TrabalhoTransacional<T> trabalho) {
        Objects.requireNonNull(trabalho, "O trabalho transacional não pode ser nulo.");

        try (Connection conn = ConnectionFactory.getConnection()) {
            boolean autoCommitOriginal = conn.getAutoCommit();
            conn.setAutoCommit(false);

            try {
                T resultado = trabalho.executar(conn);
                conn.commit();
                return resultado;
            } catch (Throwable ex) {
                fazerRollbackSeguro(conn, ex);
                if (ex instanceof RuntimeException runtimeEx) {
                    throw runtimeEx;
                }
                if (ex instanceof Error err) {
                    throw err;
                }
                throw new RuntimeException("Erro ao executar operação em transação.", ex);
            } finally {
                restaurarAutoCommitSeguro(conn, autoCommitOriginal);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao obter ou encerrar conexão transacional.", e);
        }
    }

    /**
     * Executa uma unidade de trabalho dentro de uma transação JDBC sem retorno.
     */
    public static void executarSemRetorno(TrabalhoSemRetorno trabalho) {
        Objects.requireNonNull(trabalho, "O trabalho transacional não pode ser nulo.");
        executarEmTransacao(conn -> {
            trabalho.executar(conn);
            return null;
        });
    }

    private static void fazerRollbackSeguro(Connection conn, Throwable causaOriginal) {
        try {
            conn.rollback();
        } catch (SQLException rollbackEx) {
            causaOriginal.addSuppressed(rollbackEx);
        }
    }

    private static void restaurarAutoCommitSeguro(Connection conn, boolean autoCommitOriginal) {
        try {
            if (!conn.isClosed()) {
                conn.setAutoCommit(autoCommitOriginal);
            }
        } catch (SQLException ignored) {
            // Conexão será fechada logo em seguida pelo try-with-resources.
        }
    }
}

