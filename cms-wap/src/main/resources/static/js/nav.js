// 菜单高亮
var urlstr = location.href;
//alert((urlstr + '/').indexOf($(this).attr('href')));
var urlstatus=false;
$("#menu a").each(function () {
    if ((urlstr + '/').indexOf($(this).attr('href')) > -1&&$(this).attr('href')!='') {
      $(this).addClass('cur'); urlstatus = true;
    } else {
      $(this).removeClass('cur');
    }
  });
if (!urlstatus) {$("#menu a").eq(0).addClass('cur'); }

