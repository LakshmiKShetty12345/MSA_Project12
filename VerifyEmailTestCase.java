package com.api.testCases;

import static com.utilities.ExcelOperations.dataFactory;

import java.io.IOException;
import java.util.HashMap;

import org.testng.annotations.Test;

import com.apis.GetUserMasterData;
import com.apis.SetEmailVerification;
import com.base.DataVariables;
import com.utilities.ResponseUtilities;

import io.restassured.response.Response;

public class VerifyEmailTestCase extends SetEmailVerification {

    @Test
    public void VerifyEmail() throws IOException {
    	DataVariables DV = DataVariables.getInstance();
        //Get whole sheet data
        Object[][] arrayObject = dataFactory(TestData, DV.getEmailVerifiedSheetSheetName());

        //Loop to get the data out of the data sheet
        for (int i = 0; i < arrayObject.length - 1; i++) {

            //Get data in a HashMap to pull data easily
            HashMap < String, String > data = getDataMap(arrayObject, i);

            //Call API 
            Response response = SetEmailVerificationFlag(data);
            System.out.println(response.getBody().prettyPrint());
            //Verify API
            ResponseUtilities ru = new ResponseUtilities();
            ru.VerifyStatusCode(response, data.get(DV.getResponseCode()));
            ru.VerifyResponseTime(response);
            ru.VerifyResponseSchema(response, data.get(DV.getjson_Schema()));
            
            if(ru.GetStatusCode(response) == 202) {
            	GetUserMasterData GUMD = new GetUserMasterData();
            	Response response2 = GUMD.GetUserData(data);
            	ru.verifyJSONElementValue(response2, DV.getEmail_verified().toLowerCase(), data.get(DV.getEmail_verified()).toLowerCase());
            }
           
        }

    }

}