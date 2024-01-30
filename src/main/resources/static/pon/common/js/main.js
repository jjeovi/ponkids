
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



// 클래스 > 좋아요 event
function likeClass( e ) {
		var classSn = $(e).closest(".swiper-slide").data("classSn");
		
		alert("좋아요 실행 : " + classSn);
//		location.href = "/class/detail?pk=" + classSn;
	
}
