package com.billnex.billManage.service.Impl;

import com.billnex.billManage.dto.SignInRequestDto;
import com.billnex.billManage.dto.SignInResponseDto;
import com.billnex.billManage.dto.UserRequestDto;
import com.billnex.billManage.entity.User;
import com.billnex.billManage.entity.UserStatus;
import com.billnex.billManage.repository.UserRepository;
import com.billnex.billManage.service.AuthService;
import com.billnex.billManage.service.JWTService;
import com.billnex.billManage.utils.CommonDBMethods;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CommonDBMethods commonDBMethods;

    @Autowired
    JWTService jwtService;

    @Override
    public void signUp(UserRequestDto userRequestDto) {
        Optional<User> existingUser = userRepository.findByEmail(userRequestDto.getEmail());
        if(existingUser.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }
        User user = modelMapper.map(userRequestDto, User.class);
        user.setCreatedOn(LocalDateTime.now());
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setModifiedOn(LocalDateTime.now());
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);
    }

    @Override
    public SignInResponseDto signIn(SignInRequestDto signInRequestDto) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        signInRequestDto.getEmail(),
                        signInRequestDto.getPassword())
                );
        User user = commonDBMethods.findUserByEmail(signInRequestDto.getEmail());
        String token = jwtService.generateToken(user);

        return new SignInResponseDto(token);
    }
}
