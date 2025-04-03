package com.mine.blog.controller.api;

import com.mine.blog.config.auth.PrincipalDetail;
import com.mine.blog.dto.ReplySaveRequestDto;
import com.mine.blog.dto.ResponseDTO;
import com.mine.blog.model.Board;
import com.mine.blog.model.Reply;
import com.mine.blog.model.RoleType;
import com.mine.blog.model.User;
import com.mine.blog.service.BoardService;
import com.mine.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class BoardApiController {


    @Autowired
    private BoardService boardService;

    @PostMapping("/api/board")
    public ResponseDTO<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal){
        boardService.글쓰기(board, principal.getUser());
        return new ResponseDTO<>(HttpStatus.OK.value(), 1);
    }

    @DeleteMapping("/api/board/{id}")
    public ResponseDTO<Integer> deleteById(@PathVariable int id){
        boardService.글삭제(id);
        return new ResponseDTO<>(HttpStatus.OK.value(), 1);
    }

    @PutMapping("/api/board/{id}")
    public ResponseDTO<Integer> update(@PathVariable int id, @RequestBody Board board){
        boardService.글수정(id, board);
        return new ResponseDTO<>(HttpStatus.OK.value(), 1);
    }

    @PostMapping("/api/board/{boardId}/reply")
    public ResponseDTO<Integer> replySave(@RequestBody ReplySaveRequestDto replySaveRequestDto){


        boardService.댓글작성(replySaveRequestDto);
        return new ResponseDTO<>(HttpStatus.OK.value(), 1);
    }

    @DeleteMapping("/api/board/{boardId}/reply/{replyId}")
    public ResponseDTO<Integer> replyDelete(@PathVariable int replyId){
        boardService.댓글삭제(replyId);
        return new ResponseDTO<>(HttpStatus.OK.value(), 1);
    }

}
