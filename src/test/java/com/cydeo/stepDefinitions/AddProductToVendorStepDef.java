package com.cydeo.stepDefinitions;

import com.cydeo.pojo.*;
import com.cydeo.utilities.ConfigurationReader;
import com.cydeo.utilities.Utils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.it.Ma;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;

import javax.security.auth.login.Configuration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AddProductToVendorStepDef {

int index=0;
    Vendors vendors;
    String vendorId;
    String name;
    int price;
    String category;
    int id;
    CreatedProduct createdProduct;
    Map<String,Object> testMap=new LinkedHashMap<>();
    @Given("I find get vendor id from vendor {string}")
    public void i_find_get_vendor_id_from_vendor(String string) {
        Response response = RestAssured.given().accept(ContentType.JSON).and().contentType(ContentType.JSON)
                .and().get(ConfigurationReader.getProperty("base_url") + "vendors/");

        JsonPath jsonPath = response.jsonPath();




        vendors=jsonPath.getObject("",Vendors.class);
        //Vendors vendors=jsonPath.getJsonObject("vendors");


        System.out.println("vendors = " + vendors);

        for (VendorList vendor : vendors.getVendors()) {
            System.out.println(vendor.getName());
            if (vendor.getName().equals(string)){
                vendorId=vendor.getVendorUrl();
                break;
            }

        }


    }



    @Then("i verify produce is added")
    public void iVerifyProductedIsAdded() {
        JsonPath jsonPath = RestAssured.given().accept(ContentType.JSON).and().contentType(ContentType.JSON)
                .and().pathParam("id", vendorId)
                .get(ConfigurationReader.getProperty("base_url") + "vendors/{id}/products/").then().extract().jsonPath();

        MatcherAssert.assertThat(name,Matchers.is(createdProduct.getName()));
        MatcherAssert.assertThat(price,Matchers.equalTo(createdProduct.getPrice()));
        MatcherAssert.assertThat("/shop/categories/"+category,Matchers.equalTo(createdProduct.getCategory_url()));

    }


    @When("I add a product with the name {string} price {int} and category {string} to selected vendor")
    public void iAddAProductWithTheNamePriceAndCategoryToSelectedVendor(String name, int price, String category) {
        Map<String,Object> testMap=new LinkedHashMap<>();
        this.name=name;
        this.price=price;
        this.category=category;

        testMap.put("name",name);
        testMap.put("price",price);
        testMap.put("category",category);

        id=Utils.StringtoInt(vendorId);

        JsonPath jsonPath = RestAssured.given().accept(ContentType.JSON).and().contentType(ContentType.JSON)
                .and().body(testMap).pathParam("id", id)
                .post(ConfigurationReader.getProperty("base_url") + "vendors/{id}/products/").then().statusCode(201).log().all().extract().jsonPath();


        createdProduct = jsonPath.getObject("", CreatedProduct.class);

        System.out.println("createdProduct = " + createdProduct);

    }


    @Then("I delete the product")
    public void iDeleteTheProduct() {
        JsonPath jsonPath = RestAssured.given().accept(ContentType.JSON).and().contentType(ContentType.JSON)
                .and().pathParam("id", id)
                .get(ConfigurationReader.getProperty("base_url")+"vendors/{id}/products/").then().statusCode(200).log().all().extract().jsonPath();


        ListOfProducts listOfProducts=jsonPath.getObject("",ListOfProducts.class);
        int productUrl=0;


        for (DeleteProductPojo product : listOfProducts.getProducts()) {
            if (product.getName().equals(name)){
            productUrl=Utils.StringtoInt(product.getProductUrl());
            break;
            }
        }
        //deleting
        RestAssured.given().accept(ContentType.JSON).and().contentType(ContentType.JSON)
                .and().pathParam("id", productUrl)
                .delete(ConfigurationReader.getProperty("base_url")+"products/{id}").then().statusCode(200).log().all().extract().jsonPath();

// confirm deletion
        RestAssured.given().accept(ContentType.JSON).and().contentType(ContentType.JSON)
                .and().pathParam("id", productUrl)
                .get(ConfigurationReader.getProperty("base_url")+"products/{id}").then().statusCode(404).log().all().extract().jsonPath();

    }
}
