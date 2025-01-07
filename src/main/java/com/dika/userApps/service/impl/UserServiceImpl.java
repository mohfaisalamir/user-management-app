package com.dika.userApps.service.impl;

import com.dika.userApps.service.UserService;
import com.dika.userApps.dto.request.NewUserRequest;
import com.dika.userApps.dto.request.SearchUserRequest;
import com.dika.userApps.dto.request.UpdateUserRequest;
import com.dika.userApps.dto.response.UserResponse;
import com.dika.userApps.entity.User;
import com.dika.userApps.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final ValidationUtil validationUtil;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserServiceImpl(ValidationUtil validationUtil, JdbcTemplate jdbcTemplate) {
        this.validationUtil = validationUtil;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserResponse createNewUser(NewUserRequest request) {
        // Debugging: Log data yang diterima
        System.out.println("Creating user with email: " + request.getEmail() + " and phone: " + request.getPhone());

        // Validasi untuk memastikan tidak ada pengguna dengan email atau telepon yang sama
        String checkUserQuery = "SELECT COUNT(*) FROM m_user WHERE email = ? OR phone = ?";
        int count = jdbcTemplate.queryForObject(checkUserQuery, Integer.class, request.getEmail(), request.getPhone());
        System.out.println("Count of users with the same email or phone: " + count);

        if (count > 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User data already exists for email: " + request.getEmail() + " or phone: " + request.getPhone());
        }

        try {
            // SQL query untuk memasukkan data pengguna ke dalam tabel `m_user` dan mendapatkan ID
            String insertUserQuery = "INSERT INTO m_user (name, phone, email) VALUES (?, ?, ?) RETURNING id";
            String userId = jdbcTemplate.queryForObject(insertUserQuery, String.class, request.getName(), request.getPhone(), request.getEmail());

            // Mengembalikan response dengan ID yang baru
            User user = new User(userId, request.getName(), request.getPhone(), request.getEmail());
            return mapToResponse(user);

        } catch (DataIntegrityViolationException e) {
            e.printStackTrace(); // Tambahkan ini untuk melihat detail pengecualian
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User data already exists");
        } catch (Exception e) {
            e.printStackTrace(); // Menangkap pengecualian lain yang mungkin terjadi
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while creating the user");
        }
    }



    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserResponse updateUser(UpdateUserRequest request) {
        validationUtil.validate(request);

        // Mengupdate data pengguna
        String updateUserQuery = "UPDATE m_user SET name = ?, phone = ?, email = ? WHERE id = ?";
        jdbcTemplate.update(updateUserQuery, request.getName(), request.getPhone(), request.getEmail(), request.getId());

        // Mengembalikan response dengan ID pengguna yang telah diperbarui
        User updatedUser = new User(request.getId(), request.getName(), request.getPhone(), request.getEmail());
        return mapToResponse(updatedUser);
    }

    @Override
    public UserResponse getOne(String id) {
        // Query untuk mendapatkan user berdasarkan ID
        String getUserByIdQuery = "SELECT id, name, phone, email FROM m_user WHERE id = ?";
        User user = jdbcTemplate.queryForObject(getUserByIdQuery, (rs, rowNum) ->
                new User(rs.getString("id"), rs.getString("name"), rs.getString("phone"), rs.getString("email")), id);

        return mapToResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponse> getAllUsers(SearchUserRequest request) {
        Sort.Direction direction = Sort.Direction.fromString(request.getDirection());
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize(), direction, request.getSortBy());

        // Menggunakan JdbcTemplate untuk mengambil data
        String getAllUsersQuery = "SELECT id, name, phone, email FROM m_user LIMIT ? OFFSET ?";
        List<User> users = jdbcTemplate.query(getAllUsersQuery,
                (rs, rowNum) -> new User(rs.getString("id"), rs.getString("name"), rs.getString("phone"), rs.getString("email")),
                pageable.getPageSize(), pageable.getOffset());

        // **Mapping User ke UserResponse**
        List<UserResponse> userResponses = users.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        // Mengembalikan Page<UserResponse>
        return new PageImpl<>(userResponses, pageable, users.size());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteUserById(String id) {
        // Menghapus data pengguna berdasarkan ID
        String deleteUserQuery = "DELETE FROM m_user WHERE id = ?";
        jdbcTemplate.update(deleteUserQuery, id);
    }

    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .phone(user.getPhone())
                .email(user.getEmail())
                .build();
    }
}
