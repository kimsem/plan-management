package com.telco.plan.infrastructure.cache;

import com.telco.plan.api.dto.CurrentPlanDTO;
import com.telco.plan.api.dto.PlanDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

/**
 * 요금제 정보 캐싱을 관리하는 클래스입니다.
 */
@Slf4j
@Component
public class PlanCacheManager {
    
    private static final String CURRENT_PLAN_CACHE = "currentPlan";
    private static final String AVAILABLE_PLANS_CACHE = "availablePlans";
    
    /**
     * 현재 요금제 정보를 캐시에서 조회합니다.
     */
    @Cacheable(value = CURRENT_PLAN_CACHE, key = "#userId")
    public Optional<CurrentPlanDTO> getCurrentPlan(String userId) {
        return Optional.empty(); // 실제로는 캐시에서 조회
    }
    
    /**
     * 현재 요금제 정보를 캐시에 저장합니다.
     */
    public void cacheCurrentPlan(String userId, CurrentPlanDTO plan) {
        // 캐시 저장 로직
    }
    
    /**
     * 이용 가능한 요금제 목록을 캐시에서 조회합니다.
     */
    @Cacheable(value = AVAILABLE_PLANS_CACHE)
    public Optional<List<PlanDTO>> getAvailablePlans() {
        return Optional.empty(); // 실제로는 캐시에서 조회
    }
    
    /**
     * 이용 가능한 요금제 목록을 캐시에 저장합니다.
     */
    public void cacheAvailablePlans(List<PlanDTO> plans) {
        // 캐시 저장 로직
    }
    
    /**
     * 현재 요금제 캐시를 무효화합니다.
     */
    @CacheEvict(value = CURRENT_PLAN_CACHE, key = "#userId")
    public void invalidateCurrentPlan(String userId) {
        log.info("현재 요금제 캐시가 무효화되었습니다. userId: {}", userId);
    }
    
    /**
     * 이용 가능한 요금제 목록 캐시를 무효화합니다.
     */
    @CacheEvict(value = AVAILABLE_PLANS_CACHE, allEntries = true)
    public void invalidateAvailablePlans() {
        log.info("이용 가능한 요금제 목록 캐시가 무효화되었습니다.");
    }
}
