package com.profile.profile_back.controllers;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.profile.profile_back.dtos.RegisterUserRequest;
import com.profile.profile_back.dtos.UpdateUserRequest;
import com.profile.profile_back.dtos.UserDto;
import com.profile.profile_back.dtos.UserDtoListResponse;
import com.profile.profile_back.dtos.UserDtoResponse;
import com.profile.profile_back.entities.User;
import com.profile.profile_back.repositories.UserRepository;

import lombok.AllArgsConstructor;




@RestController
@AllArgsConstructor
@RequestMapping("users")
public class UserController {
    private final UserRepository userRepository;

    // public UserController(UserRepository userRepository) {
    //     this.userRepository = userRepository;
    // }

    @GetMapping
    public UserDtoListResponse getAllUsers(
        @RequestHeader (required = true, name="x-auth-token") String authToken,
        @RequestParam (required = false, defaultValue = "") String orderBy,
        @RequestParam (required = false, defaultValue = "asc") String sort
    ) {
        if (!Set.of("name", "phone", "email").contains(orderBy)) {
            orderBy = "name";
        }
        Sort sortObj = sort.equalsIgnoreCase("desc") ? Sort.by(orderBy).descending() : Sort.by(orderBy).ascending();
        Iterable<UserDto> users = userRepository.findAll(sortObj).stream()
                .map(user -> new UserDto(
                    user.getId(),
                    user.getName(),
                    user.getPhone(),
                    user.getEmail(),
                    user.getCreatedAt(),
                    user.getUpdatedAt())
                ).toList();
        // System.out.println("users: " + users);
        return new UserDtoListResponse(200, "success", users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDtoResponse> getUserById(@PathVariable Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            // return ResponseEntity.notFound().build();
            return ResponseEntity.ok(new UserDtoResponse(404, "User not found", null));
        }
        UserDto userDto = new UserDto(
            user.getId(),
            user.getName(),
            user.getPhone(),
            user.getEmail(),
            user.getCreatedAt(),
            user.getUpdatedAt()
        );
        return ResponseEntity.ok(new UserDtoResponse(200, "success", userDto));
    }

    @PostMapping()
    public ResponseEntity<UserDtoResponse> createUser(
        @RequestBody RegisterUserRequest request,
        UriComponentsBuilder uriBuilder
    ) {
        User user = new User(
            request.getName(),
            request.getPhone(),
            request.getEmail(),
            request.getPassword()
        );
        // System.out.println("Creating user: " + user);
        userRepository.save(user);
        UserDto userDto = new UserDto(
            user.getId(),
            user.getName(),
            user.getPhone(),
            user.getEmail(),
            user.getCreatedAt(),
            user.getUpdatedAt()
        );
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();

        return ResponseEntity.created(uri).body(new UserDtoResponse(201, "User created successfully", userDto));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<UserDtoResponse> updateUser(
        @PathVariable Long id,
        @RequestBody UpdateUserRequest request
    ) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            // return ResponseEntity.notFound().build();
            return ResponseEntity.ok().body(new UserDtoResponse(404, "User not found", null));
        }
        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        // user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
        UserDto userDto = new UserDto(
            user.getId(),
            user.getName(),
            user.getPhone(),
            user.getEmail(),
            user.getCreatedAt(),
            user.getUpdatedAt()
        );
        return ResponseEntity.ok().body(new UserDtoResponse(200, "User updated successfully", userDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDtoResponse> deleteUser(@PathVariable Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            // return ResponseEntity.notFound().build();
            return ResponseEntity.ok().body(new UserDtoResponse(404, "User not found", null));
        }
        userRepository.delete(user);
        return ResponseEntity.ok().body(new UserDtoResponse(200, "user deleted successfully", null));
    }

}
