package com.poltavets;

import org.json.JSONObject;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class InstitutionDao {

    protected static Collection<String> stringsOfJson = new ArrayList<>();
    // URL-адрес подключения к базе данных,
    // используя режим сервера TCP / IP (удаленное подключение)
    private static final String JDBC_URL = "jdbc:h2:~/test";
    // Имя пользователя, используемое при подключении к базе данных
    private static final String USER = "sa";
    // Пароль, используемый при подключении к базе данных
    private static final String PASSWORD = "";
    // Класс драйвера, используемый при подключении к базе данных H2.
    // Класс org.h2.Driver предоставляется самой базой данных H2 и
    // может быть найден в пакете jar базы данных H2
    private static final String DRIVER_CLASS = "org.h2.Driver";
    private Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);

    public InstitutionDao() throws SQLException {
    }

    public void writeToDateBase(List<Institution> institutions) throws ClassNotFoundException, SQLException {
        // Загружаем драйвер базы данных H2
        Class.forName(DRIVER_CLASS);
        // Получение подключения к базе данных в соответствии с URL-адресом подключения,
        // именем пользователя и паролем
        try (Statement stmt = conn.createStatement()) {
            // Создать тестовую таблицу
            stmt.execute("DROP TABLE IF EXISTS TEST");
            stmt.execute("CREATE TABLE TEST(" +
                    "STATE VARCHAR(20), " +
                    "NAME VARCHAR(100), " +
                    "TYPE VARCHAR(30)," +
                    "PHONENUMBER VARCHAR(50)," +
                    "WEBSITE VARCHAR(255))");
            //Добавлять
            Iterator<Institution> iterator = institutions.iterator();
            while (iterator.hasNext()) {
                Institution institution = iterator.next();
                final String sql = "INSERT INTO TEST (" +
                        "state, name, type, phoneNumber, website) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement statement = conn.prepareStatement(sql)) {
                    statement.setString(1, institution.getState());
                    statement.setString(2, institution.getName());
                    statement.setString(3, institution.getType());
                    statement.setString(4, institution.getPhoneNumber());
                    statement.setString(5, institution.getWebsite());
                    statement.executeUpdate();
                }
            }
        }
    }

    public void getAll() throws SQLException, FileNotFoundException {
        List<Institution> institutions = new ArrayList<>();
        try (Statement statement = conn.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT * FROM TEST");
            while (rs.next()) {
                Institution institution = createInstitutionFromDB(rs);
                institutions.add(institution);
                String lineJson = converterToJson(institution);
                    System.out.println(lineJson);
                    stringsOfJson.add(lineJson);
            }
            //Закрываем соединение
            conn.close();
        }
        System.out.println("\n Все загруженные данные в Json формате выводятся в : \n" +
                " 1 - Консоль \n" +
                " 2 - Файл output.txt \n" +
                " 3 - По ссылке http://localhost:8080/controller ");
        writeToFile(stringsOfJson);
    }

    private Institution createInstitutionFromDB(ResultSet cursor) throws SQLException {
        Institution institution = new Institution();
        institution.setState(cursor.getString("state"));
        institution.setName(cursor.getString("name"));
        institution.setType(cursor.getString("type"));
        institution.setPhoneNumber(cursor.getString("phoneNumber"));
        institution.setWebsite(cursor.getString("website"));
        return institution;
    }

    private String converterToJson(Institution institution) {
        JSONObject resultJson = new JSONObject();
        resultJson.put("state", institution.getState());
        resultJson.put("name", institution.getName());
        resultJson.put("type", institution.getType());
        resultJson.put("phoneNumber", institution.getPhoneNumber());
        resultJson.put("website", institution.getWebsite());
        return resultJson.toString();
    }

    public void writeToFile(Collection<String> stringsOfJson) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(
                new FileOutputStream("output.txt"))) {
            writer.println(stringsOfJson);
        }
    }
}
