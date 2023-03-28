// import - common.js
document.write('<script src="/jpaPublic_js/common.js"></script>');

$(function () {

    const NEWS_API_KEY = "4d855597f2e0410fae0c1a86e5ec4e7a";
    /* 카카오톡으로 공유하기 JavaScript 키 */
    Kakao.init('cd93d84738677d29fc7f1502e4457bd1');

    getPath = function () {
        let path = window.location.pathname;
        let news_page = path.substring(6, path.length);
    }

    getTopHeadlinesNews = function (country_code){
        let country = country_code;
        $.ajax({
            type: "get",
            url: `https://newsapi.org/v2/top-headlines?country=${country}&apiKey=${NEWS_API_KEY}`,
            success: function (data) {
                makeTopHeadlineNews(data);
            },
            error: function (request, status, error) {
                alert("code: " + request.status + "\n" + "error: " + request.responseText);
            }
        });// end of ajax
    };

    /* summary description */
    summaryDescription = function(string){
        if(string == null){
            return "...";
        }
        let summary_string = string.substring(0, 100);
        return summary_string+"...";
    }

    /* 특수문자 제거 */
    checkReg = function(str){
        if(str == null ){
            return "...";
        }
        let reg = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi;
        return str.replace(reg,'');
    }

    makeTopHeadlineNews = function (data){
        let news_list_body = $('#news-list-body');
        news_list_body.html('');
        let articles = data.articles;
        for(let i = 0; i < articles.length; i++){
            let author = articles[i].author;
            let title = articles[i].title;
            let description = summaryDescription(articles[i].description);
            let publishedAt = articles[i].publishedAt;
            let url = articles[i].url;
            let urlToImage = articles[i].urlToImage;
            //get article html
            let article_html = `
              <div class="card mb-2">
                <div class="row no-gutters">
                  <div class="col-md-4">
                    <img src=${urlToImage} class="article_urlToImage card-img">
                  </div>
                  <div class="col-md-8">
                    <div class="card-body">
                      <div class="card-title">
                        <a class="article_url" href=${url} target="_blank">
                        <input type="hidden" class="article_url_input" value=${url}/>
                          <h5 class="article_title">${title}</h5>
                        </a>
                        <span class="article_author">${author}</span>
                      </div>
                      <p class="article_description card-text">
                        ${description}
                      </p>
                      <p class="card-text">
                        <small class="article_publishedAt text-muted">${publishedAt}</small>
                        <div class="d-flex justify-content-center align-items-center" style="float: right;">
                            <button type="button" class="bookmarkArticle btn btn-primary">Save</button>
                            <button type="button" class="kakaotalk-sharing-btn btn" id="kakaotalk-sharing-btn">
                                <img src="https://developers.kakao.com/assets/img/about/logos/kakaotalksharing/kakaotalk_sharing_btn_medium.png" height ="38" width="38">
                            </button>
                        </div>
                      </p>
                    </div>
                  </div>
                </div>
              </div>
            `;
            //article html append
            news_list_body.append(article_html);
        }// end of for

        /* 카카오톡 공유하기 버튼 이벤트 */
        $('.kakaotalk-sharing-btn').on('click', function(e){
            e.stopImmediatePropagation();
            if(!confirm("해당 기사를 공유하시겠습니까?")) return;
            let bm_article = $(e.target);
            //기사 제목
            let article_title = bm_article.offsetParent().find(".article_title").text();
            //기사 url
            let article_url = bm_article.offsetParent().find(".article_url").attr("href");
            //기사 발간날짜
            let article_publishedAt = getDateFormat(bm_article.offsetParent().find(".article_publishedAt").text());

            Kakao.Share.sendCustom({
              templateId: 91836,
              templateArgs: {
                title: article_title,
                publishedAt: `기사 발행 시간 : ${article_publishedAt}`,
                url: article_url
              },
            });
        });

        /* bookmarkArticle click function */
        $('.bookmarkArticle').on('click', function(e){
            //현재 이벤트가 상위뿐만 아니라 현재 레벨에 걸린 다른 이벤트도 동장하지 않도록 중단
            e.stopImmediatePropagation();
            let result = confirm("해당 기사를 저장하시겠습니까?");
            if(!result) return;
            let bm_article = $(e.target);
            //기사 제목
            let article_title = bm_article.offsetParent().find(".article_title").text();
            //기사 url
            let article_url = bm_article.offsetParent().find(".article_url").attr("href");
            //기사 발간날짜
            let article_publishedAt = getDateFormat(bm_article.offsetParent().find(".article_publishedAt").text());
            //현재 page path
            let page_path = getPath();
            let jsonData = JSON.stringify({
                title : checkReg(article_title),
                url : article_url,
                publishedAt : article_publishedAt,
                news_page_path : page_path
            });
            $.ajax({
                type: "post",
                url: "/api/article",
                data: jsonData,
                contentType: 'application/json',
                success: function (data) {
                        alert("기사가 저장되었습니다.");
                },
                error: function (request, status, error) {
                    alert("code: " + request.status + "\n" + "error: " + request.responseText);
                }
            });// end of ajax
        });//end of bookmarkArticle click function

    };// end of makeTopHeadlineNews function

    initFunction = function(){
        let default_country_code = "kr";
        getTopHeadlinesNews(default_country_code);
    };

    initFunction();

}); // end jquery
