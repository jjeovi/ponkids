
var swiper = new Swiper( ".visual_swiper", {
    navigation: {
        nextEl: ".swiper-button-next",
        prevEl: ".swiper-button-prev",
    },
    pagination: {
        el: ".swiper-pagination",
    },
} );


var swiper = new Swiper( ".sec01 .swiper", {
    slidesPerView: 4,
    spaceBetween: 30,
    navigation: {
        nextEl: ".sec01 .swiper-button-next",
        prevEl: ".sec01 .swiper-button-prev",
    },
    breakpoints: {
        1240: {
            slidesPerView: 4,
            spaceBetween: 30,
        },
        768: {
            slidesPerView: 3,
            spaceBetween: 20,
        },
        365: {
            slidesPerView: 2,
            spaceBetween: 20,
        },
        10: {
            slidesPerView: 1,
            spaceBetween: 20,
        },
    }


} );

var swiper = new Swiper( ".sec02 .swiper", {
    slidesPerView: 4,
    spaceBetween: 30,
    navigation: {
        nextEl: ".sec02 .swiper-button-next",
        prevEl: ".sec02 .swiper-button-prev",
    },
    breakpoints: {
        1240: {
            slidesPerView: 4,
            spaceBetween: 30,
        },
        768: {
            slidesPerView: 3,
            spaceBetween: 20,
        },
        365: {
            slidesPerView: 2,
            spaceBetween: 20,
        },
        10: {
            slidesPerView: 1,
            spaceBetween: 20,
        },
    }
} );


var swiper = new Swiper( ".sec03 .swiper", {
    slidesPerView: 4,
    spaceBetween: 30,
    navigation: {
        nextEl: ".sec03 .swiper-button-next",
        prevEl: ".sec03 .swiper-button-prev",
    },
    breakpoints: {
        1240: {
            slidesPerView: 4,
            spaceBetween: 30,
        },
        768: {
            slidesPerView: 3,
            spaceBetween: 20,
        },
        365: {
            slidesPerView: 2,
            spaceBetween: 20,
        },
        10: {
            slidesPerView: 1,
            spaceBetween: 20,
        },
    }
} );


// 3. init main review area
function initMainReview() {
    // TODO
    // init main review area
    // - 메인 리뷰 영역 표출 초기화 함수



}


// (메인화면용) 클래스 > 상세보기 event
function detailClass( e ) {
		var classSn = $(e).closest(".swiper-slide").data("classSn");
		
		location.href = "/class/mcdClass/detail?pk=" + classSn;
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
