
// ------------- function () 함수 실행  호출시점 : DOM Tree 생성 완료 후 -----------------
$( function () {
	
	// pon menu list set
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
	



} );
// ------------- function () 함수 종료 -----------------

