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
    });

    // 카테고리 분류가 선택 되어있으면 picked-cate 에 뿌려준다.
    if ( $( ".category-list-area .category-group-box ul li.on" ).length ) {
        $( ".category-list-area .category-group-box ul li.on" ).trigger( "click" );
    }
    
    
	// dateTimePickr 클래스속성인 모든 항목
	// 1. html 태그에 [ data-pickr ] 값 date		: date  설정 
	// 2. html 태그에 [ data-pickr ] 값 time		: time  설정 
	// 3. html 태그에 [ data-pickr ] 값 dateTime : date와 time 모두 설정 
	$('[data-pickr="dateTime"').each( function ( i, item ) {
			
        flatpickr( '#'+ item.id , {
            dateFormat: 'Y-m-d H:i',	// 날짜 및 시간 형식 설정 (예: 2023-09-12 15:30)
            defaultHour : '09',			// 디폴트 시간 설정 (날짜 선택시 기본으로 설정되어있는 시간)		
            enableTime: true,        	// 시간 선택 활성화
            locale: 'ko',            	// 한국어로 지역화
        });
		
	} );



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
    // 라디오 버튼의 label들을 찾아 모든 label 에 active 클래스를 제거 후, 클릭된 label 에 active클래스 추가
    $( e ).parent().siblings( "label" ).removeClass( "active" );
    $( e ).parent().addClass( "active" );
}


// 체크박스 버튼 클릭시 active 클래스 추가
function clickCheckboxEvent( e ) {
    // 체크박스 버튼의 label들을 찾아 모든 label 에 active 클래스를 제거 후, 클릭된 label 에 active클래스 추가
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
        } );

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
                async: false,	// 동기식 ajax : 통신이 완료될 떄 까지 다음 line 진행 안함
                data: { categorySn: categorySn }, // 검색할 값
                contentType: "application/json",
                success: function ( result ) {
                    // return type : List<CategoryDto>
                    var allYn = $ul.data( 'allYn' );
                    var searchUrl = $ul.data( 'searchUrl' );

                    if ( searchUrl != null && searchUrl != '' ) {
                        url = searchUrl;
                    }

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
function searchCateAjax( url ) {

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
            async: false,	// 동기식 ajax : 통신이 완료될 떄 까지 다음 line 진행 안함
            data: data, // 검색할 값 listDto 안에 categoryDto 변수 추가
            contentType: "application/json",
            success: function ( result ) {
                // callback 함수 연결 -> callback함수로 구현
                if ( typeof searchCateAjaxCallback === 'function' ) {
                    searchCateAjaxCallback( result );
                } else {
                    console.log( 'searchCateAjaxCallback( result ) funnction is required. searchCateAjaxCallback( result ) 콜백함수 not found!' );
                }
            }
        } );

    } else {
        alert( "검색 옵션을 선택해주세요." );
    }

}


// 메뉴 구조 그리기
function drawMenuTree( resultList ) {

    $( "#menuStructure" ).empty();
    $( "#menuStructure" ).append(
        $( "<div>" ).attr( "id", "menuStructureJsTree" ).append()
    );

    // jstree 생성
    $( '#menuStructureJsTree' ).jstree( {
        'plugins': [ "dnd", "wholerow", "types" ], // plugin 목록에 추가
        'core': {
            "data": resultList,
            "themes": { "variant": "large" },
            "check_callback": function ( operation, node, node_parent, node_position, more ) {

                // 드래그한 노드를 드롭할 때 실행되는 코드
                if ( operation === "move_node" && more.ref === undefined ) {

                    // TO DO
                    console.log( "[node_parent] : " + JSON.stringify( node_parent ) ); // 선택한 노드에 대한 부모 노드 정보
                    console.log( "[node_position] : " + node_position ); // Drop 위치
                    console.log( "[drop data] : " + JSON.stringify( node.original ) ); // Drop Data


                    if ( node.original.parntsMenuYn == 'Y' ) {
                        alert( "하위메뉴를 포함한 메뉴는 이동이 불가능합니다." );
                        return false;
                    } else if ( node_parent.original.level > 2 ) {
                        alert( "3레벨 이상의 경로는 이동이 불가능합니다." );
                        return false;
                    } else if ( node_parent.original.parntsMenuYn == 'N' ) {
                        alert( "부모 메뉴로 설정된 메뉴 하위로만 이동 가능합니다." );
                        return false;
                    }

                    // [node.original.menuNm] 메뉴의 위치를 [node_parent.original.menuNm] 메뉴 하위의 [node_position+1] 번째 메뉴로 이동합니다.
                    if ( confirm( "[" + node.original.menuNm + "]메뉴의 위치를 [" + node_parent.original.menuNm + "]메뉴 하위의 [" + ( node_position + 1 ) + "]번째 메뉴로 이동합니다.\n이동 후 원복은 불가능합니다." ) ) {
                        // TODO  메뉴 이동 하여 및 같은 레벨의 순서 정렬 및 부모메뉴 업데이트
                        return true;
                    } else {
                        return false;
                    }

                }

                return true;

            }
        },
        'types': {
            "#": {
                "max_depth": 4,
                "valid_children": [ "root" ]
            }
        }
    } ).bind( "select_node.jstree", function ( e, targetData ) {

        // form 내용 초기화 작업 및 노출 버튼 설정
        menuFormAreaInit( 'U' );

        // target 메뉴 setting
        var target = targetData.node.original;
        var url = "/admin/menu/live/getPossibleRoleListAjax";

        // 해당 메뉴로 다른권한에서 연동 가능한지 여부 확인 (ajax)
        $.ajax( {
            url: url,
            type: "GET",
            dataType: "json",
            async: false,	// 동기식 ajax : 통신이 완료될 떄 까지 다음 line 진행 안함
            data: {
                upperMenuSn: target.upperMenuSn,
                menuSn: target.menuSn
            }, // 검색할 값 listDtok 안에 조회 변수 set
            contentType: "application/json",
            success: function ( result ) {
                // result : 연동가능한 권한들의 list
                // 권한 setting 함수
                menuRoleSet( result, 'U' );

            }
        } );
        // ajax END


        // S :  메뉴 updateFormDiv 항목 setting

        $( "#updateForm [name='menuSn']" ).val( target.menuSn );
        $( "#updateForm [name='upperMenuSn']" ).val( target.upperMenuSn );
        $( "#updateForm [name='menuNm']" ).val( target.menuNm );
        $( "#updateForm [name='menuPath']" ).val( "(" + target.level + "레벨)  [ " + target.menuPath + " ]" );
        $( "#updateForm [name='menuSeq']" ).val( target.menuSeq );
        $( "#updateForm [name='menuCd']" ).val( target.menuCd );
        $( "#updateForm [name='menuUrl']" ).val( target.menuUrl );
        $( "#updateForm [name='parntsMenuYn'][value='" + target.parntsMenuYn + "']" ).prop( "checked", true );
        $( "#updateForm [name='menuDcSetYn'][value='" + target.menuDcSetYn + "']" ).prop( "checked", true );
        $( "#updateForm [name='menuDc']" ).val( target.menuDc );
        $( "#updateForm [name='menuDetailDc']" ).val( target.menuDetailDc );
        $( "#updateForm [name='useYn'][value='" + target.useYn + "']" ).prop( "checked", true );
        $( "#updateForm [name='newWindowYn'][value='" + target.newWindowYn + "']" ).prop( "checked", true );

        // 하위메뉴 존재시, 부모메뉴여부 설정 변경 금지
        if ( target.childMenuCnt > 0 ) {
            $( "#updateForm [name='parntsMenuYn']" ).attr( "disabled", "disabled" );
            $( "#updateForm [name='menuUrl']" ).prop( "readonly", true );
        }

        // nowSelectMenuNm (현재선택메뉴) setting
        $( "#updateForm" ).find( ".bottom-btn-group .text-left .nowSelectMenuNm" ).text( "선택: " + target.menuNm );

        // E :  메뉴 updateFormDiv 항목 setting

    } ).bind( "create_node.jstree", function ( node, parent, position ) {
        console.log( node );
        console.log( parent );
        console.log( position );
    } );


    // S : 메뉴 jstree 생성
    $( "#menuStructureJsTree" ).jstree( "close_all" );
    $( "#menuStructureJsTree" ).jstree( "destory" );
    $( "#menuStructureJsTree" ).jstree( true ).settings.core.data = resultList;
    $( "#menuStructureJsTree" ).jstree( "loaded" );


    $( "#menuStructureJsTree" ).bind( "refresh.jstree", function ( e, data ) {
        $( this ).jstree( "open_all" );
    } );
    $( "#menuStructureJsTree" ).jstree( true ).refresh();
    // E : 메뉴 jstree 생성

}

// 메뉴 권한 목록 (체크박스) 설정 함수
function menuRoleSet( result, mode ) {
    // mode : C, U
    // C : 등록 (create)
    // U : 수정 (update)

    var form;
    if ( mode == 'C' ) {
        form = 'insertForm';
    } else if ( mode == 'U' ) {
        form = 'updateForm';
    }

    // 모든 권한 초기화 setting 모든권한에대해 disabled , checked 해제
    $( "#" + form + " .possibleRole" ).find( "[type='checkbox']" ).each( function ( i, item ) {
        $( this ).attr( "disabled", "disabled" );
        $( this ).prop( "checked", false );
    } )

    // result안의 권한들만 체크 가능한 상태로 setting

    // possibleRoleList
    for ( let item of result.possibleRoleList ) {
        $( "#" + form + " .possibleRole" ).find( "[name='roleSnList'][value='" + item.roleSn + "']" ).removeAttr( "disabled" );
    }

    if ( mode == 'U' ) {
        // checkedRoleList
        for ( let item of result.checkedRoleList ) {
            $( "#" + form + " .possibleRole" ).find( "[name='roleSnList'][value='" + item.roleSn + "']" ).removeAttr( "disabled" );
            $( "#" + form + " .possibleRole" ).find( "[name='roleSnList'][value='" + item.roleSn + "']" ).prop( "checked", true );
            // $(".possibleRole").find("[name='roleSnList'][value='" + item.roleSn + "']").prop('checked',true);
        }
    }

}

// 메뉴 form 초기화 작업
function menuFormAreaInit( mode ) {

    // mode : C, U, E
    // C : 등록 (create)
    // U : 수정 (update)
    // E : 초기화상태 (모두지움) ( erase, empty)

    if ( mode == 'C' ) {
        // C : 등록 (create)

        // S :  form 내용 초기화 작업 및 노출 버튼 설정
        $( "#insertForm" ).clearForm();

        // radio disabled 버튼 disabled 풀기
        $( "#insertForm [name='parntsMenuYn']" ).removeAttr( "disabled" );
        $( "#insertForm [name='menuUrl']" ).removeAttr( "readonly" );

        // formArea 전부 숨긴 뒤 insertFormDiv 만 보이게
        $( "#formArea" ).children().hide();
        $( "#insertFormDiv" ).show();

        // btn 설정
        // insertFormDiv 안의 하단 버튼 모두 숨긴 뒤 등록 버튼만 보이게
        $( "#insertFormDiv" ).find( ".bottom-btn-group .text-center" ).children().hide();
        $( "#insertFormDiv" ).find( ".bottom-btn-group .text-center" ).find( "[name='insertBtn']" ).show();

        var parentNode = $( "#menuStructureJsTree" ).jstree( true ).get_node( sel ).original;
        $( "#insertForm [name='menuPath']" ).val( "(" + ( parentNode.level + 1 ) + "레벨)  [ " + parentNode.menuPath + " > _______  ]" );
        $( "#insertForm [name='upperMenuSn']" ).val( parentNode.menuSn );

        // E :  form 내용 초기화 작업 및 노출 버튼 설정

    } else if ( mode == 'U' ) {
        // U : 수정 (update)

        // S :  form 내용 초기화 작업 및 노출 버튼 설정
        $( "#updateForm" ).clearForm();

        // radio disabled 버튼 disabled 풀기
        $( "#updateForm [name='parntsMenuYn']" ).removeAttr( "disabled" );
        $( "#updateForm [name='menuUrl']" ).removeAttr( "readonly" );

        // formArea 전부 숨긴 뒤 updateFormDiv 만 보이게
        $( "#formArea" ).children().hide();
        $( "#updateFormDiv" ).show();

        // btn 설정
        // updateFormDiv 안의 하단 버튼 모두 숨긴 뒤 수정 버튼만 보이게
        $( "#updateFormDiv" ).find( ".bottom-btn-group .text-center" ).children().hide();
        $( "#updateFormDiv" ).find( ".bottom-btn-group .text-center" ).find( "[name='updateBtn']" ).show();
        $( "#updateFormDiv" ).find( ".bottom-btn-group .text-center" ).find( "[name='deleteBtn']" ).show();

        // nowSelectMenuNm (현재선택메뉴) 초기화
        $( "#updateFormDiv" ).find( ".bottom-btn-group .text-left .nowSelectMenuNm" ).empty();

        // E :  form 내용 초기화 작업 및 노출 버튼 설정

    } else if ( mode == 'E' ) {
        // E : 초기화상태 (모두지움) ( erase, empty)

        // S :  form 내용 초기화 작업 및 노출 버튼 설정
        $( "#insertForm" ).clearForm();
        $( "#updateForm" ).clearForm();

        // radio disabled 버튼 disabled 풀
        $( "#insertForm [name='parntsMenuYn']" ).removeAttr( "disabled" );
        $( "#insertForm [name='menuUrl']" ).removeAttr( "readonly" );
        $( "#updateForm [name='parntsMenuYn']" ).removeAttr( "disabled" );
        $( "#updateForm [name='menuUrl']" ).removeAttr( "readonly" );

        // formArea 전부 숨긴 뒤 updateFormDiv 만 보이게
        $( "#formArea" ).children().hide();
        $( "#formArea .noData" ).show();

        // E :  form 내용 초기화 작업 및 노출 버튼 설정
    }

}

// 특정 영역 안의 input 전부 모아서 json 생성
// id값 mapping
function getDataToJson( e ) {

    // JSON.parse() 함수 내부의 문자열은 "AA":"BB" 형식으로 쌍 따옴표로 감싸주어야 함. 그렇지 않으면 error
    var data = '';
    $.each( $( e ).serializeArray(), function ( key, val ) {
        data += ',"' + val['name'] + '":"' + val['value'] + '"';
    } );

    data = '{' + data.substr( 1 ) + '}';
    // console.log( JSON.parse(data) );

    return JSON.parse( data );
}

// form 값의 모든 input (hidden, radio, checkbox 등.. 전부 ) 값을 초기화
$.fn.clearForm = function () {
    return this.each( function () {
        var type = this.type,
            tag = this.tagName.toLowerCase();
        if ( tag === "form" ) {
            return $( ":input", this ).clearForm();
        }
        if (
            type === "text" ||
            type === "password" ||
            type === "hidden" ||
            tag === "textarea"
        ) {
            this.value = "";
        } else if ( type === "checkbox" || type === "radio" ) {
            this.checked = false;
        } else if ( tag === "select" ) {
            this.selectedIndex = -1;
        }
    } );
};

// form 유효성 체크
$.fn.requiredCheck = function () {

    var result = true;
    this.each( function () {

        var tag = this.tagName.toLowerCase();
        if ( tag === "form" ) {
            return $( ":input", this ).requiredCheck();
        }

        if ( $( this )[0].hasAttribute( "required" ) && $( this )[0].value == "" ) {
            alert( $( this )[0].title + "을(를) 입력해주세요." );
            result = false;
            return false;
        }
    } );
    return result;
};


// 특정 영역 안의 input 전부 모아서 json 생성
// form 유효성 체크	requiredCheckForm($("#menuForm"))
function requiredCheckForm( e ) {

    var result = true;
    var inputList = $( e ).find( ":input" ).not( ":input[type=hidden]" );
    var form = $( e );

    inputList.each( function ( idx, ele ) {
        if ( ele.type == "text" ) {
            // ele : text 타입일 때
            if ( ele.hasAttribute( "required" ) && ele.value == "" ) {
                alert( ele.title + "을(를) 입력해주세요." );
                ele.focus();
                result = false;
                return false;
            }
            ;
        } else if ( ele.type == "radio" || ele.type == "checkbox" ) {

            // ele : radio 일 때
            if ( ele.hasAttribute( "required" ) && !form.find( "[name='" + ele.name + "']" ).is( ":checked" ) ) {
                alert( ele.title + "을(를) 입력해주세요." );
                ele.focus();
                result = false;
                return false;
            }
            ;
        }
    } );
    return result;
}


//에디터 설정
function fnSummernoteCall( id ) {
    // id : summernote 적용할 id 값

    $( '#' + id ).summernote( {
        height: 300,                 // 에디터 높이
        minHeight: null,             // 최소 높이
        maxHeight: null,             // 최대 높이
        focus: true,                 // 에디터 로딩후 포커스를 맞출지 여부
        lang: "ko-KR",				 // 한글 설정
        //placeholder: '최대 2048자까지 쓸 수 있습니다',	//placeholder 설정

        callbacks: {	//여기 부분이 이미지를 첨부하는 부분
            onImageUpload: function ( files ) {

                uploadSummernoteImageFile( files[0], this );
            },
            onPaste: function ( e ) {
                var clipboardData = e.originalEvent.clipboardData;
                if ( clipboardData && clipboardData.items && clipboardData.items.length ) {
                    var item = clipboardData.items[0];
                    if ( item.kind === 'file' && item.type.indexOf( 'image/' ) !== -1 ) {
                        e.preventDefault();
                    }
                }
            }
        }
    } );
}

// 이미지 파일 업로드
function uploadSummernoteImageFile( file, editor ) {

    data = new FormData();
    data.append( "file", file );
    var header = $( "meta[name='_csrf_header']" ).attr( "content" );
    var token = $( "meta[name='_csrf']" ).attr( "content" );

    $.ajax( {
        data: data,
        type: "POST",
        url: "/uploadSummernoteImageFile",
        beforeSend: function ( xhr ) {

            if ( header != null && token != null ) {
                xhr.setRequestHeader( header, token );
            }

        },
        contentType: false,
        processData: false,
        success: function ( data ) {
            //항상 업로드된 파일의 url이 있어야 한다.
            $( editor ).summernote( 'insertImage', data.url );
        }
    } );
}


// 첨부파일 드래그
$( "div.note-editable" ).on( 'drop', function ( e ) {
    for ( i = 0; i < e.originalEvent.dataTransfer.files.length; i++ ) {
        uploadSummernoteImageFile( e.originalEvent.dataTransfer.files[i], $( "#summernote" )[0] );
    }
    e.preventDefault();
} )

// ------------- 에디터 함수 S -----------------   


// ------------- 멀티파일 추가 S -----------------   
//파일 추가시
$( "#file_add" ).on( 'click', function () {
    $( '#multi-add' ).click();
} );


var $fileListArr = new Array();
var $totSize = 0;
var $keyNum = 0;
var $limit = 0;


$( "#multi-add" ).on( 'change', function () {

    $( "#fileNoData" ).remove();

    var files = $( this )[0].files;

    var fileArr = new Array();

    fileArr = $fileListArr;
    $limit = $totSize;
    for ( var i = 0; i < files.length; i++ ) {
        $limit = $limit + files[i].size;
        if ( $limit > 20000000 ) {
            alert( "첨부파일 용량은 20MB를 넘길수 없습니다." );
            return false;
        }
    }

    for ( var i = 0; i < files.length; i++ ) {
        $( '#file_table' ).append( "<tr id=file" + $keyNum + "><td class='txt-c'><button type='button' style='color: #A4A4A4; font-size: large;' class='deleteFile' >&#8861;</button></td>" +
            "<td>" + files[i].name + "</td>" +
            "<td id='fileSize " + $keyNum + "'><p class=file" + $keyNum + ">" + Math.floor( files[i].size / 1000 ) + " KB</p></td>" +
            "</tr>" );
        $keyNum++;
        fileArr.push( files[i] );
        $totSize = $totSize + files[i].size;
    }


    $fileListArr = new Array();
    $fileListArr = fileArr;
    //$('#totSize').text("");
    //$('#totSize').text(Math.floor($totSize / 1000000));


    var fileList = new FormData();
    for ( var i = 0; i < $fileListArr.length; i++ ) {
        fileList.append( "uploadFile", $fileListArr[i] );

    }
    //$("#multiFile").val(fileList);

} );


//파일 화면 delete
$( document ).on( "click", '.deleteFile', function () {
    //삭제할 파일의 아이디
    var DeleteID = $( this ).parent().parent().attr( 'id' );

    //삭제하려는 파일의 크기값(텍스트)
    var DeleteFileSize = $( this ).parent().next().next().children( 'p' ).text();

    //삭제하는 파일의 크기값(바이트)
    var fileSizeByteArr = new Array();
    fileSizeByteArr = DeleteFileSize.split( ' ' );
    var fileSize = Number( fileSizeByteArr[0] ) * 1000;

    //배열에서 삭제를 위한 번호
    var DeleteArrNum = DeleteID.substring( DeleteID.length, DeleteID.length - 1 );

    var fileArr = $fileListArr;

    fileArr.splice( DeleteArrNum, 1 );
    $keyNum = 0
    $fileListArr = new Array();
    $( '#file_table' ).children().remove();
    $totSize = 0;
    for ( var i = 0; i < fileArr.length; i++ ) {

        $( '#file_table' ).append( "<tr id=file" + $keyNum + "><td class='txt-c'><button type='button' style='color:  #A4A4A4; font-size: large;' class='deleteFile' >&#8861;</button></td>" +
            "<td>" + fileArr[i].name + "</td>" +
            "<td id='fileSize " + $keyNum + "'><p class=file" + $keyNum + ">" + Math.floor( fileArr[i].size / 1000 ) + " KB</p></td>" +
            "</tr>" );
        $keyNum++;
        $fileListArr.push( fileArr[i] );
        $totSize = $totSize + fileArr[i].size;
    }

    $limit = $totSize;
    $( '#totSize' ).text( "" );
    $( '#totSize' ).text( Math.floor( $totSize / 1000000 ) );

} );


//파일 db,물리 삭제 
function fnfileDelete( atchFileSn, fileSeq ) {

    $.ajax( {
        url: "/file/fileDelete",
        type: "GET",
        dataType: "json",
        data: { atchFileSn: atchFileSn, fileSeq: fileSeq },
        contentType: "application/json",
        success: function ( result ) {

            alert( "파일이 정상적으로 삭제 되었습니다." )
            $( "#attachFileList" ).remove(); // 파일 목록 삭제
            var html = "";
            html += "<div id='attachFileList'><div lass='upload-thumb-wrap'>";

            if ( result.length > 0 ) {
                html += " </div>"
                for ( i = 0; i < result.length; i++ ) {
                    html += '<div>';
                    html += '<span>' + result[i].orignlFileNm + '</span>';
                    html += '<span  class="badge badge-dark"  style="cursor:pointer"> <a href="/fileDownloand?atchFileSn=\'' + result[i].atchFileDetailPk.atchFileSn + '\'&fileSeq=\'' + result[i].atchFileDetailPk.fileSeq + '\');">다운로드</a></span>';
                    html += '<span  class="badge badge-dark"  style="cursor:pointer"> <a onclick="javascript:fnfileDelete(\'' + result[i].atchFileDetailPk.atchFileSn + '\',\'' + result[i].atchFileDetailPk.fileSeq + '\');" >삭제</a></span>';
                    html += "</div>";
                }

            } else {
                html += "<p class='card-text'>첨부파일 목록이 없습니다.</p>";
            }

            html += "</div></div>"

            $( "#attachFileDiv" ).html( html ); // 파일목록 셋팅
        }
    } );
}



// dateTime 선택 (date, time 모두 선택시) 
$.fn.dateTimePickr = function () {
	// dateTime 선택 (date, time 모두 선택시)
	// 날짜 및 시간 형식 : Y-m-d H:i , ex) 2023-09-12 15:30
	// 언어 : 한국어
	
    var result = true;
    
    this.each( function () {
		
        flatpickr( '#'+ this.id , {
            dateFormat: 'Y-m-d H:i', // 날짜 및 시간 형식 설정 (예: 2023-09-12 15:30)
            enableTime: true,        // 시간 선택 활성화
            locale: 'ko',            // 한국어로 지역화
        });
        
    } );
    
    return result;
};