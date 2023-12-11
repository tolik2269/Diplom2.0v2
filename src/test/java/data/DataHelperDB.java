package data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.ObjectUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataHelperDB {
    private static final String url = System.getProperty("db.url");
    private static final String user = System.getProperty("db.user");
    private static final String password = System.getProperty("db.password");

    @SneakyThrows
    public static void clearTables() throws Exception {
        String deleteOrderEntity = "DELETE FROM order_entity WHERE 1=1;";
        String deletePaymentEntity = "DELETE FROM payment_entity WHERE 1=1;";
        String deleteCreditRequestEntity = "DELETE FROM credit_request_entity WHERE 1=1;";
        QueryRunner runner = new QueryRunner();

        Connection conn = DriverManager.getConnection(url, user, password);
            runner.update(conn, deleteOrderEntity);
            runner.update(conn, deletePaymentEntity);
            runner.update(conn, deleteCreditRequestEntity);

    }
    @SneakyThrows
    public static String findPayStatus() throws Exception {
        String statusSQL = "SELECT status FROM payment_entity ORDER BY create_date DESC LIMIT 1;";

            return getData(statusSQL);

    }
    @SneakyThrows
    public static String findCreditStatus() throws Exception {
        String statusSQL = "SELECT status FROM credit_request_entity ORDER BY create_date DESC LIMIT 1;";

            return getData(statusSQL);

    }

    @SneakyThrows
    private static String getData(String query) throws Exception {
        QueryRunner runner = new QueryRunner();
        String data = "";
        Connection conn = DriverManager.getConnection(url, user, password);
        data = runner.query(conn, query, new ScalarHandler<>());

        return data;
    }

    @SneakyThrows
    public static long getOrderEntityCount() throws Exception {
        String countSQL = "SELECT COUNT(*) FROM order_entity;";
        Connection conn = DriverManager.getConnection(url, user, password);
            QueryRunner runner = new QueryRunner();
            Long count = runner.query(conn, countSQL, new ScalarHandler<>());
            return count;
        }
}

