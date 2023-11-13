package az.coders.taskmanagement.services;

import az.coders.taskmanagement.dto.*;
import az.coders.taskmanagement.entities.User;
import az.coders.taskmanagement.enums.Role;
import az.coders.taskmanagement.repositories.UserRepository;
import az.coders.taskmanagement.services.Impl.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;


    public User signUpReq(SignUpRequest signUpRequest){
        User user = new User();

        if(userRepository.findByUsername(signUpRequest.getEmail())==null){
            user.setFirstName(signUpRequest.getFirstName());
            user.setSecondName(signUpRequest.getLastName());
            user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
            user.setUsername(signUpRequest.getEmail());
            user.setRole(Role.USER);

            userRepository.save(user);
        }

        return user;

    }

    public JwtAuthResponse signIn(SIgnInRequest sIgnInRequest){
        System.out.println(sIgnInRequest.getPassword());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(sIgnInRequest.getEmail(),
                sIgnInRequest.getPassword()));

        User user = userRepository.findByUsername(sIgnInRequest.getEmail());
        String jwt = jwtService.generateToken(user);

        String refreshToken = jwtService.generateRefreshToken(new HashMap<>(),user);
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();


        jwtAuthResponse.setToken(jwt);
        jwtAuthResponse.setRefreshToken(refreshToken);

        return jwtAuthResponse;
    }

    public JwtAuthResponse refreshToken (RefreshTokenRequest refresh){

        String token = refresh.getToken();
        String username = jwtService.extractUsername(token);
        User user = userRepository.findByUsername(username);


        if (jwtService.isTokenValid(token,user)){
            String newToken = jwtService.generateToken(user);

            JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();

            jwtAuthResponse.setToken(newToken);
            jwtAuthResponse.setRefreshToken(refresh.getToken());


            return jwtAuthResponse;
        }else return null;

    }

    public JwtAuthResponse grant(GrantRequest grantRequest) {
        String token = grantRequest.getToken();
        String username = jwtService.extractUsername(token);
        User user = userRepository.findByUsername(username);


        user.setRole(Role.ADMIN);

        userRepository.save(user);

      return JwtAuthResponse.builder()
               .token(jwtService.generateToken(user))
               .build();
    }
}
