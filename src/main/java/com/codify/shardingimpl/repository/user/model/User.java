package com.codify.shardingimpl.repository.user.model;

import com.codify.shardingimpl.repository.gym.model.Gym;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "gym_users")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "gym_id")
    private Gym gym;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "membership_plan")
    private String membershipPlan;

    @Column(name = "membership_status")
    private String membershipStatus;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "joined_at")
    private OffsetDateTime joinedAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;
}
