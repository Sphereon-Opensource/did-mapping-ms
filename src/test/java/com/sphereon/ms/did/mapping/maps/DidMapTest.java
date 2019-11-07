package com.sphereon.ms.did.mapping.maps;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sphereon.ms.did.mapping.Application;
import com.sphereon.ms.did.mapping.maps.dto.DidMappingRequest;
import com.sphereon.ms.did.mapping.maps.model.DidMap;
import io.restassured.RestAssured;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {Application.class})
public class DidMapTest {
    @LocalServerPort
    private int port;

    @Autowired
    private DidMapRepository didMapRepository;

    @Value("classpath:rest-tests/dummy-did-maps-single.json")
    private Resource dummyDidMapsSingle;

    @Value("classpath:rest-tests/dummy-did-maps-single-invalid.json")
    private Resource dummyDidMapsSingleInvalid;

    @Value("classpath:rest-tests/dummy-did-maps-multiple.json")
    private Resource dummyDidMapsMultiple;

    @Before
    public void setUp() {
        didMapRepository.deleteAll();
        RestAssured.port = port;
    }

    @Test
    public void didMapsShouldBePersisted() throws IOException {
        assertTrue(didMapRepository.findAll().isEmpty());

        given()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(dummyDidMapsSingle.getInputStream())
                .post("/didmaps")
                .then()
                .assertThat()
                .statusCode(200)
                .body("didMaps.size()", is(1))
                .body("didMaps.get(0)['userId']", is("test-user"))
                .body("didMaps.get(0)['applicationId']", is("test-application"))
                .body("didMaps.get(0)['didInfo']['did']", is("did:ethr:0xf3beac30c498d9e26865f34fcaa57dbb935b0d74"))
                .body("didMaps.get(0)['didInfo']['boxPub']", is("test-box-pub"))
                .body("didMaps.get(0)['didInfo']['pushToken']", is("test-push-token"));

        assertEquals(didMapRepository.count(), 1);
        final Optional<DidMap> didMap = didMapRepository.findByApplicationIdAndUserId("test-application", "test-user");
        assertTrue(didMap.isPresent());
        assertEquals(didMap.get().getUserId(),"test-user");
        assertEquals(didMap.get().getApplicationId(), "test-application");
        assertEquals(didMap.get().getDidInfo().getDid(), "did:ethr:0xf3beac30c498d9e26865f34fcaa57dbb935b0d74");
        assertEquals(didMap.get().getDidInfo().getBoxPub(), "test-box-pub");
        assertEquals(didMap.get().getDidInfo().getPushToken(), "test-push-token");
    }

    @Test
    public void validGetRequestShouldReturnDidMap() throws IOException{
        String didMapString = IOUtils.toString(dummyDidMapsSingle.getInputStream(), StandardCharsets.UTF_8);
        DidMappingRequest didMappingRequest = new Gson().fromJson(didMapString, DidMappingRequest.class);
        didMapRepository.save(didMappingRequest.getDidMaps());

        given()
                .get("didmaps/test-application/test-user")
                .then()
                .assertThat()
                .statusCode(200)
                .body("didMaps.size()", is(1))
                .body("didMaps.get(0)['userId']", is("test-user"))
                .body("didMaps.get(0)['applicationId']", is("test-application"))
                .body("didMaps.get(0)['didInfo']['did']", is("did:ethr:0xf3beac30c498d9e26865f34fcaa57dbb935b0d74"))
                .body("didMaps.get(0)['didInfo']['boxPub']", is("test-box-pub"))
                .body("didMaps.get(0)['didInfo']['pushToken']", is("test-push-token"));
    }

    @Test
    public void malformedDidMapPostShouldReturn400BadRequest() throws IOException{
        given()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(dummyDidMapsSingleInvalid.getInputStream())
                .post("/didmaps")
                .then()
                .assertThat()
                .statusCode(400);
    }

    @Test
    public void unknownDidMapGetShouldReturn404NotFound(){
        given()
                .get("didmaps/non-existent-application/non-existent-user")
                .then()
                .assertThat()
                .statusCode(404);
    }

    @Test
    public void multipleDidMapsShouldBePersisted() throws IOException{
        given()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .body(dummyDidMapsMultiple.getInputStream())
                .post("/didmaps")
                .then()
                .assertThat()
                .statusCode(200)
                .body("didMaps.size()", is(3));
        List<String> userIds = Arrays.asList("test-user0", "test-user1", "test-user2");
        for(String userId : userIds){
            assertTrue(didMapRepository.findByApplicationIdAndUserId("test-application", userId).isPresent());
        }
    }
}
