//动态页面内容显示
setTimeout(() => {//其实也无需延时，代码的执行是顺序的，加载完成后才会进行映射
    //可以部分映射吗？还是要全部字段----正常情况下并不需要自己定义一个js对象进行接收响应体数据，除非有特殊需求可能需要
    // let post = {
    //     postName: "1"
    // }
    // let userInfo = {
    //     username: "John Doe",
    //     email: "johndoe@example.com",
    //     avatar: "picture/logo/tag1.png",
    //     bio: "Hello, I'm John Doe. Nice to meet you!",
    //     posts: [
    //         post,
    //         post,
    //         post
    //     ]
    // };

    var xhr = new XMLHttpRequest();
    xhr.open("get","http://localhost:9000/myInfo",true);//异步请求user模块
    xhr.setRequestHeader("Content-Type", "text/plain; charset=utf8");
    // xhr.setRequestHeader("userInfo", localStorage.getItem("loginUser"));
    xhr.setRequestHeader("username",localStorage.getItem("token"))
    xhr.send();
    xhr.onreadystatechange = function (){
        if (xhr.status === 200 && xhr.readyState === 4) {
            var userInfo = JSON.parse(xhr.responseText);
            console.log(userInfo)
            // 更新用户信息
            document.getElementById('username').innerHTML = userInfo.username;
            document.getElementById('email').innerHTML = userInfo.email;
            // document.getElementById('avatar').src = userInfo.avatar;
            // document.getElementById('bio').textContent = userInfo.bio;

            // 动态添加帖子列表
            const newPostsList = document.getElementById('posts');
            userInfo.postsList.forEach(post => {
                //创建<li>标签挂载到<ul>父标签上

                const li = document.createElement('li');
                const a = document.createElement('a');
                a.href = "javascript:void(0);"
                a.classList.add("myInfoPost")
                a.textContent = post.postName;//显示帖子的名字
                li.appendChild(a);
                newPostsList.appendChild(li);
            });
        }
        //页面跳转逻辑
        //因为帖名可以重复，所以携带帖名到服务端
        // 然后跳转到帖子页面的时候携带帖子页面的相关信息过去
        var myInfoPost = document.getElementsByClassName("myInfoPost");
        for (let i = 0; i < myInfoPost.length; i++) {
            myInfoPost[i].onclick = function (){
                var postName = myInfoPost[i].innerText;
                alert("帖子名字" + postName)
                xhr.open("get","http://localhost:9002/getPostInfo?postName=" + postName ,true);//异步请求post模块
                xhr.setRequestHeader("Content-Type", "text/plain; charset=utf8");
                // xhr.setRequestHeader("userInfo",localStorage.getItem("loginUser"))
                xhr.setRequestHeader("username",localStorage.getItem("token"))
                xhr.send();
                xhr.onreadystatechange = function (){
                    if (xhr.status === 200 && xhr.readyState === 4){
                        localStorage.setItem("postInfo",xhr.responseText)//持久化了json格式的数据
                        window.location.href = "post.html"
                    }
                }
            }
        }
    }

}, 1000);