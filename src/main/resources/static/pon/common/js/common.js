
// ------------- function () 함수 실행  호출시점 : DOM Tree 생성 완료 후 -----------------
$( function () {
	


} );
// ------------- function () 함수 종료 -----------------




// 클래스 조회 
function searchClassList( e ) {
	alert("test");
	var $parntsClSn = $(e).data("parntsClSn");
	var $clSn = $(e).data("clSn");
	
	$( "[name='page']" ).val( page );
	
	
	
}
