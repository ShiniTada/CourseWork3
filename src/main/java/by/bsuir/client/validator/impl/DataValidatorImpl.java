package by.bsuir.client.validator.impl;

import by.bsuir.client.validator.DataValidator;

public class DataValidatorImpl implements DataValidator {
    private static final String LOG_PASSWORD_REGEX = "[\\w|а-яА-Я+]+\\s[\\w|а-яА-Я+]+";

    private static final String TRAIN_FLIGHT_REGEX = "(\\d+\\s[а-яА-Я]+\\s[а-яА-Я]+-[а-яА-Я]+)(\\s\\d+\\.\\d+_\\d+:\\d+){2}";
    private static final String TRAIN_FLIGHT_NUMBER_REGEX = "\\d+";
    private static final String TRAIN_FLIGHT_TYPE_REGEX = "(высокоскоростной)|(скоростной)|(скорый)|(фирменный)";
    private static final String TRAIN_FLIGHT_ROUTE_REGEX = "[а-яА-Я]+-[а-яА-Я]+";
    private static final String TRAIN_FLIGHT_TIME_REGEX = "\\d+\\.\\d+_\\d+:\\d+";

    private static final DataValidatorImpl INSTANCE = new DataValidatorImpl();

    private DataValidatorImpl() {}

    public static DataValidatorImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean checkUserData(String string) {
        return string.matches(LOG_PASSWORD_REGEX);
    }


    @Override
    public boolean checkTrainFlightData(String string) {
        return string.matches(TRAIN_FLIGHT_REGEX);
    }

    @Override
    public boolean checkTrainFlightNumber(String string) {
        return string.matches(TRAIN_FLIGHT_NUMBER_REGEX);
    }

    @Override
    public boolean checkTrainFlightType(String string)  {
        return string.matches(TRAIN_FLIGHT_TYPE_REGEX);
    }

    @Override
    public boolean checkTrainFlightRoute(String string)  {
        return string.matches(TRAIN_FLIGHT_ROUTE_REGEX);
    }

    @Override
    public boolean checkTrainFlightTime(String string)  {
        return string.matches(TRAIN_FLIGHT_TIME_REGEX);
    }

}
