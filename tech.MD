### Technical introduction

#### We should distinguish three kind of objects in our project:
- `Aggregate`
- `Entity`
- `Value`

It is easy to distinguish value objects from other two.
`Aggregate` and `Entity` objects have identity history and one entity is equal to another if their ids are the same.
This id equality applies also for aggregates.
One `Value` object is equal to another one when all fields(or one subset of the fields in some edge scenarios) are the same.
`Value` objects don't have identity.
Example for the `Value` object would be:All request/response classes, domain events, Param classes(those are arguments in Aggregate update methods).
Also, some classes that are stored in the db can be values. Consider this one:

```
@Embeddable
class Address {
    private String zip;
    private String city;
    private long lat;
    private long long;
    //...equals method compare two addresses by fields value, not by some id  
}
```
When we have that in mind it has sense to add `@EqualsAndHashCode` `lombok` annotation on `Value` objects.
 
Difference between `Aggregate` and `Entity` is that `Entity` cannot live without `Aggregate`.
Lifecycle of the `Entity` should be managed by `Aggregate`.
`Aggregate` is the root of the graph.
`Aggregate` example would be `Incident/Person/Item` and example for entity would be `Container/ContainerPayload/ContainerMetadata`.


Our `Entity` classes should have `package` visibility. The reason for this is to not reference entities somewhere outside it's corresponding root aggregate. This might sound like a banal thing currently, but it will help us a lot later. At any case we should aim for lower visibility and `final` classes.
Entity class should NOT have `Repository` or `Controller`. NOTE: We broke this concept by introducing `Controller` for `Container` and `Container` is `Entity`(this is debatable) since it cannot live without `Incident/Person/Item`. We will fix this as soon as possible.

#### In our project we have 4 layers:
- api
- application
- domain
- repository

Responsibility of the layers:

##### Api:
Purpose of this layer is exposing our backend api as rest api.
In this layer we should keep Controllers, Requests and Responses.
This layer is responsible for setting status codes, checking if all mandatory request params are present, adding HATEOAS links etc.
This layer delegates to the application layer.

`api` layer is responsible for keeping swagger annotations.
Rules for creating swagger annotations:
Don't put things like `@ApiResponse(code = 400, message = "Bad Request")` because that things should not be returned to the client in any case.
Whenever we want to send `400` status to the client, we should throw `BadRequestException` with appropriate message so "Bad request" does not really help any client, it is too generic, and those are just more lines and confusion in our code.
The same applies for all other codes.

##### Application:
Usually those classes will have suffixes `*Service`.
This layer is responsible for handling transactions.
This layer is responsible for receiving requests from `Controller` and delegate those requests to the appropriate domain classes.
This layer is responsible for calling repositories to find entity based on id. NOTE: This functionality is currently in our domain model instead in our application layer(you can see `StaticBean` on a lot of places).
We did it this way in order not to introduce Value objects which will need to invest time to create one Value object for each container. Good thing is that at least that leak is really consistent(on all places `StaticBean` is called). We will fix this when we find time.
This layer is the place where you would like to call `camunda`(since `camunda` is holding part of our domain)
This layer should save our changes to the database and elasticsearch.

##### Domain:
Core layer of our application.
THis layer is responsible ONLY for domain and nothing else. Make sure all DOMAIN rules are in this package.
This layer is NOT responsible for either handling transactions or calling any infrastructure(Take a look at note about `StaticBean`, you can keep being consistent in calling repository that way for now)
We should keep our business rules in this layer and make sure our business logic does not leave this layer.

Be very sceptical when putting `@Getter` of `@Setter` on our domain classes.
One example how our domain logic leaked to the application layer: we have incidentDate field in our Incident class and obviously we can have some business rule regarding that field(for example it must be in the past) or maybe some more
complicated rule. With current @Setter annotation something like this is possible:
 
 ```
Incident incident = new Incident(); 
incident.setIncidentDate(someDateInTheFuture);
```

and just like that, our domain class/rule is broken. On the other hand, purpose of the setter is encapsulation and blindly setting every value that is passed to that setter.
We currently have rule "incident is open if one of the containers is open" and with our current approach it is totally possible to call setter for containers on CLOSED incident and add there few more incidents that are open and then our incident is closed but we have open containers and our domain class is broken.

Good approach would be to have one update method that accepts `IncidentUpdate` `Value` object and check all domain rules before make any update!

##### application.BPMN:
We will keep our task handlers in this package.

##### Repository:
All infrastructure things like spring repositories or any other infrastructure that we can have in the future belongs to this layer.


#### Tests

We should have integration and unit/domain tests.
Either of those two tests have 3 stages: setting data before test(called "given" stage), call thing that you are testing("when" phase), checking if you result of the test is right("then" phase)
This `given/when/then` thing I saw from `Martin Fowler` [here](https://martinfowler.com/bliki/GivenWhenThen.html)
Having that in mind, here is test naming in our project: 
`test_given_when_then` where we substitute `given` with things that should be set before test, `when` is action that run test behaviour and `then` word then is substituted with things that are checked

Example:
`test_createPolicy_submitStep1_checkIfFormIsSubmittedCorrectlyAndResponsePointsMeToRightNextStep`

We are trying to test if our endpoint for form submit works as expected(form submited and next step returned correctly)

`//given` part is that before testing our steps we need to create policy
`//when` part is what we are actually testing
`//then` part is checking response and see if we got desired result

Test name is long, but it perfectly names what test does and all three steps that are required.
In case we are writing test to verify that we resolved bug than this is the right naming:

`CLA-900_test_createIncidentAndUpdateIncidentInfoAndTrailerThreeTimes_getContainerHistory_TwoHistoryRecordsPresentForBothContainers`


I took a deeper look at some naming conventions that other members of the team proposed on some other projects, and I came across this:

`should_createIncident_And_setInitialContainers`

Taking look at this naming, logical question would be: Where is your mockMvc call for `setInitialContainers` in your `when` phase? How you can check in `then` phase that your
containers are initialized if you didn't call API for initializing those containers?
This problem is problem with naming convention because this test name does not contain distinction between our three phases.
Also, there was proposal that we should name our tests as sentences but I also don't think that "sentences naming convention" is good since human sentences are not
precise enough. Also, since order of our phases is always the same it is good that first word in naming convention is first phase,
 second word is second phase and third phase is third word.
Also, split body of the test on three parts like this:

```
@Test
    void test_given_when_then() {
        //given
        ...initializing data before test

        //when
        ...calling method that we are testing
        //then
        check if response is appropriate
    }
```

##### Integration tests:
This kind of tests simulates client, and they don't have ANY information about implementation. This is black box testing.
Imagine using swagger/postman/curl to test endpoint, that is the thing we want to cover here.
Client does not know about our elasticsearch,database, camunda and it really does not care.
Purpose of this test is to check if our endpoints are working as expected. Every command(in all three phases) go through mockMvc.
It is true that our test for saving container will fail if incident creation is not working or if fetching data about container is not working but that's good thing, we discovered bug!
On the other hand if we try to create whitebox test, then our test will fail if we drop down camunda, and how our implementation affects client?
He doesn't care about implementation.


##### Unit/Domain tests: 
This kind of test should test if our domain classes behaves in way we want.
This kind of tests should cover things like:
- Do we really have limited number of pets on one policy at the time
- If pet died in foreign country, we are not obligated to cover that case
- Pet birth date must be in the past
- If someone want to insure his already sick pet, he needs to pay more etc. etc.

Those two kinds of tests are mandatory, but in some cases we need more tests like validation tests, tests for camunda etc.