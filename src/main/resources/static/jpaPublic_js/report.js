$(function () {

    /* 전역변수 */
    let content_id;
    let writer;
    let item;

    /* call report modal function */
    reportContent = function(content_id1, writer1, item1){
        content_id = content_id1;
        writer = writer1;
        item = item1;
        $('#reportModal').modal('show');
    }// end of reportContent function

    /* 모달창 나타나고 실행되는 함수 */
    $('#reportModal').on('show.bs.modal', function (event) {
        let modal = $(this)
        //report 버튼 비활성화
        $('#doReport').prop('disabled', true);
    })

    /* 셀렉트박스 선택한 option 에 따라 report 버튼 활성화 컨트롤 */
    changeReasonSelectBox = function(){
        let selectBox_val = $('#reason_selectBox').val();
        if(selectBox_val != 'none'){
            $('#doReport').prop('disabled', false);
            return;
        }
        $('#doReport').prop('disabled', true);
    }

    /* send report data to server */
    doReport = function(){
        if(!confirm("신고 하시겠습니까?")) return;
        let selectBox_val = $('#reason_selectBox').val();
        let jsonData = JSON.stringify({
            id : content_id,
            reported_mem_name : writer,
            report_item : item,
            reason : selectBox_val
        });
        if(item == 'BOARD'){
            reportBoard(jsonData);
        }
        else if(item == 'COMMENT'){
            reportComment(jsonData);
        }
    }// end of doReport function

    /* Board report */
    reportBoard = function(jsonData){
        $.ajax({
            type: "post",
            url: "/api/report-board",
            data: jsonData,
            contentType: 'application/json',
            success: function(data){
                alert("신고가 완료되었습니다.");
                $('#reportModal').modal('hide');
            },
            error: function (request, status, error) {
                alert("code: " + request.status + "\n" + "error: " + request.responseText);
            }
        });// end of ajax
    }// end of reportBoard function

    /* Comment report */
    reportComment = function(jsonData){
        $.ajax({
            type: "post",
            url: "/api/report-comment",
            data: jsonData,
            contentType: 'application/json',
            success: function(data){
                alert("신고가 완료되었습니다.");
                $('#reportModal').modal('hide');
            },
            error: function (request, status, error) {
                alert("code: " + request.status + "\n" + "error: " + request.responseText);
            }
        });// end of ajax
    }// end of reportComment function

}); // end jquery