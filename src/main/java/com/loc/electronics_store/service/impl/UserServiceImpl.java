package com.loc.electronics_store.service.impl;

import com.loc.electronics_store.dto.request.user.UserCreationRequest;
import com.loc.electronics_store.dto.request.user.UserUpdateRequest;
import com.loc.electronics_store.dto.response.user.UserResponse;
import com.loc.electronics_store.entity.User;
import com.loc.electronics_store.enums.Role;
import com.loc.electronics_store.exception.AppException;
import com.loc.electronics_store.exception.ErrorCode;
import com.loc.electronics_store.mapper.UserMapper;
import com.loc.electronics_store.repository.UserRepository;
import com.loc.electronics_store.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class UserServiceImpl implements UserService {
    final UserRepository userRepository;
    final UserMapper userMapper;
    final PasswordEncoder passwordEncoder;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers() {
        log.info("In methos get users");
        List<User> users = userRepository.findAll();

        List<UserResponse> userReponses = new ArrayList<>();

        for (User user : users) {
            userReponses.add(userMapper.toResponse(user));
        }

        return userReponses;
    }

    @Override
    public UserResponse createUser(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());

        // user.setRoles(roles);

        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponse updateUser(Long userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));

        userMapper.updateUserFromRequest(request, user);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toResponse(user);
    }

    @Override
    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUserById(Long userId) {
        log.info("in method get user");
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toResponse(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return List.of();
    }
}
