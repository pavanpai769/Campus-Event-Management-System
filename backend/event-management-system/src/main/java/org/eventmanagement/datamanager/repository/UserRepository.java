package org.eventmanagement.datamanager.repository;

import org.eventmanagement.datamanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.isDeleted = false and u.username = :username")
    Optional<User> getUserByUsername(@Param("username")String username);

    @Query("SELECT u FROM User u WHERE u.isDeleted=false and u.userId = :userId")
    Optional<User> getUserById(@Param("userId") Long userId);

    List<User> findByIsApprovedFalseAndIsDeletedFalse();

    List<User> findByIsApprovedTrueAndIsDeletedFalse();

    @Query("SELECT u FROM User u WHERE u.isDeleted=false")
    List<User> getAllUsers();

    @Query("SELECT COUNT(u) FROM Event u WHERE u.isDeleted= false")
    Long countAllUsers();

    @Query("SELECT COUNT(u) FROM User u WHERE u.isDeleted= false AND u.isApproved = true")
    Long countApprovedUsers();

    @Query("SELECT COUNT(u) FROM User u WHERE u.isDeleted= false AND u.isApproved = false")
    Long countPendingUsers();
}
