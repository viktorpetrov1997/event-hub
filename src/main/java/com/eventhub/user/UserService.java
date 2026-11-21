package com.eventhub.user;

import com.eventhub.user.dto.UserDto;
import com.eventhub.user.dto.UserRegisterRequest;
import com.eventhub.user.exception.UserAlreadyExistsException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto register(UserRegisterRequest userRegisterRequest)
    {
        if(userRepository.existsByUsername(userRegisterRequest.getUsername())
                || userRepository.existsByEmail(userRegisterRequest.getEmail()))
        {
            throw new UserAlreadyExistsException("User with this username or email already exists!");
        }

        String encodedPassword = passwordEncoder.encode(userRegisterRequest.getPassword());

        User user = UserMapper.toUserEntity(userRegisterRequest, encodedPassword);

        User savedUser = userRepository.save(user);

        return UserMapper.toUserDto(savedUser);
    }
}
