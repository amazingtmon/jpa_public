$(function () {

    member_logout = function(){

        if(!confirm('로그아웃 하시겠습니까?')) return;

        let logout_form = document.createElement('form');
        logout_form.setAttribute('method', 'post');
        logout_form.setAttribute('action', '/logout');
        document.body.appendChild(logout_form);
        logout_form.submit();

    }
}); // end jquery