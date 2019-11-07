package com.sphereon.ms.did.mapping.maps;

import com.sphereon.ms.did.mapping.Application;
import com.sphereon.ms.did.mapping.maps.dto.DidMappingRequest;
import com.sphereon.ms.did.mapping.maps.exceptions.DuplicateDidMapException;
import com.sphereon.ms.did.mapping.maps.exceptions.InvalidDidMapExcepion;
import com.sphereon.ms.did.mapping.maps.model.DidMap;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
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

    @Test(expected = DuplicateDidMapException.class)
    public void saveDidMapRejectsAlreadySavedDid() {
        didMapRepository.save(dummyDidMapsSingle.getDidMaps());
        didMapService.storeDidMaps(dummyDidMapsSingle.getDidMaps());
    }

    @Test(expected = InvalidDidMapExcepion.class)
    public void saveDidMapRejectsMalformedDid() {
        didMapService.storeDidMaps(getDummyDidMapsSingleMalformedDid.getDidMaps());
    }
}
