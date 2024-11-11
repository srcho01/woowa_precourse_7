package store.service;

import store.util.Constant;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataReader {

    public List<String> promotionReader() {
        return dataReader(Constant.PROMOTION_FILE_PATH);
    }

    public List<String> productReader() {
        return dataReader(Constant.PRODUCT_FILE_PATH);
    }

    private List<String> dataReader(String filePath) {
        List<String> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.readLine();  // 헤더 건너뛰기
            readData(reader, data);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return data;
    }

    private void readData(BufferedReader reader, List<String> data)
            throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            data.add(line);
        }
    }

}
