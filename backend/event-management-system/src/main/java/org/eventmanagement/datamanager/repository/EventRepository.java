package org.eventmanagement.datamanager.repository;

import org.eventmanagement.datamanager.entity.Event;
import org.eventmanagement.datamanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByIsApprovedFalseAndIsDeletedFalse();
    List<Event> findByIsApprovedTrueAndIsDeletedFalse();

    @Query("SELECT e from Event e WHERE e.eventId=:eventId AND e.isDeleted=false")
    Optional<Event> getEventById(@Param("eventId") Long eventId);

    @Query("SELECT e FROM Event e WHERE e.isDeleted=false")
    List<Event> getAllEvents();


    @Query("SELECT COUNT(e) FROM Event e WHERE e.isDeleted= false AND e.isApproved = true")
    Long countApprovedEvents();


    @Query("""
        SELECT e FROM Event e 
        WHERE e.isDeleted = false 
        AND e.isApproved = true
        AND e.eventId NOT IN (
            SELECT r.event.eventId 
            FROM Registration r 
            WHERE r.user.userId = :userId
        )
    """)
    List<Event> findEventsNotRegisteredByUser(@Param("userId") Long userId);

    @Query("SELECT e FROM Event e WHERE e.isDeleted = false and e.isApproved = false and e.host.userId = :userId")
    List<Event> getAllPendingEventsByUser(@Param("userId") Long userId);

    @Query("SELECT COUNT(e) FROM Event e WHERE e.isDeleted = false")
    long countTotalEvents();

    @Query("SELECT COUNT(e) FROM Event e WHERE e.host.userId =:userId AND e.isDeleted = false ")
    long countCreatedEvents(@Param("userId") Long userId);

    @Query("SELECT COUNT(e) FROM Event e WHERE e.host.userId = :userId AND e.isDeleted = false AND e.isApproved = true")
    long countApprovedEventsCreatedByUser(@Param("userId")Long userId);

    @Query("SELECT e FROM Event e where e.host.userId = :userId and e.isApproved= true and e.isDeleted = false")
    List<Event> getAllApprovedEventsByUser(@Param("userId")Long userId);

    @Modifying
    @Query("UPDATE Event e SET e.isDeleted = true WHERE e.host.userId = :userId")
    void deleteEventByUserId(@Param("userId") Long userId);

    @Query("SELECT e FROM Event e WHERE e.host.userId = :userId AND e.isDeleted = false ")
    List<Event> findByHostId(@Param("userId") Long userId);
}
