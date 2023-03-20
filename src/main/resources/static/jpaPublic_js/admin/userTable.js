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

    /* 데이터테이블 관련 함수 */
    initDataTableFunction = function(table_id){
        table_id.DataTable({
          "paging": true,
          "lengthChange": false,
          "searching": false,
          "ordering": true,
          "info": true,
          "autoWidth": false,
          "responsive": true,
        });
    }

    /* 유저 컨텐츠별 생성갯수 */
    userStatistics = function(){
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
    }// end of userStatistics function

    /* 유저 컨텐츠갯수 테이블 생성 */
    getNewTable_userStatistics = function(data){
        let userData_total_count = data.count;
        let userStatistics_data = data.data;
        let table_html = `
        <div class="row">
            <div class="col-sm-12">
                <table id="statistics-table" class="table table-bordered table-hover dataTable dtr-inline" aria-describedby="statistics-table_info">
                    <thead>
                        <tr>
                            <th class="sorting sorting_asc" tabindex="0" aria-controls="statistics-table" rowspan="1" colspan="1" aria-sort="ascending" aria-label="id engine: activate to sort column descending">id</th>
                            <th class="sorting" tabindex="0" aria-controls="statistics-table" rowspan="1" colspan="1" aria-label="name: activate to sort column ascending">name</th>
                            <th class="sorting" tabindex="0" aria-controls="statistics-table" rowspan="1" colspan="1" aria-label="Board Count: activate to sort column ascending">Board Count</th>
                            <th class="sorting" tabindex="0" aria-controls="statistics-table" rowspan="1" colspan="1" aria-label="Comment Count: activate to sort column ascending">Comment Count</th>
                            <th class="sorting" tabindex="0" aria-controls="statistics-table" rowspan="1" colspan="1" aria-label="Bookmark Count: activate to sort column ascending">Bookmark Count</th>
                            <th class="sorting" tabindex="0" aria-controls="statistics-table" rowspan="1" colspan="1" aria-label="News Count: activate to sort column ascending">News Count</th>
                            <th class="sorting" tabindex="0" aria-controls="statistics-table" rowspan="1" colspan="1" aria-label="Report Count: activate to sort column ascending">Report Count</th>
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
        for(let i = 0; i < userStatistics_data.length; i++){
            let user_id = userStatistics_data[i].user_id;
            let user_name = userStatistics_data[i].name;
            let board_count = userStatistics_data[i].board_count;
            let comment_count = userStatistics_data[i].comment_count;
            let bookmark_count = userStatistics_data[i].bookmark_count;
            let news_count = userStatistics_data[i].news_count;
            let report_count = userStatistics_data[i].report_count;
            let tbody_html = `
            <tr>
                <td class="dtr-control sorting_1" tabindex="0">${user_id}</td>
                <td class>${user_name}</td>
                <td class>${board_count}</td>
                <td class>${comment_count}</td>
                <td class>${bookmark_count}</td>
                <td class>${news_count}</td>
                <td class>${report_count}</td>
            </tr>
            `;
            user_tbody.append(tbody_html);
        }// end of for
        let table_id = $('#statistics-table');
        initDataTableFunction(table_id);
    }// end of getNewTable_userStatistics function

}); // end jquery