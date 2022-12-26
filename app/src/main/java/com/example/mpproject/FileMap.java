package com.example.mpproject;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class FileMap {
    String path;

    public FileMap(String path) {
        this.path = path;
    }

    public boolean read(Map<String, String> map) {
        try {
            try (CSVReader reader = new CSVReader(new FileReader(path))) {

                String[] nextLine;
                while ((nextLine = reader.readNext()) != null) {
                    if (nextLine.length > 1) {
                        map.put(nextLine[0], nextLine[1]);
                    }
                }
            }
            return true;
        }
        catch (IOException ex) {
            return false;
        }
    }

    public boolean write(Map<String, String> map) {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(path));

            for(String key : map.keySet()) {
                writer.writeNext(new String[] { key, map.get(key) });
            }
            return true;
        }
        catch (IOException ex) {
            return false;
        }
    }
}