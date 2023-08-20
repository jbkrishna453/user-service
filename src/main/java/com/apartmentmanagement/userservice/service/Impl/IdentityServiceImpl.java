package com.apartmentmanagement.userservice.service.Impl;

import com.apartmentmanagement.userservice.model.User;
import com.apartmentmanagement.userservice.service.IdentityService;
import com.apartmentmanagement.userservice.model.IdentityAddUserRequest;
import io.github.resilience4j.retry.annotation.Retry;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class IdentityServiceImpl implements IdentityService {
    @Value("${identityServiceEndpoint:http://localhost:8001/identity/v1/user/}")
    private String identityServiceEndpoint;

    private final RestTemplate restTemplate;



    @Override
    @Retry(name = "auth-retry", fallbackMethod = "addUserFallBack")
    public String addUser(User user) {
        IdentityAddUserRequest request = IdentityAddUserRequest.builder()
                .recovery(user.getRecovery())
                .userName(user.getFirstName() + " " + user.getLastName())
                .emailId(user.getEmailId())
                .password(user.getPassword())
                .build();
        System.out.println("calling identity service");
        ResponseEntity<String> response = restTemplate
                .postForEntity(identityServiceEndpoint, request, String.class, new HashMap<>());
        return response.getBody();
    }

    public String addUserFallBack(User user, Throwable throwable){
        throw new RuntimeException("identity service is down");
    }

}
