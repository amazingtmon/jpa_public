$(function () {

    /*
        LocalDateTime format change
        ex) '2023-02-21T21:30:34.503003' ->  '2023-02-21 21:30:34'
    */
    changeDateTimeFormat = function(string_date){
        let filter = string_date.replace(/T/g , ' '); // '2023-02-21 21:30:34.503003'
        let index = filter.indexOf('.'); // 소수점 자리의 index = 19
        let result = filter.substring(0, 19); // '2023-02-21 21:30:34'

        return result;
    }

    /* 북마크 게시물 가져오기 function */
    getBookmarkBoards = function(){
        let id = $('#nav-myBoard-tab').attr("href");
        let load_url = $('#nav-myBoard-tab').data("url")
        $(id).load(load_url);
    }// end of getBookmarkBoards function

    /* 북마크 취소 게시물 function */
    getCanceledBoards = function(){
        $.ajax({
            type: "get",
            url: '/api/bookmark/board-canceled',
            contentType: 'application/json',
            success: function (data) {
                getNewTable_CanceledBoards(data);
            },
            error: function (request, status, error) {
                alert("code: " + request.status + "\n" + "error: " + request.responseText);
            }
        });// end of ajax
    }

    /* 북마크 취소 게시물 new table */
    getNewTable_CanceledBoards = function(data){
        if(data.count == 0) {
            alert("취소한 북마크 게시물이 없습니다.");
            return;
        }
        //버튼 토글 function
        $('.get_bookmark').toggleClass('bookmark_button_hide');
        $('.deleted_bookmark').toggleClass('bookmark_button_hide');
        let dbBookmarkBoard = data.bookmark_data;
        let current_thead = $('thead');
        let current_tbody = $('tbody');
        // 현재 thead, tbody html 삭제
        current_thead.html('');
        current_tbody.html('');
        let thead_html = `
          <tr>
            <th class="col-md-1">게시글 NO.</th>
            <th class="col-md-4">게시글 제목</th>
            <th class="col-md-2">작성자</th>
            <th class="col-md-3">북마크 취소 시간</th>
            <th class="col-md-1"></th>
          </tr>
        `;
        // 새로운 thead 추가
        current_thead.append(thead_html);
        for(let i = 0; i < dbBookmarkBoard.length; i++){
            let bookmark_id = dbBookmarkBoard[i].bookmark_id;
            let board_id = dbBookmarkBoard[i].board_id;
            let board_title = dbBookmarkBoard[i].title;
            let board_name = dbBookmarkBoard[i].name;
            let bookmark_cancel_time = changeDateTimeFormat(dbBookmarkBoard[i].bookmark_cancel_time);
            let tbody_html = `
              <tr>
                <td>${board_id}</td>
                <td><a href="/board/post/${board_id}">${board_title}</a></td>
                <td>${board_name}</td>
                <td>${bookmark_cancel_time}</td>
                <td>
                  <button class="btn btn-sm btn-danger" onclick="deleteBookmarkData(${bookmark_id})"/>del</button>
                </td>
              </tr>
            `;
            //새로운 tbody 추가
            current_tbody.append(tbody_html);
        }// end of for

    }// end of getNewTable_CanceledBoards function

    /* 북마크 데이터 영구삭제 */
    deleteBookmarkData = function(bookmark_id){
        console.log("bookmark_id = "+bookmark_id);
        if(!confirm("해당 게시물을 북마크 데이터에서 \"영구삭제\"하시겠습니까?")) return;
        let jsonData = JSON.stringify({
            bookmark_id : bookmark_id
        });
        $.ajax({
            type: "delete",
            url: '/api/bookmark/board',
            data : jsonData,
            contentType: 'application/json',
            success: function (data) {
                alert("북마크 데이터 영구삭제완료!!");
                //북마크 게시물로 이동
                getBookmarkBoards();
            },
            error: function (request, status, error) {
                alert("code: " + request.status + "\n" + "error: " + request.responseText);
            }
        });// end of ajax

    };// end of deleteBookmark function

}); // end jquery