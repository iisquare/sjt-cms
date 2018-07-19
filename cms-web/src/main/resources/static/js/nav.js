 $(function () {             
    //绑定滚动条事件 
    //绑定滚动条事件 
    $(window).bind("scroll", function () { 
        var sTop = $(window).scrollTop(); 
        var sTop = parseInt(sTop); 
        if (sTop < 30) { 
            $(".top2").css({"position":"absolute","top":"31px","z-index":9999});
        }else{ 
            $(".top2").css({"position":"fixed","top":"0","z-index":9999});
        }  
    }); 
}) 

 //显示二级菜单
 $(".arra i").click(function(){
    if($(".subMenu").is(':hidden')){
        $(".subMenu").show();
        $(this).removeClass("fa-angle-down").addClass("fa-angle-up");
    }else{
        $(".subMenu").hide();
        $(this).removeClass("fa-angle-up").addClass("fa-angle-down");
    }
 });


window.onmousewheel=document.onmousewheel=function(){
    $(".subMenu").hide();
    $(".arra i").removeClass("fa-angle-up").addClass("fa-angle-down");
    $(".index_search").show();
    $(".search").hide();
};

$(".index_search i").click(function(){
    if($(".search").is(':hidden')){
        $(".index_search").hide();
        $(".search").slideToggle(300);
    }
});

//$(selector).slideToggle(speed,callback);

