document.addEventListener('DOMContentLoaded', function () {
    //dom对象获取
    // var post1 = document.getElementById("post1");
    // var post2 = document.getElementById("post2");
    // var post3 = document.getElementById("post3");
    var posts = document.getElementsByClassName("post");

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
//页面内容显示
    var xhr = new XMLHttpRequest();
    xhr.open("post","http://localhost:9002/getRecentlyPublish" ,true);//异步请求user模块
    xhr.setRequestHeader("Content-Type", "application/json; charset=utf8");
    xhr.send(JSON.stringify(localStorage.getItem("token")));
    xhr.onreadystatechange = function (){
        if (xhr.status === 200 && xhr.readyState === 4){
           var postList = JSON.parse(xhr.responseText);
            for (let i = 0; i < postList.length; i++) {
                postNames[i].innerHTML = postList[i].postName//postName引的为什么是post.js的
                contents[i].innerHTML = postList[i].content
            }
        }
    }
//页面跳转逻辑
    //因为帖名可以重复，所以携带帖名到服务端
    // 然后跳转到帖子页面的时候携带帖子页面的相关信息过去
    for (let i = 0; i < posts.length; i++) {
        posts[i].onclick = function (){
            var postName = postNames[i].innerText;
            alert("帖子名字" + postName)
            xhr.open("get","http://localhost:9002/getPostInfo?postName=" + postName ,true);//异步请求post模块
            xhr.setRequestHeader("Content-Type", "text/plain; charset=utf8");
            // xhr.setRequestHeader("userInfo",localStorage.getItem("loginUser"))
            xhr.setRequestHeader("username",localStorage.getItem("token"));
            xhr.send();
            xhr.onreadystatechange = function (){
                if (xhr.status === 200 && xhr.readyState === 4){
                    localStorage.setItem("postInfo",xhr.responseText)//持久化了json格式的数据
                    window.location.href = "post.html"
                }
            }
        }
    }
})