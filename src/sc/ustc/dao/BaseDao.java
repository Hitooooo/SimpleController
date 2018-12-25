package sc.ustc.dao;

import sc.ustc.helpers.ScHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author HitoM
 * @date 2018/12/25 15:45
 */
public abstract class BaseDao {

    protected String driver;

    protected String url;

    protected String userName;

    protected String userPassword;

    private Connection connection;

    public Connection openDBConnection() {
        if (connection == null) {
            try {
                if (ScHelper.isEmpty(driver) || ScHelper.isEmpty(url) ||
                        ScHelper.isEmpty(userName) || ScHelper.isEmpty(userPassword)) {
                    System.out.println("miss necessary config");
                    return null;
                }
                Class.forName(driver);
                //1.getConnection()方法，连接MySQL数据库！！
                connection = DriverManager.getConnection(url, userName, userPassword);
                if (!connection.isClosed()) {
                    System.out.println("Succeeded connecting to the Database!");
                }
            } catch (ClassNotFoundException e) {
                System.out.println("Sorry,can`t find the Driver!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public boolean closeDBConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public abstract Object query(String sql);

    public abstract boolean insert(String sql);

    public abstract boolean update(String sql);

    public abstract boolean delete(String sql);

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
