package by.bsuir.client.txtworker.impl;

import by.bsuir.client.exception.ReadDataException;
import by.bsuir.client.exception.WriteDataException;
import by.bsuir.client.txtworker.TxtWorker;
import by.bsuir.client.validator.FileValidator;
import by.bsuir.client.validator.impl.FileValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class TxtWorkerImpl implements TxtWorker {
    private static final Logger LOGGER = LogManager.getLogger(TxtWorker.class);

    private static final TxtWorkerImpl INSTANCE = new TxtWorkerImpl();

    private TxtWorkerImpl() {}

    public static TxtWorkerImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public List<String> readDataTxt(String path) throws ReadDataException {
        File file = new File(path);
        FileValidator fileValidator = FileValidatorImpl.getInstance();
        boolean resultValidator = fileValidator.checkFile(file);
        if (!resultValidator) {
            LOGGER.warn("File:" + path + "  is not exist or empty");
            throw new ReadDataException("File is not exist or empty - " + path);
        }
        try {
            List<String> allLines = Files.readAllLines(file.toPath());
            LOGGER.info("File:" + file + " was successfully read");
            return allLines;
        } catch (IOException e) {
            LOGGER.warn("File:" + path + ". Exception with readAllLines method");
            throw new ReadDataException(e);
        }
    }

    @Override
    public void addDataTxt(String path, String record) throws WriteDataException {
        File file = new File(path);
        try {
            FileWriter fooWriter = new FileWriter(file, true); //true to append, false to overwrite
            fooWriter.write(record + "\n");
            fooWriter.close();

        } catch (IOException e) {
            LOGGER.warn("File:" + path + ". Exception with add record");
            throw new WriteDataException(e);
        }
    }

    @Override
    public void rewriteDataTxt(String path, List<String> records) throws WriteDataException {
        File file = new File(path);
        try {
            FileWriter fooWriter = new FileWriter(file, false); //true to append, false to overwrite
            for(String record : records){
                fooWriter.write(record + "\n");
            }
            fooWriter.close();

        } catch (IOException e) {
            LOGGER.warn("File:" + path + ". Exception with rewrite data");
            throw new WriteDataException(e);
        }
    }

    @Override
    public void createOtchetTxt(String path, List<String> lines) throws IOException {
        Path file = Paths.get(path);
        Files.write(file, lines, Charset.forName("UTF-8"));
    }

}
