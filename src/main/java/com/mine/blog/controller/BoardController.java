package com.mine.blog.controller;

import com.mine.blog.config.auth.PrincipalDetail;
import com.mine.blog.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping({"", "/"})
    public String index(
            @AuthenticationPrincipal PrincipalDetail principal,
            Model model,
            @PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        if (principal != null) {
            System.out.println("현재 로그인 유저: " + principal.getUsername());
        } else {
            System.out.println("현재 로그인 유저 없음");
        }

        model.addAttribute("boards", boardService.글목록(pageable));

        return "index";
    }


    @GetMapping("/board/{id}")
    public String findById(@PathVariable int id,Model model,@AuthenticationPrincipal PrincipalDetail principalDetail){
        model.addAttribute("board", boardService.글상세보기(id));
        model.addAttribute("principal", principalDetail);
        return "board/detail";
    }

    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable int id,Model model){
        model.addAttribute("board", boardService.글상세보기(id));
        return "board/updateForm";
    }

    @GetMapping("/board/saveForm")
    public String saveForm(@AuthenticationPrincipal PrincipalDetail principal) {
        return "board/saveForm";
    }


}
