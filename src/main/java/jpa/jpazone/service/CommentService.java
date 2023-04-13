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
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public Comment findCommentById(Long id) {
        log.info("[[ Service - findComment ]]");

        return commentRepository.findComment(id);
    }

    @Transactional
    public Long saveComment(String comment_content, Long board_id, Member member) {
        log.info("[[ Service - saveComment ]]");
        //Board 엔티티
        Board findBoard = boardRepository.findBoard(board_id);
        //Member 엔티티
        Member findMember = memberRepository.findOne(member.getId());

        //새로운 Comment 엔티티 생성
        Comment newComment = Comment.createComment(findBoard, findMember, findMember.getName(), comment_content, LocalDateTime.now());

        commentRepository.save(newComment);

        return newComment.getId();
    }

    @Transactional
    public Long saveRecomment(String recomment, Long parentComment_id, Long board_id, Member member) {
        log.info("[[ Service - saveRecomment ]]");
        //Board 엔티티
        Board findBoard = boardRepository.findBoard(board_id);
        //Member 엔티티
        Member findMember = memberRepository.findOne(member.getId());
        //parentComment 엔티티
        Comment parentComment = commentRepository.findComment(parentComment_id);
        //recomment 생성
        Comment newRecomment = Comment.createRecomment(findBoard, findMember, findMember.getName(),
                                                        recomment, parentComment_id, LocalDateTime.now());
        //recomment parent 세팅 및 childList 세팅
        newRecomment.addChildComment(newRecomment, parentComment);

        //save 대댓글
        commentRepository.save(newRecomment);

        return newRecomment.getId();
    }

    @Transactional
    public void updateComment(Long comment_id, String comment_content) {
        log.info("[[ Service - updateComment ]]");
        //Comment 엔티티
        Comment comment = commentRepository.findComment(comment_id);
        //comment_content 내용 수정
        comment.change(comment_content, LocalDateTime.now());
    }

    @Transactional
    public void deleteComment(Long comment_id) {
        log.info("[[ Service - deleteComment ]]");
        //Comment 엔티티
        Comment comment = commentRepository.findComment(comment_id);
        //Comment 엔티티 isRemoved 필드의 값을 true로 변경
        comment.delete(comment);
    }

    public List<Comment> findBoardComments(Long boardId){
        log.info("[[ Service - findBoardComments ]]");

        return commentRepository.findBoardComments(boardId);
    }
}
