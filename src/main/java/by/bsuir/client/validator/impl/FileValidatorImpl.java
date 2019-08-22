package by.bsuir.client.validator.impl;

import by.bsuir.client.validator.FileValidator;

import java.io.File;

public class FileValidatorImpl implements FileValidator {


    private final static FileValidatorImpl INSTANCE = new FileValidatorImpl();


    private FileValidatorImpl() {
    }


    public static FileValidatorImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean checkFile(File file) {
        return !(file == null || !file.exists() || file.isDirectory() || file.length() == 0);
    }

}
