package jpa.jpazone.service;

import jpa.jpazone.controller.form.CommentForm;
import jpa.jpazone.domain.Board;
import jpa.jpazone.domain.Comment;
import jpa.jpazone.domain.Member;
import jpa.jpazone.repository.BoardRepository;
import jpa.jpazone.repository.CommentRepository;
import jpa.jpazone.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long saveComment(CommentForm comment, Long board_id, Member member) {
        log.info("[[ Service - saveComment ]]");
        //Board 엔티티
        Board findBoard = boardRepository.findBoard(board_id);
        //Member 엔티티
        Member findMember = memberRepository.findOne(member.getId());
        //입력한 댓글 내용
        String comment_content = comment.getComment_content();

        //새로운 Comment 엔티티 생성
        Comment newComment = Comment.createComment(findBoard, findMember, findMember.getName(), comment_content, LocalDateTime.now());

        commentRepository.save(newComment);

        return newComment.getId();
    }
}
