var positionArr=[];
var deptArr=[];
jQuery(function($) {
	$("#sector span").bind("click",bindDeptClick);
	$("#jobs span").bind("click",bindPositionClick);
	$("#sex span").click(function(){
		$(this).addClass("cur").siblings().removeClass();
		$("#sex1").val(this.innerHTML);
	});
	$.validator.setDefaults(Core.Validator.Options2);
	
	$("#deptButton").bind("click",addDept);
	$("#positionButton").bind("click",addPosition);
	$("#sub").bind("click",submitForm);
	
	$("#commit").bind("click",checkcardMobile);
	
	
	
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

//验证手机号是否被注册过
function checkcardMobile(){
	var mobile = $('#mobile').val();
	if(mobile==''){
		Core.PopUtil.warn("手机号码不能为空！");
		return false;
	}
	$.get("checkcardMobile",{mobile:$('#mobile').val()},function(data){
		if(data){
			$("#sub").click();
		}else{
			Core.PopUtil.warn("该手机号码已经被绑定,请更换！");
			return false;
		}
	});
	return false;
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
	$("#addForm").validate({
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
				required : "邮箱地址不能为空",
				email: "邮箱格式不正确"
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
				url : "addNewEmployee",
				type : 'post',
				success : function(result) {
					window.location.href = "card-search";
				}
			});
		}
	});
}




