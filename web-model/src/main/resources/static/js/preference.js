//获得选中的选项的值，插入到偏好表中
document.addEventListener('DOMContentLoaded', function () {
    var preference = document.getElementById("preference-bt");
    var form = document.getElementById("preference-form");
    let tempArr = [];

    //限制用户选且只能选3个偏好
    let hobbyElements = document.getElementsByClassName('hobbyClass');
    for (const hobbyElement of hobbyElements) {
        hobbyElement.flag = true
        hobbyElement.onclick = function () {

            if (hobbyElement.flag) {
                tempArr.push(hobbyElement)
            } else {
                tempArr.pop()
            }
            hobbyElement.flag = !hobbyElement.flag
            if (tempArr.length >= 3) {
                for (const hobbyElement of hobbyElements) {
                    if (hobbyElement.flag) {
                        hobbyElement.setAttribute("disabled", "disabled")
                    }
                }
            }else{
                for (const hobbyElement of hobbyElements) {
                    hobbyElement.removeAttribute("disabled");
                }
            }

            // if (hobbyElement.flag) {
            //     tempArr.push(hobbyElement);
            // } else {
            //     tempArr.pop();
            //     for (const hobbyElement of hobbyElements) {
            //         hobbyElement.removeAttribute("disabled");
            //     }
            // }
            // hobbyElement.flag = !hobbyElement.flag;
            //
            // if (tempArr.length === 3) {
            //     for (const hobbyElement of hobbyElements) {
            //         if (hobbyElement.flag) {
            //             hobbyElement.setAttribute("disabled", "disabled")
            //         }
            //     }
            // }
        }
    }
    //获取选中的偏好写入数据库
    preference.onclick = function (){
        //获取被选中的复选框的value值
        const checkboxes = form.querySelectorAll('input[type="checkbox"]:checked');
        const selectedPreferences = [];
        checkboxes.forEach(function(checkbox) {
            selectedPreferences.push(checkbox.value);
        })
        console.log('Selected preferences:', selectedPreferences);

        //上面拿到的数据写入数据库中
        //先写后端看看需要前端传什么数据
        var xhr = new XMLHttpRequest();
        xhr.open("post","http://localhost:9005/setPreference",true);//异步请求user模块
        xhr.setRequestHeader("Content-Type", "application/json; charset=utf8");
        xhr.setRequestHeader("loginSalt",localStorage.getItem("loginSalt"))
        xhr.setRequestHeader("username", localStorage.getItem("token"));
        xhr.send(JSON.stringify(selectedPreferences));
        xhr.onreadystatechange = function (){
            if (xhr.status === 200 && xhr.readyState === 4) {
                //需不需要传该用户的偏好信息，看后面逻辑
                window.location.href = 'afterLogin.html'
            }
        }
    }
})