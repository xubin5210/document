var isLogin = false;
var _imgVcode;
jQuery(function($) {
	$("#password").on('focus', function() {
		$(this).next().hide();
	}).on('blur', function() {
		var v = $.trim($(this).val());
		if (v == "") {
			$(this).val('');
		}
		if (v.length == 0)
			$(this).next().show();
	});
	$('.placeholder').on('focus', function() {
		var holder = $(this).attr('data-holder'), v = $.trim($(this).val());
		if (holder === v) {
			$(this).val('');
		}
	}).on('blur', function() {
		var holder = $(this).attr('data-holder'), v = $.trim($(this).val());
		if (!v) {
			$(this).val(holder);
		}
	});
	
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
	
	$(".submit").bind("click",checkCode);
	_imgVcode.click();
});
var count=0;
//校验验证码
function checkCode(){
	count++;
	if(count>1)return;
	var mobile = $("#account").val();
	if(mobile=="" || mobile=="输入帐号"){
		Core.PopUtil.warn("账号不能为空！");
		count=0;
		return;
	}
	
	var pwd = $("#password").val();
	if(pwd==""){
		Core.PopUtil.warn("密码不能为空！");
		count=0;
		return;
	}
	var code = $("#vcode").val();
	if(code=="" || code=="验证码"){
		Core.PopUtil.warn("验证码不能为空！");
		count=0;
		return;
	}
	$.get("validateCode-check",{validateCode:code},function(data){
		if(data=="true"){
			login(mobile,pwd);
		}else{
			count=0;
			Core.PopUtil.warn("验证码错误！");
		}
	});
}

//登录
function login(mobile,pwd){
	var ifRemember = false;
	if($("#zddl").is(':checked')){
		ifRemember = true;
	}
	var parameter = {mobile:mobile,pwd:Core.DigestUtils.sha256(pwd),ifRemember:ifRemember};
	$.post("login",parameter,function(data){
		count=0;
		if(data.code == 120002){
			//如果存在则直接跳转到第二步
			Core.UrlUtil.redirect("regist-second?mobile="+mobile);
			
		}else if(data.code == 120004){
			Core.PopUtil.warn("账号不存在！");
		}else if(data.data){
			window.location.href = "system-notice";
		}else{
			Core.PopUtil.warn("密码错误！");
		}
	});
	
}



