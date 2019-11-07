package com.sphereon.ms.did.mapping.maps;

import com.google.gson.Gson;
import com.sphereon.ms.did.mapping.Application;
import com.sphereon.ms.did.mapping.maps.dto.DidMappingRequest;
import com.sphereon.ms.did.mapping.maps.exceptions.DuplicateDidMapException;
import com.sphereon.ms.did.mapping.maps.exceptions.InvalidDidMapExcepion;
import com.sphereon.ms.did.mapping.maps.model.DidMap;
import io.restassured.RestAssured;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {Application.class})
public class DidMapServiceTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @LocalServerPort
    private int port;

    @Autowired
    private DidMapRepository didMapRepository;

    @Autowired
    private DidMapService didMapService;

    @Value("classpath:rest-tests/dummy-did-maps-single.json")
    private Resource dummyDidMapsSingle;

    @Value("classpath:rest-tests/dummy-did-maps-single-invalid.json")
    private Resource dummyDidMapsSingleInvalid;

    @Value("classpath:rest-tests/dummy-did-maps-single-malformed-did.json")
    private Resource getDummyDidMapsSingleMalformedDid;

    @Before
    public void setUp() {
        didMapRepository.deleteAll();
        RestAssured.port = port;
    }

    @Test
    public void saveDidMapPersistsDidMap() throws IOException{
        String didMapString = IOUtils.toString(dummyDidMapsSingle.getInputStream(), StandardCharsets.UTF_8);
        DidMappingRequest didMappingRequest = new Gson().fromJson(didMapString, DidMappingRequest.class);
        didMapService.storeDidMaps(didMappingRequest.getDidMaps());
        Optional<DidMap> didMap = didMapService.findDidMap("test-application", "test-user");

        assertTrue(didMap.isPresent());
        assertEquals(didMap.get().getApplicationId(), "test-application");
        assertEquals(didMap.get().getUserId(), "test-user");
        assertEquals(didMap.get().getDidInfo().getDid(), "did:ethr:0xf3beac30c498d9e26865f34fcaa57dbb935b0d74");
        assertEquals(didMap.get().getDidInfo().getPushToken(), "test-push-token");
        assertEquals(didMap.get().getDidInfo().getBoxPub(), "test-box-pub");
    }

    @Test
    public void saveDidMapRejectsAlreadySavedDid() throws IOException {
        expectedException.expect(DuplicateDidMapException.class);
        expectedException.expectMessage("One or more of the submitted DID maps has already been stored.");

        String didMapString = IOUtils.toString(dummyDidMapsSingle.getInputStream(), StandardCharsets.UTF_8);
        DidMappingRequest didMappingRequest = new Gson().fromJson(didMapString, DidMappingRequest.class);
        didMapRepository.save(didMappingRequest.getDidMaps());
        didMapService.storeDidMaps(didMappingRequest.getDidMaps());
    }

    @Test
    public void saveDidMapRejectsMalformedDid() throws IOException {
        expectedException.expect(InvalidDidMapExcepion.class);
        expectedException.expectMessage("One or more of the submitted DID maps was not formatted properly.");

        String didMapString = IOUtils.toString(getDummyDidMapsSingleMalformedDid.getInputStream(), StandardCharsets.UTF_8);
        DidMappingRequest didMappingRequest = new Gson().fromJson(didMapString, DidMappingRequest.class);
        didMapService.storeDidMaps(didMappingRequest.getDidMaps());
    }
}
