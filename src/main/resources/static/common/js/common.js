const emailPattern = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-za-z0-9\-]+/;   // 이메일 유효성 검사

$( function () {


} );


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
    if ( emailPattern.test( email ) === false ) {
        // alert( "유효한 이메일 형식으로 입력해주세요." );
        return false;
    } else {
        return true;
    }
}


// 특정 문자열 전체 replace
function replaceAll( str, searchStr, replaceStr ) {
    return str.split( searchStr ).join( replaceStr );
};


// 중복확인 결과 뿌리기
function idDupResult( dupCheckFlag, checkResult ) {

    // all color class remove
    $( "#" + checkResult ).removeClass( "text-primary text-danger" );

    // color setting
    if ( dupCheckFlag ) $( "#" + checkResult ).addClass( "text-primary" );
    else $( "#" + checkResult ).addClass( "text-danger" );

    // 사용 여부
    if ( dupCheckFlag ) $( "#" + checkResult ).text( "사용가능" );
    else $( "#" + checkResult ).text( "사용불가" );

}

// 휴대폰 유효성 검사
function isTelNoFormat( telNo ) {
    if ( telNo == "" ) {
        return true;
    }
    var phoneRule = /^(01[016789]{1})[0-9]{3,4}[0-9]{4}$/;
    return phoneRule.test( telNo );
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


// 라디오 버튼 클릭시 active 클래스 추가
function clickRadioEvent(e){
	// 라디오 버튼의 label들을 찾아 모든 label 에 active클래스를 제거 후, 클릭된 label 에 active클래스 추가
	$(e).parent().siblings("label").removeClass("active");
	$(e).parent().addClass("active");
	
}