package org.eventmanagement.datamanager.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "events")

@Data
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;

    @NonNull
    private String name;

    private String description;

    @NonNull
    private String venue;

    @NonNull
    private LocalDate date;

    @NonNull
    private LocalTime time;

    private boolean isApproved;

    @Column(name="is_deleted")
    private boolean isDeleted = false;
    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="host_id")
    private User host;
}
