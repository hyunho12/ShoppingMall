package E_Commerce.BookStore.controller;

import E_Commerce.BookStore.domain.Member;
import E_Commerce.BookStore.security.ShopUserDetails;
import E_Commerce.BookStore.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class AccountController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/account")
    public String viewDetails(@AuthenticationPrincipal ShopUserDetails loggedMember,
                              Model model){
        String email = loggedMember.getUsername();
        Member member = memberService.getByEmail(email);

        model.addAttribute("user", member);

        return "account_form";
    }

    @PostMapping("/account/update")
    public String saveDetails(Member member, RedirectAttributes redirectAttributes,
                              @AuthenticationPrincipal ShopUserDetails loggedMember) throws IOException{
        loggedMember.setFirstName(member.getFirstName());
        loggedMember.setLastName(member.getLastName());

        redirectAttributes.addFlashAttribute("message", "회원정보가 수정되었습니다.");

        return "redirect:/account";
    }
}
