  var urlstr = location.href;
  var urlstatus=false;
  $(".menu a").each(function () {  
    if ((urlstr + '/').indexOf($(this).attr('href')) > -1&&$(this).attr('href')!='') {
      $(this).addClass('current'); urlstatus = true;
    } else {
      $(this).removeClass('current');
    }
  });
  if (!urlstatus) {$("#menu a").eq(0).addClass('current'); }