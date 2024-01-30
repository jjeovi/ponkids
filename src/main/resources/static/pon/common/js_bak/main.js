$( function () {

    // 1. init main banner area
    // initMainBanner();

    // 2. init main class area
    // initMainClass();

    // 3. init main review area
    // initMainReview();

    $( '.class_2dep > div' ).hide();
    $( '.class_1dep .tabnav a' ).click( function () {
        $( '.class_2dep > div' ).hide().filter( this.hash ).fadeIn();
        $( '.class_1dep .tabnav a' ).removeClass( 'active' );
        $( this ).addClass( 'active' );
        return false;
    } ).filter( ':eq(0)' ).click();


    $( '.detail_tab_content div' ).hide();
    $( '.detail_tab_nav a' ).click( function () {
        $( '.detail_tab_content div' ).hide().filter( this.hash ).fadeIn();
        $( '.detail_tab_nav a' ).removeClass( 'active' );
        $( this ).addClass( 'active' );
        return false;
    } ).filter( ':eq(0)' ).click();

} );

$( '.class_2dep .tabnav_2dep a' ).click( function () {
    $( '.class_2dep .tabnav_2dep a' ).removeClass( 'active' );
    $( this ).addClass( 'active' );
} )

$( '.paging_box span' ).click( function () {
    $( '.paging_box span' ).removeClass( 'active' );
    $( this ).addClass( 'active' );
} )

$( '.mb_menu' ).click( function () {
    $( '.nav_wrap' ).addClass( 'active' );
    $( '.dark_bg' ).addClass( 'active' );
} )
$( ' .close' ).click( function () {
    $( '.nav_wrap' ).removeClass( 'active' );
    $( '.dark_bg' ).removeClass( 'active' );
} )
$( '.dark_bg' ).click( function () {
    $( '.nav_wrap' ).removeClass( 'active' );
    $( '.dark_bg' ).removeClass( 'active' );
} )

$( '.mb_search' ).click( function () {
    $( '.mb_search_form' ).toggleClass( 'active' );
} )

$( function () {
    $( '.chatWrap .tabcontent > div' ).hide();
    $( '.chatWrap .tabnav a' ).click( function () {
        $( '.chatWrap .tabcontent > div' ).hide().filter( this.hash ).fadeIn();
        $( '.chatWrap .tabnav a' ).removeClass( 'active' );
        $( this ).addClass( 'active' );
        return false;
    } ).filter( ':eq(0)' ).click();
} );

$( '.inquiry' ).click( function () {
    $( '.chatWrap' ).addClass( 'show' )
    $( '.chat_close' ).addClass( 'show' )
} )
$( '.chat_close' ).click( function () {
    $( '.chatWrap' ).removeClass( 'show' )
    $( '.chat_close' ).removeClass( 'show' )
} )


var swiper = new Swiper( ".visual_swiper", {
    navigation: {
        nextEl: ".swiper-button-next",
        prevEl: ".swiper-button-prev",
    },
    pagination: {
        el: ".swiper-pagination",
    },
} );


var swiper = new Swiper( ".sec02 .propose_swiper", {
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

var swiper = new Swiper( ".sec03 .english_swiper", {
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


var swiper = new Swiper( ".sec04 .science_swiper", {
    slidesPerView: 4,
    spaceBetween: 30,
    navigation: {
        nextEl: ".sec04 .swiper-button-next",
        prevEl: ".sec04 .swiper-button-prev",
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

// 1. init main banner area
function initMainBanner() {
    // TODO
    // init main banner area
    // - 메인 배너 영역 표출 초기화 함수

    var url = '/banner/live/getMainListAjax';
    $.ajax( {
        url: url,
        type: "GET",
        dataType: "json",
        async: false,	// 동기식 ajax : 통신이 완료될 떄 까지 다음 line 진행 안함
        contentType: "application/json",
        success: function ( result ) {

            console.log("실행완료");

        }
    } );


}

// 2. init main class area
function initMainClass() {
    // TODO
    // init main class area
    // - 메인 클래스 영역 표출 초기화 함수

    var url = '/menu/live/pon/getMenuListAjax';
    $.ajax( {
        url: url,
        type: "GET",
        dataType: "json",
        async: false,	// 동기식 ajax : 통신이 완료될 떄 까지 다음 line 진행 안함
        contentType: "application/json",
        success: function ( result ) {

            if ( result ) {
                var ponMenuList = result.resultList;

                // menuList 등록
                for( let menu of ponMenuList ) {
                    if ( menu.level == 1 ) {
                        $(".pon_menu").append(
                            $("<li>").append(
                                $("<a>").attr("href", menu.menuUrl).append(
                                    menu.menuNm
                                )
                            )
                        )
                    }
                }
            }
        }
    } );


}


// 3. init main review area
function initMainReview() {
    // TODO
    // init main review area
    // - 메인 리뷰 영역 표출 초기화 함수



}
