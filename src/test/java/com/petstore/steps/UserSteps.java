package com.petstore.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;

public class UserSteps {
    private final BaseSteps baseSteps;
    private final CommonSteps commonSteps;

    public UserSteps(BaseSteps baseSteps, CommonSteps commonSteps) {
        this.baseSteps = baseSteps;
        this.commonSteps = commonSteps;
    }


    @Then("I should receive a status code {int} for user operation")
    public void i_should_receive_status_code_for_user_operation(int statusCode) {
        Assert.assertNotNull("Response is null. Did you forget to execute a request?", baseSteps.getResponse());
        Assert.assertEquals(statusCode, baseSteps.getResponse().getStatusCode());
    }
}
