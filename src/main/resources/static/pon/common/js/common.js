
// ------------- function () 함수 실행  호출시점 : DOM Tree 생성 완료 후 -----------------
$( function () {
	


} );
// ------------- function () 함수 종료 -----------------

$( function () {

    // 1. init main banner area
    // initMainBanner();

    // 2. init main class area
    // initMainClass();

    // 3. init main review area
    // initMainReview();

//    $( '.class_2dep > div' ).hide();
    $( '.class_1dep .tabnav a' ).click( function () {
        $( '.class_2dep > div' ).hide().filter( this.hash ).fadeIn();
        $( '.class_1dep .tabnav a' ).removeClass( 'active' );
        $( this ).addClass( 'active' );
        return false;
    } );

    $( '.detail_day button' ).click( function () {
        $( '.detail_day button' ).removeClass( 'active' );
        $( this ).addClass( 'active' );
        return false;
    } );


    $( '.detail_tab_content div' ).hide();
//    $( '.detail_tab_nav a' ).click( function () {
//        $( '.detail_tab_content div' ).hide().filter( this.hash ).fadeIn();
//        $( '.detail_tab_nav a' ).removeClass( 'active' );
//        $( this ).addClass( 'active' );
//        return false;
//    } ).filter( ':eq(0)' ).click();
    $( '.detail_tab_nav a' ).click( function () {
        $( '.detail_tab_content div' ).hide().filter( this.hash ).fadeIn();
        $( '.detail_tab_nav a' ).removeClass( 'active' );
        $( this ).addClass( 'active' );
        return false;
    } ).filter( ':eq(0)' ).click();;

} );

$( '.class_2dep .tabnav_2dep a' ).click( function () {
    $( '.class_2dep .tabnav_2dep a' ).removeClass( 'active' );
    $( this ).addClass( 'active' );
} )

$( '.paging_box span.page_num ' ).click( function () {
    $( '.paging_box  span.page_num' ).removeClass( 'active' );
    $( this ).addClass( 'active' );
} )

$( '.mb_menu' ).click( function () {
    $( '.nav_wrap' ).addClass( 'active' );
    $( '.dark_bg' ).addClass( 'active' );
} )
$( ' .close' ).click( function () {
    $( '.nav_wrap' ).removeClass( 'active' );
    $( '.dark_bg' ).removeClass( 'active' );
} )
$( '.dark_bg' ).click( function () {
    $( '.nav_wrap' ).removeClass( 'active' );
    $( '.dark_bg' ).removeClass( 'active' );
} )

$( '.mb_search' ).click( function () {
    $( '.mb_search_form' ).toggleClass( 'active' );
} )

$( function () {
    $( '.chatWrap .tabcontent > div' ).hide();
    $( '.chatWrap .tabnav a' ).click( function () {
        $( '.chatWrap .tabcontent > div' ).hide().filter( this.hash ).fadeIn();
        $( '.chatWrap .tabnav a' ).removeClass( 'active' );
        $( this ).addClass( 'active' );
        return false;
    } ).filter( ':eq(0)' ).click();
} );

$( '.inquiry' ).click( function () {
    $( '.chatWrap' ).addClass( 'show' )
    $( '.chat_close' ).addClass( 'show' )
} )
$( '.chat_close' ).click( function () {
    $( '.chatWrap' ).removeClass( 'show' )
    $( '.chat_close' ).removeClass( 'show' )
} )



// 클래스 조회 
function searchClassList( e ) {
	
	$("#cate01").val($(e).data("parntsClSn"));		// 분류1 값 setting
	$("#cate02").val($(e).data("clSn"));			// 분류2 값 setting
	
	$( "[name='page']" ).val( 0 );					// 페이징 초기화
	
	$( "[name='listForm']").submit();				// 리스트 조회 실행
	
}


// list search 함수 'form' 이름을 가진 form 을 'page' 의 페이지로 submit
// parameter : formname,page
function searchListPage( e ) {
	
		
	var form = $(e).data("form");		// 분류1 값 setting
	var page = $(e).data("page");		// 분류1 값 setting

    $( "[name='page']" ).val( page );
    $( "form[name='" + form + "']" ).submit();
}



// 클래스 > 상세보기 event
function detailClass( classSn ) {
	location.href = "/class/mcdClass/detail?pk=" + classSn;
}


