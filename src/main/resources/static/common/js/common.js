const emailPattern = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-za-z0-9\-]+/;   // 이메일 유효성 검사


// ------------- function () 함수 실행  호출시점 : DOM Tree 생성 완료 후 -----------------
$( function () {

    // 해당 데이터가 없으면 colspan값 th개수만큼 자동으로 set
    if($("#noDataTd").length){
        $("#noDataTd").attr("colspan",$('#listTable th').length);
    }


    // input type=radio 에서 readonly 를 주면
    // 해당 label에 readonly 클래스 추가

    // readonly 속성인 모든 radio 순회
    $("input:radio[readonly=readonly]").each(function(i,item) {

        // 해당하는 label 값에 readonly 추가
        if(item.readOnly) {
            $("label[for='" + item.id + "']").addClass("readonly");
        }

        // 변경 불가 처리
        $(this).attr("onclick","return false;");
    });

    
});
// ------------- function () 함수 종료 -----------------


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
function clickRadioEvent( e ) {
    // 라디오 버튼의 label들을 찾아 모든 label 에 active클래스를 제거 후, 클릭된 label 에 active클래스 추가
    $( e ).parent().siblings( "label" ).removeClass( "active" );
    $( e ).parent().addClass( "active" );
}

// list search 함수 'form' 이름을 가진 form 을 'page' 의 페이지로 submit
// parameter : formname,page
function searchListPage( form, page ) {

    $("[name='page']").val(page);
    $("form[name='" + form + "']").submit();
}

// listform 의 size Selectbox 변경시 submit
function searchListSize( form, size ) {
    $("[name='size']").val(size);
    $("[name='page']").val(0);
    $("form[name='" + form + "']").submit();
}


// delete function
function deleteItem( delPk ){
	
	if ( confirm("삭제하시겠습니까?")){
		$("[name='deleteForm']").find("#delPk").val(delPk);
		$("[name='deleteForm']").submit();
		
	}
}


// S : file upload (img) 관련
// 출처: https://webdir.tistory.com/435 [WEBDIR:티스토리]
//preview image
var imgTarget = $('.preview-image .upload-hidden');

imgTarget.on('change', function(){
    var parent = $(this).parent();
    parent.children('.upload-display').remove();
    parent.children('.upload-file-name').remove();

    if(window.FileReader && $(this)[0].files[0] != null){

        parent.prepend('<div class="upload-file-name"><input class="input-file-name" value="' + $(this)[0].files[0].name + '" disabled="disabled"></div>');

        //image 파일만
        if (!$(this)[0].files[0].type.match(/image\//)) {
            parent.prepend('<div class="upload-display"><div class="upload-thumb-wrap"><img class="upload-thumb noimg"></div></div>');
            return;
        }

        var reader = new FileReader();
        reader.onload = function(e){
            var src = e.target.result;
            parent.prepend('<div class="upload-display"><div class="upload-thumb-wrap"><img src="'+src+'" class="upload-thumb"></div></div>');
        }
        reader.readAsDataURL($(this)[0].files[0]);
    } else {
        // $(this)[0].select();
        // $(this)[0].blur();
        // var imgSrc = document.selection.createRange().text;
        parent.prepend('<div class="upload-file-name"><input class="input-file-name" value="선택된 파일 없음" disabled="disabled"></div>');
        parent.prepend('<div class="upload-display"><div class="upload-thumb-wrap"><img class="upload-thumb noimg"></div></div>');
        //
        // var img = $(this).siblings('.upload-display').find('img');
        // img[0].style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(enable='true',sizingMethod='scale',src=\""+imgSrc+"\")";
    }
});
// E : file upload (img) 관련