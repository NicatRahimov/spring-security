package az.coders.taskmanagement.controller;

import az.coders.taskmanagement.dto.*;
import az.coders.taskmanagement.entities.User;
import az.coders.taskmanagement.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {


    private final AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<User> signUp(@RequestBody SignUpRequest sign){
        return new ResponseEntity<>(authService.signUpReq(sign), HttpStatusCode.valueOf(200));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> signIn(@RequestBody SIgnInRequest sign){
        return new ResponseEntity<>(authService.signIn(sign), HttpStatusCode.valueOf(200));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthResponse>refresh(@RequestBody RefreshTokenRequest refresh){
        return new ResponseEntity<>(authService.refreshToken(refresh)
                ,HttpStatusCode.valueOf(200));

    }

    @PostMapping("/grant")
    public JwtAuthResponse grantUser(@RequestBody GrantRequest grantRequest){
       return authService.grant(grantRequest);


    }

}
