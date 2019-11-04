package com.sphereon.ms.did.mapping.maps;

import com.sphereon.ms.did.mapping.maps.model.DidMap;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface DidMapRepository extends MongoRepository<DidMap, String> {
    Optional<DidMap> findByApplicationIdAndUserId(String applicationId, String userId);
}
