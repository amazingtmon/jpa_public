$(function () {

/*
=========== jquery 전역함수 ===========
		$.myfunc = function () {
			$('img').animate({
				opacity: 0.2
			}, 'slow', function() {
				alert('완료');
			});
		};

		$(function () {
			$.myfunc();
		});
======================================
*/
    $.fn.validateReComment = function (content) {
        //댓글 내용이 없을 경우 false
        if(content == ""){
            return false;
        } else {
            return true;
        }
    };

    /* 댓글등록 function */
    $('.addComment').each(function(index){
        $(this).on('click', function(e){
            let rc_body = $(this).offsetParent().offsetParent().find("#reComment_body");
            let reComment_html = "";
                reComment_html = `
                      <div class="form-group mb-3">
                        <form class="reComment">
                          <div class="form-group">
                            <label for="reComment_content" class="col-sm-2 control-label" style="float: left">답글</label>
                            <div class="col-auto">
                              <textarea id="reComment_content" class="reComment_content form-control mb-1" placeholder="내용을 입력해주세요.."></textarea>
                            </div>
                          </div>
                          <div class="form-group ml-2">
                            <button type="button" id="reComment_register" class="reComment_register btn btn-primary waves-effect waves-light">등록</button>
                            <button type="button" id="reComment_cancel" class="reComment_cancel btn btn-danger waves-effect waves-light">취소</button>
                          </div>
                        </form>
                      </div>
                      `;
            //답글 폼 생성
            rc_body.html(reComment_html);

            //답글 등록 취소
            $(document).on('click','#reComment_cancel', function(e){
                //현재 이벤트가 상위뿐만 아니라 현재 레벨에 걸린 다른 이벤트도 동장하지 않도록 중단
                e.stopImmediatePropagation();
                let rc_body2 = $(this).offsetParent().find("#reComment_body");
                rc_body2.html("");
            });

            //답글 등록
            $(document).on('click','#reComment_register', function(e){
                e.stopImmediatePropagation();
                let content = $("#reComment_content").val();
                let comment_id = $(this).offsetParent().find(".comment_id").val();
                let board_id = $(this).offsetParent().find("#board_id").val();

                //대댓글 content validation
                if(!$.fn.validateReComment(content)) {
                    alert("댓글 내용을 입력해주세요.")
                    return;
                }
                let result = confirm("댓글을 등록하시겠습니까?");
                if(!result) return;
                let jsonData = JSON.stringify({
                    "recomment" : content,
                    "parentComment_id": comment_id,
                    "board_id": board_id
                });
                $.ajax({
                    type: "post",
                    url: "/api/recomment/post",
                    data: jsonData,
                    contentType: 'application/json',
                    success: function (data) {
                            alert("댓글 등록이 완료되었습니다.");
                            location.reload();
                    },
                    error: function (request, status, error) {
                        alert("code: " + request.status + "\n" + "error: " + request.responseText);
                    }
                });// end of ajax
            });// end of reComment_register
        });// end click function
    });// end of addComment function

    /* 댓글수정 function */
    $('.updateComment').each(function(index){
        $(this).on('click', function(e){
            let comment_id = $(this).offsetParent().find(".comment_id").val();
            console.log("comment_id = "+comment_id);
            let comment_content = $(this).offsetParent().find(".comment_content").val();
            //댓글 내용이 공백일 때
            if(comment_content == '') {
                alert("댓글 내용을 입력해주세요.");
                return;
            }
            if(!confirm("댓글을 수정하시겠습니까?")) return;
            let jsonData = JSON.stringify({
                            "comment_id": comment_id,
                            "comment_content": comment_content
                           });
            $.ajax({
                type: "put",
                url: "/api/comment",
                data: jsonData,
                contentType: 'application/json',
                success: function(data){
                    alert("수정이 완료됐습니다.");
                    location.reload();
                },
                error: function (request, status, error) {
                    alert("code: " + request.status + "\n" + "error: " + request.responseText);
                }
            });// end of ajax
        });// end click function
    });// end of updateComment function

    /* 댓글삭제 function */
    deleteComment = function (comment_id){
        let delete_confirm = confirm("해당 댓글을 삭제하시겠습니까?");
        if(!delete_confirm) return;
        let jsonData = JSON.stringify({
            "comment_id": comment_id
        });
        $.ajax({
            type: "delete",
            url: "/api/comment",
            data: jsonData,
            contentType: 'application/json',
            success: function(data){
                alert("삭제되었습니다.");
                location.reload();
            },
            error: function (request, status, error) {
                alert("code: " + request.status + "\n" + "error: " + request.responseText);
            }
        });//end of ajax
    };// end of deleteComment function

}); // end jquery