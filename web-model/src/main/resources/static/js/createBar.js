document.addEventListener('DOMContentLoaded', function () {
    var createForm = document.getElementById('crate-bar-form');

    // 提交创建表单
    // createForm.addEventListener('submit', function(event) {
    //     event.preventDefault(); // 阻止表单默认提交行为
    document.getElementById('createNewBar').onclick = function () {
        // 获取表单数据
        var userId = document.getElementById("user_id").value;
        var barName = document.getElementById('title').value;
        var description = document.getElementById('description').value;
        var kindName = null;
        //获取下拉框中被选中的信息
        let myOptions = document.getElementsByClassName('myOptions');
        console.log(myOptions)
        for (const myOption of myOptions) {
            if (myOption.selected) {
                alert(myOption.value);
                kindName = myOption.value;
            }
        }

        // 处理创建逻辑
        // 可以使用 AJAX 或其他方式将登录数据发送到后端进行验证
        // 根据后端返回的结果进行处理
        let bar = {
            barName: barName,
            kindName: kindName,
            userId: userId,
            description: description
        }
        console.log('吧名:', barName);
        console.log('描述:', description);
        console.log('用户ID:', userId);
        console.log('贴吧的类别:', kindName);
        var xhr = new XMLHttpRequest();
        xhr.open("post", 'http://localhost:9001/createBar', true)//异步请求user模块
        xhr.setRequestHeader("Content-Type", "application/json; charset=utf8");
        xhr.send(JSON.stringify(bar));
        xhr.onreadystatechange = function () {
            if (xhr.status === 200 && xhr.readyState === 4) {
                var responseText = xhr.responseText;
                switch (responseText) {
                    case "0"://创建失败
                        alert("创建失败")
                        window.location.href = 'createBar.html';
                        break;
                    case "1"://创建成功直接跳转到该吧的界面
                        alert("创建成功");//可以试试在框的后面提示，不用弹窗
                        window.location.href = 'concern.html';
                        break;
                    case "2"://该吧名已存在
                        alert("该吧名已存在");//可以试试在框的后面提示，不用弹窗
                        window.location.href = 'createBar.html';
                        break;
                }
            }
        }
        // 清空表单
        createForm.reset();
    }
});