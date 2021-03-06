package com.sphereon.ms.did.mapping.maps;

import com.sphereon.ms.did.mapping.maps.exceptions.DuplicateDidMapException;
import com.sphereon.ms.did.mapping.maps.exceptions.InvalidDidMapException;
import com.sphereon.ms.did.mapping.maps.model.DidMap;
import com.sphereon.ms.did.mapping.utils.DidUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import static java.util.stream.Collectors.toList;

@Component
public class DidMapService {
    private final DidMapRepository didMapRepository;

    public DidMapService(final DidMapRepository didMapRepository) {
        this.didMapRepository = didMapRepository;
    }

    List<DidMap> storeDidMaps(List<DidMap> didMaps) throws InvalidDidMapException {
        //ToDo: add a check for didMap boxPub and pushToken formats
        List<DidMap> duplicateDidMaps = didMaps.stream()
                .filter(this::didMapExists)
                .collect(toList());
        if (!duplicateDidMaps.isEmpty()){
            String message = "One or more of the submitted DID maps has already been stored.";
            throw new DuplicateDidMapException(message, duplicateDidMaps);
        }
        didMaps.forEach(DidUtils::assertValidDidMap);
        didMapRepository.save(didMaps);
        return didMaps;
    }

    Optional<DidMap> findDidMap(String applicationId, String userId) {
        return didMapRepository.findByApplicationIdAndUserId(applicationId, userId);
    }

    private Boolean didMapExists(DidMap didMap) {
        return didMapRepository.findByApplicationIdAndUserId(didMap.getApplicationId(), didMap.getUserId()).isPresent();
    }
}
