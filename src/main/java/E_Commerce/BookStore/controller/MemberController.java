package E_Commerce.BookStore.controller;

import E_Commerce.BookStore.domain.Member;
import E_Commerce.BookStore.domain.Role;
import E_Commerce.BookStore.exception.MemberNotFoundException;
import E_Commerce.BookStore.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class MemberController {
    @Autowired
    private MemberService memberService;

    @GetMapping("/users")
    public String listFirstPage(Model model){
        return listByPage(1, model, "firstName", "asc",null);
    }

    @GetMapping("/users/page/{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model,
                             @Param("sortField") String sortField,
                             @Param("sortDir") String sortDir,
                             @Param("keyword") String keyword){
        Page<Member> page = memberService.listByPage(pageNum, sortField, sortDir, keyword);
        List<Member> listMembers = page.getContent();

        long startCount = (pageNum - 1) * memberService.MEMBER_PER_PAGE + 1;
        long endCount = startCount + memberService.MEMBER_PER_PAGE - 1;
        if(endCount > page.getTotalElements()){
            endCount = page.getTotalElements();
        }

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("listUsers", listMembers);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("keyword", keyword);

        return "users";
    }

    @GetMapping("/users/new")
    public String newMember(Model model){
        List<Role> listRoles = memberService.listRoles();
        Member member = new Member();
        member.setEnabled(true);

        model.addAttribute("user", member);
        model.addAttribute("listRoles", listRoles);
        model.addAttribute("pageTitle", "Create New User");

        return "user_form";
    }

    @PostMapping("/users/save")
    public String saveMember(Member member, RedirectAttributes redirectAttributes){
        memberService.save(member);

        redirectAttributes.addFlashAttribute("message", "신규유저가 성공적으로 등록되었습니다.");

        return "redirect:/users";
    }

    @GetMapping("/users/edit/{id}")
    public String editMember(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes){
        try{
            Member member = memberService.getId(id);
            List<Role> listRoles = memberService.listRoles();

            model.addAttribute("user", member);
            model.addAttribute("pageTitle", "Edit User(ID: "+ id + ")");
            model.addAttribute("listRoles", listRoles);

            return "user_form";

        } catch (MemberNotFoundException ex){
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            return "redirect:/users";
        }
    }

    @GetMapping("/users/delete/{id}")
    public String deleteMember(@PathVariable("id") Long id,
                               Model model,
                               RedirectAttributes redirectAttributes){
        try{
            memberService.deleteId(id);
            redirectAttributes.addFlashAttribute("message",
                     id + "번째 유저가 성공적으로 삭제되었습니다.");
        } catch (MemberNotFoundException ex){
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
        }

        return "redirect:/users";
    }

    @GetMapping("/users/{id}/enabled/{status}")
    public String updateMemberEnabledStatus(@PathVariable("id") Long id,
                                            @PathVariable("status") boolean enabled,
                                            RedirectAttributes redirectAttributes){
        memberService.updateMemberEnabledStatus(id, enabled);
        String status = enabled ? "enabled" : "disabled";
        String message = "The user ID" + id + "has been " + status;
        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/users";
    }
}
