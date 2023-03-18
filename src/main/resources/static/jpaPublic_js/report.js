$(function () {

    /* 전역변수 */
    let content_id;
    let writer;
    let item;

    /* call report modal function */
    reportContent = function(p_content_id, p_writer, p_item){
        content_id = p_content_id;
        writer = p_writer;
        item = p_item;
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
            report_content_id : content_id,
            reported_mem_name : writer,
            report_item : item,
            reason : selectBox_val
        });

        /* ajax api function */
        reportContent_item(jsonData);

    }// end of doReport function

    /* Report Content */
    reportContent_item = function(jsonData){
        $.ajax({
            type: "post",
            url: "/api/report-content",
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
    }// end of reportContent

}); // end jquery