package by.bsuir.client.parser;

import by.bsuir.client.exception.IncorrectDataException;

import java.util.List;

public interface DataParser {
    List<String> parseUser(String string) throws IncorrectDataException;

    List<String> parseTrainFlight(String string) throws IncorrectDataException;
}

