package com.codify.shardingimpl.controller.User.dto;

import com.codify.shardingimpl.repository.gym.model.Gym;
import com.codify.shardingimpl.repository.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.Objects;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    private Long gymId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String membershipPlan;
    private String membershipStatus;
    private String city;
    private String country;
    private OffsetDateTime joinedAt;
    private OffsetDateTime updatedAt;

    public User toUser() {
        return User.builder()
                .id(id)
                .gym(buildGymReference())
                .fullName(fullName)
                .email(email)
                .phoneNumber(phoneNumber)
                .membershipPlan(membershipPlan)
                .membershipStatus(membershipStatus)
                .city(city)
                .country(country)
                .joinedAt(joinedAt)
                .updatedAt(updatedAt)
                .build();
    }

    public static UserDto fromUser(User user) {
        return UserDto.builder()
                .id(user.getId())
                .gymId(Objects.isNull(user.getGym()) ? null : user.getGym().getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .membershipPlan(user.getMembershipPlan())
                .membershipStatus(user.getMembershipStatus())
                .city(user.getCity())
                .country(user.getCountry())
                .joinedAt(user.getJoinedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    private Gym buildGymReference() {
        if (Objects.isNull(gymId)) {
            return null;
        }

        return Gym.builder()
                .id(gymId)
                .build();
    }
}
