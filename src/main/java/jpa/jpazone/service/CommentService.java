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

    /**
     * 게시판 id로 해당 게시판의 댓글들을 모두 가져오려고 만든 메소드이나
     * 현재로썬 확인 불가능한 exception 발생으로 다른방법을 사용하여 댓글 가져오는 것으로 처리.
     * @param boardId
     * @return
     */
    public List<Comment> findAllCommentByBoardId(Long boardId) {
        log.info("[[ Service - findAllComment ]]");
        return commentRepository.findAllCommentByBoardId(boardId);

    }
}
