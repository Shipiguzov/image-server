package com.ifmo.imageserver.repository;

import com.ifmo.imageserver.entity.AdditionalImageInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AdditionalImageInfoRepository extends PagingAndSortingRepository<AdditionalImageInfo, Long>,
        JpaSpecificationExecutor<AdditionalImageInfo> {
}
