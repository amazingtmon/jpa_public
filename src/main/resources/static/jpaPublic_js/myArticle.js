$(function () {

    /* .article_title_dto */
    let title = $('.article_title_dto').text();

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

    /* 저장한 기사 삭제 */
    deleteArticle = function(article_id){
        console.log("article_id = "+article_id);
        if(!confirm("해당 기사를 삭제하시겠습니까?")) return;
        console.log("confirm pass");
        let jsonData = JSON.stringify({
            id : article_id
        });
        $.ajax({
            type: "put",
            url: '/api/article-put',
            data : jsonData,
            contentType: 'application/json',
            success: function (data) {
                alert("data : "+data);
            },
            error: function (request, status, error) {
                alert("code: " + request.status + "\n" + "error: " + request.responseText);
            }
        });// end of ajax
    };

    /* select box change event */
    chageSelectBox = function(){
        let selectBox_option = $("#selectbox").val();
        if(selectBox_option == 'All'){
            let id = $('#nav-myArticle-tab').attr("href");
            let load_url = $('#nav-myArticle-tab').data("url")
            $(id).load(load_url);
            return;
        }
        getArticleList(selectBox_option);
    }

    /*
        선택한 옵션 (Top Headline : 'topHeadline', Search News : 'everything')에 맞는
        기사리스트 가져오기
    */
    getArticleList = function(option_val){
        console.log("getArticleList = "+option_val);
        $.ajax({
            type: "get",
            url: '/api/article-path',
            data : {
                selected_option : option_val
            },
            contentType: 'application/json',
            success: function (data) {
                $('tbody').html('');
                getNewArticleTbody(data);
            },
            error: function (request, status, error) {
                alert("code: " + request.status + "\n" + "error: " + request.responseText);
            }
        });// end of ajax

    }// end of getArticleList

    /* 기사리스트로 새로운 tbody html 생성 */
    getNewArticleTbody = function(data){
        let totalResult = data.count;
        let current_tbody = $('tbody');
        let newArticle = data.data;
        for(let i = 0; i < newArticle.length; i++){
            let article_url = newArticle[i].article_url;
            let article_title = newArticle[i].article_title;
            let article_publishedAt = newArticle[i].article_publishedAt;
            let article_regiTime = changeDateTimeFormat(newArticle[i].article_regiTime);
            let article_id = newArticle[i].id;
            let tbody_html =`
                <tr>
                  <td><a class="article_title_dto" href=${article_url} target="_blank">${article_title}</a></td>
                  <td>${article_publishedAt}</td>
                  <td>${article_regiTime}</td>
                  <td>
                    <button class="btn btn-sm btn-danger " onclick="deleteArticle(${article_id})">삭제</button>
                  </td>
                </tr>
            `;
            current_tbody.append(tbody_html);
        }// end of for

    }// end of getNewArticleTbody

}); // end jquery