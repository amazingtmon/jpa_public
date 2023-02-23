$(function () {

    /* bootstrap tooltip function */
    $('[data-toggle="tooltip"]').tooltip();

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
        if(!confirm("해당 기사를 \"My Article\" 리스트에서 제거하시겠습니까?")) return;
        let jsonData = JSON.stringify({
            id : article_id
        });
        $.ajax({
            type: "put",
            url: '/api/article-put',
            data : jsonData,
            contentType: 'application/json',
            success: function (data) {
                alert("\""+data.article_title+"\""+"\n"+"\n"+"삭제완료!!");
                chageSelectBox();
            },
            error: function (request, status, error) {
                alert("code: " + request.status + "\n" + "error: " + request.responseText);
            }
        });// end of ajax
    };

    /* 해당 기사 완전 삭제(DB data delete) */
    deleteArticleData = function(article_id){
        if(!confirm("영구삭제를 진행하시겠습니까?")) return;
        let jsonData = JSON.stringify({
            id : article_id
        });
        $.ajax({
            type: "delete",
            url: '/api/article-row',
            data : jsonData,
            contentType: 'application/json',
            success: function (data) {
                alert("영구삭제완료!!");
                chageSelectBox();
            },
            error: function (request, status, error) {
                alert("code: " + request.status + "\n" + "error: " + request.responseText);
            }
        });// end of ajax
    }

    /* 해당 기사 "My Article" 리스트로 복원 */
    restoreArticle = function(article_id){
        if(!confirm("\"My Article\" 리스트로 복원 시키시겠습니까?")) return;
        let jsonData = JSON.stringify({
            id : article_id
        });
        $.ajax({
            type: "put",
            url: '/api/article-row',
            data : jsonData,
            contentType: 'application/json',
            success: function (data) {
                alert("\""+data.article_title+"\""+"\n"+"\n"+"리스트로 복원완료!!");
                chageSelectBox();
            },
            error: function (request, status, error) {
                alert("code: " + request.status + "\n" + "error: " + request.responseText);
            }
        });// end of ajax
    }

    /* 삭제한 기사들만 가져오기 */
    getDeletedArticles = function(){
        $.ajax({
            type: "get",
            url: '/api/article-deleted',
            contentType: 'application/json',
            success: function (data) {
                getNewArticleTbody(data);
            },
            error: function (request, status, error) {
                alert("code: " + request.status + "\n" + "error: " + request.responseText);
            }
        });// end of ajax
    }

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
        let dbArticle = data.article_data;
        if(!data.article_deleted){
            current_tbody.html('');
            // 삭제하지 않은 기사들 보여주는 tbody_html
            for(let i = 0; i < dbArticle.length; i++){
                let article_url = dbArticle[i].article_url;
                let article_title = dbArticle[i].article_title;
                let article_publishedAt = dbArticle[i].article_publishedAt;
                let article_regiTime = changeDateTimeFormat(dbArticle[i].article_regiTime);
                let article_id = dbArticle[i].id;
                let tbody_html =`
                    <tr>
                      <td><a class="article_title_dto" href=${article_url} target="_blank">${article_title}</a></td>
                      <td>${article_publishedAt}</td>
                      <td>${article_regiTime}</td>
                      <td class="text-center">
                        <button class="btn btn-sm btn-danger" onclick="deleteArticle(${article_id})">remove</button>
                      </td>
                    </tr>
                `;
                current_tbody.append(tbody_html);
            }// end of for
        }
        // 삭제한 기사들 보여주는 tbody_html
        else {
            //삭제한 기사가 없는 경우
            if(dbArticle.length == 0) {
                alert("삭제한 기사가 없습니다.");
                return;
            }
            $('.article_time').text("삭제한 시간");
            current_tbody.html('');
            for(let i = 0; i < dbArticle.length; i++){
                let article_url = dbArticle[i].article_url;
                let article_title = dbArticle[i].article_title;
                let article_publishedAt = dbArticle[i].article_publishedAt;
                let article_deleteTime = changeDateTimeFormat(dbArticle[i].article_deleteTime);
                let article_id = dbArticle[i].id;
                let tbody_html =`
                    <tr>
                      <td><a class="article_title_dto" href=${article_url} target="_blank">${article_title}</a></td>
                      <td>${article_publishedAt}</td>
                      <td>${article_deleteTime}</td>
                      <td class="text-center">
                        <button class="btn btn-sm btn-danger mb-1" onclick="deleteArticleData(${article_id})">del</button>
                        <button class="btn btn-sm btn-warning" onclick="restoreArticle(${article_id})">restore</button>
                      </td>
                    </tr>
                `;
                current_tbody.append(tbody_html);
            }// end of for
        }// end if

    }// end of getNewArticleTbody

}); // end jquery