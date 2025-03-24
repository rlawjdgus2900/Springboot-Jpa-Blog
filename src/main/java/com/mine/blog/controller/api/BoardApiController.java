package com.mine.blog.controller.api;

import com.mine.blog.config.auth.PrincipalDetail;
import com.mine.blog.dto.ResponseDTO;
import com.mine.blog.model.Board;
import com.mine.blog.model.RoleType;
import com.mine.blog.model.User;
import com.mine.blog.service.BoardService;
import com.mine.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoardApiController {


    @Autowired
    private BoardService boardService;

    @PostMapping("/api/board")
    public ResponseDTO<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal){
        boardService.글쓰기(board, principal.getUser());
        return new ResponseDTO<>(HttpStatus.OK.value(), 1);
    }



}
