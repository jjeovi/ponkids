
// ------------- function () 함수 실행  호출시점 : DOM Tree 생성 완료 후 -----------------
$( function () {
	
	// pon menu list set
    // ponMenuListSet();


} );
// ------------- function () 함수 종료 -----------------



// 메뉴 리스트 set
function ponMenuListSet() {
    // 메뉴 list setting
    // - level 1 인 메뉴만 setting
    // - 메뉴 펼치는 기능은 사용자단에서는 하지 않음.


    var url = '/menu/live/pon/getMenuListAjax';
    $.ajax( {
        url: url,
        type: "GET",
        dataType: "json",
        async: false,	// 동기식 ajax : 통신이 완료될 떄 까지 다음 line 진행 안함
        contentType: "application/json",
        success: function ( result ) {

            if ( result ) {
                var ponMenuList = result.resultList;

                // menuList 등록
                for( let menu of ponMenuList ) {
                    if ( menu.level == 1 ) {
                        $(".pon_menu").append(
                            $("<li>").append(
                                $("<a>").attr("href", menu.menuUrl).append(
                                    menu.menuNm
                                )
                            )
                        )
                    }
                }
            }
        }
    } );

}

