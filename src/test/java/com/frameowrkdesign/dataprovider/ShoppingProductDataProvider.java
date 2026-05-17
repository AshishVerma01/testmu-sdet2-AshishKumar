package com.frameowrkdesign.dataprovider;

import com.frameworkdesign.ui.utility.FrameworkUtilityMethods;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class ShoppingProductDataProvider {

    @DataProvider
    public Object[][] getProducts() {
        return new Object[][] {{List.of("iphone 13 pro"), "testframeworkdesign@gmail.com","Test@123"},
                {List.of("Zara Coat 3"), "testframeworkdesign@gmail.com","Test@123"}};
    }

    @DataProvider
    public Object[][] getProductsUsingHashMap() {
        HashMap<String, String> data = new HashMap<>();
//        data.put("product", "iphone 13 pro");
//        data.put("email", "testframeworkdesign1@gmail.com");
//        data.put("password", "Test@123");
        HashMap<String, String> data_2 = new HashMap<>();
//        data_2.put("product", "Zara Coat 3");
//        data_2.put("email", "testframeworkdesign1@gmail.com");
//        data_2.put("password", "Test@123");
        try {
            List<HashMap<String, String>> jsonData = FrameworkUtilityMethods.getJsonDataToMap(new File(System.getProperty("user.dir") +
                    "//src//test//resources//inputTestData//getProductsData.json"));
            data = jsonData.get(0);
            data_2 = jsonData.get(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Object[][] {{data}, {data_2}};
    }


}
