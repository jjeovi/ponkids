
// ------------- function () 함수 실행  호출시점 : DOM Tree 생성 완료 후 -----------------
$( function () {
	// mypage용 mcd setting
	if ( mypageMcd != null || mypageMcd != '' ) {
		// mypageMcd 가 존재한다면
		// mypage의 mcd setting
		$(".mp_list .list_area ul ." + mypageMcd ).addClass("active");
		
    }
	
//	$("input[name='reviewGrade']").on("click", function(){
//		alert("eee");
//	})

	

} );
// ------------- function () 함수 종료 -----------------

// 마이페이지 > 신청내역 > item 클릭
function detailHistory( pk ) {
	location.href = "/mypage/reqstHistory/detail?pk=" + pk;
	
}

/*  S : 후기 작성하기 */
// ======================================================================================

// 후기 작성하기 버튼 클릭 event
function goClassReview( classSn ){
	

	var url = '/class/live/getClassByIdAjax';
	$.ajax( {
        url: url,
        type: "GET",
        dataType: "json",
        async: false,	// 동기식 ajax : 통신이 완료될 떄 까지 다음 line 진행 안함
        data: {pk : classSn}, // 검색할 값 setting
        contentType: "application/json",
        success: function ( ajaxResult ) {
			
			if ( ajaxResult.resultOne == null ) {
				alert("클래스 정보가 없습니다. 다시 시도해 주세요.");
				
			} else {
				// 1. 후기 작성하기 layer 초기화
				eraseReviewLayerData();
				
				// 2. 후기 작성하기 layer data setting 
				setReviewLayerData( ajaxResult );	
				
				// 3. 문의 등록 layer 표출
				showLayer('registClassReview');
			}
		}
	} );
}
	
	
		
	// 후기 작성하기 모달창 ( 관리 클릭시 ) 내용 초기화 작업
	function eraseReviewLayerData() {
		
		// reviewData class 초기화 
		$(".reviewData").empty();
		
	}
	
	
	// 문의 상세 조회 모달창 내용 setting 작업
	function setReviewLayerData( ajaxResult ) {
		
		var result 			= ajaxResult.resultOne;
		
		$("#reviewClassSn").val( result.classSn );
		$("#reviewImg").append(
			$( "<img>" ).attr("src","/getImage?atchFileSn="+ result.thumbAtchFileSn).append()
		);
		
		$("#reviewClassSj").append( result.classSj );
		
	}
	
	
	function insertClassReview(){
		
		// 후기 insert 진행
		if ( confirm("후기를 등록하시겠습니까?") ) {
			
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
			success: function ( result ) {
				
				// return type : Map<String, Object>
				var resultOne = result.resultOne;
				
				console.log( resultOne );
			}
		});
		
	}
	
	/*  E : 후기 작성하기 */