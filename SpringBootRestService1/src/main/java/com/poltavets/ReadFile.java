package com.poltavets;

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class ReadFile {

    public List<Institution> readFile(List<Institution> institutions) {
        try {
            CSVReader reader = new CSVReader(
                    new FileReader("institutionsOfUSA.csv"), ';', '\t' , 1);
            List<String[]> records = reader.readAll();
            Iterator<String[]> iterator = records.iterator();
            while (iterator.hasNext()) {
                String[] record = iterator.next();
                Institution institution = new Institution();
                institution.setState(record[0]);
                institution.setName(record[1]);
                institution.setType(record[2]);
                institution.setPhoneNumber(record[3]);
                institution.setWebsite(record[4]);

                institutions.add(institution);
            }
        } catch (IOException e) {
            System.out.println("Error input");
        }
        return institutions;
    }
}
