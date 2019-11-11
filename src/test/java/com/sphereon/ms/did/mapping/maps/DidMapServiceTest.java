package com.sphereon.ms.did.mapping.maps;

import com.sphereon.ms.did.mapping.Application;
import com.sphereon.ms.did.mapping.maps.dto.DidMappingRequest;
import com.sphereon.ms.did.mapping.maps.exceptions.DuplicateDidMapException;
import com.sphereon.ms.did.mapping.maps.exceptions.InvalidDidMapException;
import com.sphereon.ms.did.mapping.maps.model.DidMap;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

    @Value("#{@ResourceHelper.didMappingRequestFrom('classpath:rest-tests/dummy-did-maps-single.json')}")
    private DidMappingRequest dummyDidMapsSingle;

    @Value("#{@ResourceHelper.didMappingRequestFrom('classpath:rest-tests/dummy-did-maps-single-malformed-did.json')}")
    private DidMappingRequest getDummyDidMapsSingleMalformedDid;

    @Value("#{@ResourceHelper.didMappingRequestFrom('classpath:rest-tests/dummy-did-maps-duplicate-dids.json')}")
    private DidMappingRequest dummyDidMapsSameDid;

    @Value("#{@ResourceHelper.didMappingRequestFrom('classpath:rest-tests/dummy-did-maps-duplicate-id-combo.json')}")
    private DidMappingRequest dummyDidMapSameUserAppIdCombo;

    @Before
    public void setUp() {
        didMapRepository.deleteAll();
        RestAssured.port = port;
    }

    @Test
    public void saveDidMapPersistsDidMap() {
        didMapService.storeDidMaps(dummyDidMapsSingle.getDidMaps());
        Optional<DidMap> didMap = didMapService.findDidMap("test-application", "test-user");

        assertTrue(didMap.isPresent());
        assertEquals(didMap.get().getApplicationId(), "test-application");
        assertEquals(didMap.get().getUserId(), "test-user");
        assertEquals(didMap.get().getDidInfo().getDid(), "did:ethr:0xf3beac30c498d9e26865f34fcaa57dbb935b0d74");
        assertEquals(didMap.get().getDidInfo().getPushToken(), "test-push-token");
        assertEquals(didMap.get().getDidInfo().getBoxPub(), "test-box-pub");
    }

    @Test
    public void saveDidMapRejectsAlreadySavedDid() {
        expectedException.expect(DuplicateDidMapException.class);
        expectedException.expectMessage("One or more of the submitted DID maps has already been stored.");

        didMapRepository.save(dummyDidMapsSingle.getDidMaps());
        didMapService.storeDidMaps(dummyDidMapsSingle.getDidMaps());
    }

    @Test
    public void saveDidMapRejectsMalformedDid() {
        expectedException.expect(InvalidDidMapException.class);
        expectedException.expectMessage("One or more of the submitted DID maps was not formatted properly.");

        didMapService.storeDidMaps(getDummyDidMapsSingleMalformedDid.getDidMaps());
    }
}
