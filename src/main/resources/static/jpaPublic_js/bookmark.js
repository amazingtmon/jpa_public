$(function () {

    bookmark = function (session_loginMemberName, board_id) {
        let id = board_id;
        let name = session_loginMemberName;
        let result = confirm("북마크 하시겠습니까?");
        if(!result) return;
        let jsonData = JSON.stringify({
            "board_id" : id,
            "member_name" : name
        });
        $.ajax({
            type: "post",
            url: "/api/bookmark/board",
            data: jsonData,
            contentType: 'application/json',
            success: function(data){
                if(data == "update"){
                    alert("북마크 재등록 완료!!");
                    location.reload();
                    return;
                }
                alert("북마크 완료!!");
                location.reload();
            },
            error: function (request, status, error) {
                alert("code: " + request.status + "\n" + "error: " + request.responseText);
            }
        });//end of ajax
    }//end of bookmark function

    bookmark_cancel = function(session_loginMemberName, board_id){
        let id = board_id;
        let name = session_loginMemberName;
        let result = confirm("북마크를 해제 하시겠습니까?");
        if(!result) return;
        let jsonData = JSON.stringify({
            "board_id" : id,
            "member_name" : name
        });
        $.ajax({
            type: "put",
            url: "/api/bookmark/board",
            data: jsonData,
            contentType: 'application/json',
            success: function(data){
            alert("북마크를 해제했습니다.");
            location.reload();
            },
            error: function (request, status, error) {
                alert("code: " + request.status + "\n" + "error: " + request.responseText);
            }
        });//end of ajax
    }//end of bookmark_cancel function

}); // end jquery