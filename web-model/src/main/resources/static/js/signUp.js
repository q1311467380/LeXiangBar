document.addEventListener('DOMContentLoaded', function () {
    //获取一段随机字符串和用户密码拼接，起到混淆的效果
        var xhr = new XMLHttpRequest();
        xhr.open("get", "http://localhost:9000/signUpAddSalt", true);//异步请求user模块
        xhr.setRequestHeader("Content-Type", "application/json; charset=utf8");
        xhr.send();
        xhr.onreadystatechange = function () {
            if (xhr.status === 200 && xhr.readyState === 4) {
                console.log("服务端缓存的客户端信息 : " + xhr.responseText)
                window.localStorage.setItem("signUpSalt", xhr.responseText);
            }
        }
        var signUp = document.getElementById("signUp");
        signUp.onclick = function () {
            // 获取表单数据
            var username = document.getElementById('username').value;
            var password = document.getElementById('password').value;
            // 处理注册逻辑
            // 根据后端返回的结果进行处理
            let user = {
                username: username,
                password: md5(password) + "-" + localStorage.getItem("signUpSalt")
            }
            console.log('用户名:', username);
            console.log('密码:', md5(password));
            console.log('用户:', user);
            var xhr = new XMLHttpRequest();
            xhr.open("post", "http://localhost:9000/signUp", true);//异步请求user模块
            xhr.setRequestHeader("Content-Type", "application/json; charset=utf8");
            xhr.send(JSON.stringify(user));
            xhr.onreadystatechange = function () {
                if (xhr.status === 200 && xhr.readyState === 4) {
                    var responseText = xhr.responseText;
                    var responseData = JSON.parse(responseText);
                    switch (responseData.code) {
                        case 11002://注册失败，可以刷新一下页面用户名密码栏清空，看需求吧
                            alert("用户名已存在")
                            window.location.href = 'signUp.html';
                            break;
                        case 11001://注册成功直接跳转到afterLogin（登录后界面）,登记注册时间到redis的set对象中，用于进行一些定时任务的进行（如：定时设置偏好）
                            alert("注册成功");//可以试试在框的后面提示，不用弹窗
                            // localStorage.setItem("userInfo",JSON.stringify(responseData.data))
                            window.location.href = 'index.html';
                            break;
                    }
                }
            }
            // 清空表单
            var signUpForm = document.getElementById("signUpForm");
            signUpForm.reset();
        }
    }
)
;








