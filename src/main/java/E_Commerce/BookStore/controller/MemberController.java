package E_Commerce.BookStore.controller;

import E_Commerce.BookStore.domain.Member;
import E_Commerce.BookStore.domain.Role;
import E_Commerce.BookStore.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class MemberController {
    @Autowired
    private MemberService memberService;

    @GetMapping("/users")
    public String listAll(Model model){
        List<Member> listMembers = memberService.listAll();
        model.addAttribute("listUsers", listMembers);

        return "users";
    }

    @GetMapping("/users/new")
    public String newMember(Model model){
        List<Role> listRoles = memberService.listRoles();
        Member member = new Member();
        member.setEnabled(true);

        model.addAttribute("user", member);
        model.addAttribute("listRoles", listRoles);

        return "user_form";
    }

    @PostMapping("/users/save")
    public String saveMember(Member member, RedirectAttributes redirectAttributes){
        memberService.save(member);

        redirectAttributes.addFlashAttribute("message", "신규유저가 성공적으로 등록되었습니다.");

        return "redirect:/users";
    }
}
