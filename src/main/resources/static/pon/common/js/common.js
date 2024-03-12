
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
// - 문의 설문조사만 레이아웃디자인이 달라서 함수를 따로 구현 ( 설문조사 그룹 코드로 조회 )
function setQestnar( qestnarGroupCd, layerName ) {
	
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
				
				// 로그인 필수 여부 체크하여 
				// 로그인 필수 일 시 로그인 체크 로직 수행
				var targetDto = ajaxResult.targetDto;									// 설문조사 그룹
				if ( targetDto.loginEssntlYn == 'Y' && loginYn == 'N' ) {
					// 1. 로그인 여부 check
					alert("로그인 후 이용 가능합니다.");
					showLayer('login');
					return false;
				}
				
				// 1. layer 초기화
				eraseLayerData( ['changeQestnarData'] );
	
				// layout 에 따라 디자인을 다르게 설정.
				if ( layerName == 'qestnar' ) {
					// 2. layer data setting
					setQestnarLayerData( ajaxResult );
				} else if ( layerName == 'inquiry' ) {
					// 2. layer data setting
					setQestnarForInquiryLayerData( ajaxResult );
					
				}
	
				// 3. layer 표출
				showLayer( layerName );
			}
			
		}
	});
	
}


function setInquiryData( qestnarGroupCd, layerName ){
	
	// 문의 버튼 클릭시 process
	// ==========================
	// - 1. FAQ 리스트 조회
	// - 2. 내 문의 내역
	// - 3. 문의하기
	
	// - 1. FAQ 리스트 조회
	var url = "/ntt/live/getListByBbsNmAjax";
		$.ajax({
			url: url,
			type: "GET",
			dataType: "json",
			async: false,	// 동기식 ajax : 통신이 완료될 떄 까지 다음 line 진행 안함
			data : { bbsNm : "FAQ" },
			contentType: "application/json",
			success: function ( ajaxResult ) {
				
				// 1. 문의 > FAQ 리스트 초기화
				$(".tab.faqNttList").empty();
				
				// 2.문의 > FAQ 리스트 data setting
				if ( ajaxResult.flag == "E" ) {
					// 문의 내역이 없습니다.
					$(".tab.faqNttList").append(
						$( "<li>" ).attr("class","no-data").append(
							"게시물이 존재하지 않습니다."
						)
					)
				} else {
					
					// FAQ 개수만큼 순회하며 li 에 뿌림
					for ( let item of ajaxResult.targetList ) {
						
						$(".tab.faqNttList").append(
							$( "<li>" ).attr( "onclick", "detailFaqNtt( '" + item.nttSn + "' )" ).append(
								$( "<div>" ).attr( "class", "subInfo" ).append(
									$( "<span>" ).append( "[" + item.userNm + "]" ),
									$( "<span>" ).attr( "class", "regDt" ).append( item.regDt )
								),
								$( "<div>" ).attr( "class", "titleInfo mt_15p" ).append(
									$( "<p>" ).append( item.nttNm )
								)
							)
						)
					}
				}
				
				
				
			}
		});
	
	
	// - 2. 내 문의 내역
	// 특정 클래스의 특정 요일 의 수업 리스트 조회
	// 로그인 여부 check
	if ( loginYn == 'N' ) {
		alert("로그인 후 이용 가능합니다.");
		showLayer('login');
		return false;
	} else {
		var url = "/qestnarAnswer/live/getListByUserSnAjax";
		$.ajax({
			url: url,
			type: "GET",
			dataType: "json",
			async: false,	// 동기식 ajax : 통신이 완료될 떄 까지 다음 line 진행 안함
			data : { qestnarGroupCd : qestnarGroupCd },
			contentType: "application/json",
			success: function ( ajaxResult ) {
				
				// 1. 문의 > 내 문의내역 초기화
//				eraseLayerData( ['changeInqryData'] );
				$(".tab.myInquiryList").empty();
				
				// 2.문의 > 내 문의내역 data setting
				if ( ajaxResult.targetDto == null || ajaxResult.targetDto.length == 0 ) {
					// 문의 내역이 없습니다.
					$(".tab.myInquiryList").append(
						$( "<li>" ).attr("class","no-data").append(
							"문의 내역이 없습니다."
						)
					)
				} else {
					
					// 내 문의 내역 개수만큼 순회하며 li 에 뿌림
					for( let item of ajaxResult.targetDto ) {
						
						var type = '';
						var title = '';
						
						for( let itemInfo of ajaxResult.targetQestnarAnswerDetailList ) {
							if ( item.qestnarAnswerSn == itemInfo.qestnarAnswerSn ) {
								if ( itemInfo.qestnarQestnItemCn.includes("유형") ) {
									type = itemInfo.qestnarQestnDetailCn;
								} else if ( itemInfo.qestnarQestnItemCn.includes("제목") ) {
									title = itemInfo.qestnarAnswer;
								}
							}
						}
						
						// 답변 개수 존재한다면 표출 
						var replyHtml = '';
						if ( item.replyYn == 'Y' ) {
							replyHtml = "<em class='replyCnt'> [" + item.replyCnt + "]</em>"
						}
						
						$(".tab.myInquiryList").append(
							$( "<li>" ).attr( "onclick", "detailInquiry( '" + item.qestnarGroupCd + "', '" + item.qestnarAnswerSn + "' )" ).append(
								$( "<div>" ).attr( "class", "subInfo" ).append(
									$( "<span>" ).append( "[" + type + "]" ),
									$( "<span>" ).attr( "class", "regDt" ).append( item.regDt )
								),
								$( "<div>" ).attr( "class", "titleInfo mt_15p" ).append(
									$( "<p>" ).append( title  + replyHtml )
								)
							)
						)
					}
				}
				
			}
		});
		
	}
	
	
	// - 3. 문의하기
	setQestnar('QESTN003', 'inquiry');
	 
}



//var _targetQestnarGroup;			// 설문조사 그룹 
//var _targetQestnarQestnList;			// 설문조사 질문 list
//var _targetQestnarQestnDetailList;		// 설문조사 질문 상세 list

// 설문조사 조회 layer 내용 setting 작업
function setQestnarLayerData( ajaxResult ) {

	var _targetQestnarGroup = ajaxResult.targetDto;									// 설문조사 그룹
	var _targetQestnarQestnList = ajaxResult.targetQestnarQestnList;					// 설문조사 질문
	var _targetQestnarQestnDetailList = ajaxResult.targetQestnarQestnDetailList;		// 설문조사 질문 상세 (선택지)
	
	// title
	$(".pop_header .qestnarGroupNm").text( _targetQestnarGroup.qestnarGroupNm );
	
	// qestnarGroupSn
	$("[name='qestnarInsertForm']").find("[name='qestnarGroupSn']").val( _targetQestnarGroup.qestnarGroupSn );
	
	// upendGdcc : 상단 안내문
	if ( _targetQestnarGroup.upendGdccSetYn == 'Y' ) {
		$("[name='qestnarInsertForm']").find(".pop_content .upendGdcc").append( 
			$( "<div>" ).attr( "class", "upendGdccArea qBox").append(
				$("<pre>").attr("class", "preContent").append(
					_targetQestnarGroup.upendGdcc
				)
			)
		);
	}
	
	// qestnarList
	if ( _targetQestnarQestnList != null && _targetQestnarQestnList.length > 0 ) {
		for(let qItem of _targetQestnarQestnList) {
			
			$answerType = '';
			
			// 필수여부 변수
			var qestnarQestnEssntlYn = ( qItem.qestnarQestnEssntlYn == 'Y') ? ' required' : '' ;
			
			switch( qItem.qestnarQestnItemTyCd ) {
				
				case "ANSWER" :
					
					$answerType = $( "<input>" ).attr("type", "text" ).attr("class", "qAnswer" + qestnarQestnEssntlYn ).attr( "title", qItem.qestnarQestnItemCn ).attr("name", "qestnarAnswer" )
					break;
				
				case "ANSWER_LONG" :
					
					$answerType = $( "<textarea>" ).attr("class", "qAnswer" + qestnarQestnEssntlYn ).attr( "title", qItem.qestnarQestnItemCn ).attr("name", "qestnarAnswer" ).attr("rows", "10").attr("cols", "30")
					break;
					
				case "SELECTIVE_ONE" :
					
					$answerType += "<div class='optionList'>";
					for ( let qOption of _targetQestnarQestnDetailList ) {
						
						if( qItem.qestnarQestnSn == qOption.qestnarQestnSn) {
							$answerType += "<div class='option_item'>";
						    $answerType += "    <div class='item_content'>";
						    $answerType += "        <label class='radioLabel'>";
						    $answerType += "        <input type='radio' name='qestnarQestnDetailSn' value='" + qOption.qestnarQestnDetailSn + "' class='qAnswer" + qestnarQestnEssntlYn + "' title='" + qItem.qestnarQestnItemCn + "'  >";
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
					for ( let qOption of _targetQestnarQestnDetailList ) {
						
						if( qItem.qestnarQestnSn == qOption.qestnarQestnSn) {
							$answerType += "<div class='option_item'>";
						    $answerType += "    <div class='item_content'>";
						    $answerType += "        <input type='checkbox' name='qestnarQestnDetailSnList' value='" + qOption.qestnarQestnDetailSn + "' id='" + qOption.qestnarQestnSn  + "_" + qOption.qestnarQestnDetailSeq  + "' class='qAnswer" + qestnarQestnEssntlYn + "' title='" + qItem.qestnarQestnItemCn + "' >";
						    $answerType += "        <label for='" + qOption.qestnarQestnSn  + "_" + qOption.qestnarQestnDetailSeq  + "'>" + qOption.qestnarQestnDetailCn + "</label>";
						    $answerType += "    </div>";
						    $answerType += "</div>";
						}
					}
					$answerType += "</div>";
					
					break;
			}
			
			$("[name='qestnarInsertForm']").find(".pop_content .qestnarList").append(
				$( "<div>" ).attr( "class", "qBox").append(
					
					$( "<input>" ).attr( "type", "hidden" ).attr( "name", "qestnarQestnSn" ).attr( "value", qItem.qestnarQestnSn ).append(),
					$( "<input>" ).attr( "type", "hidden" ).attr( "name", "qestnarQestnItemTyCd" ).attr( "value", qItem.qestnarQestnItemTyCd ).append(),
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


// 설문조사 조회 layer 내용 setting 작업 -> 1:1문의 용 layer set 
function setQestnarForInquiryLayerData( ajaxResult ) {
	var _targetQestnarGroup = ajaxResult.targetDto;									// 설문조사 그룹
	var _targetQestnarQestnList = ajaxResult.targetQestnarQestnList;					// 설문조사 질문
	var _targetQestnarQestnDetailList = ajaxResult.targetQestnarQestnDetailList;		// 설문조사 질문 상세 (선택지)
	
	// qestnarGroupSn
	$("[name='qestnarForInquiryInsertForm']").find("[name='qestnarGroupSn']").val( _targetQestnarGroup.qestnarGroupSn );
	
	// upendGdcc : 상단 안내문
	if ( _targetQestnarGroup.upendGdccSetYn == 'Y' ) {
		$("[name='qestnarForInquiryInsertForm']").find(".pop_content .upendGdcc").append( 
			$( "<div>" ).attr( "class", "upendGdccArea qBox").append(
				$("<pre>").attr("class", "preContent").append(
					_targetQestnarGroup.upendGdcc
				)
			)
		);
	}
	
	// qestnarList
	if ( _targetQestnarQestnList != null && _targetQestnarQestnList.length > 0 ) {
		for(let qItem of _targetQestnarQestnList) {
			
			$answerType = '';
			
			var classNm = 'chat_tit_form';	// 기본 클래스 ( ANSWER_LONG 일때만 다른 class 사용.. )
			
			// 필수여부 변수
			var qestnarQestnEssntlYn = ( qItem.qestnarQestnEssntlYn == 'Y') ? ' required' : '' ;
			
			
			switch( qItem.qestnarQestnItemTyCd ) {
				
				case "ANSWER" :
					
					$answerType = $( "<input>" ).attr("type", "text" ).attr("class", "qAnswer" + qestnarQestnEssntlYn ).attr("name", "qestnarAnswer" ).attr( "title", qItem.qestnarQestnItemCn ).attr("placeholder", qItem.qestnarQestnItemCn)
					break;
									
				case "ANSWER_LONG" :
					
					classNm = 'chat_disc_form';
					$answerType = $( "<textarea>" ).attr("class", "qAnswer" + qestnarQestnEssntlYn ).attr("name", "qestnarAnswer" ).attr("rows", "10").attr( "title", qItem.qestnarQestnItemCn ).attr("cols", "30").attr("placeholder", qItem.qestnarQestnItemCn)
					break;
					
				case "SELECTIVE_ONE" :
					
					$answerType += "<select name='qestnarQestnDetailSn' class='qAnswer" + qestnarQestnEssntlYn + "' title='" + qItem.qestnarQestnItemCn + "' >";
					$answerType += "	<option value='' >" + qItem.qestnarQestnItemCn + " 선택</option>";
					
					for ( let qOption of _targetQestnarQestnDetailList ) {
						
						if( qItem.qestnarQestnSn == qOption.qestnarQestnSn) {
							$answerType += "	<option value='" + qOption.qestnarQestnDetailSn + "' >" + qOption.qestnarQestnDetailCn + "</option>";
						}
					}
					$answerType += "</select>";
					break;
					
				case "SELECTIVE_MULTI" :
					// TODO
					
//					$answerType += "<div class='optionList'>";
//					for ( let qOption of _targetQestnarQestnDetailList ) {
//						
//						if( qItem.qestnarQestnSn == qOption.qestnarQestnSn) {
//							$answerType += "<div class='option_item'>";
//						    $answerType += "    <div class='item_content'>";
//						    $answerType += "        <input type='checkbox' name='qestnarQestnDetailSnList' value='" + qOption.qestnarQestnDetailSn + "' id='" + qOption.qestnarQestnSn  + "_" + qOption.qestnarQestnDetailSeq  + "'>";
//						    $answerType += "        <label for='" + qOption.qestnarQestnSn  + "_" + qOption.qestnarQestnDetailSeq  + "'>" + qOption.qestnarQestnDetailCn + "</label>";
//						    $answerType += "    </div>";
//						    $answerType += "</div>";
//						}
//					}
//					$answerType += "</div>";
					
					break;
			}
			
			$("[name='qestnarForInquiryInsertForm']").find(".qestnarList").append(
				$( "<div>" ).attr( "class", classNm + " qBox").append(
					
					$( "<input>" ).attr( "type", "hidden" ).attr( "name", "qestnarQestnSn" ).attr( "value", qItem.qestnarQestnSn ).append(),
					$( "<input>" ).attr( "type", "hidden" ).attr( "name", "qestnarQestnItemTyCd" ).attr( "value", qItem.qestnarQestnItemTyCd ).append(),
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
function insertQestnarAnswer( formName ){
	
	
	var qestnarCnt =  $("[name='" + formName + "']").find( ".qestnarList" ).find(".qBox").length;
	
	var checkValid = false;	// 유효성 걸렸을 때 true
	$("[name='" + formName + "']").find( ".qestnarList" ).find(".qBox").each( function ( index ) {
		// input 
		if ( checkValid ) {
			return false;
		}
		var qestnarQestnItemTyCd = $(this).find("[name='qestnarQestnItemTyCd']").val();
		var $targetInput;
		
		// S :  유효성 체크
		// ============================================================================================
		switch( qestnarQestnItemTyCd ) {
				
			case "ANSWER" :
				$targetInput = $(this).find("input").not("[type='hidden']");
				if ( 
					$targetInput.hasClass("required") && 
					$targetInput.val() == ''
				 ) {
					 alert("항목을 입력해주세요. [" + $targetInput[0].title + "]");
					 $targetInput.focus();
					 checkValid = true;
				 }
				break;
								
			case "ANSWER_LONG" :
				
				$targetInput = $(this).find("textarea").not("[type='hidden']");
				if ( 
					$targetInput.hasClass("required") && 
					$targetInput.val() == ''
				 ) {
					 alert("항목을 입력해주세요. [" + $targetInput[0].title + "]");
					 $targetInput.focus();
					 checkValid = true;
				 }
				break;
				
			case "SELECTIVE_ONE" :
				
				$targetInput = $(this).find("input").not("[type='hidden']");
				
				// $targetInput 이 null 일 경우는 selectbox로도 target을 체크해준다. ( 1:1문의 를 위해 )
				
				if ( 
					$targetInput.hasClass("required") && 
					$(this).find("input[type='radio']:checked").length == 0
				) {
					alert("항목을 입력해주세요. [" + $targetInput[0].title + "]");
					$targetInput.focus();
					checkValid = true;
				}
				
				if( !$targetInput.length ) {
					// 선택지를 selectbox로 했을 경우
					
					$targetInput = $(this).find("select");
					
					if ( 
					$targetInput.hasClass("required") && 
					$targetInput.find(":selected").val() == ''
					) {
						alert("항목을 입력해주세요. [" + $targetInput[0].title + "]");
						$targetInput.focus();
						checkValid = true;
					}
					
				}
				 
				 
				break;
				
			case "SELECTIVE_MULTI" :
				
				$targetInput = $(this).find("input").not("[type='hidden']");
				if ( 
					$targetInput.hasClass("required") && 
					$(this).find("input[type='checkbox']:checked").length == 0
				 ) {
					 alert("항목을 입력해주세요. [" + $targetInput[0].title + "]");
					 $targetInput.focus();
					 checkValid = true;
				 }
				break;
		}
		// ============================================================================================
		// E :  유효성 체크
		
		// 마지막 index 인 경우 submit
		if ( index == ( qestnarCnt - 1 ) && !checkValid ) {
			
			// 필수 항목 유효성 검사.   
			if(confirm("제출하시겠습니까?")) {
			
				// - 주관식
				// - 주관식 장문형
				// - 객관식 (단일) 
				// - 객관식 (다중)
				
				// 입력항목 정보 배열 처리 :
				// 입력항목 정보들 각각 값들을 배열화시켜 submit
				// div name : qestnarQestnAddDiv 중 newForm 이 아닌 div 를 배열로 정리
				
				$("[name='" + formName + "']").find( ".qestnarList" ).find(".qBox").each( function ( index ) {
					
						// list 를 수동으로 처리
						// =========================
						// qestnarQestnItemTyCd
						// qestnarQestnSn
						// qestnarAnswer
						// qestnarQestnDetailSn
						// qestnarQestnDetailSnList
						// =========================
						// 입력 유형, 입력 항목, 필수 여부
						$( this ).find( "[name=qestnarQestnSn]" ).attr( "name", "qestnarAnswerDetails[" + index + "].qestnarQestnSn" );		// 입력 유형 항목 코드
						$( this ).find( "[name=qestnarQestnItemTyCd]" ).attr( "name", "qestnarAnswerDetails[" + index + "].qestnarQestnItemTyCd" );		// 입력유형항목코드
						$( this ).find( "[name=qestnarAnswer]" ).attr( "name", "qestnarAnswerDetails[" + index + "].qestnarAnswer" );		// 입력 유형 항목 코드
						$( this ).find( "[name=qestnarQestnDetailSn]" ).attr( "name", "qestnarAnswerDetails[" + index + "].qestnarQestnDetailSn" );		// 입력 유형 항목 코드
						$( this ).find( "[name=qestnarQestnDetailSnList]" ).attr( "name", "qestnarAnswerDetails[" + index + "].qestnarQestnDetailSnList" );		// 입력 유형 항목 코드
						
				});
				
				$("[name='" + formName + "']").submit();
			}
		}
	});
}


function detailInquiry( qestnarGroupCd, qestnarAnswerSn ) {
	
	// detailInqruiyChangeData 초기화
	$("[name='qestnarForInquiryInsertForm']").find(".detailInqruiyChangeData").val();
	$("[name='qestnarForInquiryInsertForm']").find(".detailInqruiyChangeData").text();
	$("[name='qestnarForInquiryInsertForm']").find(".detailInqruiyChangeData").empty();
	
	var url = "/qestnarAnswer/live/detailAjax";
	$.ajax({
		url: url,
        type: "GET",
        dataType: "json",
        async: false,	// 동기식 ajax : 통신이 완료될 떄 까지 다음 line 진행 안함
        data: { 
				qestnarGroupCd : qestnarGroupCd, 
        		qestnarAnswerSn : qestnarAnswerSn
        		}, 
        contentType: "application/json",
        success: function ( ajaxResult ) {
			
			// targetDto 존재 여부 확인
			if ( ajaxResult.targetDto == null ) {
				alert("문의 내역을 불러오는 중 오류가 발생했습니다. 다시 시도해주세요.");
				return false;
			} else {
				
				// 1. 문의 내용 setting
				// 2. 답변 내용 setting (답변이 존재할때만)
				
				// 1. 문의 내용 setting
				for ( let answer of ajaxResult.targetQestnarAnswerDetailList ){
					
					if ( answer.qestnarQestnItemCn.includes("유형") ) {
						$("[name='qestnarForInquiryInsertForm']").find("[name='detailInquiryTy']").text( answer.qestnarQestnDetailCn);
					} else if ( answer.qestnarQestnItemCn.includes("제목") ) {
						$("[name='qestnarForInquiryInsertForm']").find("[name='detailInqruiySj']").val( answer.qestnarAnswer);
					} else if ( answer.qestnarQestnItemCn.includes("내용") ) {
						$("[name='qestnarForInquiryInsertForm']").find("[name='detailInqruiyCn']").val( answer.qestnarAnswer);
					}
					
					// 목록 ( myInquiryList )  와 상세 ( myInquiryDetail ) 표출 swap\
					showLayerTabType( 'myInquiryDetail' );
				}
				
				// 2. 답변 내용 setting (답변이 존재할때만)
				if ( ajaxResult.targetDto.replyYn == "Y" && ajaxResult.targetQestnarAnswerReplyList != null 
						&& ajaxResult.targetQestnarAnswerReplyList.length > 0 ) {
							
					$(".myInquiryDetail .myInquiryDetailReplyWrap").append(
						$("<div>").attr("class", "myInquiryDetailReplyList")
					)
					
					for ( let answerReply of ajaxResult.targetQestnarAnswerReplyList ) {
						// 답변 개수만큼 순회 
						$(".myInquiryDetailReplyList").append(
							$( "<div>" ).attr( "class", "replyWrap" ).append(
								$( "<div>" ).attr( "class", "chat_tit_form qBox" ).append(
									$( "<div>" ).attr( "class", "qAnswerWrap" ).append(
										$( "<input>" ).attr("type", "text" ).attr("class", "qAnswer replyUserNm" ).attr("readonly", "readonly" ).attr( "value", answerReply.userNm ),
										$( "<input>" ).attr("type", "text" ).attr("class", "qAnswer replyRegDt" ).attr("readonly", "readonly" ).attr( "value", answerReply.regDt ),
									)
								),	
								$( "<div>" ).attr( "class", "chat_disc_form qBox mt_3p" ).append(
									$( "<div>" ).attr( "class", "qAnswerWrap" ).append(
										$( "<textarea>" ).attr("class", "qAnswer replyCn" ).attr( "rows", "3" ).attr( "cols", "30" ).attr( "readonly", "readonly" ).append( answerReply.qestnarAnswerReplyCn )
									)
								)	
							)
						);
					}
					
				}
				
				
			}
			
		}
		
	});
	
}


function detailFaqNtt( nttSn ) {
	
	// detailFaqNttChangeData 초기화
	$("[name='qestnarForInquiryInsertForm']").find(".detailFaqNttChangeData").val();
	$("[name='qestnarForInquiryInsertForm']").find(".detailFaqNttChangeData").text();
	$("[name='qestnarForInquiryInsertForm']").find(".detailFaqNttChangeData").empty();
	
	var url = "/ntt/live/detailAjax";
	$.ajax({
		url: url,
        type: "GET",
        dataType: "json",
        async: false,	// 동기식 ajax : 통신이 완료될 떄 까지 다음 line 진행 안함
        data: { 
				nttSn : nttSn 
        		}, 
        contentType: "application/json",
        success: function ( ajaxResult ) {
			
			// targetDto 존재 여부 확인
			if ( ajaxResult.flag == "E" ) {
				alert("FAQ 게시글을 불러오는 중 오류가 발생했습니다. 다시 시도해주세요.");
				return false;
			} else {
				
				// 1. FAQ 상세 내용 setting
				target = ajaxResult.target;
				$("[name='qestnarForInquiryInsertForm']").find("[name='detailFaqNttUserNm']").text( target.userNm );
				$("[name='qestnarForInquiryInsertForm']").find("[name='detailFaqNttNm']").val( target.nttNm );
				$("[name='qestnarForInquiryInsertForm']").find("[name='detailFaqNttCn']").html( target.nttCn );
				
				showLayerTabType('faqNttDetail');
				
				
			}
			
		}
		
	});
	
}



// 내 문의내역 > 내역 상세보기 > '목록' 버튼 클릭 시
function showLayerTabType( divName ){
	
	if ( divName == 'myInquiryList') {
		
		$("[name='qestnarForInquiryInsertForm']").find(".myInquiryList").show();
		$("[name='qestnarForInquiryInsertForm']").find(".myInquiryDetail").hide();
	} else if ( divName == 'myInquiryDetail' ) {
		
		$("[name='qestnarForInquiryInsertForm']").find(".myInquiryDetail").show();
		$("[name='qestnarForInquiryInsertForm']").find(".myInquiryList").hide();
		
		
	} else if ( divName == 'faqNttDetail' ) {
		
		$("[name='qestnarForInquiryInsertForm']").find(".faqNttDetail").show();
		$("[name='qestnarForInquiryInsertForm']").find(".faqNttList").hide();
		
	} else if ( divName == 'faqNttList' ) {
		
		$("[name='qestnarForInquiryInsertForm']").find(".faqNttList").show();
		$("[name='qestnarForInquiryInsertForm']").find(".faqNttDetail").hide();
	}
}

/* E : 설문조사 set */