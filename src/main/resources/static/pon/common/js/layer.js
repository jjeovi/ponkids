const brdrDateRegexp = /^(?=\d)(?:(?:31(?!.(?:0?[2469]|11))|(?:30|29)(?!.0?2)|29(?=.0?2.(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00)))(?:\x20|$))|(?:2[0-8]|1\d|0?[1-9]))(?:1[012]|0?[1-9])\1(?:1[6-9]|[2-9]\d)?\d\d(?:(?=\x20\d)\x20|$))?(((0?[1-9]|1[012])(:[0-5]\d){0,2}(\x20[AP]M))|([01]\d|2[0-3])(:[0-5]\d){1,2})?$/;
const telNoRegexp = /^(01[016789]{1})[0-9]{3,4}[0-9]{4}$/;

$( function() {
    
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

            //image 파일만
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
//            parent.prepend( '<div class="upload-file-name"><input class="input-file-name" value="선택된 파일 없음" disabled="disabled"></div>' );
            
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
	var $targetChldrnDiv = $( "[name='chldarnAddDiv'].newForm" );

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
//	var $newChldrnForm = $( "<div name='chldrnAddDiv' class='newForm'>" ).load( "/admin/user/addChldrnFormModal.html" );
//	$( "#addChldrnModalBody" ).append( $newChldrnForm );

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
	// $("#chldrnTableBody tr").length  // /현재 테이블 tr 개수
	
	var chldrnNm = $targetChldrnDiv.find( "[name='chldrnNm']" ).val();
	var chldrnGender = $targetChldrnDiv.find( "[name='chldrnGenderRadio']:checked" ).val() == "M" ? "남" : "여"  ;
	var chldrnBrdtDate = $targetChldrnDiv.find( "[name='chldrnBrdtDate']" ).val();
	var chldrnTelNo = $targetChldrnDiv.find( "[name='chldrnTelNo']" ).val();
	var chldrnEmail = $targetChldrnDiv.find( "[name='chldrnEmail']" ).val();
	
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
			$("<input>").attr("type","text").attr("name", "chldrnEmail" ).attr( "value", chldrnEmail ).attr("placeholder", "이메일" ).attr("readonly", "readonly"),
			
			$( "<div>" ).attr( "class", "right" ).append(
				$("<label>").attr ( "class", "btn btn_delete" ).append(
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