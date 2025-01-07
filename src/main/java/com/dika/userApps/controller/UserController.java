package com.dika.userApps.controller;


import com.dika.userApps.dto.request.NewUserRequest;
import com.dika.userApps.service.UserService;
import com.dika.userApps.dto.request.SearchUserRequest;
import com.dika.userApps.dto.request.UpdateUserRequest;
import com.dika.userApps.dto.response.CommonResponse;
import com.dika.userApps.dto.response.UserResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody NewUserRequest request) {
        UserResponse userResponse = userService.createNewUser(request);
        CommonResponse<UserResponse> response = CommonResponse.<UserResponse>builder()
                .message("Successfully created user")
                .statusCode(HttpStatus.CREATED.value())
                .data(userResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        UserResponse userResponse = userService.getOne(id);
        CommonResponse<UserResponse> response = CommonResponse.<UserResponse>builder()
                .message("Successfully get user by id")
                .statusCode(HttpStatus.OK.value())
                .data(userResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<?> findAll(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "asc") String direction,
            @RequestParam(required = false, defaultValue = "name") String sortBy
    ) {
        SearchUserRequest searchUserRequest = SearchUserRequest.builder()
                .page(page)
                .size(size)
                .direction(direction)
                .sortBy(sortBy)
                .build();
        Page<UserResponse> allUsers = userService.getAllUsers(searchUserRequest);

        CommonResponse<List<UserResponse>> response = CommonResponse.<List<UserResponse>>builder()
                .message("Successfully get all users")
                .statusCode(HttpStatus.OK.value())
                .data(allUsers.getContent())
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody UpdateUserRequest request) {
        UserResponse userResponse = userService.updateUser(request);
        CommonResponse<UserResponse> commonResponse = CommonResponse.<UserResponse>builder()
                .message("Successfully updated user")
                .statusCode(HttpStatus.OK.value())
                .data(userResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commonResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        userService.deleteUserById(id);
        CommonResponse<?> commonResponse = CommonResponse.builder()
                .message("Successfully deleted user")
                .statusCode(HttpStatus.OK.value())
                .data("OK")
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(commonResponse);
    }
}
