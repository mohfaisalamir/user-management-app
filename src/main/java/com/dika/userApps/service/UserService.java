package com.dika.userApps.service;
import com.dika.userApps.dto.request.NewUserRequest;
import com.dika.userApps.dto.request.SearchUserRequest;
import com.dika.userApps.dto.request.UpdateUserRequest;
import com.dika.userApps.dto.response.UserResponse;
import org.springframework.data.domain.Page;

public interface UserService {
    UserResponse createNewUser(NewUserRequest request);
    UserResponse updateUser(UpdateUserRequest request);
    UserResponse getOne(String id);
    Page<UserResponse> getAllUsers(SearchUserRequest request);
    void deleteUserById(String id);
}
