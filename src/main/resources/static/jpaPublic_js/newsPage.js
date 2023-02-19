$(function () {

    const NEWS_API_KEY = "4d855597f2e0410fae0c1a86e5ec4e7a";

    getTopHeadlinesNews = function (){
        console.log("getTopHeadlinesNews");
        let country = "kr";
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

    makeTopHeadlineNews = function (data){
        console.log($('#news-list-body'));
        console.log(data.articles.length);
        let news_list_body = $('#news-list-body');
        news_list_body.html('');
        let articles = data.articles;
        for(let i = 0; i < articles.length; i++){
            let author = articles[i].author;
            let title = articles[i].title;
            let description = articles[i].description;
            let publishedAt = articles[i].publishedAt;
            let url = articles[i].url;
            let urlToImage = articles[i].urlToImage;
            let div = `<div class="title">${title}</div>`;
            let article_html = `
              <div class="card mb-auto">
                <div class="row no-gutters">
                  <div class="col-md-4">
                    <img src="..." class="card-img" alt="...">
                  </div>
                  <div class="col-md-8">
                    <div class="card-body">
                      <div class="card-title">
                        <h5 class="article_title">${title}</h5>
                        <span class="article_author">${author}</span>
                      </div>
                      <p class="article_description card-text">
                        ${description}
                      </p>
                      <p class="card-text">
                        <small class="article_publishedAt text-muted">${publishedAt}</small>
                        <button type="button" class="bookmarkArticle btn btn-primary" style="float: right;">Bookmark</button>
                      </p>
                    </div>
                  </div>
                </div>
              </div>
            `;
            news_list_body.append(article_html);
        }
        /* bookmarkArticle click function */
    };

    $('.bookmarkArticle').on('click', function(e){
        //현재 이벤트가 상위뿐만 아니라 현재 레벨에 걸린 다른 이벤트도 동장하지 않도록 중단
        e.stopImmediatePropagation();
        let bm_article = $(e.target);
        let article_title = bm_article.offsetParent().find(".article_title");
        console.log(article_title.text());
    });

    initFunction = function(){
//        getTopHeadlinesNews();
    };

    initFunction();

}); // end jquery
