package org.example.likelion.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "User Resource")
public class UserController {
//    @Autowired
//    private AuthenticationService authenticationService;
//    @Autowired
//    UserRepository userRepository;
//
//    @Operation(summary = "Get User Info")
//    @GetMapping("/userInfo")
//    @ResponseStatus(HttpStatus.OK)
//    public Optional<User> getUserInfo() throws IOException {
//        return authenticationService.getCurrentUserInfo();
//    }
}
