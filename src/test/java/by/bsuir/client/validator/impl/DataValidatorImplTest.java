package by.bsuir.client.validator.impl;

import by.bsuir.client.validator.DataValidator;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DataValidatorImplTest {

    private DataValidator validator = DataValidatorImpl.getInstance();


    @Test(description = "correct data")
    public void testCheckTrainData() {
        //when
        String testTrain = "24 скорый Минск-Брест 26.05_12:00 26.05_19:22";
        boolean expected = validator.checkTrainFlightData(testTrain);
        //then
        Assert.assertTrue(expected);
    }

    @Test(description = "incorrect data")
    public void testCheckWrongTrainData() {
        //when
        String testTrain = "24 скорый Минск-Брест 12:00 26.05_19:22";
        boolean expected = validator.checkTrainFlightData(testTrain);
        //then
        Assert.assertFalse(expected);
    }


    @Test
    public void testCheckTrainFlightType() {
        //when
        String testTrain = "скорый";
        boolean expected = validator.checkTrainFlightType(testTrain);
        //then
        Assert.assertTrue(expected);
    }
}