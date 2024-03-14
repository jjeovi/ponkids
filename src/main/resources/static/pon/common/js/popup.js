
// ------------- function () 함수 실행  호출시점 : DOM Tree 생성 완료 후 -----------------
$( function () {
	
})

function showPopup() {
	if ( popupList != null && popupList.length > 0 ) {
		
		// popupList에서 한개씩 꺼내 팝업 생성 하여 호출
		for ( let item of popupList ) {
			
			var popCookie = getCookie( 'pop_' + item.popupSn );
			
//			 변수가 없을경우 팝업 출력
			if (!popCookie ) {
				window.open( '/popup/detail?pk=' + item.popupSn, '_blank', 'width=auto,height=auto,web-app' );
			}
			
		}
		
	}
}

// 쿠키 가져오기 
function getCookie( name ) {
	var nameOfCookie = name + "=";
	var x = 0; while (x <= document.cookie.length) {
		var y = (x + nameOfCookie.length);
		if (document.cookie.substring(x, y) == nameOfCookie) {
			if ((endOfCookie = document.cookie.indexOf(";", y)) == -1)
				endOfCookie = document.cookie.length;
			return unescape(document.cookie.substring(y, endOfCookie));
		}
		x = document.cookie.indexOf(" ", x) + 1; if (x == 0)
			break;
	}
	return "";
}

// 24시간 기준 쿠키 설정하기 
// expiredays 후의 클릭한 시간까지 쿠키 설정 
function setCookie24( e ) {
	
	var name = "pop_" + $(e).data("popupSn");
	var value = 'done';
	var expiredays = 1;
	var todayDate = new Date();
	
	var todayDate = new Date(); todayDate.setDate(todayDate.getDate() + expiredays);
	document.cookie = name + "=" + escape(value) + "; path=/; expires=" + todayDate.toGMTString() + ";"
	
	self.close();
}


// 00:00 시 기준 쿠키 설정하기 
// expiredays 의 새벽 00:00:00 까지 쿠키 설정 
function setCookie00( e ) {
	
	var name = "pop_" + $(e).data("popupSn");
	var value = 'done';
	var expiredays = 1;
	var todayDate = new Date();
	todayDate = new Date(parseInt(todayDate.getTime() / 86400000) * 86400000 + 54000000);
	if (todayDate > new Date()) {
		expiredays = expiredays - 1;
	}
	todayDate.setDate(todayDate.getDate() + expiredays);
	document.cookie = name + "=" + escape(value) + "; path=/; expires=" + todayDate.toGMTString() + ";"
}

// 팝업출력
function popUpAction(name) {
	// name으로 해당 팝업창 열기 
	$("div[name=" + name + "]").fadeIn();
}

// 닫기버튼 클릭 이벤트 
$('.btn_close').click(function () {
	$(this).parent('.main_notice_pop').fadeOut();
	// 오늘하루 보지않기 체크 확인 
	if ($("input:checkbox[name=today_close1]").is(":checked") == true) {
		setCookie00('popup1', "done", 1);
	}

	// 오늘하루 보지않기 체크 확인
	if ($("input:checkbox[name=today_close2]").is(":checked") == true) {
		setCookie00('popup2', "done", 1);
	}

	// 오늘하루 보지않기 체크 확인 
	if ($("input:checkbox[name=today_close3]").is(":checked") == true) {
		setCookie00('popup3', "done", 1);
	}

	// name으로 해당 팝업창 닫기 
	$(this).parent("div[name=" + name + "]").fadeOut();
});

