/**
 * 两部分
 * 我的关注--fansModel
 * 我的创建--postModel
 */
setTimeout(() => {//延迟执行事件，用全局事件监听应该也一样，可以后试试
    //我的创建
    //先不管图片的问题，照片问题可以根据贴吧的id匹配到对应头像，文件的上传和下载
    let barInfo = {
        barName: "kang",
        create: "描述"
    };
    var xhr = new XMLHttpRequest();
    xhr.open("get","http://localhost:9001/getMyCreate",true);//异步请求user模块
    xhr.setRequestHeader("Content-Type", "test/plain; charset=utf8");
    xhr.send();
    xhr.onreadystatechange = function (){
        if (xhr.status === 200 && xhr.readyState === 4){
            barInfo = JSON.parse(xhr.responseText)
        }
    }


    // 动态添加帖子列表
    const postsList = document.getElementById('posts');
    userInfo.posts.forEach(post => {
        //创建<li>标签挂载到<ul>父标签上
        const li = document.createElement('li');
        li.textContent = post;
        postsList.appendChild(li);
    });




}, 1000);