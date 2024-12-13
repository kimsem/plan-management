package com.telco.plan.domain.repository;

import com.telco.plan.domain.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

/**
 * 요금제 정보에 접근하기 위한 Repository 인터페이스입니다.
 */
public interface PlanRepository extends JpaRepository<Plan, String> {
    
    /**
     * 현재 사용자의 요금제를 조회합니다.
     */
    @Query("SELECT p FROM Plan p JOIN UserPlan up ON p.planId = up.planId WHERE up.userId = :userId AND up.endDate IS NULL")
    Optional<Plan> findCurrentPlan(@Param("userId") String userId);
    
    /**
     * 이용 가능한 모든 요금제를 조회합니다.
     */
    @Query("SELECT p FROM Plan p WHERE p.status = 'ACTIVE'")
    List<Plan> findAvailablePlans();
    
    /**
     * 사용자의 요금제를 업데이트합니다.
     */
    @Query("UPDATE UserPlan up SET up.planId = :planId WHERE up.userId = :userId AND up.endDate IS NULL")
    boolean updateUserPlan(@Param("userId") String userId, @Param("planId") String planId);
}
