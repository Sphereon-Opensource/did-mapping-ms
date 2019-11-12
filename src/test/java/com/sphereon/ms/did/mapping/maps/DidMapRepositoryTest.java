package com.sphereon.ms.did.mapping.maps;

import com.sphereon.ms.did.mapping.Application;
import com.sphereon.ms.did.mapping.maps.dto.DidMappingRequest;
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
import org.springframework.dao.DuplicateKeyException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {Application.class})
public class DidMapRepositoryTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @LocalServerPort
    private int port;

    @Autowired
    private DidMapRepository didMapRepository;

    @Value("#{@ResourceHelper.didMappingRequestFrom('classpath:rest-tests/dummy-did-maps-duplicate-dids.json')}")
    private DidMappingRequest dummyDidMapsSameDid;

    @Value("#{@ResourceHelper.didMappingRequestFrom('classpath:rest-tests/dummy-did-maps-duplicate-id-combo.json')}")
    private DidMappingRequest dummyDidMapSameUserAppIdCombo;

    @Value("#{@ResourceHelper.didMappingRequestFrom('classpath:rest-tests/dummy-did-maps-same-user-ids-valid.json')}")
    private DidMappingRequest dummyDidMapSameUserName;

    @Value("#{@ResourceHelper.didMappingRequestFrom('classpath:rest-tests/dummy-did-maps-duplicate-dids-valid.json')}")
    private DidMappingRequest dummyDidMapSameDidDifferentAppIds;

    @Before
    public void setUp() {
        didMapRepository.deleteAll();
        RestAssured.port = port;
    }

    @Test
    public void repoWontAllowSameDidForDifferentUsersAndSameApplicationId() {
        expectedException.expect(DuplicateKeyException.class);
        didMapRepository.save(dummyDidMapsSameDid.getDidMaps());
    }

    @Test
    public void repoWontAllowSameApplicationIdAndUserIdForDifferentDids() {
        expectedException.expect(DuplicateKeyException.class);
        didMapRepository.save(dummyDidMapSameUserAppIdCombo.getDidMaps());
    }

    @Test
    public void repoWillAllowSameUserIdForDifferentApplications() {
        didMapRepository.save(dummyDidMapSameUserName.getDidMaps());
        assert (didMapRepository.findByApplicationIdAndUserId("test-application", "test-user").isPresent());
        assert (didMapRepository.findByApplicationIdAndUserId("test-application1", "test-user").isPresent());
    }

    @Test
    public void repoWillAllowSameDidForDifferentApplications() {
        didMapRepository.save(dummyDidMapSameDidDifferentAppIds.getDidMaps());
        assert (didMapRepository.findByApplicationIdAndUserId("test-application0", "test-user").isPresent());
        assert (didMapRepository.findByApplicationIdAndUserId("test-application1", "test-user").isPresent());
    }
}