const emailRegexp = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-za-z0-9\-]+/;   // 이메일 유효성 검사
const telNoRegexp = /^(01[016789]{1})[0-9]{3,4}[0-9]{4}$/;
const passwordRegexp = /^(?=.*\d)(?=.*[a-z])(?=.*[!@#$%^&*]).{8,20}$/;

var dupCheckFlag 			= false;	// 중복체크 변수 선언
var passwordValidCheck		= false;	// 비밀번호 유효성 체크 확인 
var passwordMatchCheckFlag	= false;  	// 비밀번호 비교 일치 여부 확인


$( function() {
	
	const lgStatus = urlParams.get('lgStatus');
	if ( lgStatus == 'login' ) {
		showPopup(lgStatus);
		
		if ( errMsg != null && errMsg != '' ) {
			alert(errMsg);
		}
	}
	

	// 아이디 입력 focusout 처리 ( 중복체크 로직 )
	$( "#userInsertForm" ).find("[name='userId']").focusout( function () {
		dupCheckFlag = false;
		var userId = $( this ).val();	  // userId값 넘기기

		if ( validId( userId ) ) {

			$.ajax( {
				url: "/live/idDupCheck",
				type: "GET",
				dataType: "json",
				data: { userId: userId },
				contentType: "application/json",
				success: function ( result ) {
					if ( !result ) dupCheckFlag = true;
					idDupResult( dupCheckFlag, "checkResult" );
				}
			} );
		} else {
			idDupResult( dupCheckFlag, "checkResult" );
		}
	} )
	
	// 비밀번호 유효성 체크 focusout 처리
	$( "#userInsertForm" ).find("[name='password']").focusout( function () {
		checkPasswordMatching();
	} );
	
	// 비밀번호 일치 여부 체크 
	$( "#userInsertForm" ).find("[name='password']").keyup(function() {
		checkPasswordMatching();
	});
    
	// 비밀번호 일치 여부 체크 
	$( "#userInsertForm" ).find("[name='password2']").keyup(function() {
		checkPasswordMatching();
	});
    
});

// S : file upload (img) 관련
//preview image
function fileChange( e ) {
    {
        var parent = $( e ).parent().parent().parent();
        var parent2 = $( e ).parent().parent();
        parent.children( '.upload-display' ).remove();
        parent2.children( '.upload-file-name' ).empty();

        // id값 제거
        parent.children( "[name='atchFileSn']" ).remove();
        
        // 파일 업로드 액션 여부 체크 
        if ( $("[name='fileAtchActYn']").length ) {	// 해당 name 으로 된 input 항목 있을 때만 실행
        	// 해당 여부의 값을 Y 로 변경
        	if ( $("[name='fileAtchActYn']").val() != 'Y' ) {
				$("[name='fileAtchActYn']").val("Y");
			}
		}

        if ( window.FileReader && $( e )[0].files[0] != null ) {

            parent2.children( '.upload-file-name' ).prepend( '<input class="input-file-name" value="' + $( e )[0].files[0].name + '" disabled="disabled">' );

			// image 파일만
            if ( !$( e )[0].files[0].type.match( /image\// ) ) {
				parent.prepend( '<div class="upload-display"><div class="upload-thumb-wrap"><img class="upload-thumb noimg" name="thumbSrc"></div></div>' );
                return;
            }

			var reader = new FileReader();
			reader.onload = function ( e ) {
				var src = e.target.result;
				parent.prepend( '<div class="upload-display"><div class="upload-thumb-wrap"><img src="' + src + '" class="upload-thumb" name="thumbSrc"></div></div>' );
			}
			reader.readAsDataURL( $( e )[0].files[0] );
		} else {
			// var imgSrc = document.selection.createRange().text;
//			parent.prepend( '<div class="upload-file-name"><input class="input-file-name" value="선택된 파일 없음" disabled="disabled"></div>' );
			
			parent2.children( '.upload-file-name' ).prepend( '<input class="input-file-name" value="선택된 파일 없음(.gif, .jpg, .png)" disabled="disabled">' );
			parent.prepend( '<div class="upload-display"><div class="upload-thumb-wrap"><img class="upload-thumb noimg" name="thumbSrc"></div></div>' );
			//
			// img[0].style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(enable='true',sizingMethod='scale',src=\""+imgSrc+"\")";
		}
	}
}

// E : file upload (img) 관련

// 라디오 버튼 클릭시 active 클래스 추가
function clickRadioEvent( e ) {
    // 라디오 버튼의 label들을 찾아 모든 label 에 active 클래스를 제거 후, 클릭된 label 에 active클래스 추가
    $( e ).parent().siblings( "label" ).removeClass( "active" );
    $( e ).parent().addClass( "active" );
}

// 자녀 추가 버튼 클릭
function addChldrn() {
	// target 자녀 div
	var $targetChldrnDiv = $( "[name='chldrnAddDiv'].newForm" );

	// 이름, 성별 유효성 검사
	if ( !chldrnValidCheck( $targetChldrnDiv ) ) return false;

	// 성별 (라디오) 값 hidden 태그에 저장
	$targetChldrnDiv.find( "[name='chldrnGender']" ).val( $targetChldrnDiv.find( "[name='chldrnGenderRadio']:checked" ).val() );

	// 신규자녀정보를 회원가입 layer 에 뿌려준다.(사진 정보도 있으면 같이 뿌려준다.)
	addChldrnRow( $targetChldrnDiv );	// 자녀정보테이블에추가

	// 모든 chldrnAddDiv newForm 제거
	$( "[name='chldrnAddDiv']" ).removeClass( "newForm" );
	// 모든 chldrnAddDiv 숨기기
	$( "[name='chldrnAddDiv']" ).hide();

	// 새로운 폼 추가
	var $newChldrnForm = $( "<div name='chldrnAddDiv' class='addChldrn_wrap newForm'>" ).load( "/pon/layer/addChldrnForm.html" );
	$( "#addChldrnModalBody" ).append( $newChldrnForm );


	hidePopupLv2('addChldrn');
	// 모달창 닫기
//	$( "#addChldrnModal" ).modal( 'hide' );
	return true;
}


// 자녀 form 유효성 검사
function chldrnValidCheck( $targetChldrnDiv ) {

	var chldrnNm = $targetChldrnDiv.find( "[name='chldrnNm']" ).val();
	var chldrnGenderRadio = $targetChldrnDiv.find( "[name='chldrnGenderRadio']:checked" ).val();
	

	if ( !validCheckName( chldrnNm ) ) {
		return false;
	} else if ( chldrnGenderRadio == null || chldrnGenderRadio == '' ) {
		alert( "자녀 성별을 선택해주세요." );
		return false;
	}
	
	// 생년월일 유효성 검사 로직 추가
	var chldrnBrdtDate = $targetChldrnDiv.find( "[name='chldrnBrdtDate']" ).val();
	
	if (!isValidDate(chldrnBrdtDate) ) {
		alert("날짜는 yyyymmdd 형식으로 입력해주세요. ex)20130119");
		return false;
	}
	
	// 연락처 유효성 검사 
	var chldrnTelNo = $targetChldrnDiv.find( "[name='chldrnTelNo']" ).val();
	
	if( chldrnTelNo == "" ) { 
		
	} else {
		 
		if ( !telNoRegexp.test( chldrnTelNo ) ) {	// telNoRegexp = /^(01[016789]{1})[0-9]{3,4}[0-9]{4}$/;
			alert("연락처는 숫자 10~11자리로만 입력해주세요.");
			return false;
		}
		
	}

	return true;
}



// 이름 유효성 검사
// - 2자 < name < 10자
function validCheckName( name ) {
	if ( name == '' ) {
		alert( "이름을 입력해주세요." );
		return false;
	} else if ( name.length < 2 || name.length > 10 ) {
		alert( "이름은 2자 이상 10자 이하로 입력해주세요." );
		return false;
	}
	return true;
}

// 자녀 정보 테이블에 add
function addChldrnRow( $targetChldrnDiv ) {
	2
	// $("#chldrnTableBody tr").length  // /현재 테이블 tr 개수
	
	var chldrnNm = $targetChldrnDiv.find( "[name='chldrnNm']" ).val();
	var chldrnGender = $targetChldrnDiv.find( "[name='chldrnGenderRadio']:checked" ).val() == "M" ? "남" : "여"  ;
	var chldrnBrdtDate = $targetChldrnDiv.find( "[name='chldrnBrdtDate']" ).val();
	var chldrnTelNo = $targetChldrnDiv.find( "[name='chldrnTelNo']" ).val();
	var chldrnEmail = $targetChldrnDiv.find( "[name='chldrnEmail']" ).val();
	
	chldrnBrdtDate = chldrnBrdtDate.substring(0,4) + "-" + chldrnBrdtDate.substring(4,6) + "-" + chldrnBrdtDate.substring(6,8);
	var nmGender = chldrnNm + "(" +  chldrnGender + ")";
	
	var thumbSrc =  $targetChldrnDiv.find( "[name='thumbSrc']" ).attr("src");
	
	var noimg = "";
	if( thumbSrc == '' || thumbSrc == undefined) { 
		noimg = " noimg";
	}
	
	
	// 추가
	$( "#chldrnTableBody" ).append(
		$( "<div>" ).attr( "class", "info_chldrn_list" ).append(
			
			$( "<div>" ).attr( "class", "filebox preview-image" ).append(
				$("<div>").attr ( "class", "upload-display" ).append(
					$("<div>").attr("class", " upload-thumb-wrap").append(
						$( "<img>" ).attr("class", "upload-thumb" + noimg ).attr("src", thumbSrc ).append( "" )
					)
				)
			),
			$("<input>").attr("type","text").attr("name", "chldrnNm" ).attr( "value",  nmGender ).attr("placeholder", "이름 (성별)" ).attr("readonly", "readonly"),
			$("<input>").attr("type","text").attr("name", "chldrnBrdtDate" ).attr( "value", chldrnBrdtDate ).attr("placeholder", "생년월일" ).attr("readonly", "readonly"),
			$("<input>").attr("type","text").attr("name", "chldrnTelNo" ).attr( "value", chldrnTelNo ).attr("placeholder", "연락처" ).attr("readonly", "readonly"),
			$("<input>").attr("type","text").attr("name", "chldrnEmail" ).attr( "value", chldrnEmail ).attr("placeholder", "이메일" ).attr("class", "last" ).attr("readonly", "readonly"),
			
			$( "<div>" ).attr( "class", "right" ).append(
				$("<label>").attr ( "class", "btn btn_delete" ).attr("onclick", "deleteChldrn(this)" ).append(
					"삭제"
				)
			),
		)
		
		
//		$( "<tr>" ).append(
//			$( "<td>" ).attr( "name", "trNum" ).append( $( "#chldrnTableBody tr" ).length + 1 ),					// 순번
//			$( "<td>" ).attr( "name", "chldrnNm" ).append( $targetChldrnDiv.find( "[name='chldrnNm']" ).val() ),		// 이름
//			$( "<td>" ).attr( "name", "chldrnGenderRadio" ).append( $targetChldrnDiv.find( "[name='chldrnGenderRadio']:checked" ).val() == "M" ? "남자" : "여자" ),	// 성별
//			$( "<td>" ).attr( "name", "chldrnBrdtDate" ).append( $targetChldrnDiv.find( "[name='chldrnBrdtDate']" ).val() ),  // 생년월일
//			$( "<td>" ).attr( "name", "chldrnTelNo" ).append( $targetChldrnDiv.find( "[name='chldrnTelNo']" ).val() ),	 // 연락처
//			$( "<td>" ).attr( "name", "chldrnEmail" ).append( $targetChldrnDiv.find( "[name='chldrnEmail']" ).val() ),	 // 이메일
//			$( "<td>" ).append(														 // 관리
//				$( "<button>" ).attr( "onclick", "modifyChldrn(this)" ).attr( "type", "button" ).addClass( "btn btn-success btn-sm table-btn" ).append( "수정" ),
//				$( "<button>" ).attr( "onclick", "deleteChldrn(this)" ).attr( "type", "button" ).addClass( "btn btn-danger btn-sm table-btn" ).append( "삭제" )
//			)
//		)
	);
}





function isValidDate(yyyymmdd) {

	var r = true;

	try {

		var date = [];
		if (yyyymmdd.length == 8) {

			date[0] = yyyymmdd.substring(0, 4);
			date[1] = yyyymmdd.substring(4, 6);
			date[2] = yyyymmdd.substring(6, 8);

		} else if (yyyymmdd.length == 10) {

			date = yyyymmdd.split("-");
		}

		var yyyy = parseInt(date[0], 10);
		var mm = parseInt(date[1], 10);
		var dd = parseInt(date[2], 10);

		var dateRegex = /^(?=\d)(?:(?:31(?!.(?:0?[2469]|11))|(?:30|29)(?!.0?2)|29(?=.0?2.(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00)))(?:\x20|$))|(?:2[0-8]|1\d|0?[1-9]))([-.\/])(?:1[012]|0?[1-9])\1(?:1[6-9]|[2-9]\d)?\d\d(?:(?=\x20\d)\x20|$))?(((0?[1-9]|1[012])(:[0-5]\d){0,2}(\x20[AP]M))|([01]\d|2[0-3])(:[0-5]\d){1,2})?$/;

		r = dateRegex.test(dd + '-' + mm + '-' + yyyy);

	} catch (err) {
		r = false;
	}

	return r;

}


// 모달 버튼 setting
function setModalBtn( mode ) {

	// mode : C, U
	// C : 등록 (create)
	// U : 수정 (update)

	if ( mode == "C" ) {
		// 모달창의 수정 버튼 숨기고 저장 버튼을 표시
		$( "#addChldrnBtn" ).show();
		$( "#updateChldrnBtn" ).hide();

		// chldrnAddDiv newForm show
		$( "[name='chldrnAddDiv'].newForm" ).show();
		// chldrnModDiv hide
		$( "[name='chldrnModDiv']" ).hide();
		
		// 자녀 추가 팝업 레이어 효출
		showPopupLv2('addChldrn');

	} else if ( mode == "U" ) {
		
		// 모달창의 저장 버튼 숨기고 수정 버튼을 표시
		$( "#addChldrnBtn" ).hide();
		$( "#updateChldrnBtn" ).show();

		// chldrnModDiv show
		$( "[name='chldrnModDiv']" ).show();
		// chldrnAddDiv newForm hide
		$( "[name='chldrnAddDiv'].newForm" ).hide();
		
	}
}


// 자녀 삭제 버튼
function deleteChldrn( e ) {
	// 몇번째 테이블인지 체크
	var trNum = $( e ).closest( '.info_chldrn_list' ).prevAll().length;

	// 테이블의 tr 과 모달의 trNum 번째 div 2가지를 remove 한다.
	// - 테이블 tr remove
	$( e ).parent().parent().remove();
	// - 모달의 trNum 번째 div remove
	$( "[name='chldrnAddDiv']" ).eq( trNum ).remove();

	$( '#chldrnTable tr' ).each( function () {
		$( this ).find( "[name='trNum']" ).text( ( $( this ).prevAll().length ) + 1 );
	} );

}



// 자녀 추가
function userInsert() {
	
	var validCheck = {};
	var validCheck = validUserForm('userInsertForm');
	
	if ( !validCheck.flag ) {
		alert(validCheck.msg);
		return false;
	} else {
		// 유효성 끝난 후 값 setting 작업

		// ==================== 유효성 모두 통과 후 dataset 정리 ==================================
		// ==================== 유효성 모두 통과 후 dataset 정리 ==================================

		// 거주지역 그 외만 선택 했을 시 return false;
		var resideArea = $( "input:radio[name='resideArea']:checked" ).val();
		var country = $("#join_countryList option:selected").val();
		// 거주지역 set
		if ( resideArea == 'other' ) {
			$( "[name='resideArea']" ).val( country );
		}


		// 자녀정보 배열 처리 :
		// 자녀정보들 각각 값들을 배열화시켜 submit
		// div name : chldrnAddDiv / newForm 이 아닌 div 를 배열로 정리
		$( "[name='chldrnAddDiv']" ).not( '.newForm' ).each( function ( index ) {
			// 자녀이름, 자녀성별, 자녀생년월일, 자녀이메일, 자녀연락처
			$( this ).find( "[name=chldrnNm]" ).attr( "name", "userChldrns[" + index + "].chldrnNm" );					// 자녀 이름
			$( this ).find( "[name=chldrnGender]" ).attr( "name", "userChldrns[" + index + "].chldrnGender" );			// 자녀 성별
			$( this ).find( "[name=chldrnBrdtDate]" ).attr( "name", "userChldrns[" + index + "].chldrnBrdtDate" );		// 자녀 생년월일
			$( this ).find( "[name=chldrnEmail]" ).attr( "name", "userChldrns[" + index + "].chldrnEmail" );			// 자녀 이메일
			$( this ).find( "[name=chldrnTelNo]" ).attr( "name", "userChldrns[" + index + "].chldrnTelNo" );			// 자녀 연락처
			$( this ).find( "[name=file]" ).attr( "name", "userChldrns[" + index + "].file" );							// 파일

		});
		
        var form = $( "#userInsertForm" )[0];
        var formData = new FormData(form);

		var url = "/user/live/mcd/insertAjax"
		$.ajax({
		            url: url,
		            type: "POST",	// 회원저장 POST로
		            async: false,	// 동기식 ajax : 통신이 완료될 떄 까지 다음 line 진행 안함
		            data: formData, // 검색할 값
		            cache: false,
                    contentType : false,
			        processData : false ,
		            success: function ( result ) {
		                // return type : List<CategoryDto>
		                
		                if ( result.flag == "E" ) {
							alert(result.msg);
							
						} else if ( result.flag == "S" ) {
							// TODO : ajax 통신 이후 로직 ( 성공시 ) 구현 
							
							alert(result.msg); 
							
							// 로그인 하러 가기 layer 표출
							hideAllPopup(); 
							showPopup( 'completeJoin' );
							
						}
		                
		            }
	        	});
		
	}
	
}


// 유효성 검사 ID
function validId( userId ) {
    if ( userId == "" ) {
        // alert( "아이디를 입력해주세요." );
        return false;
    }
    return emailValidChk( userId );
}


// 이메일 유효성 정규식 체크 로직
function emailValidChk( email ) {
    if ( emailRegexp.test( email ) === false ) {
        // alert( "유효한 이메일 형식으로 입력해주세요." );
        return false;
    } else {
        return true;
    }
}

// 중복확인 결과 뿌리기
function idDupResult( dupCheckFlag, checkResult ) {

    // all color class remove
    $( "#" + checkResult ).removeClass( "text-primary text-danger" );

    // color setting
    if ( dupCheckFlag ) $( "#" + checkResult ).addClass( "text-primary" );
    else $( "#" + checkResult ).addClass( "text-danger" );

    // 사용 여부
    if ( dupCheckFlag ) $( "#" + checkResult ).text( "가능" );
    else $( "#" + checkResult ).text( "불가" );
}




// 사용자 회원가입 유효성 체크
function validUserForm(formName) {
	
	var result = {};
	
	
	// 이메일 체크 ( 정규식 )
	var userId = $( "#userInsertForm" ).find("[name='userId']").val();
	
	if ( !emailValidChk( userId ) ) {
		result.flag = false;
		result.msg = "ID는 이메일 형식으로 입력해주세요.";
		return result;
	}
	
	if ( !dupCheckFlag ) {
		result.flag = false;
		result.msg = "중복된 이메일이 존재합니다.";
		return result;
	}


	// 비밀번호 유효성 체크
	var password = $( "#userInsertForm" ).find("[name='password']").val();
	var passwordRe = $( "#userInsertForm" ).find("[name='password2']").val();

	if ( passwordRegexp.test( password ) === false ) {
		result.flag = false;
		result.msg = "비밀번호는 영문, 숫자, 문자 조합으로 구성된 8~20자리 여야 합니다.";
		return result;
	}

	if ( ! passwordMatchCheckFlag ) {
		result.flag = false;
		result.msg = "비밀번호와 비밀번호재입력이 일치하지 않습니다.";
		return result;
	}

	if ( password != passwordRe ) {
		result.flag = false;
		result.msg = "비밀번호와 비밀번호재입력이 일치하지 않습니다. 다시 확인하여 주세요.";
		return result;
	}


	// 이름 체크 (2자 이상 )
	var name =  $( "#userInsertForm" ).find("[name='userNm']").val();
	
    if ( name == '' ) {
		result.flag = false;
		result.msg = "이름을 입력해주세요.";
		return result;

    } else if ( name.length < 2 || name.length > 10 ) {
		result.flag = false;
		result.msg = "이름은 2자 이상 10자 이하로 입력해주세요.";
		return result;

    }
	
	// 성별 체크 
	var gender =  $( "#userInsertForm" ).find("[name='gender']:checked").val();
	
	if ( gender == null || gender == '' ) {
		result.flag = false;
		result.msg = "성별을 선택해 주세요.";
		return result;
	}

	// 생년월일 체크
	var brdtDate = $( "#userInsertForm" ).find("[name='brdtDate']").val();
	
	if (!isValidDate(brdtDate) ) {
		result.flag = false;
		result.msg = "날짜는 yyyymmdd 형식으로 입력해주세요. ex) 19930119"
		return result;
	}

	// 연락처 체크
	var telNo = $( "#userInsertForm" ).find("[name='telNo']").val();
	
	if ( telNo != '' &&  !telNoRegexp.test( telNo ) ) {	// telNoRegexp = /^(01[016789]{1})[0-9]{3,4}[0-9]{4}$/;
		result.flag = false;
		result.msg = "연락처 형식에 맞게 입력해주세요.";
		return result;
	}
	
	
	// 이용약관 동의 처리 체크 여부
	var agreeUseofTermsBool = $( "#userInsertForm" ).find("[name='agreeUseofTerms']").is(":checked")
	
	if ( !agreeUseofTermsBool ) {
		result.flag = false;
		result.msg = "이용약관에 동의해주세요.";
		return result;
	}
	
	// 개인정보처리방침 동의 처리
	var agreePersonalInfoBool = $( "#userInsertForm" ).find("[name='agreePersonalInfo']").is(":checked");
		
	if ( !agreePersonalInfoBool ) {
		result.flag = false;
		result.msg = "개인정보 처리방침에 동의해주세요.";
		return result;
	}
	
	

	result.flag = true;
	// result.msg = "통과"
	return result;

}


// 비밀번호 일치 여부 체크
function checkPasswordMatching() {
	
	passwordMatchCheckFlag = false;
	// all color class remove
    $( "#passwordMatchingResult" ).removeClass( "text-primary text-danger" );
    $( "#passwordMatchingResult" ).text( "" );

	var password = $( "#userInsertForm" ).find("[name='password']").val();
	var passwordRe = $( "#userInsertForm" ).find("[name='password2']").val();
	
	if ( password != "" && passwordRe != "" ) {
		
		if ( password == passwordRe ) {
			$( "#passwordMatchingResult" ).addClass( "text-primary" );
			$( "#passwordMatchingResult" ).text( "일치" );
			passwordMatchCheckFlag = true;
			
		} else {
			$( "#passwordMatchingResult" ).addClass( "text-danger" );
			$( "#passwordMatchingResult" ).text( "불일치" );
		}
		
	}
	
	
	
}

function refreshAndShowLoginPop() {
	location.href = '?lgStatus=login';
}

function readyLogin(){
	
	// 비밀번호 유효성 체크
	var userId = $( "#userInsertForm" ).find("[name='password']").val();
	var password = $( "#userInsertForm" ).find("[name='password2']").val();
	
	
	var pathName = window.location.pathname;
	var queryString = window.location.search;
	
	// returnUrl setting
	var returnUrl = pathName + queryString;
	var returnUrl = replaceAll(returnUrl, 'lgStatus=login', '');
	
	// failUrl setting 
	if ( queryString.indexOf('?') != -1  && queryString.indexOf('lgStatus=login') != -1) {
		
	} else if ( queryString.indexOf('?') != -1  && queryString.indexOf('lgStatus=login') == -1 ) {
		queryString = queryString + '&lgStatus=login';
	} else {
		queryString = queryString + '?lgStatus=login'; 
	}
	var faileUrl = pathName + queryString;
	
	$( "#loginForm" ).find("[name='returnUrlAfterLogin']").val( returnUrl );
	$( "#loginForm" ).find("[name='returnUrlAfterLoginFail']").val( faileUrl );
	
	var form = $( "#loginForm" )[0];
    var formData = new FormData(form);

		var url = "/readyLogin"
		$.ajax({
					url: url,
		            type: "POST",	// 회원저장 POST로
		            async: false,	// 동기식 ajax : 통신이 완료될 떄 까지 다음 line 진행 안함
		            data: formData, // 검색할 값
		            async: false,
		            cache: false,
                    contentType : false,
			        processData : false ,
	                success: function ( result ) {
	                    // return type : List<CategoryDto>
	                    
		                if ( result.flag == "E" ) {
							alert(result.msg);
							
						} else if ( result.flag == "S" ) {
							// TODO : ajax 통신 이후 로직 ( 성공시 ) 구현 
							
							$( "#loginForm" ).submit();	// 로그인 구현
						}
	                }
	        	});
	
}


