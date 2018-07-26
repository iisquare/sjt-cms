//首页项卡js
$(function(){
    function tabs(tabTit,on,tabCon){
        $(tabTit).children().click(function(){
            $(this).addClass(on).siblings().removeClass(on);
            //var index = $(tabTit).children().index(this);
           	//$(tabCon).children().eq(index).show().siblings().hide();
            $('#js-nextpage-container').html('');
            $('.news_more').nextpage({
                url: '/category/list',
                paramters: function(page) {
                    return {
                        id: $('.news_act').data('category'),
                        page: page
                    };
                }
            });
    	});
        $(tabTit).children().eq(0).trigger('click');
	};
    tabs(".news_tab","news_act",".news_info");//首页新闻选项卡
});

//首页导航下拉隐藏显示
// $(document).ready(function(){
//     $('.info div.rq').mouseover(function(){//鼠标悬停
//         $(this).show();
//     });
//     $('.info div.rq').mouseout(function(){//鼠标离开
//         $(this).hide();
//     });
//     $("li.active").mouseout(function(){
//         $('.info div.rq').hide();
//     });
// })
