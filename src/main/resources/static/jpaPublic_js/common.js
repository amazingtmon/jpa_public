$(function () {

    /*
        News 페이지에서 현재 url path 값만 string 으로 가져오기
        ex) news/everything -> everything
    */
    getPath = function () {
        let path = window.location.pathname;
        let news_page = path.substring(6, path.length);
        return news_page;
    }

    /*
        NewsApi - article.publishedAt : "2023-01-27T21:30:00Z" T,Z 제거.
        ex) "2023-01-27T21:30:00Z" -> "2023-01-27 21:30:00"
    */
    getDateFormat = function (string_date) {
        let filter = string_date.replace(/T/g , ' ');
        let result = filter.replace(/Z/g , '');
        return result;
    }

}); // end jquery
