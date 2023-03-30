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
            //checkBox order disable 을 위한 추가옵션
            "columnDefs": [{
                "orderable": false,
                "className": 'select-checkbox',
                "targets": [ 0 ]
            }],
            "order": [[ 1, 'asc' ]]
          });
    }// end of initDataTableFunction

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
                <table id="report-statistics-table" class="table table-bordered table-hover dataTable dtr-inline text-center" aria-describedby="report-statistics-table_info">
                    <thead>
                        <tr>
                            <th class="select-checkbox"></th>
                            <th class="sorting sorting_asc" tabindex="0" aria-controls="report-statistics-table" rowspan="1" colspan="1" aria-sort="ascending" aria-label="id engine: activate to sort column descending">id</th>
                            <th class="sorting" tabindex="0" aria-controls="report-statistics-table" rowspan="1" colspan="1" aria-label="신고된 컨텐츠: activate to sort column ascending">신고된 컨텐츠</th>
                            <th class="sorting" tabindex="0" aria-controls="report-statistics-table" rowspan="1" colspan="1" aria-label="신고한 멤버: activate to sort column ascending">신고한 멤버</th>
                            <th class="sorting" tabindex="0" aria-controls="report-statistics-table" rowspan="1" colspan="1" aria-label="신고당한 멤버: activate to sort column ascending">신고당한 멤버</th>
                            <th class="sorting" tabindex="0" aria-controls="report-statistics-table" rowspan="1" colspan="1" aria-label="신고 시간: activate to sort column ascending">신고 시간</th>
                            <th class="sorting" tabindex="0" aria-controls="report-statistics-table" rowspan="1" colspan="1" aria-label="신고 이유: activate to sort column ascending">신고 이유</th>
                            <th class="sorting" tabindex="0" aria-controls="report-statistics-table" rowspan="1" colspan="1" aria-label="신고 관리 시간: activate to sort column ascending">신고 관리 시간</th>
                            <th class="sorting" tabindex="0" aria-controls="report-statistics-table" rowspan="1" colspan="1" aria-label="상태: activate to sort column ascending">상태</th>
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
                <td class onclick="event.cancelBubble=true;">
                    <input type="checkbox" name="select-checkbox" value=${report_id}>
                </td>
                <td class="sorting_1" tabindex="0">${report_id}</td>
                <td class>${report_item}</td>
                <td class>${reporter_mem_name}</td>
                <td class>${reported_mem_name}</td>
                <td class>${report_time}</td>
                <td class>${report_reason}</td>
                <td class>${report_handle_time}</td>
                <td class onclick="event.cancelBubble=true;">
                    <span style="font-weight: 1000;">${report_handle_status}</span>
                    <div>
                        <select id="status_selectBox" class="form-control" onchange="changeStatusSelectBox(${report_id}, this)">
                          <option value="none">Choose</option>
                          <option value="PROCEEDING">처리중</option>
                          <option value="COMPLETE">처리완료</option>
                          <option value="DENIED">이유부적합</option>
                        </select>
                    </div>
                </td>
            </tr>
            `;
            report_tbody.append(tbody_html);
        }// end of for
        let table_id = $('#report-statistics-table');
        initDataTableFunction(table_id);

        let chk_data = []; //체크된 report_id 배열
        /* select-checkbox click function */
        $("input:checkbox[name='select-checkbox']").change(function(){
            let checkbox = $(this);
            let report_id = checkbox.val();

            //체크박스 체크여부 확인
            if(checkbox.is(':checked')){
                $('.status_changeAll_div').removeClass('changeAll_button_hide');
                chk_data.push(report_id);
            } else{
                console.log("check false");
                //체크해제한 report_id 삭제 후 return 배열
                chk_data = chk_data.filter((val) => val !== report_id);
                console.log(chk_data);
            }

            // chk_data length == 0 일 경우, [Change All] 버튼 숨김
            if(chk_data.length < 1){
                $('.status_changeAll_div').addClass('changeAll_button_hide');
            }

        });// end of select-checkbox click function

        /* 모달창을 보여주는 [Change All] 버튼 function */
        reportStatus_changeAll = function(){
            $('#reportStatusModal').modal('show');
        }// end of reportStatus_changeAll

        /* 모달창 나타나고 실행되는 함수 */
        $('#reportStatusModal').on('show.bs.modal', function (event) {
            let modal = $(this)
            //send 버튼 비활성화
            $('#sendReportStatus').prop('disabled', true);
        });

        /* modal 창의 셀렉트박스 선택한 option 에 따라 send 버튼 활성화 컨트롤 */
        reportStatusModal_SelectBox = function(){
            let selectBox_val = $('#modalStatus_SelectBox').val();
            if(selectBox_val != 'none'){
                $('#sendReportStatus').prop('disabled', false);
                return;
            }
            $('#sendReportStatus').prop('disabled', true);
        }// end of modalStatus_SelectBox

        /* send report status data to server */
        sendReportStatus = function(){
            if(!confirm("상태값 변경을 진행하시겠습니까?")) return;
            let selectBox_val = $('#modalStatus_SelectBox').val();
            let jsonData = JSON.stringify({
                report_handle_status : selectBox_val,
                reportIdArray : chk_data
            });
            /* ajax api function */
            $.ajax({
                type: "put",
                url: '/api/admin-report/status-all',
                contentType: 'application/json',
                data : jsonData,
                success: function (data) {
                    alert(`${data} 개 데이터의 상태값이 변경되었습니다.`);
                    chk_data.pop();//배열의 데이터 비우기
                    $('#reportStatusModal').modal('hide');// modal 창 close
                    reportHandle();// Report Handle page reload
                },
                error: function (request, status, error) {
                    alert("code: " + request.status + "\n" + "error: " + request.responseText);
                }
            });// end of ajax

        }// end of sendReportStatus function

        /* selectBox click function */
        changeStatusSelectBox = function(p_report_id, obj){
            if(!confirm("상태를 변경하시겠습니까?")){
                $(obj).val("none").attr("selected", "selected");
                return;
            }
            let id = p_report_id;
            let select_val = $(obj).val();
            let jsonData = JSON.stringify({
                report_id : id,
                report_handle_status : select_val
            });
            $.ajax({
                type: "put",
                url: '/api/admin-report/status',
                contentType: 'application/json',
                data : jsonData,
                success: function (data) {
                    console.log('select data = ', data);
                    reportHandle();// Report Handle page reload
                },
                error: function (request, status, error) {
                    alert("code: " + request.status + "\n" + "error: " + request.responseText);
                }
            });// end of ajax
        }// end of changeStatusSelectBox function

        /* table tr click function*/
        $('#report-statistics-table tbody tr').on('click', function(){
            // 현재 클릭된 Row(<tr>)
			let tr = $(this);
			let td = tr.children();

			// td.eq(index)를 통해 값을 가져올 수도 있다.
			let id = td.eq(1).text();
			console.log('td id = ', id);
        });

    }//end of getNewTable_reportHandle function

}); // end jquery