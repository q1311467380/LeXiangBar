document.addEventListener('DOMContentLoaded', function () {

    alert(localStorage.getItem("postInfo"))
    //获取帖子标题元素
    var title = document.getElementById("title");
    //获取帖子作者名元素
    var author = document.getElementById("author");
    //获取帖子内容元素
    var content = document.getElementById("content");
    //获取帖子内容父元素
    var postContent = document.getElementById("post-content");
    // 获取评论表单元素
    const commentForm = document.getElementById('comment-form');
    // 获取作者姓名输入框元素
    const authorInput = document.getElementById('author-input');
// 获取评论内容输入框元素
    const contentInput = document.getElementById('content-input');

    //根据myPost（上一个页面传过来的相关信息，进行页面内容的显示）
    var postInfo = JSON.parse(localStorage.getItem("postInfo"));//帖子页面到达相关信息
    var data = postInfo.data;//记录帖子页面的相关内容,封装在user对象中
    var postsList = data.postsList;//这里发现了一个问题,就是一个人需要只能发送一个相同帖子名的帖子,帖子需要唯一,发帖的部分逻辑得改了
    //上面那个问题改了后,该post集合就只有一个元素了
    for (const postsListElement of postsList) {
        title.innerText = postsListElement.postName
        author.innerText = postsListElement.username
        var type = postInfo.message;//记录帖子的内容类型
        //三种情况，文本+图片、文本+视频、文本
        switch (type) {
            case "picture":
                alert("ok")
                //文本显示
                content.innerText = postsListElement.content
                //显示图片
                var myImg = document.createElement("img");
                //写个class样式再去引用
                // myImg.width = 100
                // myImg.height = 100
                myImg.src = postsListElement.filePath //遇到一个问题:文件的访问
                postContent.appendChild(myImg)
                break
            case "video":
                alert("ok2")
                //文本显示
                content.innerText = postsListElement.content
                //显示视频
                var videoElement = document.createElement("video");
                videoElement.src = postsListElement.filePath
                videoElement.controls
                postContent.appendChild(videoElement)
                break
            case "other":
                alert("ok3")
                //文本显示
                content.innerText = postsListElement.content
                break
        }

    }

    //评论区内容展示
    var commentsList = data.commentsList;
    for (const commentsListElement of commentsList) {
        // 创建新的评论元素
        const newComment = document.createElement('div');
        newComment.classList.add('comment');
        var commentAuthor = commentsListElement.username;
        var commentContent = commentsListElement.content;
        newComment.innerHTML = `
        <p class="comment-author">${commentAuthor}</p>
        <p>${commentContent}</p>
      `;
        // 将新的评论添加到评论区域
        const commentsSection = document.querySelector('.container');
        commentsSection.insertBefore(newComment, commentForm);
    }

    //发评论
    var publishComment = document.getElementById("publishComment");
    publishComment.onclick = function () {
        //把发布的评论写入表中，再插入新的评论到评论区中（暂时先不考虑评论的内容审核，还需要考虑评论过多，页面展示的问题，是翻页还是下拉（下拉的话，发帖部分得相对浏览器窗口定位））
        //需要什么数据：username、postName(title的值就是了)、content
        var username = document.getElementById("author-input").value;
        var publishContent = document.getElementById("content-input").value;
        var commentPost = title.innerText
        var comment = {
            username: username,
            content: publishContent,
            postName: commentPost
        }
        var xhr = new XMLHttpRequest();
        xhr.open("post", "http://localhost:9003/publishComment", true)
        xhr.setRequestHeader("Content-Type", "application/json; charset= utf8")
        xhr.setRequestHeader("viewBarName",localStorage.getItem("viewBarName"))
        xhr.send(JSON.stringify(comment))
        xhr.onreadystatechange = function () {
            if (xhr.status === 200 && xhr.readyState === 4) {
                switch (xhr.responseText) {
                    case "1":
                        // 创建新的评论元素
                        const newComment = document.createElement('div');
                        newComment.classList.add('comment');
                        newComment.innerHTML = `
                        <p class="comment-author">${username}</p>
                        <p>${publishContent}</p>
                        `;

                        // 将新的评论添加到评论区域
                        const commentsSection = document.querySelector('.container');
                        commentsSection.insertBefore(newComment, commentForm);
                        break
                    case "0":
                        alert("评论失败")
                        break
                }

            }
        }
        // 清空输入框内容
        authorInput.value = '';
        contentInput.value = '';
    }
})