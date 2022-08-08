package com.poltavets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ApplicationMain {

    public static void main(String[] args) throws SQLException, ClassNotFoundException, FileNotFoundException {
        ApplicationMain main = new ApplicationMain();
        main.run();
        SpringApplication.run(ApplicationMain.class, args);
    }

    private void run() throws SQLException, ClassNotFoundException, FileNotFoundException {
        List<Institution> institutions = new ArrayList<>();
        ReadFile read = new ReadFile();
        institutions = read.readFile(institutions);
        InstitutionDao institutionDao = new InstitutionDao();
        institutionDao.writeToDateBase(institutions);
        institutions.clear();
        institutionDao.getAll();
    }
}
