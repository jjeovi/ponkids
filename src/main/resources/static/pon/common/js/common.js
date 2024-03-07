
// ------------- function () 함수 실행  호출시점 : DOM Tree 생성 완료 후 -----------------
$( function () {
	
	 
	 // 팝업 show
	$(".show_layer").on("click", function(e) {
		var layerId = $(this).data("layerId")	;	// 클릭한 레이어 팝업의 id 값 setting 
		showLayer(layerId);
	});
	 
	 // 팝업 show : 다른 팝업들 켜져있다면 유지하고 해당 팝업만 show ( close 할때도 해당 팝업만 close 하도록 구현 ) 
	$(".show_layer_lv2").on("click", function(e) {
		var layerId = $(this).data("layerId")	;	// 클릭한 레이어 팝업의 id 값 setting 
		showLayerLv2(layerId);
	});

	 
	// 팝업 hide
	$(".hide_layer").on("click", function(e) {
		var layerId = $(this).data("layerId")	;	// 클릭한 레이어 팝업의 id 값 setting 
		hideLayer(layerId);
	});
	 
	// 팝업 hide : 다른 팝업들 켜져있다면 유지하고 해당 팝업만 hide 
	$(".hide_layer_lv2").on("click", function(e) {
		var layerId = $(this).data("layerId")	;	// 클릭한 레이어 팝업의 id 값 setting 
		hideLayerLv2(layerId);
	});


} );
// ------------- function () 함수 종료 -----------------

$( function () {

//	$( '.class_2dep > div' ).hide();
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


//	$( '.detail_tab_content > div' ).hide();
//	$( '.detail_tab_nav a' ).click( function () {
//		$( '.detail_tab_content div' ).hide().filter( this.hash ).fadeIn();
//		$( '.detail_tab_nav a' ).removeClass( 'active' );
//		$( this ).addClass( 'active' );
//		return false;
//	} ).filter( ':eq(0)' ).click();
		$( '.detail_tab_nav a' ).click( function () {
//		$( '.detail_tab_content div' ).hide().filter( this.hash ).fadeIn();
		$( '.detail_tab_nav a' ).removeClass( 'active' );
		$( this ).addClass( 'active' );
		
		// 모든 tab hide 한 뒤
		$("[id^='detail_tab']").hide();
		
		// 해당 tab 만 show
		var tabId = $( this ).data("tabId");
		$("#" + tabId).show();
		
		return false;
	} ).filter( ':eq(0)' ).click();
//	
//	
//		
//	$(".button_added_area" ).hide();
//	$(".button_added_area" ).show();
//			
//		$("#join_countryList").change(function(){
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
function showLayer( layerId ) {
	
	$("[class^='layer_']").hide();
	$(".layer_" + layerId ).show();
	$("#pop_dim").fadeIn();
}


// 팝업창 실행 event 
function showLayerLv2( layerId ) {
	
	$(".layer_" + layerId ).show();
	$("#pop_dim_lv2").fadeIn();
}


// 팝업창 숨김 
function hideLayer( layerId ) {
	
	$(".layer_" + layerId ).hide();
	$("#pop_dim").fadeOut();
	
	$("#pop_dim").fadeOut( '10', function(){
		$(".layer_" + layerId ).fadeOut('20');
	});
}


// 팝업창 숨김 
function hideLayerLv2( layerId ) {
	
	$(".layer_" + layerId ).hide();
	$("#pop_dim_lv2").fadeOut();
}

// 모든 팝업창 숨김 
function hideAllPopup( layerId ) {
	
	$("[class^='layer_']").hide();
	$("#pop_dim").fadeOut();
	$("#pop_dim_lv2").fadeOut();
	
}


// 카카오 로그인
function kakaoLogin(){
	
}

// 구글 로그인
function googleLogin(){
	
}


function replaceAll(string, search, replace) {
	return string.replace(new RegExp(search, 'g'), replace);
}



function goLogout() {
	
	var pathName = window.location.pathname;
	var queryString = window.location.search;
	
	// returnUrl setting
	var returnUrl = pathName + queryString;
	returnUrl = getUrlExceptLgStatus(returnUrl);
	
	$( "#logoutForm" ).find("[name='returnUrl']").val( returnUrl );
	
	$( "#logoutForm" ).submit();	// 로그아웃 구현
	
}

// 좋아요 이벤트
function likeClass( classSn, e ){

	var url = "/live/authenticationCheckAjax";	// 현재 로그인 세션 존재하는지 여부 체크
	$.ajax( {
		url: url,
		type: "GET",	// 회원저장 POST로
		async: false,	// 동기식 ajax : 통신이 완료될 떄 까지 다음 line 진행 안함
		cache: false,
		contentType: false,
		processData: false,
		success: function ( ajaxResult ) {
			
			if ( !ajaxResult ) {
				// 로그인 layer 표출
				hideAllPopup();
				showLayer( 'login' );
				
			} else {
				// 좋아요 insert ( classSn / userSn )
				
				data = {};
				data.classSn = classSn;
					

				var url = "/classLike/live/toggleLikeAjax"
				$.ajax( {
					url: url,
					type: "GET",
					async: false,
					data: data, // 검색할 값
					contentType: "application/json",
					success: function ( result ) {
						// return type : List<CategoryDto>
						if ( result.flag == "E" ) {
							alert( result.msg );
							return ;
						} else if ( result.flag == "S" ) {
							var likeStatus = result.likeStatus;
							
							if ( likeStatus == 'insert' ){
								$(e).find('img').attr("src", "/pon/common/image/heart-full.svg");
							} else if ( likeStatus == 'delete' ) {
								
								// detailLike 는 detail 페이지에 있는 하트 -> detail 페이지와 list 페이지가 기본 하트 색상이 달라서 분기처리.
								if ( $(e).hasClass('detailLike') ) {
									$(e).find('img').attr("src", "/pon/common/image/heart_color_bg.svg");
								} else {
									$(e).find('img').attr("src", "/pon/common/image/heart.svg");
									
								}
							}

							return ;
						}
					}
				} );
			}
		}
	});
}


// 금액 3자리수마다 콤마
function amtSetComma( val ){
	if ( typeof(val) == 'number' ){
		return val.toString().replace(/\,/g, '').replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,')
	} else {
		return val.replace(/\,/g, '').replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,')
	}
	
	
}

/*  S : 문의 */
	// ======================================================================================
	// 문의하기 버튼 클릭 event
	function goClassInqry(){
			
		// 1. 로그인 여부 check
		if ( loginYn == 'N' ) {
			alert("로그인 후 이용 가능합니다.");
			showLayer('login');
			return false;
		}
		
		// 문의 등록 layer 표출
		showLayer('registClassInqry');
		
	}
	
	function insertClassInqry(){
			
		// 1. 로그인 여부 check
		if ( loginYn == 'N' ) {
			alert("로그인 후 이용 가능합니다.");
			showLayer('login');
			return false;
		}
		
		// 문의 insert 진행
		// 수업 신청 진행
		if ( confirm("문의를 등록하시겠습니까?") ) {
			
			return true;
		}
		
		return false;
	}
	
	function detailInqryLayer( classInqrySn ) {
		
		// 문의 상세 조회 layer 표출
		showLayer('detailClassInqry');
		
		// 문의 상세 조회 layer 내용 setting
		// 특정 클래스의 특정 요일 의 수업 리스트 조회
		var url = "/classInqry/live/detailClassInqryAjax";
		$.ajax( {
			url: url,
			type: "GET",
			dataType: "json",
			async: false,	// 동기식 ajax : 통신이 완료될 떄 까지 다음 line 진행 안함
			data: {classInqrySn : classInqrySn}, // 검색할 값
			contentType: "application/json",
			success: function ( ajaxResult ) {
				
				// 1. 문의 조회 layer 초기화
				eraseLayerData( ['changeInqryData'] );

				// 2. 문의 조회 layer data setting
				setInqryLayerData( ajaxResult );

				// 3. 문의 등록 layer 표출
				showLayer('detailClassInqry');
				
			}
		});
		
	}
	
	
	function eraseLayerData( classNameArray ) {
		
		for( let className of classNameArray ){
			
			$( "." + className).empty();
		}
		
	}
	
	// 후기 상세 조회 모달창 내용 setting 작업
	function setInqryLayerData( ajaxResult ) {

		var result = ajaxResult.resultOne;

		$("#thumbImg").append(
				$( "<img>" ).attr("src","/getImage?atchFileSn="+ result.thumbAtchFileSn).append()
		);

		$("#inqryClassSj").append( result.classSj );

		// S: 작성자(마스킹), 별점, 등록일, 후기내용 setting
		$("#detailInqryUserNm").text( result.userNm.substring(0,1) + "**" );
		$("#detailInqryRegDt").text( result.regDt );
		$("#detailInqryCn").text( result.inqryCn );
		// E : 작성자(마스킹), 별점, 등록일, 후기내용 setting
	}
	
	
	/*  E : 문의 */

	/* S : 리뷰 ( 후기 ) */

	// 후기 조회
	function detailReviewLayer( classReviewSn ){

		var url = '/classReview/live/getByClassReviewSnAjax';
		$.ajax( {
			url: url,
			type: "GET",
			dataType: "json",
			async: false,	// 동기식 ajax : 통신이 완료될 떄 까지 다음 line 진행 안함
			data: {pk : classReviewSn}, // 검색할 값 setting
			contentType: "application/json",
			success: function ( ajaxResult ) {

				if ( ajaxResult.resultOne == null ) {
					alert("후기 정보가 없습니다. 다시 시도해 주세요.");

				} else {
					
					// 1. 문의 조회 layer 초기화
					eraseLayerData( ['changeReviewData'] ); 
					
					// 2. 후기 조회 layer data setting
					setReviewLayerData( ajaxResult );

					// 3. 문의 등록 layer 표출
					showLayer('detailClassReview');
				}
			}
		} );
	}

	// 후기 상세 조회 모달창 내용 setting 작업
	function setReviewLayerData( ajaxResult ) {

		var result = ajaxResult.resultOne;

		$("#reviewImg").append(
				$( "<img>" ).attr("src","/getImage?atchFileSn="+ result.thumbAtchFileSn).append()
		);

		$("#reviewClassSj").append( result.classSj );

		// S: 작성자(마스킹), 별점, 등록일, 후기내용 setting
		$("#detailReviewUserNm").text( result.userNm.substring(0,1) + "**" );

		// 별점 setting
		var strStar = "";

		// n번만큼 반복하여 별점 생성
		for(let i = 0; i < result.reviewGradeLong ; i++ ) {
			strStar += "<img src='/pon/common/image/star_like.svg' alt='후기별점'>";
		}

		$("#detailReviewGrade").append( strStar );
		$("#detailReviewRegDt").text( result.regDt );
		$("#detailReviewCn").text( result.reviewCn );
		// E : 작성자(마스킹), 별점, 등록일, 후기내용 setting
	}

/* E : 리뷰 ( 후기 ) */



/* S : 설문조사 set */

// 설문지 setting 함수 ( 설문조사 그룹 코드로 조회 )
function setQestnar( qestnarGroupCd ) {
	
	// 설문조사 그룹 조회 ( 질문과 선택지까지 모두 조회 )
	var url = "/qestnarGroup/live/getByQestnarGroupCdAjax";
	$.ajax( {
		url: url,
		type: "GET",
		dataType: "json",
		async: false,	// 동기식 ajax : 통신이 완료될 떄 까지 다음 line 진행 안함
		data: { qestnarGroupCd : qestnarGroupCd }, // 검색할 값
		contentType: "application/json",
		success: function ( ajaxResult ) {
			
			if ( ajaxResult.flag == "E" ) {
				alert( ajaxResult.msg );
				return ;
			} else if ( ajaxResult.flag == "S" ) {
				
				// 0.설문조사 그룹코드별로 설문조사 setting
				// - QESTN00X : 1:1문의 는 layer 를 --- 로 쓴다. 
				// - 나머지 그룹코드는 layer_qestn으로 통일
				var layerName = '';
				
				if ( qestnarGroupCd == '' ) {
					
				}  else {
					layerName = 'qestnar';
				}
				
				
				// 1. 설문조사 layer 초기화
				eraseLayerData( ['changeQestnarData'] );
	
				// 2. 문의 조회 layer data setting
				setQestnarLayerData( ajaxResult );
	
				// 3. 문의 등록 layer 표출
				showLayer( layerName );
			}
			
		}
	});
	
}



var _targetQestnarGroup;			// 설문조사 그룹 
var _targetQestnarQestn;			// 설문조사 질문 list
var _targetQestnarQestnDetail;		// 설문조사 질문 상세 list
// 설문조사 조회 layer 내용 setting 작업
function setQestnarLayerData( ajaxResult ) {

	var _targetQestnarGroup = ajaxResult.targetDto;									// 설문조사 그룹
	var _targetQestnarQestn = ajaxResult.targetQestnarQestnList;					// 설문조사 질문
	var _targetQestnarQestnDetail = ajaxResult.targetQestnarQestnDetailList;		// 설문조사 질문 상세 (선택지)
	
	// TODO 
	// title
	$(".pop_header .qestnarGroupNm").text( _targetQestnarGroup.qestnarGroupNm );
	
	// qestnarGroupSn
	$("[name='qestnarInsertForm']").find("[name='qestnarGroupSn']").text( _targetQestnarGroup.qestnarGroupSn );
	
	
	
	
	// upendGdcc : 상단 안내문
	if ( _targetQestnarGroup.upendGdccSetYn == 'Y' ) {
		$(".pop_content .upendGdcc").append( 
			$( "<div>" ).attr( "class", "upendGdccArea qBox").append(
				_targetQestnarGroup.upendGdcc
			)
		);
	}
	
	// qestnarList
	if ( _targetQestnarQestn != null && _targetQestnarQestn.length > 0 ) {
		for(let qItem of _targetQestnarQestn) {
			
			$answerType = '';
			switch( qItem.qestnarQestnItemTyCd ) {
				
				case "ANSWER" :
					
					$answerType = $( "<input>" ).attr("type", "text" ).attr("class", "qAnswer").attr("name", "qestnarAnswer" ).attr("id", "A_" + qItem.qestnarQestnSn )
					break;
					
				case "SELECTIVE_ONE" :
					
					$answerType += "<div class='optionList'>";
					for ( let qOption of _targetQestnarQestnDetail ) {
						
						if( qItem.qestnarQestnSn == qOption.qestnarQestnSn) {
							$answerType += "<div class='option_item'>";
						    $answerType += "    <div class='item_content'>";
						    $answerType += "        <label class='radioLabel'>";
						    $answerType += "        <input type='radio' name='qestnarQestnDetailSn' value='" + qOption.qestnarQestnDetailSn + "' >";
						    $answerType += "        " + qOption.qestnarQestnDetailCn ;
						    $answerType += "        </label>";
						    $answerType += "    </div>";
						    $answerType += "</div>";
						}
					}
					$answerType += "</div>";
					break;
					
				case "SELECTIVE_MULTI" :
					
					$answerType += "<div class='optionList'>";
					for ( let qOption of _targetQestnarQestnDetail ) {
						
						if( qItem.qestnarQestnSn == qOption.qestnarQestnSn) {
							$answerType += "<div class='option_item'>";
						    $answerType += "    <div class='item_content'>";
						    $answerType += "        <input type='checkbox' name='qestnarQestnDetailSn' value='" + qOption.qestnarQestnDetailSn + "' id='" + qOption.qestnarQestnSn  + "_" + qOption.qestnarQestnDetailSeq  + "'>";
						    $answerType += "        <label for='" + qOption.qestnarQestnSn  + "_" + qOption.qestnarQestnDetailSeq  + "'>" + qOption.qestnarQestnDetailCn + "</label>";
						    $answerType += "    </div>";
						    $answerType += "</div>";
						}
					}
					$answerType += "</div>";
					
					break;
			}
			
			$(".pop_content .qestnarList").append(
				$( "<div>" ).attr( "class", "qBox").attr("id", "qestarItem_" +  + qItem.qestnarQestnSn ).append(
					$( "<div>" ).attr( "class", "qQestnWrap").append(
						$( "<p>" ).append( qItem.qestnarQestnItemCn )
					),
					$( "<div>" ).attr( "class", "qAnswerWrap").append(
						$answerType
					)
				)
			);
			
		}
		
	}
	
	// lptGdcc : 하단 안내문
	if ( _targetQestnarGroup.lptGdccSetYn == 'Y' ) {
		$(".pop_content .lptGdcc").append( 
			$( "<div>" ).attr( "class", "lptGdccArea qBox").append(
				_targetQestnarGroup.lptGdcc
			)
		);
	}
	
}

// 설문조사 제출 버튼 클릭시 
function insertQestnarAnswer(){
	
	// 필수 항목 유효성 검사.   
	
	// =========================== 유효성 검사 종료 이후 값 정형화 setting ================
	// =========================== 유효성 검사 종료 이후 값 정형화 setting ================
	// =========================== 유효성 검사 종료 이후 값 정형화 setting ================
	
	// - 주관식
	// - 객관식 (단일) 
	// - 객관식 (다중)
	
	return false;
	
}



/* E : 설문조사 set */