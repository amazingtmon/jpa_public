$(function () {

    const TIME_ZONE = 9 * 60 * 60 * 1000; // 9시간

    /*
        LocalDateTime format change
        ex) "2023-02-21T21:30:34.503003" ->  2023-02-21 21:30:34
    */
    changeDateTimeFormat = function(string_date){
        if(string_date == null) return null;
        let filter = string_date.replace(/\"/g , '').replace(/T/g , ' ').replace(/Z/g , ''); // 2023-02-21 21:30:34.503003
        let index = filter.indexOf('.'); // 소수점 자리의 index = 19
        let result = filter.substring(0, 19); // 2023-02-21 21:30:34

        return result;
    }// end of changeDateTimeFormat function

    /* 현재시간과 ban_end_time 을 비교하는 function */
    compareDate = function(format_end_time){
        // 현재시간 가져와서 포맷 변경, 2023-03-23T07:55:31.828Z => 2023-03-23 07:09:40
        let now_date = changeDateTimeFormat(new Date().toISOString());
        // 한국시간으로 가져오기 위해 TIME_ZONE(9시간)을 더함
        let current_date = new Date(now_date).getTime()+TIME_ZONE;
        let compare_date = new Date(format_end_time).getTime();

        //현재시간이 format_end_time 보다 클 경우 true 리턴
        if(current_date > compare_date){
            return true;
        }
        return false;
    }// end of compareDate function

    validation_createContents = function(isBanned, ban_end_time){
        let format_end_time = changeDateTimeFormat(ban_end_time);
        let isPassed = compareDate(format_end_time);
        return isPassed;
    }// end of validation_createContents function

    home_createBoard = function(isBanned, ban_end_time){
        // validation_createContents -> banValidation.js
        let isValidated = validation_createContents(isBanned, ban_end_time);
        let show_end_time = changeDateTimeFormat(ban_end_time);
        if(isBanned == 'true' && !isValidated){
            alert(`
            !! Notice !!

            누적된 신고로 인해
            게시판 및 댓글작성을 이용할 수 없습니다.

            " ${show_end_time} " 이후 이용 가능합니다.

            `);

            return;
        }

        //게시글 작성 페이지로 이동
        location.href = 'http://localhost:8080/board/new';
    }// end of home_createBoard function



}); // end jquery