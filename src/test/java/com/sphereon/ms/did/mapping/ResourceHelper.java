package com.sphereon.ms.did.mapping;

import com.google.gson.Gson;
import com.sphereon.ms.did.mapping.maps.dto.DidMappingRequest;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component("ResourceHelper")
public class ResourceHelper {
    public static DidMappingRequest didMappingRequestFrom(String path) throws IOException {
        String didMapString = FileUtils.readFileToString(ResourceUtils.getFile(path), StandardCharsets.UTF_8);
        return new Gson().fromJson(didMapString, DidMappingRequest.class);
    }
}
