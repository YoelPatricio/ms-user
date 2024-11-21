package com.smartjob.challange.mapper;

import com.smartjob.challange.model.request.PhoneRequest;
import com.smartjob.challange.model.request.UserRequest;
import com.smartjob.challange.model.response.UserResponse;
import com.smartjob.challange.repository.entity.Phone;
import com.smartjob.challange.repository.entity.User;
import java.util.List;

public class UserMapper {
    public static User mapToUser(UserRequest userRequest) {

        return User.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .phones(mapListToPhone(userRequest.getPhones()))
                .build();

    }

    private static List<Phone> mapListToPhone(List<PhoneRequest> phoneRequests) {
        return phoneRequests.stream()
                .map(phoneRequest -> Phone.builder()
                        .number(phoneRequest.getNumber())
                        .citycode(phoneRequest.getCitycode())
                        .countrycode(phoneRequest.getCountrycode())
                        .build()).toList();
    }

    public static UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .created(user.getCreated())
                .modified(user.getModified())
                .lastLogin(user.getLastLogin())
                .token(user.getToken())
                .isActive(user.getIsActive())
                .build();
    }

}
