
// ------------- function () 함수 실행  호출시점 : DOM Tree 생성 완료 후 -----------------
$( function () {
	
	 
	 // 팝업 show
	$(".show_pop").on("click", function(e) {
		var layerId = $(this).data("layerId")	;	// 클릭한 레이어 팝업의 id 값 setting 
		showPopup(layerId);
	});
	 
	 // 팝업 show : 다른 팝업들 켜져있다면 유지하고 해당 팝업만 show ( close 할때도 해당 팝업만 close 하도록 구현 ) 
	$(".show_pop_lv2").on("click", function(e) {
		var layerId = $(this).data("layerId")	;	// 클릭한 레이어 팝업의 id 값 setting 
		showPopupLv2(layerId);
	});

	 
	// 팝업 hide
	$(".hide_pop").on("click", function(e) {
		var layerId = $(this).data("layerId")	;	// 클릭한 레이어 팝업의 id 값 setting 
		hidePopup(layerId);
	});
	 
	// 팝업 hide : 다른 팝업들 켜져있다면 유지하고 해당 팝업만 hide 
	$(".hide_pop_lv2").on("click", function(e) {
		var layerId = $(this).data("layerId")	;	// 클릭한 레이어 팝업의 id 값 setting 
		hidePopupLv2(layerId);
	});


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
    } ).filter( ':eq(0)' ).click();
    
    
        
    
    
//    		
//		$("#join_resideArea").change(function(){
//			alert($(this).val());
//		})
//		
//		$("[name='resideArea']").change( function() {
//			alert("ttt");
//			});


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

// 팝업창 실행 event 
function showPopup( layerId ) {
	
	$("[class^='layer_']").hide();
	$(".layer_" + layerId ).show();
	$("#pop_dim").fadeIn();
}


// 팝업창 실행 event 
function showPopupLv2( layerId ) {
	
	$(".layer_" + layerId ).show();
	$("#pop_dim_lv2").fadeIn();
}


// 팝업창 숨김 
function hidePopup( layerId ) {
	
	$(".layer_" + layerId ).hide();
	$("#pop_dim").fadeOut();
	
	$("#pop_dim").fadeOut( '10', function(){
		$(".layer_" + layerId ).fadeOut('20');
	});
}


// 팝업창 숨김 
function hidePopupLv2( layerId ) {
	
	$(".layer_" + layerId ).hide();
	$("#pop_dim_lv2").fadeOut();
}



// 카카오 로그인
function kakaoLogin(){
	
}

// 구글 로그인
function googleLogin(){
	
}