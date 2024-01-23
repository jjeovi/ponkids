
// ------------- function () 함수 실행  호출시점 : DOM Tree 생성 완료 후 -----------------
$( function () {
	
	// pon menu list set
	var url = '/menu/live/pon/getMenuListAjax';
	$.ajax( {
                url: url,
                type: "GET",
                dataType: "json",
                async: false,	// 동기식 ajax : 통신이 완료될 떄 까지 다음 line 진행 안함
                data: cateSchData, // 검색할 값
                contentType: "application/json",
                success: function ( result ) {
					
                    // return type : List<MenuListDto>
                    var allYn = $ul.data( 'allYn' );
                    var nextStepLiOnclickParamUrl = $ul.data( 'nextStepLiOnclickParamUrl' );

                    if ( nextStepLiOnclickParamUrl != null && nextStepLiOnclickParamUrl != '' ) {
                        url = nextStepLiOnclickParamUrl;
                    } else {
                        url = '';
                    }

                    if ( allYn != null && allYn == 'Y' ) {
                        $( ".category-list-area .category-group-box ul" ).eq( ulNum + 1 ).append(
                            $( "<li>" ).attr( "onclick", "getCateNextLvList('" + url + "', this )" ).attr( "value", "" ).append( "전체" )
                        );
                    }
                    if( result.resultList != null && result.resultList.length > 0 ) {
		                for ( let item of result.resultList ) {
		                    $( ".category-list-area .category-group-box ul" ).eq( ulNum + 1 ).append(
		                        $( "<li>" ).attr( "onclick", "getCateNextLvList('" + url + "', this )" ).attr( "value", item.category['categorySn'] ).append( item.category['categoryNm'] )
		                    );
		                }
                    }
                }
            } );
	



} );
// ------------- function () 함수 종료 -----------------

