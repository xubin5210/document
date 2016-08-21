jQuery(function($) {
	
	$("#phonecode").bind("click",checkPhone);
	$("#cb").bind("click",readMes);
	$("#loginNow").bind("click",loginNow);
	$("#nextButton").bind("click",checkCode);
	readMes();
	//验证码切换
	_imgVcode = $("#vcodeimage");
	var validateCodeApiUrl = _server + "/validateCode?height=32&d=";

	_imgVcode.attr('src',validateCodeApiUrl).click(function() {
		this.src = validateCodeApiUrl	+ new Date().getTime();
	});
	$("#vcodeimage").click(function(){
		var validateCodeApiUrl2 = _server + "/validateCode?height=32&d=" + new Date().getTime();
		_imgVcode.attr('src',validateCodeApiUrl2);
	});
 
});

//立即登录
function loginNow(){
	window.location.href = "index";
}
//验证验证码
function checkCode(){
	//手机号码
	var phone = $("#phone").val();
	if(phone==""){
		Core.PopUtil.warn("请先输入手机号码！");
		return;
	}
	if(!phone.match(/(1[3-9]\d{9}$)/)){
		Core.PopUtil.warn("请输入正确格式的手机号码！");
		return;
	}
	
	var code = $("#code").val();
	if(code==""){
		Core.PopUtil.warn("请输入验证码！");
		return;
	}
	var url = "validatePhone";
	$.get(url,{code:code},function(data){
		if(data.data){
			submitForm(phone);//为真 则提交
		}else{
			Core.PopUtil.warn("验证码错误！");
		}
	});
}

//验证且提交表单
function submitForm(phone){
	//密码
	var pwd = $("#pwd").val();
	if(pwd==""){
		Core.PopUtil.warn("请输入密码！");
		return;
	}
    
    	
	if(!((pwd.match(/[\d]/) && pwd.match(/[A-Za-z]/) && pwd.match(/[~!@#$%^&*()_+.;'<>,^]/)) || (pwd.match(/[\d]/) && pwd.match(/[A-Za-z]/)) || (pwd.match(/[\d]/)  && pwd.match(/[~!@#$%^&*()_+.;'<>,^]/)))){
		Core.PopUtil.warn("密码由字母、数字或者英文符号任意两种组合，最短6位，区分大小写！");
		return;
	}
	
	//确认密码
	var repwd = $("#repwd").val();
	if(repwd==""){
		Core.PopUtil.warn("请再次输入密码！");
		return;
	}
	
	if(pwd!=repwd){
		Core.PopUtil.warn("两次密码输入不正确！请重新输入！");
		$("#pwd").val("");
		$("#repwd").val("");
		return;
	}
	var parameter = {mobile:phone,pwd:Core.DigestUtils.sha256(pwd),id:$("#entId").val()};
	$.post("regist",parameter,function(data){
		if(data){
			Core.UrlUtil.redirect("regist-second?mobile="+phone);
		}else{
			Core.PopUtil.warn("注册失败！");
		}
	});
	
}

//是否已经阅读
function readMes(){
	if($('#cb').is(':checked')) {
		$("#nextButton").css('background','#2d6eac');
		$('#nextButton').attr("disabled",false);
	}else{
		$("#nextButton").css('background','#999');
		$('#nextButton').attr("disabled",true);
	}
}

//验证手机号码是否存在
function checkPhone(){
	var phone=$("#phone").val();
	if(phone==""){
		Core.PopUtil.warn("请先输入手机号码！");
		return;
	}
	//正则表达式  
	if(!phone.match(/(1[3-9]\d{9}$)/)){
		Core.PopUtil.warn("请输入正确格式的手机号码！");
		return;
	}
	$.get("checkMobile",{mobile:phone},function(data){
		if(!data.data){
			getCode(phone);
		}else{
			Core.PopUtil.confirm("该号码已经存在！",function(){//如果存在则直接跳转到第二步
				Core.UrlUtil.redirect("regist-second?mobile="+phone);
			})
		}
	});
}


var intervalId;
//获取验证码
function getCode(phone){
	var url="getCode";
	var parameter={
				mobile:phone
				};

	$.post(url,parameter,function(data){
		if(data){
			//该处防止用户短时间内重复获取验证码，脚本+发送时间控制
			//(60秒后)重新获取验证码
			$("#phonecode").css('background','#999');
			$('#phonecode').attr("disabled",true); 
			$("#phonecode").unbind("click");
			currentSeconds=60;
			intervalId=window.setInterval(changeCodebtn,1000);
			newphoneCache=phone;
		}else{
			Core.PopUtil.warn("获取是失败，请重新获取！");
		}
	});
}

//验证码获取时间控制
function changeCodebtn(){
	if(currentSeconds==0){
		$("#phonecode").val("重新获取验证码").click(function(){ getCode() });
		$("#phonecode").css('background','#2d6eac');
		$('#phonecode').attr("disabled",false); 
		window.clearInterval(intervalId);
	}else{
		$("#phonecode").val("("+currentSeconds+"秒后)重新获取验证码");
		currentSeconds--;
	}
}