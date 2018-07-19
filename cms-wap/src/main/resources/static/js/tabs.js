//点击切换选项卡js
$(function(){
    function tabs(tabTit,on,tabCon){
        $(tabTit).children().mouseover(function(){
            $(this).addClass(on).siblings().removeClass(on);
            var index = $(tabTit).children().index(this);
           	$(tabCon).children().eq(index).show().siblings().hide();
    	});
	};
    tabs(".news_tit","news_act",".news_info");//首页新闻选项卡
    tabs(".subMenu2","currMenu",".new2");
    tabs(".index_tab2","currTab",".tabCont");
});

// 悬停切换选项卡js
$(function(){
    function tabs1(tabTit,on,tabCon,sign){
        $(tabTit).children().mouseover(function(){
            $(this).addClass(on).siblings().removeClass(on);
            var index = $(tabTit).children().index(this);
            $(tabCon).children().eq(index).show().siblings().hide();
            $(sign).children().eq(index).addClass("show").siblings().removeClass("show");
        });
    };
    tabs1(".sub_tit","sub_act",".sub_info",".main_dsj");//内页选项卡
});

//底部选项卡js
$(function(){
    function tabs2(tabTit,on,tabCon,sign){
        $(tabTit).children().mouseover(function(){
            $(this).addClass(on).siblings().removeClass(on);
            var index = $(tabTit).children().index(this);
            $(tabCon).children().eq(index).show().siblings().hide();
            // $(sign).children().eq(index).show().siblings().hide();
            $(sign).children().eq(index).addClass("sho").siblings().removeClass("sho");
        });
    };
    tabs2(".nav-tit","active",".info",".sign");
});
