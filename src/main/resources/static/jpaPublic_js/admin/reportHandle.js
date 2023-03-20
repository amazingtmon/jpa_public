$(function () {

    /*
        LocalDateTime format change
        ex) '2023-02-21T21:30:34.503003' ->  '2023-02-21 21:30:34'
    */
    changeDateTimeFormat = function(string_date){
        if(string_date == null) return null;
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

    /* 신고 관리 */
    reportHandle = function(){
        $('#content-header-text').text('Report Handling');
        $('#main-container').html('');
        $.ajax({
            type: "get",
            url: '/api/admin-report/statistics',
            contentType: 'application/json',
            success: function (data) {
                getNewTable_reportHandle(data);
            },
            error: function (request, status, error) {
                alert("code: " + request.status + "\n" + "error: " + request.responseText);
            }
        });// end of ajax
    }// end of reportHandle function

    /* 신고 통계 테이블 생성 */
    getNewTable_reportHandle = function(data){
        console.log('report data = ', data);
        let reportData_total_count = data.count;
        let reports_data = data.data;
        let table_html = `
        <div class="row">
            <div class="col-sm-12">
                <table id="statistics-table" class="table table-bordered table-hover dataTable dtr-inline" aria-describedby="statistics-table_info">
                    <thead>
                        <tr>
                            <th class="sorting sorting_asc" tabindex="0" aria-controls="statistics-table" rowspan="1" colspan="1" aria-sort="ascending" aria-label="id engine: activate to sort column descending">id</th>
                            <th class="sorting" tabindex="0" aria-controls="statistics-table" rowspan="1" colspan="1" aria-label="신고된 컨텐츠: activate to sort column ascending">신고된 컨텐츠</th>
                            <th class="sorting" tabindex="0" aria-controls="statistics-table" rowspan="1" colspan="1" aria-label="신고한 멤버: activate to sort column ascending">신고한 멤버</th>
                            <th class="sorting" tabindex="0" aria-controls="statistics-table" rowspan="1" colspan="1" aria-label="신고당한 멤버: activate to sort column ascending">신고당한 멤버</th>
                            <th class="sorting" tabindex="0" aria-controls="statistics-table" rowspan="1" colspan="1" aria-label="신고 시간: activate to sort column ascending">신고 시간</th>
                            <th class="sorting" tabindex="0" aria-controls="statistics-table" rowspan="1" colspan="1" aria-label="신고 이유: activate to sort column ascending">신고 이유</th>
                            <th class="sorting" tabindex="0" aria-controls="statistics-table" rowspan="1" colspan="1" aria-label="신고 관리 시간: activate to sort column ascending">신고 관리 시간</th>
                            <th class="sorting" tabindex="0" aria-controls="statistics-table" rowspan="1" colspan="1" aria-label="상태: activate to sort column ascending">상태</th>
                        </tr>
                    </thead>
                    <tbody class="report-tbody">
                    </tbody>
                </table>
            </div>
        </div>
        `;
        $('#main-container').append(table_html);
        let report_tbody = $('.report-tbody');
        for(let i = 0; i < reports_data.length; i++){
            let report_id = reports_data[i].report_id; // report_id
            let report_item = reports_data[i].report_item; // 신고된 컨텐츠
            let reporter_mem_name = reports_data[i].reporter_mem_name; // 신고한 멤버
            let reported_mem_name = reports_data[i].reported_mem_name; // 신고당한 멤버
            let report_time = changeDateTimeFormat(reports_data[i].report_time); // 신고 시간
            let report_reason = reports_data[i].report_reason; // 신고 이유
            let report_handle_time = changeDateTimeFormat(reports_data[i].report_handle_time); // 신고 관리 시간
            if(reports_data[i].report_handle_time == null ) {
                report_handle_time = 'proceeding';
            }
            let report_handle_status = reports_data[i].report_handle_status; // 상태
            let tbody_html = `
            <tr>
                <td class="dtr-control sorting_1" tabindex="0">${report_id}</td>
                <td class>${report_item}</td>
                <td class>${reporter_mem_name}</td>
                <td class>${reported_mem_name}</td>
                <td class>${report_time}</td>
                <td class>${report_reason}</td>
                <td class>${report_handle_time}</td>
                <td class>${report_handle_status}</td>
            </tr>
            `;
            report_tbody.append(tbody_html);
        }// end of for
        let table_id = $('#statistics-table');
        initDataTableFunction(table_id);
    }//end of getNewTable_reportHandle function

}); // end jquery