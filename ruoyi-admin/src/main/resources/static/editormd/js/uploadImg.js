function initPasteDragImg(Editor){
    debugger
    var doc = document.getElementById(Editor.id)
    doc.addEventListener('paste', function (event) {
        var items = (event.clipboardData || window.clipboardData).items;
        var file = null;
        if (items && items.length) {
            // 搜索剪切板items
            for (var i = 0; i < items.length; i++) {
                if (items[i].type.indexOf('image') !== -1) {
                    file = items[i].getAsFile();
                    break;
                }
            }
        } else {
            console.log("当前浏览器不支持");
            return;
        }
        if (!file) {
            console.log("粘贴内容非图片");
            return;
        }
        uploadImg(file,Editor);
    });
    var dashboard = document.getElementById(Editor.id)
    dashboard.addEventListener("dragover", function (e) {
        e.preventDefault()
        e.stopPropagation()
    })
    dashboard.addEventListener("dragenter", function (e) {
        e.preventDefault()
        e.stopPropagation()
    })
    dashboard.addEventListener("drop", function (e) {
        e.preventDefault()
        e.stopPropagation()
        var files = this.files || e.dataTransfer.files;
        uploadImg(files[0],Editor);
    })
}
function uploadImg(file,Editor){
    debugger
    var formData = new FormData();
    var fileName=new Date().getTime()+"."+file.name.split(".").pop();
    formData.append('editormd-image-file', file, fileName);
    $.ajax({
        //配置图片上传的接口路径
        url: "/articleInfo/file/upload",
        type: 'post',
        data: formData,
        processData: false,
        contentType: false,
        dataType: 'json',
        success: function (msg) {
            var code=msg['success'];
            var height=msg['height'];
            var a = "\n";
            for (var i = 0; i < height/23; i++) {
                a += "\n";
            }
            if(code==1){
                var url=msg["url"];
                if(/\.(png|jpg|jpeg|gif|bmp|ico)$/.test(url)){
                    Editor.insertValue("![图片alt]("+url+" ''图片'')");
                    // Editor.insertValue("\n" + ''+ a +''+"")
                }else{
                    Editor.insertValue("[下载附件]("+url+")");
                }
            }else{
                console.log(msg);
                alert("上传失败");
            }
        },
        error: function () {
            console.log('请求失败');
        }
    });
}
