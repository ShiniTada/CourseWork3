package by.bsuir.client.txtworker;


import by.bsuir.client.exception.ReadDataException;
import by.bsuir.client.exception.WriteDataException;

import java.io.IOException;
import java.util.List;

public interface TxtWorker {
     List<String> readDataTxt(String path) throws ReadDataException;

     void addDataTxt(String path, String record) throws WriteDataException;

     void rewriteDataTxt(String path, List<String> records) throws WriteDataException;

     void createOtchetTxt(String path, List<String> lines) throws IOException;
}
