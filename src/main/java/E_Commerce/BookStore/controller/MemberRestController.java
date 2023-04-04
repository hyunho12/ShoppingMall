package E_Commerce.BookStore.controller;

import E_Commerce.BookStore.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberRestController {
    @Autowired
    private MemberService memberService;

    @PostMapping("/users/check_email")
    public String checkDuplicateEmail(@Param("id") Long id, @Param("email") String email){
        return memberService.isEmailUnique(id, email) ? "OK" : "Duplicated";
    }
}
