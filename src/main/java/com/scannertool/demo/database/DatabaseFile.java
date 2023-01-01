package com.scannertool.demo.database;

import com.scannertool.demo.Repository.DataRepository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DatabaseFile {

    private String path;

    public DatabaseFile() {

    }

    public String getPath() {
        String home = System.getProperty("user.home");

        return home+"/Downloads/"  + "scannerdata.txt";
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void exportCSV(DataRepository dataRepository) throws IOException {
        String home = System.getProperty("user.home");
        File file = new File(this.getPath());
        FileWriter fileWriter = new FileWriter(file);
        for (Data data : dataRepository.findAll()) {
            fileWriter.write(data.getStation() + "," + data.getMac() + "\n");
        }
        fileWriter.close();

    }

}
