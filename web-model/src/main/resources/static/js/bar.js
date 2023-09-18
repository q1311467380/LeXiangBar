document.addEventListener('DOMContentLoaded', function () {
    //贴吧页面的内容的动态显示
    var viewBarName = localStorage.getItem("viewBarName");
    alert("reBarName:" + viewBarName)
    document.getElementById("barName").innerText = viewBarName;
    document.getElementById("barName2").innerText = viewBarName;
    var body = document.getElementById('body');
    //根据贴吧的名字进行推荐内容的显示
    var xhr3 = new XMLHttpRequest();
    xhr3.open("get", "http://localhost:9005/getBarRecommend?barName=" + viewBarName, true);
    xhr3.setRequestHeader("Content-Type", "text/plain; charset=utf8")
    xhr3.send();
    xhr3.onreadystatechange = function () {
        if (xhr3.status === 200 && xhr3.readyState === 4) {
            var responseDataList = JSON.parse(xhr3.responseText);

            for (const responseDataListElement of responseDataList) {
                var data = responseDataListElement.data;
                //帖子内容区块
                var oneDiv = document.createElement('div');//整个帖子模块的最外层
                oneDiv.classList.add("post")
                body.appendChild(oneDiv)
                //帖子名
                var postNameTarget = document.createElement('a');
                postNameTarget.href = "javascript:void(0);"
                postNameTarget.innerText = data.postName;
                oneDiv.appendChild(postNameTarget)
                //内容
                var contentDiv = document.createElement('div');
                var contentTag = document.createElement('p');
                contentDiv.appendChild(contentTag)
                oneDiv.appendChild(contentTag);
                var br = document.createElement('br');
                body.appendChild(br)
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
                postNameTarget.onclick = function (){
                    var xhr4 = new XMLHttpRequest();
                    xhr4.open("get","http://localhost:9002/getPostInfo?postName=" + postNameTarget.innerText ,true);//异步请求post模块
                    xhr4.setRequestHeader("Content-Type", "text/plain; charset=utf8");
                    xhr4.setRequestHeader("username",localStorage.getItem("token"))
                    xhr4.send();
                    xhr4.onreadystatechange = function (){
                        if (xhr4.status === 200 && xhr4.readyState === 4){
                            localStorage.setItem("postInfo",xhr4.responseText)//持久化了json格式的数据
                            window.location.href = "post.html"
                        }
                    }
                }
            }
        }
    }



// 关注
// 还没完成27行
            var attention = document.getElementById("attention");
            attention.onclick = function () {
                var xhr = new XMLHttpRequest();
                xhr.open("get", "http://localhost:9004/newAttention", true);
                xhr.setRequestHeader("Content-Type", "text/plain; charset=utf8")
                xhr.setRequestHeader("userInfo", localStorage.getItem("loginUser"))
                xhr.setRequestHeader("barInfo", localStorage.getItem("barInfo"))
                //还得拿到吧名和用户名一起传到服务端
                xhr.send();
                xhr.onreadystatechange = function () {
                    if (xhr.status === 200 && xhr.readyState === 4) {
                        var responseText = xhr.responseText;
                        alert(responseText)
                        switch (responseText) {
                            case "1"://跳转到after页面并把用户名传过去
                                alert("关注成功")//优化为颜色按钮变暗
                                var attention = document.getElementById("attention");
                                attention.value = "取消关注"
                                break;
                            case "0":
                                alert("取消关注");
                                var attention = document.getElementById("attention");
                                attention.value = "关注"
                                break;
                            case "2":
                                alert("操作失败请重试");
                        }
                    }
                }
            }

            //发帖
            var publish = document.getElementById("publish");
            // 提交发帖表单
            publish.onclick = function () {
                // 获取表单数据
                var postName = document.getElementById('post-title').value;
                var content = document.getElementById('post-content').value;
                var uploadFile = document.getElementById("uploadFile").files[0];
                //
                // console.log('帖名:', postName);
                // console.log('帖子内容:', content);
                // console.log('帖子附件:', uploadFile);
                const publishForm = document.getElementById("publish-form");
                const formData = new FormData();
                formData.append("uploadFile", uploadFile)
                formData.append("title", postName)
                formData.append("content", content)
                let xhr2 = new XMLHttpRequest();
                // xhr2.open("get", "http://localhost:9002/publishPost?"+"upLoadFile=" + uploadFile + "title=" + postName + "&content=" + content , true);//异步请求user模块
                xhr2.open("post", "http://localhost:9002/publishPost", true)
                // xhr2.setRequestHeader("Content-Type", "multipart/form-data; charset = utf8");
                xhr2.setRequestHeader("barInfo", localStorage.getItem("barInfo"))
                xhr2.setRequestHeader("userInfo", localStorage.getItem("loginUser"))
                xhr2.send(formData);
                xhr2.onreadystatechange = function () {
                    alert("ready")
                    if (xhr2.status === 200 && xhr2.readyState === 4) {
                        alert("ok")
                        var responseText = xhr2.responseText;
                        switch (responseText) {
                            case "0"://创建失败
                                alert("创建失败")
                                window.location.href = 'signUp.html';
                                break;
                            case "1"://创建成功
                                alert("创建成功");
                                window.location.href = 'afterLogin.html';
                                break;
                        }
                    }
                }
            }
        }
    )


