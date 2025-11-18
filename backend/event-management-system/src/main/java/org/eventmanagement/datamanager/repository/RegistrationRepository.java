package org.eventmanagement.datamanager.repository;

import org.eventmanagement.datamanager.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    @Query("SELECT r FROM Registration r WHERE r.user.userId = :userId")
    List<Registration> findByUserId(@Param("userId")Long userId);

    @Query("SELECT COUNT(r) FROM Registration r WHERE r.user.userId = :userId")
    long countRegistrationsByUser(@Param("userId")Long userId);

    @Query("SELECT r FROM Registration r WHERE r.event.eventId = :eventId AND r.user.userId = :userId")
    Optional<Registration> findByUserIdAndEventId(@Param("userId") Long userId, @Param("eventId") Long eventId);

    @Modifying
    @Query("DELETE FROM Registration r WHERE r.event.eventId = :eventId")
    void deleteByEventId(@Param("eventId") Long eventId);

    @Modifying
    @Query("DELETE FROM Registration r WHERE r.user.userId = :userId")
    void deleteByUserId(@Param("userId") Long userId);
}
