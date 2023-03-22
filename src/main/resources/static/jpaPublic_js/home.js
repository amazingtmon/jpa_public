$(function () {

    /*
        LocalDateTime format change
        ex) '2023-02-21T21:30:34.503003' ->  '2023-02-21 21:30:34'
    */
    changeDateTimeFormat = function(string_date){
        if(string_date == null) return null;
        let filter = string_date.replace(/T/g , ' '); // '2023-02-21 21:30:34.503003'
        let index = filter.indexOf('.'); // 소수점 자리의 index = 19
        let result = filter.substring(1, 19); // '2023-02-21 21:30:34'

        return result;
    }

    home_createBoard = function(isBanned, ban_end_time){
        console.log(typeof(isBanned)+', '+isBanned);
        console.log(typeof(ban_end_time)+', '+ban_end_time);
        let time = changeDateTimeFormat(ban_end_time);
        if(isBanned == 'true'){
            alert(`
!! Notice !!

누적된 신고로 인해 게시판 및 댓글작성을 이용할 수 없습니다.

" ${time} " 이후 이용이 가능합니다.
            `);
            return;
        }

    }

}); // end jquery