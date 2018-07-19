window.onload = function(){
    //获取相关
    var mybtn = document.getElementById("go-top");
    var Time1 = null;
    var isTop = true;

    mybtn.onclick = function(){
        //设置定时器
        Time1 = setInterval(function(){
            var osTop = document.body.scrollTop||document.documentElement.scrollTop;
            //ceil向上取整，floor向下取整
            var speed = Math.ceil(osTop/2);
            //做一个变换速度的回到顶部效果
            document.body.scrollTop = document.documentElement.scrollTop = osTop - speed;
            if(osTop <= 0){
                //清除定时器
                clearInterval(Time1);
            }
            isTop = true ;
        },30)
    }
    
    //页面滚动自动触发：
    window.onscroll = function(){
        //获取页面可视区高度和滚动高度
        var osTop = document.body.scrollTop||document.documentElement.scrollTop;
        //这两个获取滚动的方法可以兼容多种浏览器
        var clientHeight = document.documentElement.clientHeight;
        //对回到顶部的隐藏和显示，在css中一开始可以设置为隐藏
        if (osTop>clientHeight) {
            mybtn.style.display = "block"
        }
        else{
            mybtn.style.display = "none"
        }
        if (!isTop) {
            clearInterval(Time1);
        }
        isTop = false;
    }
}