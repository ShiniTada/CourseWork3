package by.bsuir.client.parser.impl;

import by.bsuir.client.exception.IncorrectDataException;
import by.bsuir.client.parser.DataParser;
import by.bsuir.client.validator.DataValidator;
import by.bsuir.client.validator.impl.DataValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Arrays;
import java.util.List;

public class DataParserImpl implements DataParser {

    private static final Logger LOGGER = LogManager.getLogger(DataParserImpl.class);
    private static final String SEPARATOR_REGEX = "\\s";

    private static final DataParserImpl INSTANCE = new DataParserImpl();

    private DataParserImpl() {
    }

    public static DataParserImpl getInstance() {
        return INSTANCE;
    }

    private DataValidator dataValidator = DataValidatorImpl.getInstance();

    @Override
    public List<String> parseUser(String string) throws IncorrectDataException {
        if (dataValidator.checkUserData(string)) {
            List<String> strings = Arrays.asList(string.split(SEPARATOR_REGEX));
            LOGGER.info("Parse was successful complete");
            return strings;
        } else {
            LOGGER.warn("Incorrect input data:" + string);
            throw new IncorrectDataException("Incorrect input data:" + string);
        }
    }

    @Override
    public List<String> parseTrainFlight(String string) throws IncorrectDataException {
        if (dataValidator.checkTrainFlightData(string)) {
            List<String> strings = Arrays.asList(string.split(SEPARATOR_REGEX));
            LOGGER.info("Parse was successful complete");
            return strings;
        } else {
            LOGGER.warn("Incorrect input data:" + string);
            throw new IncorrectDataException("Incorrect input data:" + string);
        }
    }

}