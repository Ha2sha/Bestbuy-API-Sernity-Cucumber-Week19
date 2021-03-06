package com.bestbuy.bestbuyinfo.steps;

import com.bestbuy.bestbuyinfo.StoresSteps;
import com.bestbuy.utils.TestUtils;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.ValidatableResponse;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

/**
 * Created by HariKrishna
 */
public class StoresStepdefs {
    static String name = "Debenhams" + TestUtils.getRandomValue();
    static String type = "SuperStore" + TestUtils.getRandomValue();
    static String address = TestUtils.getRandomValue() + ", Harrow Road";
    static String address2 = "Harrow";
    static String city = "London";
    static String state = "London";
    static String zip = "123456";
    static Double lat = 65.122179;
    static Double lng = -95.263429;
    static String hours = "Mon: 10-9; Tue: 10-9; Wed: 10-9; Thurs: 10-9; Fri: 10-9; Sat: 10-9; Sun: 10-8";
    static int storeId;
    static ValidatableResponse response;

    @Steps
    StoresSteps storesSteps;

    @When("^User sends a GET request to the stores endpoint$")
    public void userSendsAGETRequestToTheStoresEndpoint() {
        response=storesSteps.getAllStores();
    }

    @Then("^User must get back valid status code is (\\d+)$")
    public void userMustGetBackValidStatusCodeIs(int exp) {
        storesSteps.getAllStores().log().all().assertThat().statusCode(exp);
    }

    @When("^I create a new store by providing the information name type address address city state zip lat lng and hours$")
    public void iCreateANewStoreByProvidingTheInformationNameTypeAddressAddressCityStateZipLatLngAndHours() {
        response = storesSteps.createStore(name, type, address, address2, city, state, zip, lat, lng, hours);
        response.log().all().statusCode(201);
    }

    @Then("^I verify that the new store is created$")
    public void iVerifyThatTheNewStoreIsCreated() {
        HashMap<String, Object> storeMap = storesSteps.getStoreInfoByname(name);
        Assert.assertThat(storeMap, hasValue(name));
        storeId = (int) storeMap.get("id");
        System.out.println("Created Store ID: " + storeId);
    }

    @When("^I update the store with name$")
    public void iUpdateTheStoreWithName() {
        name = name + " (Updated)";
        address = address + " (Updated)";
        storesSteps.updateStore(storeId, name, type, address, address2, city, state, zip, lat, lng, hours).log().all().statusCode(200);

    }

    @Then("^I verify that the store information is updated$")
    public void iVerifyThatTheStoreInformationIsUpdated() {
        HashMap<String, Object> value = storesSteps.getStoreInfoByname(name);
        Assert.assertThat(value, hasValue(name));
    }

    @When("^I delete the store created with id$")
    public void iDeleteTheStoreCreatedWithId() {
        storesSteps.deleteStore(storeId).statusCode(200);
    }

    @Then("^I verify that the store is deleted and get the status (\\d+)$")
    public void iVerifyThatTheStoreIsDeletedAndGetTheStatus(int exp) {
        storesSteps.getStoreById(storeId).statusCode(404);
    }



}
