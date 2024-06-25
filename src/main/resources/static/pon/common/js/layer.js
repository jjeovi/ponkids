const emailRegexp = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-za-z0-9\-]+/;   // 이메일 유효성 검사
const telNoRegexp = /^(01[016789]{1})[0-9]{3,4}[0-9]{4}$/;
const passwordRegexp = /^(?=.*\d)(?=.*[a-z])(?=.*[!@#$%^&*]).{8,20}$/;

var dupCheckFlag = false;	// 중복체크 변수 선언
var passwordValidCheck = false;	// 비밀번호 유효성 체크 확인
var passwordMatchCheckFlag = false;  	// 비밀번호 비교 일치 여부 확인


$( function () {
    if ( lgStatus == null || lgStatus == '' ) {
        lgStatus = urlParams.get( 'lgStatus' );
    }

    if ( lgStatus == 'login' || lgStatus == 'userIntegrated' || lgStatus == 'joinForSns' ) {
        showLayer( lgStatus );

        // message 출력
        if ( errMsg != null && errMsg != '' ) {
            alert( errMsg );
        } else if ( infoMsg != null && infoMsg != '' ) {
            alert( infoMsg );
        }


        // lgStatus 상태에 맞춰 회원가입 폼 disabled 및 display 여부 수정
        if ( lgStatus != null && lgStatus == 'joinForSns' ) {

            // 강제 focus 추가하여 ID 중복체크 실행
            $( "#userInsertForm" ).find( "[name='userId']" ).focus();

            $( "#joinForSnsTypeArea" ).show();
            $( "#joinForSnsType" ).attr( "disabled", true );

            if ( snsType != null && snsType != '' ) {
                $( "#joinForSnsType" ).val( snsType ).prop( "selected", true );
                $( "#userInsertForm" ).find( "[name='snsType']" ).val( snsType );

            }

        }
    }


    // 아이디 입력 focusout 처리 ( 중복체크 로직 )
    $( "#userInsertForm" ).find( "[name='userId']" ).focusout( function () {
        dupCheckFlag = false;
        var userId = $( this ).val();	  // userId값 넘기기
        
        // 회원가입 layer 가 활성화 되어있을 때에만 ajax 실행
        if ( $(".layer_join").css("display") != 'none'  ) {
		
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
        	
		}
        
        
    } )

    // 비밀번호 유효성 체크 focusout 처리
    $( "#userInsertForm" ).find( "[name='password']" ).focusout( function () {
        checkPasswordMatching();
    } );

    // 비밀번호 일치 여부 체크
    $( "#userInsertForm" ).find( "[name='password']" ).keyup( function () {
        checkPasswordMatching();
    } );

    // 비밀번호 일치 여부 체크
    $( "#userInsertForm" ).find( "[name='password2']" ).keyup( function () {
        checkPasswordMatching();
    } );

} );

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
        if ( $( "[name='fileAtchActYn']" ).length ) {	// 해당 name 으로 된 input 항목 있을 때만 실행
            // 해당 여부의 값을 Y 로 변경
            if ( $( "[name='fileAtchActYn']" ).val() != 'Y' ) {
                $( "[name='fileAtchActYn']" ).val( "Y" );
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


    hideLayerLv2( 'addChldrn' );
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

    if ( !isValidDate( chldrnBrdtDate ) ) {
        alert( "날짜는 yyyymmdd 형식으로 입력해주세요. ex)20130119" );
        return false;
    }

    // 연락처 유효성 검사
    var chldrnTelNo = $targetChldrnDiv.find( "[name='chldrnTelNo']" ).val();

    if ( chldrnTelNo == "" ) {

    } else {

        if ( !telNoRegexp.test( chldrnTelNo ) ) {	// telNoRegexp = /^(01[016789]{1})[0-9]{3,4}[0-9]{4}$/;
            alert( "연락처는 숫자 10~11자리로만 입력해주세요." );
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
    var chldrnGender = $targetChldrnDiv.find( "[name='chldrnGenderRadio']:checked" ).val() == "M" ? "남" : "여";
    var chldrnBrdtDate = $targetChldrnDiv.find( "[name='chldrnBrdtDate']" ).val();
    var chldrnTelNo = $targetChldrnDiv.find( "[name='chldrnTelNo']" ).val();
    var chldrnEmail = $targetChldrnDiv.find( "[name='chldrnEmail']" ).val();

    chldrnBrdtDate = chldrnBrdtDate.substring( 0, 4 ) + "-" + chldrnBrdtDate.substring( 4, 6 ) + "-" + chldrnBrdtDate.substring( 6, 8 );
    var nmGender = chldrnNm + "(" + chldrnGender + ")";

    var thumbSrc = $targetChldrnDiv.find( "[name='thumbSrc']" ).attr( "src" );

    var noimg = "";
    if ( thumbSrc == '' || thumbSrc == undefined ) {
        noimg = " noimg";
    }


    // 추가
    $( "#chldrnTableBody" ).append(
        $( "<div>" ).attr( "class", "info_chldrn_list" ).append(
            $( "<div>" ).attr( "class", "filebox preview-image" ).append(
                $( "<div>" ).attr( "class", "upload-display" ).append(
                    $( "<div>" ).attr( "class", " upload-thumb-wrap" ).append(
                        $( "<img>" ).attr( "class", "upload-thumb" + noimg ).attr( "src", thumbSrc ).append( "" )
                    )
                )
            ),
            $( "<input>" ).attr( "type", "text" ).attr( "name", "chldrnNm" ).attr( "value", nmGender ).attr( "placeholder", "이름 (성별)" ).attr( "readonly", "readonly" ),
            $( "<input>" ).attr( "type", "text" ).attr( "name", "chldrnBrdtDate" ).attr( "value", chldrnBrdtDate ).attr( "placeholder", "생년월일" ).attr( "readonly", "readonly" ),
            $( "<input>" ).attr( "type", "text" ).attr( "name", "chldrnTelNo" ).attr( "value", chldrnTelNo ).attr( "placeholder", "연락처" ).attr( "readonly", "readonly" ),
            $( "<input>" ).attr( "type", "text" ).attr( "name", "chldrnEmail" ).attr( "value", chldrnEmail ).attr( "placeholder", "이메일" ).attr( "class", "last" ).attr( "readonly", "readonly" ),

            $( "<div>" ).attr( "class", "right" ).append(
                $( "<label>" ).attr( "class", "btn btn_delete" ).attr( "onclick", "deleteChldrn(this)" ).append(
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


function isValidDate( yyyymmdd ) {

    var r = true;

    try {

        var date = [];
        if ( yyyymmdd.length == 8 ) {

            date[0] = yyyymmdd.substring( 0, 4 );
            date[1] = yyyymmdd.substring( 4, 6 );
            date[2] = yyyymmdd.substring( 6, 8 );

        } else if ( yyyymmdd.length == 10 ) {

            date = yyyymmdd.split( "-" );
        }

        var yyyy = parseInt( date[0], 10 );
        var mm = parseInt( date[1], 10 );
        var dd = parseInt( date[2], 10 );

        var dateRegex = /^(?=\d)(?:(?:31(?!.(?:0?[2469]|11))|(?:30|29)(?!.0?2)|29(?=.0?2.(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00)))(?:\x20|$))|(?:2[0-8]|1\d|0?[1-9]))([-.\/])(?:1[012]|0?[1-9])\1(?:1[6-9]|[2-9]\d)?\d\d(?:(?=\x20\d)\x20|$))?(((0?[1-9]|1[012])(:[0-5]\d){0,2}(\x20[AP]M))|([01]\d|2[0-3])(:[0-5]\d){1,2})?$/;

        r = dateRegex.test( dd + '-' + mm + '-' + yyyy );

    } catch ( err ) {
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
        showLayerLv2( 'addChldrn' );

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


// 회원 추가
function userInsert() {

    var validCheck = {};
    var validCheck = validUserInsertForm( 'userInsertForm' );

    if ( !validCheck.flag ) {
        alert( validCheck.msg );
        return false;
    } else {
        // 유효성 끝난 후 값 setting 작업

        // ==================== 유효성 모두 통과 후 dataset 정리 ==================================
        // ==================== 유효성 모두 통과 후 dataset 정리 ==================================

        // 거주지역 그 외만 선택 했을 시 return false;
        var resideArea = $( "input:radio[name='resideArea']:checked" ).val();
        var country = $( "#join_countryList option:selected" ).val();
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

        } );

        var form = $( "#userInsertForm" )[0];
        var formData = new FormData( form );

        var url = "/user/live/mcd/insertAjax"
        $.ajax( {
            url: url,
            type: "POST",	// 회원저장 POST로
            async: false,	// 동기식 ajax : 통신이 완료될 떄 까지 다음 line 진행 안함
            data: formData, // 검색할 값
            cache: false,
            contentType: false,
            processData: false,
            success: function ( result ) {
                // return type : List<CategoryDto>

                if ( result.flag == "E" ) {
                    alert( result.msg );

                } else if ( result.flag == "S" ) {
                    // TODO : ajax 통신 이후 로직 ( 성공시 ) 구현

                    alert( result.msg );

                    // 로그인 하러 가기 layer 표출
                    hideAllPopup();
                    showLayer( 'completeJoin' );

                }

            }
        } );

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
function validUserInsertForm( formId ) {

    var result = {};


    // 이메일 체크 ( 정규식 )
    var userId = $( "#" + formId ).find( "[name='userId']" ).val();

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
    var password = $( "#userInsertForm" ).find( "[name='password']" ).val();
    var passwordRe = $( "#userInsertForm" ).find( "[name='password2']" ).val();

    if ( passwordRegexp.test( password ) === false ) {
        result.flag = false;
        result.msg = "비밀번호는 영문, 숫자, 문자 조합으로 구성된 8~20자리 여야 합니다.";
        return result;
    }

    if ( !passwordMatchCheckFlag ) {
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
    var name = $( "#userInsertForm" ).find( "[name='userNm']" ).val();

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
    var gender = $( "#userInsertForm" ).find( "[name='gender']:checked" ).val();

    if ( gender == null || gender == '' ) {
        result.flag = false;
        result.msg = "성별을 선택해 주세요.";
        return result;
    }

    // 생년월일 체크
    var brdtDate = $( "#userInsertForm" ).find( "[name='brdtDate']" ).val();

    if ( !isValidDate( brdtDate ) ) {
        result.flag = false;
        result.msg = "날짜는 yyyymmdd 형식으로 입력해주세요. ex) 19930119"
        return result;
    }

    // 연락처 체크
    var telNo = $( "#userInsertForm" ).find( "[name='telNo']" ).val();

    if ( telNo != '' && !telNoRegexp.test( telNo ) ) {	// telNoRegexp = /^(01[016789]{1})[0-9]{3,4}[0-9]{4}$/;
        result.flag = false;
        result.msg = "연락처 형식에 맞게 입력해주세요.";
        return result;
    }


    // 이용약관 동의 처리 체크 여부
    var agreeUseofTermsBool = $( "#userInsertForm" ).find( "[name='agreeUseofTerms']" ).is( ":checked" )

    if ( !agreeUseofTermsBool ) {
        result.flag = false;
        result.msg = "이용약관에 동의해주세요.";
        return result;
    }

    // 개인정보처리방침 동의 처리
    var agreePersonalInfoBool = $( "#userInsertForm" ).find( "[name='agreePersonalInfo']" ).is( ":checked" );

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

    var password = $( "#userInsertForm" ).find( "[name='password']" ).val();
    var passwordRe = $( "#userInsertForm" ).find( "[name='password2']" ).val();

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
	
    location.href = getMakeUrlParamLgStatus('login');
}

function readyLogin( loginType ) {

    // 비밀번호 유효성 체크
    var userId = $( "#userInsertForm" ).find( "[name='password']" ).val();
    var password = $( "#userInsertForm" ).find( "[name='password2']" ).val();


    var pathName = window.location.pathname;
    var queryString = window.location.search;

    // returnUrl setting
    var returnUrl = pathName + queryString;
    returnUrl = getUrlExceptLgStatus(returnUrl);
    // returnUrl = getMakeUrlParamLgStatus('login');

    // failUrl setting
    if ( queryString.indexOf( '?' ) != -1 && queryString.indexOf( 'lgStatus=login' ) != -1 ) {

    } else if ( queryString.indexOf( '?' ) != -1 && queryString.indexOf( 'lgStatus=login' ) == -1 ) {
        queryString = queryString + '&lgStatus=login';
    } else {
        queryString = queryString + '?lgStatus=login';
    }
    var faileUrl = pathName + queryString;


    $( "#loginForm" ).find( "[name='returnUrlAfterLogin']" ).val( returnUrl );
    $( "#loginForm" ).find( "[name='returnUrlAfterLoginFail']" ).val( faileUrl );

    var form = $( "#loginForm" )[0];
    var formData = new FormData( form );

    var url = "/readyLoginAjax"
    $.ajax( {
        url: url,
        type: "POST",	// 회원저장 POST로
        async: false,	// 동기식 ajax : 통신이 완료될 떄 까지 다음 line 진행 안함
        data: formData, // 검색할 값
        async: false,
        cache: false,
        contentType: false,
        processData: false,
        success: function ( result ) {
            // return type : List<CategoryDto>

            if ( result.flag == "E" ) {
                alert( result.msg );

            } else if ( result.flag == "S" ) {
                // TODO : ajax 통신 이후 로직 ( 성공시 ) 구현

                switch ( loginType ) {

                    case 'default':

                        $( "#loginForm" ).submit();	// 로그인 구현
                        break;

                    case 'kakao':

                        location.href = '/oauth2/authorization/kakao';
                        break;

                    case 'google':

                        location.href = "/oauth2/authorization/google";
                        break;

                    default:

                        $( "#loginForm" ).submit();	// 로그인 구현
                }

            }
        }
    } );
}






// 회원 계정 통합
function userIntegrated() {

    var validCheck = {};
    var validCheck = validUserIntegratedForm( 'userIntegratedForm' );

    if ( !validCheck.flag ) {
        alert( validCheck.msg );
        return false;
    } else {

        if ( snsType != null ) {
            var returnUrl = '/' + snsType + '/userIntegratedCallbackAjax';
            $( "#userIntegratedForm" ).find( "[name='returnUrlAfterLogin']" ).val( returnUrl );
            $( "#userIntegratedForm" ).find( "[name='returnUrlAfterLoginFail']" ).val( returnUrl );
        }

        var form = $( "#userIntegratedForm" )[0];
        var formData = new FormData( form );

        var url = "/readyUserIntegratedAjax"
        $.ajax( {
            url: url,
            type: "POST",	// 회원저장 POST로
            async: false,	// 동기식 ajax : 통신이 완료될 떄 까지 다음 line 진행 안함
            data: formData, // 검색할 값
            cache: false,
            contentType: false,
            processData: false,
            success: function ( result ) {
                // return type : List<CategoryDto>
                if ( result.flag == "E" ) {
                    alert( result.msg );
                    return ;
                }


                var url = "/login"
                $.ajax( {
                    url: url,
                    type: "POST",	// 회원저장 POST로
                    async: false,	// 동기식 ajax : 통신이 완료될 떄 까지 다음 line 진행 안함
                    data: formData, // 검색할 값
                    cache: false,
                    contentType: false,
                    processData: false,
                    success: function ( result ) {
                        // return type : List<CategoryDto>
                        if ( result.flag == "E" ) {
                            alert( result.msg );
                            return ;
                        } else if ( result.flag == "S" ) {
                            alert( result.msg );


                            var pathName = window.location.pathname;
                            var queryString = window.location.search;

                            // returnUrl setting
                            var returnUrl = pathName + queryString;
                            returnUrl = getUrlExceptLgStatus(returnUrl);

                            location.href = returnUrl;

                            return ;
                        }
                    }
                } );
            }
        } );

        // new Promise( ( succ, fail ) => {

            // var url = "/readyLoginAjax"
            // $.ajax( {
            //     url: url,
            //     type: "POST",	// 회원저장 POST로
            //     async: false,	// 동기식 ajax : 통신이 완료될 떄 까지 다음 line 진행 안함
            //     data: formData, // 검색할 값
            //     cache: false,
            //     contentType: false,
            //     processData: false,
            //     success: function ( result ) {
            //         // return type : List<CategoryDto>
            //         if ( result.flag == "E" ) {
            //             alert( result.msg );
            //             return ;
            //         }
            //
            //
            //         var url = "/login"
            //         $.ajax( {
            //             url: url,
            //             type: "POST",	// 회원저장 POST로
            //             async: false,	// 동기식 ajax : 통신이 완료될 떄 까지 다음 line 진행 안함
            //             data: formData, // 검색할 값
            //             cache: false,
            //             contentType: false,
            //             processData: false,
            //             success: function ( result ) {
            //                 // return type : List<CategoryDto>
            //                 alert("gggggggssss");
            //             }
            //         } );
            //     }
            // } );
        //
        // } ).then( ( arg ) => {
        //
        //
        // } );

    }
}


// 사용자 회원가입 유효성 체크
function validUserIntegratedForm( formId ) {
    var result = {};

    // 이메일 체크 ( 정규식 )
    var userId = $( "#" + formId ).find( "[name='username']" ).val();

    if ( !emailValidChk( userId ) ) {
        result.flag = false;
        result.msg = "ID는 이메일 형식이어야 합니다.";
        return result;
    }

    // 이용약관 동의 처리 체크 여부
    var agreeUserIntegratedBool = $( "#userIntegratedForm" ).find( "[name='agreeUserIntegrated']" ).is( ":checked" )

    if ( !agreeUserIntegratedBool ) {
        result.flag = false;
        result.msg = "계정통합에 동의해주세요.";
        return result;
    }

    result.flag = true;
    // result.msg = "통과"
    return result;

}

// 현재 parameter에 lgStatus 관련 parameter 제거 후 return
function getUrlExceptLgStatus( returnUrl ) {
    returnUrl = replaceAll( returnUrl, 'lgStatus=login', '' );
    returnUrl = replaceAll( returnUrl, 'lgStatus=joinForSns', '' );
    returnUrl = replaceAll( returnUrl, 'lgStatus=userIntegrated', '' );

    return returnUrl;
}

// 현재 parameter 에 lgStatus='status' parameter 추가 후 return
function getMakeUrlParamLgStatus( status ) {
    var pathName = window.location.pathname;
    var queryString = window.location.search;


    // failUrl setting
    if ( queryString.indexOf( '?' ) != -1 && queryString.indexOf( 'lgStatus=' + status ) != -1 ) {

    } else if ( queryString.indexOf( '?' ) != -1 && queryString.indexOf( 'lgStatus=' + status ) == -1 ) {
        queryString = queryString + '&lgStatus=' + status;
    } else {
        queryString = queryString + '?lgStatus=' + status;
    }


    return  pathName + queryString;


}

/* S : 아이디/비밀번호 스크립트 */


/* E : 아이디/비밀번호 스크립트 */


/* S : 아이디 찾기 */
function findUsername(){
    //사용자 입력 값 가져오기
    var username = $("#username").val();
    var phone = $("#phone").val();

    // 서버로 전송할 데이터 구성
    var requestData = {
        userNm: username,
        telNo: phone
    };

    data = new FormData();
    data.append( "userNm", username );
    data.append( "telNo", phone );

    var header = $("meta[name='_csrf_header']").attr('content');
    var token = $("meta[name='_csrf']").attr('content');

    // Ajax 를 사용한 서버로의 요청
    $.ajax({
        url: "/findUsername",
        type: "POST",
        data: data,
        cache: false,
        contentType: false,
        processData: false,
        // contentType: "application/json; charset=utf-8",
        beforeSend: function(xhr){
            xhr.setRequestHeader(header, token);
        },
        success: function (result) {

            // 통신 이후 로직
            if ( result.flag == "E" ) {
                alert( result.msg );

            } else if ( result.flag == "S" ) {
                // TODO 마스킹 된 id 계정을 뿌려주는 작업 필요..
                /* alert(result.maskingUserId);*/
              /*   $("#userIdInfo").text(result.maskingUserId);*/
                showSuccessScreen(result.maskingUserId);
            }
        },
        error: function (){
            alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error)
        }
    })
}

// 아이디 찾기 성공 후의 처리
function showSuccessScreen(userId) {
    // 성공 화면을 보이도록 설정
    $('.user_find').hide(); // 기존 화면 감춤
    //+63
    $('#userId').text(userId); // 찾은 아이디를 성공 화면에 출력
    showLayerLv2( 'findUserInfo' );   // 아이디 찾기 결과 팝업 호출

}
/* E : 아이디 찾기 */


/* S: 비밀번호 찾기 - 이메일 찾기(이메일 발송, 타이머) */
function findUseremail(){
    //사용자 입력 값 가져오기
    var findpwname = $("#findPw_name").val();
    var findpwemail = $("#findPw_email").val();

    // 서버로 전송할 데이터 구성

    data = new FormData();
    data.append( "userNm", findpwname );
    data.append( "userId", findpwemail );

    var header = $("meta[name='_csrf_header']").attr('content');
    var token = $("meta[name='_csrf']").attr('content');

    // Ajax 를 사용한 서버로의 요청
    $.ajax({
        url: "/findUseremail",
        type: "POST",
        data: data,
        cache: false,
        contentType: false,
        processData: false,
        // contentType: "application/json; charset=utf-8",
        beforeSend: function(xhr){
            xhr.setRequestHeader(header, token);
        },
        success: function (result) {

            // 통신 이후 로직
            if ( result.flag == "E" ) {
                alert( result.msg );

            } else if ( result.flag == "S" ) {

                // TODO : 3분 타이머 해야함
                // 비동기로 실행....!?
                startTimer(180);

                // 존재하는 계정정보일 경우 인증번호 발송되었다고 알림
                alert(result.msg);


            }
        },
        error: function (){
            alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error)
        }
    })
}

// 타이머(3분)
function startTimer(duration) {
    var timer = duration, minutes, seconds;
    var intervalId = setInterval(function () {
        minutes = parseInt(timer / 60, 10);
        seconds = parseInt(timer % 60, 10);

        minutes = minutes < 10 ? "0" + minutes : minutes;
        seconds = seconds < 10 ? "0" + seconds : seconds;

        // 'timerDisplay'라는 ID를 가진 HTML 요소에 타이머 표시
        $("#timerDisplay").text("재발송 (" + minutes + ":" + seconds + ")");

        if (--timer < 0) {
            clearInterval(intervalId);
            // 타이머가 0에 도달하면 버튼 텍스트를 초기 상태로 변경
            $("#findPw_send_auth_number").text("인증번호 발송");
            // 선택적으로 타이머가 0에 도달했을 때 추가 작업 수행 가능
        }
    }, 1000);
}

/* E: 비밀번호 찾기 - 이메일 찾기(이메일 발송, 타이머) */


/* S : 비밀번호 찾기 - 버튼클릭시 - 인증번호 확인 */
    function findUserpw(){
        var authNumber = $("#findPw_auth_number").val()  // 인증번호
        // 서버로 전송할 데이터 구성
        // data = new FormData();
        // data.append( "authNumber", authNumber );
        var data = {authNumber: authNumber}

        // CSRF 토큰 및 헤더 설정
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var headers = {};
        headers[csrfHeader] = csrfToken;
        // Ajax 를 사용한 서버로 인증번호 확인 요청
        $.ajax({
            url:"/findUserpw",
            type:"POST",
            data: data,
            // beforeSend: function(xhr){
            //     xhr.setRequestHeader(header, token);
            // },
            headers: headers,
            success: function (result){
                if(result.flag ==="S"){
                    alert(result.msg);
                    // TODO 인증 성공시 원하는 동작 수행
                    showPwSuccessScreen(result.userId);
                }else{
                    alert(result.msg);
                }
            },
            error: function (){
                alert("서버 오류가 발생했습니다.");
            }

        });
    }

// 이메일 인증 성공 후의 처리
function showPwSuccessScreen(userId) {
    // 비밀번호 재설정 화면을 보이도록 설정
    $('.user_find').hide(); // 기존 화면 감춤
    showLayerLv2( 'findUserPw' );   // 비밀번호 재설정 팝업 호출

}
/* E : 성환: 비밀번호 찾기 - 인증번호 확인 */


/* S: 성환: 비빌번호 변경 */
// function changePassword( userId,newPassword ){
function changePassword( ) {

    // 1. 비밀번호변경, 비밀번호변경확인 변수 가져오기
    var new_Pw = $("#new_Pw").val();
    var new_check_Pw = $("#new_check_Pw").val();
    var findpwemail = $("#findPw_email").val();

    // 2-1. 두 값이 같은지 비교하기
    if (!checkSameValue(new_Pw, new_check_Pw)) {
        // 두 값이 같지 않을 때
        alert("두 값이 같지 않습니다.. 확인해주세요...");
        return false;
    }

    // 2-2. 유효성 체크..
    // TODO 유효성 체크해주세요.
    if (!validCheckPw(new_Pw)) {
        // 유효성이 맞지 않을떄..
            alert( "유효성 이 맞지 않습니다. [영문자, 숫자, 기호 혼합 8자 이상] 을 지켜주세요.");
            return false;
        }

        // 3. 아이디와 비밀번호변경, 비밀번호변경확인을 ajax로 보내기
        // 보낼 변수 : 비밀번호, 아이디,  + ( csrf header 에 포함시켜 보내기.. )

        /* S : 보낼 데이터 setting */
        var data = {
            newPassword: new_Pw,
            userId: findpwemail
        };

        var csrfToken = $("meta[name='_csrf']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var headers = {};
        headers[csrfHeader] = csrfToken;
        /* E : 보낼 데이터 setting */
        $.ajax({
            type: "POST",
            url: "/changePassword",
            data: data,
            headers: headers,
            success: function (response) {
                //서버로부터 응답처리
                alert("정상적으로 비밀번호가 변경되었습니다. 로그인 후 이용해주세요.")
                // 비밀번호 변경 성공 시 홈 화면으로 리다이렉션
                window.location.href = "/";
            },
            error: function (error) {
                //오류처리
                alert("비밀번호 변경 오류발생하였습니다.")
            }
        })

    }

// 변경할 비밀번호 , 변경할 비밀번호 확인 일치 여부
function checkSameValue(value1, value2) {
    // value1, value2 같은지 비교
    if (value1 == value2) {
        return true;
    } else {
        return false;
    }
}


// 비밀번호 유효성 체크
function validCheckPw(password) {
    // 영문자, 숫자, 기호를 혼합하여 8자 이상인지 확인
    var regex = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[~!@#$%^&*()_+{}\[\]:;<>,.?/\\-]).{8,}$/;

    if (!regex.test(password)) {
        return false;
    }
    return true;
}
    /* E: 성환: 비빌번호 변경 */

