/**
 * Created by yuyue on 2017/3/27.
 */
(function($){
    $.fn.extend({
        videoLady:function(srcVideo){
            videoHtml();
            $("#masklayer").show();
            $("#a1").show();
            $("#ResourcesClose").show();
            $("#ResourcesClose").attr("data-type","1");
            var flashvars = {
                f: srcVideo,
                c: 0,
                v: 85,
                p:1
            };
            var params = { bgcolor: '#FFF', allowFullScreen: true, allowScriptAccess: 'always', wmode: 'transparent' };
            CKobject.embedSWF('../PlugIn/ckplayer/ckplayer.swf', 'a1', 'ckplayer_a1', '1000', '500', flashvars, params);
        },
        imgLook:function(imgLit,thisindex,imgListMin){
            imgLookHtml();
            $("#wrapper").show();
            $("#ResourcesClose").show();
            $("#ResourcesClose").attr("data-type","2");
            if(imgLit.length>1){
                var chengepage = "<div class='imgLookList-left imgLookChange' data-change='up'><b> &lsaquo; </b></div>" +
                    "<div class='imgLookList-right imgLookChange' data-change='next'><b> &rsaquo; </b></div>" +
                    "<div class='imgListShow'><span data-type='isUP'>&uarr;</span><div id='imgListShowID'></div><span data-type='isDOWN'>&darr;</span></div>";
                $("#wrapper").append(chengepage);
                $("#wrapper").css("padding-bottom",100+"px");
                for(var i=0;i<imgListMin.length;i++){
                $("#imgListShowID").append("<img src='"+imgListMin[i]+"'/>");
                }
                //美化滚动条
                $('.imgListShow').perfectScrollbar();
            }
            function isInCircle(x, y) {
                var relative_x = x - this.x;
                var relative_y = y - this.y;
                return Math.sqrt(relative_x * relative_x + relative_y * relative_y) <= this.r;
            }

            function isInRectangle(x, y) {
                return (this.x1 <= x && x <= this.x2) && (this.y1 <= y && y <= this.y2);
            }

            function getCircleCenter() { return { x: this.x, y: this.y }; }

            function getRectangleCenter() { return { x: (this.x2 + this.x1) / 2, y: (this.y2 + this.y1) / 2 }; }

            var objects = [
                { x: 100, y: 100, r: 50, isInObject: isInCircle, title: 'big circle', getCenter: getCircleCenter },
                { x: 150, y: 250, r: 35, isInObject: isInCircle, title: 'middle circle', getCenter: getCircleCenter },
                { x: 500, y: 300, r: 10, isInObject: isInCircle, title: 'small circle', getCenter: getCircleCenter },
                { x1: 200, y1: 400, x2: 300, y2: 500, isInObject: isInRectangle, title: 'rectangle', getCenter: getRectangleCenter }
            ];
            function whereIam(x, y) {
                for (var i = 0; i < objects.length; i++) {
                    var obj = objects[i];
                    if (obj.isInObject(x, y))
                        return obj;
                }
                return null;
            }

            function showMe(ev, a) {
                $.each(objects, function (i, object) {
                    if (object.title == $(a).html()) {
                        var center = object.getCenter();
                        var offset = viewer.iviewer('imageToContainer', center.x, center.y);
                        var containerOffset = viewer.iviewer('getContainerOffset');
                        var pointer = $('#pointer');
                        offset.x += containerOffset.left - 20;
                        offset.y += containerOffset.top - 40;
                        pointer.css('display', 'block');
                        pointer.css('left', offset.x + 'px');
                        pointer.css('top', offset.y + 'px');
                    }
                });
                ev.preventDefault();
            }
            window.showMe = showMe;
            $("#viewer1").iviewer({
                src: imgLit[thisindex]
            });
            $("#viewer1").find("img").attr("src", imgLit[thisindex]);
        },
        musicPlayer:function(srcMusic){
            $("#ResourcesClose").attr("data-type","3");
            $("body").append("<div class='musicPla' id='musicPlaID'><a href='javascript:void(0)' class='MusicBtnall' id='MusicBtnallID'>暂停</a><audio id='MusicAudio' src='' preload='preload'></audio></div>");
            $("#MusicAudio").attr("src",srcMusic)
            document.getElementById("MusicAudio").play();
            $("#masklayer").show();
            $("#ResourcesClose").show();
            $("#musicPlaID").show();
            $("#musicPlaID").attr("class","musicPla");
            $("#MusicBtnallID").text("暂停");
            isendMusic();
        }
    });
    //多张图片切换预览
    var imgLit = [];
    var imgListMin =[];
    var pageImgnum = parseInt(0);
    $("body .imgLook-list").on("click","img",function(){
        var thisindex = $(this).index();
        pageImgnum = thisindex;
        $(this).parents(".imgLook-list").find("img").each(function(){
            imgLit.push($(this).attr("data-srcPub"));
        });
        $(this).parents(".imgLook-list").find("img").each(function(){
            imgListMin.push($(this).attr("data-srcMin"));
        });

        $(this).imgLook(imgLit,thisindex,imgListMin);
        $("#imgListShowID").find("img").eq(thisindex).addClass("PublicimgActive");
    });
    $("body").on("click", ".imgLookChange", function () {
        var thisischange = $(this).attr("data-change");
        if (thisischange == "up") {
            if (pageImgnum >= 1) {
                pageImgnum -= 1;
                $("#viewer1>img").attr("src", imgLit[pageImgnum]);
                $(".imgListShow").find("img").removeClass("PublicimgActive");
                $("#imgListShowID").find("img").eq(pageImgnum).addClass("PublicimgActive");
            } else {
            	ZENG.msgbox.show("已经是第一张了", 1, 1500);
            }
        } else if (thisischange == "next") {
            if (pageImgnum < imgLit.length-1) {
                pageImgnum += 1;
                $("#viewer1>img").attr("src", imgLit[pageImgnum]);
                $(".imgListShow").find("img").removeClass("PublicimgActive");
                $("#imgListShowID").find("img").eq(pageImgnum).addClass("PublicimgActive");
            } else {
                ZENG.msgbox.show("已经是最后一张了", 1, 1500);
            }
        }
    });
    //点击缩略图切换图片
    $("body").on("click","#imgListShowID>img",function(){
        $(".imgListShow").find("img").removeClass("PublicimgActive");
        $(this).addClass("PublicimgActive");
        var thispageMinImg = $(this).index();
        pageImgnum = thispageMinImg;
        $("#viewer1>img").attr("src", imgLit[pageImgnum])
    });
    //视频html代码
    var videoHtml = function(){
        $("body").append("<div id='a1'></div>");
    };
    //图片预览html代码
    var imgLookHtml = function(){
        var htmlimg = "<div class='wrapper' id='wrapper'><div id='viewer1' class='viewer'></div>"
        $("body").append(htmlimg);
    };
    //遮罩层和关闭按钮html代码
    $(function(){
        $("body").append("<div id='masklayer'></div><div id='ResourcesClose'></div>");
    });
    //关闭资源预览
    $("body").on("click","#ResourcesClose",function(){
        var thistype = $(this).attr("data-type");
        $("#ResourcesClose").hide();
        imgLit = [];
        imgListMin =[];
        $("#masklayer").hide();
        if(thistype==1){
            CKobject.getObjectById('ckplayer_a1').videoPause();
            $("#a1").remove();
        }
        if(thistype==2){
            $("#wrapper").remove();
        }
        if(thistype==3){
            document.getElementById("MusicAudio").pause();
            $("#musicPlaID").hide();

        }
    });
    //控制图片缩略图滚动条翻页
    $("body").on("click",".imgListShow span",function(){
        var thistype = $(this).attr("data-type");
        var thissctop = $('.imgListShow').scrollTop();
        if(thistype=="isUP"){
            $('.imgListShow').animate({'scrollTop':thissctop-106},200);
        }else if(thistype=="isDOWN"){
            $('.imgListShow').animate({'scrollTop':thissctop+106},200);
        }
    });
    //控制音频的暂停与播放
    $("body").on("click","#MusicBtnallID",function(){
        var isyesplay = $("#musicPlaID").hasClass("musicPla-stop");
        var MusicAudio = document.getElementById("MusicAudio");
        $("#musicPlaID").toggleClass("musicPla-stop");
        if(isyesplay){
            MusicAudio.play();
            $(this).text("暂停");
        }else{
            MusicAudio.pause();
            $(this).text("播放");
        }
    })
    //监听音频是否播放完毕
    function isendMusic(){
    var timeMusics = setInterval(function(){
        var isPlayerMusic = document.getElementById("MusicAudio").ended;
        if(isPlayerMusic==true){
            clearInterval(timeMusics);
            $("#musicPlaID").attr("class","musicPla musicPla-stop");
            $("#MusicBtnallID").text("播放");
        }
    },1000)
    }
})(jQuery);