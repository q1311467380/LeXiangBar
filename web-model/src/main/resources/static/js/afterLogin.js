/**
 * 使用document.addEventListener()进行页面在被访问时的动态显示
 * 该监听事件会在html部分加载完成后触发
 *
 */
document.addEventListener('DOMContentLoaded', function () {
    //偏好设置

    //三个吧分别绑上事件，点哪个发哪个的值（吧名）
    //优化的时候看看有没有更方便的代码，把三个事件整合成一个
    //获得该标签的值（吧名），发送到服务端获得指定贴吧的信息，并持久化到localStorage,然后跳转到该吧的页面
    //注意持久化的贴吧的信息会不会被覆盖--会
    // var bar1 = document.getElementById("bar1");
    // var bar2 = document.getElementById("bar2");
    // var bar3 = document.getElementById("bar3");
    //
    // var xhr = new XMLHttpRequest();
    // bar1.onclick = function (){
    //     var barName = bar1.innerText;
    //     alert("贴吧名字" + barName)
    //     xhr.open("get","http://localhost:9001/enterBar?barName=" + barName,true);
    //     xhr.setRequestHeader("Content-Type", "text/plain; charset=utf8");
    //     xhr.send();
    //     xhr.onreadystatechange = function (){
    //         if (xhr.status === 200 && xhr.readyState === 4){
    //             alert(xhr.responseText)
    //             localStorage.setItem("barInfo",xhr.responseText)//持久化了json格式的数据
    //             localStorage.setItem("viewBarName",barName)
    //             window.location.href = "bar.html"
    //         }
    //     }
    // }
    // bar2.onclick = function (){
    //     var barName = bar2.innerText;
    //     alert("贴吧名字" + barName)
    //     xhr.open("get","http://localhost:9001/enterBar?barName=" + barName,true);
    //     xhr.setRequestHeader("Content-Type", "text/plain; charset=utf8");
    //     xhr.send();
    //     xhr.onreadystatechange = function (){
    //         if (xhr.status === 200 && xhr.readyState === 4){
    //             localStorage.setItem("barInfo",xhr.responseText)//持久化了json格式的数据
    //             window.location.href = "bar.html"
    //         }
    //     }
    // }
//页面内容显示
    var xhr3 = new XMLHttpRequest();
    xhr3.open("get", "http://localhost:9005/afterLoginRecommendPost", true);
    xhr3.setRequestHeader("Content-Type", "text/plain; charset=utf8");
    // xhr3.setRequestHeader("userInfo", localStorage.getItem("loginUser"));
    xhr3.setRequestHeader("username",localStorage.getItem("token"))
    xhr3.send();
    xhr3.onreadystatechange = function () {
        if (xhr3.status === 200 && xhr3.readyState === 4) {
            var responseDataList = JSON.parse(xhr3.responseText);
            var count = 1;
            for (const responseDataListElement of responseDataList) {
                var data = responseDataListElement.data;
                // 动态添加帖子列表
                var oneDiv = document.createElement('div');//整个帖子模块的最外层
                oneDiv.classList.add("post")
                //吧名
                var twoDiv = document.createElement('div');
                var barNameTarget = document.createElement('a');
                barNameTarget.classList.add("barName")
                barNameTarget.href = "javascript:void(0);"
                barNameTarget.innerText = data.barName;

                //帖子名
                var postNameTarget = document.createElement('a');
                postNameTarget.href = "javascript:void(0);"
                postNameTarget.innerText = data.postName;
                oneDiv.appendChild(postNameTarget)
                //内容
                var contentDiv = document.createElement('div');
                var contentTag = document.createElement('p');
                contentDiv.appendChild(contentTag)

                //页面主体标签
                var bodyTag = document.getElementById("bodyContent");
                bodyTag.appendChild(oneDiv)
                oneDiv.appendChild(contentTag);
                oneDiv.appendChild(twoDiv);
                twoDiv.appendChild(barNameTarget);
                switch (responseDataListElement.message) {
                    case "picture":
                        //文本显示
                        contentTag.innerText = data.content
                        //显示图片
                        var myImg = document.createElement("img");
                        //写个class样式再去引用
                        // myImg.width = 100
                        // myImg.height = 100
                        myImg.src = data.filePath //遇到一个问题:文件的访问
                        contentTag.appendChild(myImg)
                        break
                    case "video":
                        //文本显示
                        contentTag.innerText = data.content
                        //显示视频
                        var videoElement = document.createElement("video");
                        videoElement.src = data.filePath
                        videoElement.controls
                        contentTag.appendChild(videoElement)
                        break
                    case "other":
                        //文本显示
                        contentTag.innerText = data.content
                        break
                }
                //页面跳转
                // var barNames = document.getElementsByClassName("barName");
                // for (const barName of barNames) {
                //     barName.onclick = function (){
                //         alert("点击事件")
                //
                //         // localStorage.setItem("viewBarName",barName.innerHTML)
                //         // alert("跳转的贴吧的吧名：" + localStorage.getItem("viewBarName"))
                //         window.location.href = 'bar.html'
                //     }
                // }
                barNameTarget.onclick = function () {
                    var barName = barNameTarget.innerText;
                    alert("贴吧名字" + barName)
                    var xhr4 = new XMLHttpRequest();
                    xhr4.open("get", "http://localhost:9001/enterBar?barName=" + barName, true);
                    xhr4.setRequestHeader("Content-Type", "text/plain; charset=utf8");
                    xhr4.send();
                    xhr4.onreadystatechange = function () {
                        if (xhr4.status === 200 && xhr4.readyState === 4) {
                            // alert(xhr4.responseText)
                            localStorage.setItem("barInfo", xhr4.responseText)//持久化了json格式的数据
                            localStorage.setItem("viewBarName", barName)
                            window.location.href = "bar.html"
                        }
                    }
                }
            }
        }
    }

    // var xhr4 = new XMLHttpRequest();
    // xhr4.open("get","http://localhost:9005/getExpireTime",true);
    // xhr4.setRequestHeader("Content-Type", "text/plain; charset=utf8");
    // xhr4.setRequestHeader("userInfo", localStorage.getItem("loginUser"));
    // xhr4.send();
    // xhr4.onreadystatechange = function (){
    //     if (xhr4.status === 200 && xhr4.readyState === 4){
    //
    //     }
    // }

    //定期进行偏好设置
    var xhr2 = new XMLHttpRequest();
    xhr2.open("get", "http://localhost:9005/getExpireTime", true);
    xhr2.setRequestHeader("Content-Type", "text/plain; charset=utf8");
    // xhr2.setRequestHeader("userInfo", localStorage.getItem("loginUser"));
    xhr2.setRequestHeader("username",localStorage.getItem("token"))
    xhr2.send();
    xhr2.onreadystatechange = function () {
        if (xhr2.status === 200 && xhr2.readyState === 4) {
            //判断时间是否到期，到期就设置偏好并更新到期时间
            var date = new Date();
            var currentTime = date.getTime();
            console.log("当前时间：" + currentTime)
            var expireTime = JSON.parse(xhr2.responseText);
            console.log("下一次偏好设置时间：" + expireTime)
            if (currentTime >= expireTime) {
                xhr2.open("get", "http://localhost:9005/setExpireTime", false);
                xhr2.setRequestHeader("Content-Type", "text/plain; charset=utf8");
                // xhr2.setRequestHeader("userInfo", localStorage.getItem("loginUser"));
                xhr2.setRequestHeader("username",localStorage.getItem("token"))
                xhr2.send()
                window.location.href = "preference.html"
            }
        }
    }
})