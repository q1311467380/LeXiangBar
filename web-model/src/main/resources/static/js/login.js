document.addEventListener('DOMContentLoaded', function() {
    // //加盐
    // var xhr = new XMLHttpRequest();
    // xhr.open("get", "http://localhost:9000/loginAddSalt", true);//异步请求user模块
    // xhr.setRequestHeader("Content-Type", "application/json; charset=utf8");
    // xhr.send();
    // xhr.onreadystatechange = function () {
    //     if (xhr.status === 200 && xhr.readyState === 4) {
    //         console.log("服务端缓存的客户端信息 : " + xhr.responseText)
    //         window.localStorage.setItem("loginSalt", xhr.responseText);
    //     }
    // }

    var login = document.getElementById('login');
    var modal = document.getElementById('myModal');
    var loginForm = document.getElementById('loginForm');

    // 打开模态框（登录弹窗）
    login.addEventListener('click', function() {
        modal.style.display = 'block';/*通过改变模态框的状态，看见/不可见*/
    });

    // 关闭模态框（登录弹窗）
    window.addEventListener('click', function(event) {
        if (event.target === modal) {
            modal.style.display = 'none';
        }
    });
    // 提交登录表单
    // loginForm.addEventListener('submit', function(event) {
    document.getElementById('btn').onclick = function() {
        // event.preventDefault(); // 阻止表单默认提交行为

        // 获取表单数据
        var username = document.getElementById('username').value;
        var password = document.getElementById('password').value;
        // 处理登录逻辑
        // 可以使用 AJAX 或其他方式将登录数据发送到后端进行验证
        // 根据后端返回的结果进行处理
        let user = {
            username : username,
            password : md5(password) + ":" + localStorage.getItem("loginSalt"),
        }
//         // 要加密的数据和密钥
//         var data = "Hello, world!";
//         var secretKey = "mySecretKey123";
//
// // AES 加密
//         var encryptedData = CryptoJS.AES.encrypt(data, secretKey).toString();
//
//         console.log("加密后的数据:", encryptedData);
//
// // AES 解密
//         var decryptedBytes = CryptoJS.AES.decrypt(encryptedData, secretKey);
//         var decryptedData = decryptedBytes.toString(CryptoJS.enc.Utf8);
//
//         console.log("解密后的数据:", decryptedData);

        console.log('用户名:', username);
        console.log('密码:', password);
        console.log('用户:', user);
        var xhr = new XMLHttpRequest();
        xhr.open("post","http://localhost:9000/login",true);//异步请求user模块
        xhr.setRequestHeader("Content-Type", "application/json; charset=utf8");
        xhr.setRequestHeader("loginSalt",localStorage.getItem("loginSalt"))
        xhr.send(JSON.stringify(user));
        xhr.onreadystatechange = function (){
            if (xhr.status === 200 && xhr.readyState === 4){
                /*
                * 根据返回的响应体决定前端的行为：是用户名错误或不存在、密码错误、还是登录成功
                * 成功转跳到afterlogin.html页面
                *
                * 根据自定义的请求头loginState属性的值
                * ”1“ -- 用户名密码都对
                * ”2“--用户名对，密码错
                * ”3“--用户名错*/

                var responseText = xhr.responseText;
                var responseData = JSON.parse(responseText);
                var code = responseData.code;
                // alert(responseText);
                // alert(responseData.code + "---" + responseData.message + "---" + responseData.data);
                // alert("状态码：" + code)
                switch (code){
                    case 10000:
                        alert("无法访问")
                    case 10001://跳转到afterLogin页面并把用户的相关信息传过去
                        alert("登录成功")
                        localStorage.setItem("token", JSON.stringify(responseData.data));
                        console.log("token:" +localStorage.getItem("token"))
                        //登录成功存储账号密码指定时间实现指定时间内免登录
                        localStorage.setItem("usernameAt",username)
                        localStorage.setItem("passwordAt",user.password)
                        //设置过期时间
                        localStorage.setItem("loginExpireTime",(new Date().getTime() + 7 * 24 * 60 * 60 * 1000).toString())
                        // console.log("expireTime" + localStorage.getItem("loginExpireTime"))
                        // _______________________________
                        // _________________________________________
                        // localStorage.setItem("loginUser", JSON.stringify(responseData.data));//这种方式会导致敏感信息暴露
                        // alert("--------------------------------------------- : " + localStorage.getItem("loginUser"))

                        // _________________________________________________________________________

                        //可以试试下面这种方法转跳页面，才能携带数据
                        // xhr.open("get",'afterLogin.html',false);//同步请求
                        // xhr.setRequestHeader("Content-Type", "text/plain; charset=utf8")
                        // xhr.send();
                        window.location.href = 'afterLogin.html';
                        break;
                    case 10002://提示密码错误
                        alert("密码错误");//可以试试在框的后面提示，不用弹窗
                        break;
                    case 10003://提示用户名错误
                        alert("用户名错误");
                        break;
                }

            }
        }
        // 清空表单
        loginForm.reset();
        // 关闭模态框
        modal.style.display = 'none';
    }
});







