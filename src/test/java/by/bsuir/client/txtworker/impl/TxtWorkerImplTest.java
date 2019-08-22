package by.bsuir.client.txtworker.impl;

import by.bsuir.client.exception.ReadDataException;
import by.bsuir.client.txtworker.TxtWorker;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class TxtWorkerImplTest {

    private TxtWorker reader;

    @BeforeClass
    public void init(){
        reader = TxtWorkerImpl.getInstance();
    }

    @Test(description = "read data from file")
    public void testReadDataTxtSuccessfully() throws ReadDataException {
        //given
        List<String> expected = Arrays.asList("admin admin", "user1 user1", "user2 user2", "user3 user3");
        //when
        List<String> actual = reader.readDataTxt("D:\\university\\course work\\trains\\trains\\src\\resources\\testUsers.txt");
        //then
        Assert.assertEquals(actual, expected);
    }

    @Test(description = "file is not exist or empty", expectedExceptions = ReadDataException.class)
    public void testFileExistence() throws ReadDataException {
        List<String> actual = reader.readDataTxt("src\\main\\resources\\another.txt");
    }
}