package com.api.testCases;

import static com.utilities.ExcelOperations.dataFactory;

import java.io.IOException;
import java.util.HashMap;

import org.testng.annotations.Test;

import com.apis.GetUserMasterData;
import com.base.DataVariables;
import com.utilities.ResponseUtilities;

import io.restassured.response.Response;

public class VerifyUserMasterData extends GetUserMasterData {


    @Test
    public void VerifyUserData() throws IOException {
    	DataVariables DV = DataVariables.getInstance();
        //Get whole sheet data
        Object[][] arrayObject = dataFactory(TestData, DV.getUserDataSheetSheetName());

        //Loop to get the data out of the data sheet
        for (int i = 0; i < arrayObject.length - 1; i++) {

            //Get data in a HashMap to pull data easily
            HashMap < String, String > data = getDataMap(arrayObject, i);
            
            //Call API 
            Response response = GetUserData(data);

            //Verify API
            ResponseUtilities ru = new ResponseUtilities();
            ru.verifyJSONElementValue(response, DV.getPrimaryEmailAddress(), data.get( DV.getPrimaryEmailAddress()));
            ru.VerifyResponseSchema(response, data.get(DV.getjson_Schema()));
            System.out.println(response.getBody().prettyPrint());

        }

    }
}