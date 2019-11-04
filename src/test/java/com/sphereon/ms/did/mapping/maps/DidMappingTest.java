package com.sphereon.ms.did.mapping.maps;

import com.sphereon.ms.did.mapping.maps.dto.DidMappingResponse;
import com.sphereon.ms.did.mapping.maps.model.DidInfo;
import com.sphereon.ms.did.mapping.maps.model.DidMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
public class DidMappingTest {

    @Test
    public void didMappingResponseShouldConstructFromDidMapList() {
        DidInfo didInfo = new DidInfo("did", "pushToken", "boxPub");
        List<DidMap> didMapList = Collections.singletonList(new DidMap("id", "userId", "applicationId", didInfo));
        DidMappingResponse didMappingResponse = DidMappingResponse.of(didMapList);
        assert(didMappingResponse.getUserIds().equals(Collections.singletonList("userId")));
    }
}
