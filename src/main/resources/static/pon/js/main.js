$(function(){
  $('.class_1dep_content > div').hide();
  $('.class_1dep .tabnav a').click(function () {
    $('.class_1dep_content > div').hide().filter(this.hash).fadeIn();
    $('.class_1dep .tabnav a').removeClass('active');
    $(this).addClass('active');
    return false;
  }).filter(':eq(0)').click();
  });

  $(function(){
    $('.detail_tab_content div').hide();
    $('.detail_tab_nav a').click(function(){
     $('.detail_tab_content div').hide().filter(this.hash).fadeIn();
     $('.detail_tab_nav a').removeClass('active');
     $(this).addClass('active');
     return false;
    }).filter(':eq(0)').click();
})

$('.class_1dep_content .tabnav_2dep a').click(function(){
  $('.class_1dep_content .tabnav_2dep a').removeClass('active');
  $(this).addClass('active');
})

$('.paging_box span').click(function(){
  $('.paging_box span').removeClass('active');
  $(this).addClass('active');})

$('.mb_menu').click(function(){
  $('.nav_wrap').addClass('active');
  $('.dark_bg').addClass('active');
})
$(' .close').click(function(){
  $('.nav_wrap').removeClass('active');
  $('.dark_bg').removeClass('active');
})
$('.dark_bg').click(function(){
  $('.nav_wrap').removeClass('active');
  $('.dark_bg').removeClass('active');
})

$('.mb_search').click(function(){
  $('.mb_search_form').toggleClass('active');
  
})



$(function(){
  $('.chatWrap .tabcontent > div').hide();
  $('.chatWrap .tabnav a').click(function () {
    $('.chatWrap .tabcontent > div').hide().filter(this.hash).fadeIn();
    $('.chatWrap .tabnav a').removeClass('active');
    $(this).addClass('active');
    return false;
  }).filter(':eq(0)').click();
  });
  
$('.inquiry').click(function(){
  $('.chatWrap').addClass('show')
  $('.chat_close').addClass('show')
})
  $('.chat_close').click(function(){
    $('.chatWrap').removeClass('show')
    $('.chat_close').removeClass('show')
  })
  