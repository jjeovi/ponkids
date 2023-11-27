const emailPattern = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-za-z0-9\-]+/;   // 이메일 유효성 검사


// ------------- function () 함수 실행  호출시점 : DOM Tree 생성 완료 후 -----------------
$( function () {

    // 해당 데이터가 없으면 colspan값 th개수만큼 자동으로 set
    if ( $( ".noDataTd" ).length ) {
        $( ".noDataTd" ).attr( "colspan", $( '#listTable th' ).length );
    }

    // input type=radio 에서 readonly 를 주면
    // 해당 label에 readonly 클래스 추가

    // readonly 속성인 모든 radio 순회
    $( "input:radio[readonly=readonly]" ).each( function ( i, item ) {

        // 해당하는 label 값에 readonly 추가
        if ( item.readOnly ) {
            $( "label[for='" + item.id + "']" ).addClass( "readonly" );
        }

        // 변경 불가 처리
        $( this ).attr( "onclick", "return false;" );
    } );

    // 카테고리 분류가 선택 되어있으면 picked-cate 에 뿌려준다.
    if ( $( ".category-list-area .category-group-box ul li.on" ).length ) {
        $( ".category-list-area .category-group-box ul li.on" ).trigger( "click" );
    }


} );
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

    $( "[name='page']" ).val( page );
    $( "form[name='" + form + "']" ).submit();
}

// listform 의 size Selectbox 변경시 submit
function searchListSize( form, size ) {
    $( "[name='size']" ).val( size );
    $( "[name='page']" ).val( 0 );
    $( "form[name='" + form + "']" ).submit();
}


// delete function
function deleteItem( delPk ) {

    if ( confirm( "삭제하시겠습니까?" ) ) {
        $( "[name='deleteForm']" ).find( "#delPk" ).val( delPk );
        $( "[name='deleteForm']" ).submit();

    }
}


// S : file upload (img) 관련
//preview image
function fileChange( e ) {
    {
        var parent = $( e ).parent().parent().parent();
        parent.children( '.upload-display' ).remove();
        parent.children( '.upload-file-name' ).remove();

        // id값 제거
        parent.children( "[name='atchFileSn']" ).remove();

        if ( window.FileReader && $( e )[0].files[0] != null ) {

            parent.prepend( '<div class="upload-file-name"><input class="input-file-name" value="' + $( e )[0].files[0].name + '" disabled="disabled"></div>' );

            //image 파일만
            if ( !$( e )[0].files[0].type.match( /image\// ) ) {
                parent.prepend( '<div class="upload-display"><div class="upload-thumb-wrap"><img class="upload-thumb noimg"></div></div>' );
                return;
            }

            var reader = new FileReader();
            reader.onload = function ( e ) {
                var src = e.target.result;
                parent.prepend( '<div class="upload-display"><div class="upload-thumb-wrap"><img src="' + src + '" class="upload-thumb"></div></div>' );
            }
            reader.readAsDataURL( $( e )[0].files[0] );
        } else {
            // var imgSrc = document.selection.createRange().text;
            parent.prepend( '<div class="upload-file-name"><input class="input-file-name" value="선택된 파일 없음" disabled="disabled"></div>' );
            parent.prepend( '<div class="upload-display"><div class="upload-thumb-wrap"><img class="upload-thumb noimg"></div></div>' );
            //
            // img[0].style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(enable='true',sizingMethod='scale',src=\""+imgSrc+"\")";
        }
    }
}

// E : file upload (img) 관련


// 프로필이미지 제거
function removeImage( e ) {

    // atchFileSn 값 제거
    $( e ).parent().siblings( "[name='atchFileSn']" ).remove();

    // 이미지 썸네일 제거 및 파일명 제거작업
    $( e ).parent().siblings( '.upload-display' ).remove();
    $( e ).parent().siblings( '.upload-file-name' ).remove();
    $( e ).parent().parent().prepend( '<div class="upload-file-name"><input class="input-file-name" value="선택된 파일 없음" disabled="disabled"></div>' );
    $( e ).parent().parent().prepend( '<div class="upload-display"><div class="upload-thumb-wrap"><img class="upload-thumb noimg"></div></div>' );

    // 이미지 제거 버튼 또한 제거
    $( e ).remove();
}


function emptyNextCateText( ulNum ) {

}

// 카테고리 박스 안 li 클릭시 이벤트
function getCateNextLvList( url, e ) {
    // on class 추가
    if ( !$( e ).hasClass( "on" ) ) {
        $( e ).siblings().removeClass( "on" );
        $( e ).addClass( "on" );

        var $ul = $( e ).parent();
        var ulNum = $ul.prevAll().length;	// 몇번쨰 ul 인지 체크 (0부터 카운트..)

        // 뒷단계 카테고리 전부 비움
        // $(".category-list-area .category-group-box ul li.on").each(function(i,item){
        $( ".category-list-area .category-group-box ul" ).each( function ( i, item ) {
            if ( i > ulNum ) {
                $( this ).empty();
                $( "#picked-cate" ).find( ".cateLv" + i ).empty();
            }
        } )

        // $ul = $(e).parent()			// 선택한 태그의 <ul class="data-group"> 을 선택
        // var ulNum = $ul.prevAll().length+1;	// 몇번쨰 ul 인지 체크 (1부터 카운트..)

        // picked-cate 태그 안에 해당 내용 삽입
        $( "#picked-cate" ).find( ".cateLv" + ulNum ).empty();
        $( "#picked-cate" ).find( ".cateLv" + ulNum ).text( $( e ).text() );

        // 깜박임 class 지웠다 다시 실행해서 애니메이션 재실행
        $( "#picked-cate" ).find( ".cateLv" + ulNum ).removeClass( "blink" );
        setTimeout( function () {
            $( "#picked-cate" ).find( ".cateLv" + ulNum ).addClass( "blink" );
        }, 100 );

        var categorySn = $( e ).val();

        // 카테고리 박스 개수 체크하여
        // 마지막 박스 클릭 아닌 경우에 다음 카테고리 조회 실행
        var ulCnt = $( ".category-list-area .category-group-box ul" ).length;
        if ( ulNum != ( ulCnt - 1 ) ) {

            $.ajax( {
                url: url,
                type: "GET",
                dataType: "json",
                async: false,
                data: { categorySn: categorySn }, // 검색할 값
                contentType: "application/json",
                success: function ( result ) {
                    // return type : List<CategoryDto>
                    var allYn = $ul.data( 'allYn' );
                    if ( allYn != null && allYn == 'Y' ) {
                        $( ".category-list-area .category-group-box ul" ).eq( ulNum + 1 ).append(
                            $( "<li>" ).attr( "onclick", "getCateNextLvList('" + url + "', this )" ).attr( "value", "" ).append( "전체" )
                        );
                    }
                    for ( let i in result ) {
                        $( ".category-list-area .category-group-box ul" ).eq( ulNum + 1 ).append(
                            $( "<li>" ).attr( "onclick", "getCateNextLvList('" + url + "', this )" ).attr( "value", result[i].categorySn ).append( result[i].categoryNm )
                        );
                    }
                }
            } );
        }
    }
}

// 카테고리 검색 버튼 function
function searchCate( url ) {

    if ( $( ".category-list-area .category-group-box ul li.on" ).length ) {
        var data = {};

        $( ".category-list-area .category-group-box ul li.on" ).each( function ( i, item ) {
            if ( i == 0 ) {
                data['category.lv1Sn'] = $( this ).val();
            } else if ( i == 1 ) {
                data['category.lv2Sn'] = $( this ).val();
            } else if ( i == 2 ) {
                data['category.lv3Sn'] = $( this ).val();
            } else if ( i == 3 ) {
                data['category.lv4Sn'] = $( this ).val();
            } else if ( i == 4 ) {
                data['category.lv5Sn'] = $( this ).val();
            }
        } );

        $.ajax( {
            url: url,
            type: "GET",
            dataType: "json",
            async: false,
            data: data, // 검색할 값 listDto 안에 categoryDto 변수 추가
            contentType: "application/json",
            success: function ( result ) {
                // callback 함수 연결 -> callback함수로 구현
                searchCateCallback( result );
            }
        } );

    } else {
        alert( "검색 옵션을 선택해주세요." );
    }
}

// 메뉴 구조 그리기
function drawMenuTree( resultList ) {
    // console.log( resultList );

    $( "#menuStructure" ).append(
        $( "<div>" ).attr( "id", "menuStructureJsTree" ).append()
    );

    var rootYn = false;

    for ( let data of resultList ) {

        if ( data.upperMenuSn == null ) {
            // ROOT 메뉴 일 시,
            $( "#menuStructureJsTree" ).append(
                $( "<ul>" ).append(
                    $( "<li>" ).attr( "id", "id" + data.menuSn ).attr( "class", "mcd" + data.menuCd ).append( data.menuNm )
                )
            );
            rootYn = true;

        } else if ( rootYn ) {
            // root 구조가 없으면 메뉴를 그리지 않음.
            // "#id" + data.upperMenuSn 밑에 ul 이 있으면, 하위 첫번째 ul 안에 li를 그리고
            //                                 이 없으면, ul을 그린 뒤 그 안에 li를 그린다.
            if ( $( "#id" + data.upperMenuSn ).find( "ul" ).length ) {
                $( "#id" + data.upperMenuSn + " ul" ).eq( 0 ).append(
                    $( "<li>" ).attr( "id", "id" + data.menuSn ).attr( "class", "mcd" + data.menuCd ).append( data.menuNm )
                );
            } else {
                $( "#id" + data.upperMenuSn ).append(
                    $( "<ul>" ).append(
                        $( "<li>" ).attr( "id", "id" + data.menuSn ).attr( "class", "mcd" + data.menuCd ).append( data.menuNm )
                    )
                );
            }

        }
    }

    // jstree 생성
    $( '#menuStructureJsTree' ).jstree();

    // jstree 전부 open 하기
    $("#menuStructureJsTree").jstree("open_all");


    $( '#menuStructureJsTree' ).on( "changed.jstree", function ( e, data ) {
        console.log( data.selected );
    } );


}