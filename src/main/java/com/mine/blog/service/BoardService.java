package com.mine.blog.service;

import com.mine.blog.dto.ReplySaveRequestDto;
import com.mine.blog.model.Board;
import com.mine.blog.model.Reply;
import com.mine.blog.model.User;
import com.mine.blog.repository.BoardRepository;
import com.mine.blog.repository.ReplyRepository;
import com.mine.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void 글쓰기(Board board, User user) {
        board.setViews(0);
        board.setUser(user);
        boardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public Page<Board> 글목록(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Board 글상세보기(int id) {
        return boardRepository.findById(id)
                .orElseThrow(()->{
                    return new IllegalArgumentException("글을 찾을 수 없습니다");
                });
    }

    @Transactional
    public void 글삭제(int id){
        boardRepository.deleteById(id);
    }

    @Transactional
    public void 글수정(int id, Board requestBoard){
        Board board = boardRepository.findById(id)
                .orElseThrow(()->{
                    return new IllegalArgumentException("글을 찾을 수 없습니다");
                });
        board.setTitle(requestBoard.getTitle());
        board.setContent(requestBoard.getContent());
    }

    @Transactional
    public void 댓글작성(ReplySaveRequestDto replySaveRequestDto){

        replyRepository.mSave(replySaveRequestDto.getUserId(), replySaveRequestDto.getBoardId(), replySaveRequestDto.getContent());
    }

    public void 댓글삭제(int replyId) {
        replyRepository.deleteById(replyId);
    }
}
