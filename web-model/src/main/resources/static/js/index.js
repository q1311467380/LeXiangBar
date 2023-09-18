/**
 * 页面加载完成异步请求获取要被推荐的帖子
 * 动态创建推荐的帖子
 * 绑定点击事件 点击增加浏览数
 */
document.addEventListener('DOMContentLoaded', function () {
    /*
    7天免登录
    首先判断是否过期
     */
    console.log("过期时间" + parseInt(localStorage.getItem("loginExpireTime"),10))
    console.log("当前时间" + new Date().getTime())
    if (new Date().getTime() < parseInt(localStorage.getItem("loginExpireTime"),10)){
        console.log("没过期，自动登录")
       //没过期
        let user = {
            username : localStorage.getItem("usernameAt"),
            password : localStorage.getItem("passwordAt"),
        }
        var xhr5 = new XMLHttpRequest();
        xhr5.open("post", "http://localhost:9000/login", true);//异步请求user模块
        xhr5.setRequestHeader("Content-Type", "application/json; charset=utf8");
        xhr5.setRequestHeader("loginSalt",localStorage.getItem("loginSalt"))
        xhr5.send(JSON.stringify(user));
        xhr5.onreadystatechange = function () {
            if (xhr5.status === 200 && xhr5.readyState === 4) {
                window.location.href = 'afterLogin.html';
            }
        }

    }else {
        //登录逻辑加盐，如果是免登录就不用这一步了，用之前的token（uuid）
        var xhr4 = new XMLHttpRequest();
        xhr4.open("get", "http://localhost:9000/loginAddSalt", true);//异步请求user模块
        xhr4.setRequestHeader("Content-Type", "application/json; charset=utf8");
        xhr4.send();
        xhr4.onreadystatechange = function () {
            if (xhr4.status === 200 && xhr4.readyState === 4) {
                console.log("服务端缓存的客户端信息 : " + xhr4.responseText)
                window.localStorage.setItem("loginSalt", xhr4.responseText);
            }
        }
    }


    var barName1 = document.getElementById("bar1");
    var barName2 = document.getElementById("bar2");
    var barName3 = document.getElementById("bar3");
    var barNames = [
        barName1,
        barName2,
        barName3
    ]
    var postName1 = document.getElementById("postName1");
    var postName2 = document.getElementById("postName2");
    var postName3 = document.getElementById("postName3");
    var postNames = [
        postName1,
        postName2,
        postName3
    ]

    var content1 = document.getElementById("content1");
    var content2 = document.getElementById("content2");
    var content3 = document.getElementById("content3");
    var contents = [
        content1,
        content2,
        content3
    ]

    var xhr = new XMLHttpRequest();
    xhr.open("get", "http://localhost:9005/beforeLoginRecommendPost", true);//异步请求user模块
    xhr.setRequestHeader("Content-Type", "text/plain; charset=utf8");
    xhr.send();
    xhr.onreadystatechange = function () {
        if (xhr.status === 200 && xhr.readyState === 4) {
            //返回的内容包括： 贴吧名 帖子名 帖子内容
            //返回List<ResponseData<Post>> 帖子内容类别怎么办--在message中表明
            var responseDataList = JSON.parse(xhr.responseText);
            for (let i = 0; i < responseDataList.length; i++) {
                var data = responseDataList[i].data;
               barNames[i].innerText = data.barName;
               postNames[i].innerText = data.postName;
                switch (responseDataList[i].message) {
                    case "picture":
                        // alert("ok")
                        //文本显示
                        contents[i].innerText = data.content
                        //显示图片
                        var myImg = document.createElement("img");
                        //写个class样式再去引用
                        // myImg.width = 100
                        // myImg.height = 100
                        myImg.src = data.filePath //遇到一个问题:文件的访问
                        contents[i].appendChild(myImg)
                        break
                    case "video":
                        // alert("ok2")
                        //文本显示
                        contents[i].innerText = data.content
                        //显示视频
                        var videoElement = document.createElement("video");
                        videoElement.src = data.filePath
                        videoElement.controls
                        contents[i].appendChild(videoElement)
                        break
                    case "other":
                        // alert("ok3")
                        //文本显示
                        contents[i].innerText = data.content
                        break
                }
            }

                // // 动态添加帖子列表
                // var oneDiv = document.createElement('div');//整个帖子模块的最外层
                // oneDiv.classList.add("post")
                // //吧名
                // var twoDiv = document.createElement('div');
                // var barNameTarget = document.createElement('a');
                // barNameTarget.href = "javascript:void(0);"
                // barNameTarget.innerText = data.barName;
                //
                // //帖子名
                // var postNameTarget = document.createElement('a');
                // postNameTarget.href = "javascript:void(0);"
                // postNameTarget.innerText = data.postName;
                // oneDiv.appendChild(postNameTarget)
                // //内容
                // var contentDiv = document.createElement('div');
                // var contentTag = document.createElement('p');
                // contentDiv.appendChild(contentTag)
                //
                // //页面主体标签
                // var bodyTag = document.getElementById("bodyContent");
                // bodyTag.appendChild(oneDiv)
                // oneDiv.appendChild(contentTag);
                // oneDiv.appendChild(twoDiv);
                // twoDiv.appendChild(barNameTarget);

            //点击跳转到帖吧，并吧贴吧名持久化在前端，使得跳转后可以显示指定贴吧
            for (const bar of barNames) {
                bar.onclick = function (){
                    localStorage.setItem("viewBarName",bar.innerText)
                    window.location.href = "bar.html"
                    //修改失败也不能影响用户的浏览
                    alert("浏览数：")
                }
            }
            //点击跳转,并增加帖子的浏览量，但是因为没登录所有只加该帖子的全部访问量不加个人的
            for (const post of postNames) {
                post.onclick = function (){
                    localStorage.setItem("viewPostName",post.innerText);
                    // alert("浏览帖子：" + localStorage.getItem("viewPostName"))
                    var xhr3 = new XMLHttpRequest();
                    xhr3.open("get", "http://localhost:9005/thePostViewNumber", true);//异步请求user模块
                    xhr3.setRequestHeader("Content-Type", "text/plain; charset=utf8");
                    xhr3.setRequestHeader("viewPostName",localStorage.getItem("viewPostName"))
                    xhr3.send();
                    // xhr3.onreadystatechange = function () {
                    //     if (xhr.status === 200 && xhr.readyState === 4) {
                    //         // alert("浏览数：" + JSON.parse(xhr3.responseText))//浏览数正确得到了，后面再考虑放哪吧
                    //     }
                    // }
                }
            }

        }
    }
})
