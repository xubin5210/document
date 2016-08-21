var _imgVcode;
jQuery(function($) {
	$("#getCode").bind("click",checkPhone);
	//验证码切换
	_imgVcode = $("#imgVcodeFm");
	var validateCodeApiUrl = _server + "/validateCode?height=32&d=";

	_imgVcode.attr('src',validateCodeApiUrl).click(function() {
		this.src = validateCodeApiUrl	+ new Date().getTime();
	});
	$("#imgVcodeFm").click(function(){
		var validateCodeApiUrl2 = _server + "/validateCode?height=32&d=" + new Date().getTime();
		_imgVcode.attr('src',validateCodeApiUrl2);
	});
	
		$.validator.setDefaults(Core.Validator.Options2);
	$("#setpwdSubmit").bind("click",resetPwd);
	
	$("#mobileSubmit").bind("click",checkCode);
});

//校验手机验证码
function checkPhoneCode(code){
	
	var url = "validatePhone";
	$.get(url,{code:code},function(data){
		if(data.data){
			sendPwd();//为真 则提交
		}else{
			Core.PopUtil.warn("手机校验码错误！");
		}
	});
}

//校验验证码
function checkCode(){
	//手机号码
	var phone = $("#mobileaccount").val();
	if(phone==""){
		Core.PopUtil.warn("请先输入手机号码！");
		return;
	}
	if(!phone.match(/(1[3-9]\d{9}$)/)){
		Core.PopUtil.warn("请输入正确格式的手机号码！");
		return;
	}
	
	var pcode = $("#m_jiaoyancode").val();
	if(pcode==""){
		Core.PopUtil.warn("请输入手机验证码！");
		return;
	}
	
	var code = $("#vcode").val();
	if(code==""){
		Core.PopUtil.warn("验证码不能为空！");
		return;
	}
	$.get("validateCode-check",{validateCode:code},function(data){
		if(data=="true"){
			checkPhoneCode(pcode);
		}else{
			Core.PopUtil.warn("验证码错误！");
		}
	});
}

//验证手机号码是否存在
function checkPhone(){
	trueId = -1;
	var phone=$("#mobileaccount").val();
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
//			Core.PopUtil.confirm("该号码不存在！",function(){//如果存在则直接跳转到第二步
//				Core.UrlUtil.redirect("regist-second?mobile="+phone);
//			})
			Core.PopUtil.warn("该账号不存在！");
		}else{
			$('#trueId').val(data.data)
			getCode(phone);
		}
	});
}

var intervalId;
//获取验证码
function getCode(phone){
	var url="getUpdatePwdCode";
	var parameter={
				mobile:phone
				};

	$.post(url,parameter,function(data){
		if(data){
			//该处防止用户短时间内重复获取验证码，脚本+发送时间控制
			//(60秒后)重新获取验证码
			//$("#getCode").removeClass('button').addClass('button_down');
			$("#getCode").unbind("click");
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
		$("#getCode").val("重新获取校验码").click(function(){ getCode() });
		//$("#getCode").removeClass('button_down').addClass('button');
		window.clearInterval(intervalId);
	}else{
		$("#getCode").val("("+currentSeconds+"秒后)重新获取校验码");
		currentSeconds--;
	}
}

//发送密码
function sendPwd(){
	$("#content_1").css('display','block');
	$("#content").css('display','none');
}


function resetPwd() {
	//新密码
	var pwd = $("#pwd").val();
	if(pwd==""){
		Core.PopUtil.warn("请输入新密码！");
		return;
	}
	if (!((pwd.match(/[\d]/) && pwd.match(/[A-Za-z]/) && pwd
			.match(/[~!@#$%^&*()_+.;'<>,^]/))
			|| (pwd.match(/[\d]/) && pwd.match(/[A-Za-z]/)) || (pwd
			.match(/[\d]/) && pwd.match(/[~!@#$%^&*()_+.;'<>,^]/)))) {
		Core.PopUtil.warn("密码由字母、数字或者英文符号任意两种组合，最短6位，区分大小写！");
		return;
	}
	
	//确认新密码
	var repwd = $("#repwd").val();
	if(repwd==""){
		Core.PopUtil.warn("请再次输入新密码！");
		return;
	}
	
	if(pwd!=repwd){
		Core.PopUtil.warn("两次新密码输入不正确！请重新输入！");
		return;
	}
	
	var btnSubmit = $('#setpwdSubmit');
	Core.appendLoadingIcon(btnSubmit);
	btnSubmit.val('提交中...').attr('disabled', true);
			
	var parameter = {pwd:Core.DigestUtils.sha256(pwd),id:$('#trueId').val()};
	$.post("updateMobile",parameter,function(data){
		Core.removeLoadingIcon(btnSubmit);
		btnSubmit.val('提  交').attr('disabled', false);
		if (data.data) {
			$("#container_2").css('display','block');
			$("#content_1").css('display','none');
		}else{
			Core.PopUtil.success(popStr, function() {
				//Core.UrlUtil.redirect(_loginUrl);
			}, {
				autohide : false
			});
		}
	});
	
}

//立即登录
function login(){
	window.location.href='login_system-notice?mobile='+$("#mobileaccount").val();
}



