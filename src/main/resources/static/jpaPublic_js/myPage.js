$(function () {

    /* myPage tab 클릭시 data-url 비동기처리 */
    $('a[data-toggle="tab"]').on('show.bs.tab', function (e) {
      // 선택되는 요소를 오브젝트화 한다.
      $this = $(e.target);
      // data-load가 false의 경우는 content에 data-load를 한다.
      if(!$this.data("load")) {
        // tab-content의 id를 취득한다.
        var id = $this.attr("href");
        // 페이지 로드를 한다.
        $(id).load($this.data("url"));
        // data-load를 true로 변환하여 중복 로딩이 없게 한다.
        $this.data("load", true);
      }
    });

}); // end jquery