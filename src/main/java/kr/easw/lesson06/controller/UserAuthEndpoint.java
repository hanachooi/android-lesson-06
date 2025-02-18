package kr.easw.lesson06.controller;
import kr.easw.lesson06.model.dto.ExceptionalResultDto;
import kr.easw.lesson06.model.dto.UserAuthenticationDto;
import kr.easw.lesson06.model.dto.UserDataEntity;
import kr.easw.lesson06.service.UserDataService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class UserAuthEndpoint {
    private final UserDataService userDataService;
    private final BCryptPasswordEncoder encoder;


    // JWT 인증을 위해 사용되는 엔드포인트입니다.
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserDataEntity entity) {
        try {
            // 로그인을 시도합니다.
            return ResponseEntity.ok(userDataService.createTokenWith(entity));
        } catch (Exception ex) {
            // 만약 로그인에 실패했다면, 400 Bad Request를 반환합니다.
            return ResponseEntity.badRequest().body(new ExceptionalResultDto(ex.getMessage()));
        }
    }

    @PostMapping("/register")
    public void register(@ModelAttribute UserDataEntity entity) {
        // 유저 회원가입을 구현하십시오.
        // 해당 메서드를 작성하기 위해서는, UserDataService와 admin_dashboard.html을 참고하십시오.
        // 해당 메서드는 register.html에서 사용됩니다.
        throw new IllegalStateException("Not implemented yet");
        System.out.println(entity.getUserId());
        if (!userDataService.isUserExists(entity.getUserId())) {
            try {
                String pw = entity.getPassword();
                entity.setPassword(encoder.encode(pw));
                System.out.println(pw);
                System.out.println(entity.getPassword());
                userDataService.createUser(entity);
                System.out.println("Success");
            } catch (Exception ex) {
                System.out.println("Problem while create user.");
            }
        }
        //throw new IllegalStateException("Not implemented yet");
    }
}