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
                console.log("data = "+data);
                alert("북마크 완료!");
                //location.reload();
            },
            error: function (request, status, error) {
                alert("code: " + request.status + "\n" + "error: " + request.responseText);
            }
        });//end of ajax
    }//end of bookmark function


}); // end jquery