var positionArr=[];
var deptArr=[];
jQuery(function($) {
	$("#sector span").bind("click",bindDeptClick);
	$("#jobs span").bind("click",bindPositionClick);
	$("#sex span").click(function(){
		$(this).addClass("cur").siblings().removeClass();
		$("#sex1").val(this.innerHTML);
	});
	
	$("#zz span").click(function(){
		$(this).addClass("cur").siblings().removeClass();
		if(this.innerHTML=="在职")
			$("#ifOnJob").val(0);
		else
			$("#ifOnJob").val(1);
	});
	
	$.validator.setDefaults(Core.Validator.Options2);
	
	$("#deptButton").bind("click",addDept);
	$("#positionButton").bind("click",addPosition);
	
	$("#sub").bind("click",submitForm);
	
	$("#commit").bind("click", function(){
		$("#sub").click();
	});
	var positions = $("#positionArr").val();
	if(positions!=""){
		positions = positions.substring(0,positions.lastIndexOf(','));
		positionArr = positions.split(",");
	}
	var depts = $("#deptArr").val();
	if(depts!=""){
		depts = depts.substring(0,depts.lastIndexOf(','));
		deptArr = depts.split(",");
	}
	
//	$("#file-1").bind("change",showPhoto);
});

//绑定部门点击事件
function bindDeptClick(){
	$("#sector span").bind("click");
	if($(this).attr("name")=="entDepartmentStr"){
		$(this).attr("name","");
		$(this).removeClass(); 
		deptArr.splice(deptArr.indexOf(this.innerHTML),1);
	}else{
		$(this).attr("name","entDepartmentStr");
		$(this).addClass("cur");
		deptArr.push(this.innerHTML);
	}
}
//绑定职位点击事件
function bindPositionClick(){
	if($(this).attr("name")=="entPositionStr"){
		$(this).attr("name","");
		$(this).removeClass(); 
		positionArr.splice(positionArr.indexOf(this.innerHTML),1);
	}else{
		$(this).attr("name","entPositionStr");
		$(this).addClass("cur");
		positionArr.push(this.innerHTML);
	}
}	
//增加部门
function addDept(){
	var newDept = $("#newDept").val();
	if($.trim(newDept)==""){
		Core.PopUtil.warn("请先输入新部门！");
		return;
	}
	if(newDept.length>10){
		Core.PopUtil.warn("部门最大长度为10个字节！");
		return;
	}
	var iftrue = true;
	$("#sector").children().each(function(i,n){
     var obj = $(n)
     if(obj.html()==newDept){
     	iftrue = false;
     }
    });
     if(iftrue){
     	$("#sector").append(' <span>'+newDept+'</span>');
		$("#sector span").unbind("click");
		$("#sector span").bind("click",bindDeptClick);
		$("#newDept").val("");
    }else{
    	Core.PopUtil.warn("部门已经存在！");
    }
}

//增加职位
function addPosition(){
	var newPosition = $("#newPosition").val();
	if($.trim(newPosition)==""){
		Core.PopUtil.warn("请先输入新职位！");
		return;
	}
	if(newPosition.length>10){
		Core.PopUtil.warn("职位最大长度为10个字节！");
		return;
	}
	var iftrue = true;
	$("#jobs").children().each(function(i,n){
     var obj = $(n)
     if(obj.html()==newPosition){
     	iftrue = false;
     	
     }
    });
    if(iftrue){
    	$("#jobs").append(' <span>'+newPosition+'</span>');
		$("#jobs span").unbind("click");
		$("#jobs span").bind("click",bindPositionClick);
		$("#newPosition").val("");
    }else{
    	Core.PopUtil.warn("职位已经存在！");
    }
}

//提交表单
function submitForm(){
	var dateStr = $("#entEntryDate").val();
	if(dateStr==''){
		Core.PopUtil.warn("入职年月不能为空！");
		return false;
	}
	var now = new Date().getTime();//当前时间戳
	var time = new Date(dateStr).getTime();
	
	if(time>=now){
		Core.PopUtil.warn("入职年月不能大于当前时间！");
		return false;
	}
	
	if(deptArr.toString()==''){
		Core.PopUtil.warn("请先选择对应的部门！");
		return false;
	}
	if(deptArr.length>2){
		Core.PopUtil.warn("部门最多不能超过2个！");
		return false;
	}
	if(positionArr.toString()==''){
		Core.PopUtil.warn("请先选择对应的职位！");
		return false;
	}
	if(positionArr.length>3){
		Core.PopUtil.warn("职位最多不能超过3个！");
		return false;
	}
	$("#deptArr").val(deptArr.toString());
	$("#positionArr").val(positionArr.toString());
	$("#editForm").validate({
		ignore : ':hidden',
		rules : {
			trueName : {
				required : true,
				maxlength : 32
			},
			mobile : {
				required : true,
				mobile : true
			},
			email : {
				required : true,
				email:true
			},
			entEntryDate : {
				required : true
			},
			userIdcard : {
				idCardNo:true
			},
			ename : {
				onlyEnglish:true
			},
			nickName : {
				username:true
			},
			phone : {
				phoneOrMobile : true,
				maxlength : 16
			},
			fax : {
				phoneOrMobile : true,
				maxlength : 16
			},
			phoneShort : {
				onlyNumber : true,
				maxlength : 32
			}


		},
		messages : {
			trueName : {
				required : "真实姓名不能为空",
				maxlength : "真实名称长度不能超过32位"
			},
			mobile : {
				required : "手机号码不能为空"
			},
			email : {
				required : "邮箱地址不能为空"
			},
			entEntryDate : {
				required : "入职时间不能为空"
			},
			phone : {
				maxlength : "最大长度为16位"
			},
			fax : {
				maxlength : "最大长度为16位"
			},
			phoneShort : {
				maxlength : "最大长度为32位"
			}
		},

		submitHandler : function(f) {

			$(f).ajaxSubmit({
				url : "updateEmployee",
				type : 'post',
				success : function(result) {
					window.location.href = "card-search";
				}
			});
		}
	});
}

//function showPhoto() {  
//        var strSrc = $("#file-1").val();  
//        img = new Image();  
//        img.src = getFullPath(strSrc);  
//        //验证上传文件格式是否正确  
//        var pos = strSrc.lastIndexOf(".");  
//        var lastname = strSrc.substring(pos, strSrc.length)  
//        if (lastname.toLowerCase() != ".jpg") {  
//            alert("您上传的文件类型为" + lastname + "，图片必须为 JPG 类型");  
//            return false;  
//        }  
//        //验证上传文件宽高比例  
// 
//        if (img.height / img.width > 1.5 || img.height / img.width < 1.25) {  
//            alert("您上传的图片比例超出范围，宽高比应介于1.25-1.5");  
//            return;  
//        }  
//        //验证上传文件是否超出了大小  
//        if (img.fileSize / 1024 > 150) {  
//            alert("您上传的文件大小超出了150K限制！");  
//            return false;  
//        } 
//        $("#iconb").attr("src", getFullPath(this));
//} 
//
//function getFullPath(obj) {    //得到图片的完整路径  
//    if (obj) {  
//        //ie  
//        if (window.navigator.userAgent.indexOf("MSIE") >= 1) {  
//            obj.select();  
//            return document.selection.createRange().text;  
//        }  
//        //firefox  
//        else if (window.navigator.userAgent.indexOf("Firefox") >= 1) {  
//            if (obj.files) {  
//                return obj.files.item(0).getAsDataURL();  
//            }  
//            return obj.value;  
//        }  
//        return obj.value;  
//    }  
//} 



