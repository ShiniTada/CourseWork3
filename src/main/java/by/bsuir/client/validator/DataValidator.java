package by.bsuir.client.validator;

public interface DataValidator {

    boolean checkUserData(String string);

    boolean checkTrainFlightData(String string);

    boolean checkTrainFlightNumber(String string);

    boolean checkTrainFlightType(String string);

    boolean checkTrainFlightRoute(String string);

    boolean checkTrainFlightTime(String string);

}
