package com.cydeo.stepDefinitions;

import com.cydeo.pojo.DeleteProductPojo;
import com.cydeo.pojo.ListOfProducts;
import com.cydeo.utilities.ConfigurationReader;
import com.cydeo.utilities.Utils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import javax.security.auth.login.Configuration;
import java.util.LinkedHashMap;
import java.util.Map;

public class UpdateProductStepDef {
    int productId;
    String updatedName;
    double updatedPrice;

    @Given("Find product named {string}")
    public void find_product_named(String productName) {
        JsonPath jsonPath = RestAssured.given().accept(ContentType.JSON).and().contentType(ContentType.JSON)
                .when().get(ConfigurationReader.getProperty("base_url") + "products/").then().extract().jsonPath();

        ListOfProducts listOfProducts = jsonPath.getObject("", ListOfProducts.class);
// find matching name in list of products and store product id;
        for (DeleteProductPojo product : listOfProducts.getProducts()) {

            if (product.getName().equals(productName)) {
                productId = Utils.StringtoInt(product.getProductUrl());
                break;
            }

        }


    }

    @When("I update product with name {string} and price {double};")
    public void i_update_product_with_name_and_price(String name, Double price) {
        Map<String, Object> change = new LinkedHashMap<>();
        change.put("name", name);
        change.put("price", price);
        RestAssured.given().accept(ContentType.JSON).and().contentType(ContentType.JSON).pathParam("id", productId)
                .body(change)
                .when().patch(ConfigurationReader.getProperty("base_url") + "products/{id}").then().statusCode(200);

        updatedName=name;
        updatedPrice=price;
//a

    }

    @Then("I confirm product update")
    public void iConfirmProductUpdate() {

        JsonPath jsonPath = RestAssured.given().accept(ContentType.JSON).and().contentType(ContentType.JSON).pathParam("id", productId)
                .when().get(ConfigurationReader.getProperty("base_url") + "products/{id}").then().statusCode(200).extract().jsonPath();

        MatcherAssert.assertThat(updatedName, Matchers.is(jsonPath.getString("name")));
        MatcherAssert.assertThat(updatedPrice, Matchers.is(jsonPath.getDouble("price")));
       /* System.out.println("jsonPath.getString(\"name\") = " + jsonPath.getString("name"));
        System.out.println("jsonPath.getInt(\"price\") = " + jsonPath.getDouble("price"));
        System.out.println("updatedName = " + updatedName);
        System.out.println("updatedPrice = " + updatedPrice);

        */

    }
}
