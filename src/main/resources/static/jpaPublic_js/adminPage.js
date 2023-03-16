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

    userStatistics = function(){
        console.log('userStatistics');
        $('#content-header-text').text('User Contents Statistics');
        $('#main-container').html('');
                $.ajax({
                    type: "get",
                    url: '/api/admin-user/statistics',
                    contentType: 'application/json',
                    success: function (data) {
                        getNewTable_userStatistics(data);
                    },
                    error: function (request, status, error) {
                        alert("code: " + request.status + "\n" + "error: " + request.responseText);
                    }
                });// end of ajax
    }

    getNewTable_userStatistics = function(data){
        console.log("getNewTable_userStatistics data = ", data);
        let user_count = data.count;
        let contents_count = data.data;
        let table_html = `
        <div class="row">
            <div class="col-sm-12">
                <table id="user-statistics-table" class="table table-bordered table-hover dataTable dtr-inline" aria-describedby="user-statistics-table_info">
                    <thead>
                        <tr>
                            <th class="sorting sorting_asc" tabindex="0" aria-controls="user-statistics-table" rowspan="1" colspan="1" aria-sort="ascending" aria-label="id engine: activate to sort column descending">id</th>
                            <th class="sorting" tabindex="0" aria-controls="user-statistics-table" rowspan="1" colspan="1" aria-label="name: activate to sort column ascending">name</th>
                            <th class="sorting" tabindex="0" aria-controls="user-statistics-table" rowspan="1" colspan="1" aria-label="Board Count: activate to sort column ascending">Board Count</th>
                            <th class="sorting" tabindex="0" aria-controls="user-statistics-table" rowspan="1" colspan="1" aria-label="Comment Count: activate to sort column ascending">Comment Count</th>
                            <th class="sorting" tabindex="0" aria-controls="user-statistics-table" rowspan="1" colspan="1" aria-label="News Count: activate to sort column ascending">News Count</th>
                        </tr>
                    </thead>
                    <tbody class="user-contents-tbody">
                    </tbody>
                </table>
            </div>
        </div>
        `;
        $('#main-container').append(table_html);
        let user_tbody = $('.user-contents-tbody');
        for(let i = 0; i < contents_count.length; i++){
            let user_id = contents_count[i].user_id;
            let user_name = contents_count[i].name;
            let board_count = contents_count[i].board_count;
            let comment_count = contents_count[i].comment_count;
            let news_count = contents_count[i].news_count;
            let tbody_html = `
            <tr>
                <td class="dtr-control sorting_1" tabindex="0">${user_id}</td>
                <td class>${user_name}</td>
                <td class>${board_count}</td>
                <td class>${comment_count}</td>
                <td class>${news_count}</td>
            </tr>
            `;
            user_tbody.append(tbody_html);
        }// end of for
        // 데이터테이블 각 기능 관련된 함수
        $('#user-statistics-table').DataTable({
          "paging": true,
          "lengthChange": true,
          "searching": false,
          "ordering": true,
          "info": true,
          "autoWidth": false,
          "responsive": true,
        });
    }

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

}); // end jquery