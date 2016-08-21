jQuery(function($) {
			$.validator.setDefaults(Core.Validator.Options2);
			initActive();

			$(".placeholder").on('focus', function() {
						$(this).next().hide();
					}).on('blur', function() {
						var v = $.trim($(this).val());
						if (v == "") {
							$(this).val('');
						}
						if (v.length == 0) {
							$(this).next().show();
						}
					});
					
			
		});

$(document).ready(function() {
			$('#img1').imgAreaSelect({
				selectionColor : 'blue', // 截取颜色
				x1 : 0,
				y1 : 0,
				x2 : 50,
				y2 : 50,
				maxWidth : 400,
				maxHeitht : 400, // 限定的截取部分宽高：
				minWidth : 50,
				minHeight : 50,
				autoHide:true,
				hide : true,
				selectionOpacity : 0.1, // 截取的透明度
				handles : true, // 截取边框的小柄
				aspectRatio : '1:1', // 固定比例大小
				onSelectChange : preview
					// 截取操作完后，需要获取什么的函数，自定义
				});
		});

function initActive() {
	// alert(1111111);
	$('#nav dd').on('click', function() {
				// alert(222222);
				var index = $(this).index();
				$(this).addClass('nav-cur').siblings().removeClass('nav-cur');
				if (index == 1) {
					$('div[name="infoDiv"]').show().siblings('.basic-info')
							.hide();
				} else if (index == 2) {
					bindUpdateDiv();
				} else if (index == 4) {
					$('div[name="pwdDiv"]').show().siblings('.basic-info')
							.hide();
					$("#commitPwd").bind("click", updatePwd);
				} else if (index == 5) {
					$('div[name="phoneDiv"]').show().siblings('.basic-info')
							.hide();
					$("#commitPhone").bind("click", checkCode);
					$("#phonecode").bind("click", checkPhone);
				}
				if (_index == 2 && isclick == "false" && index != _index) {
					Core.UrlUtil.redirect(_backUrl + "?index=" + index);
				} else {
					_index = index;
				}
			});
	if (_index == 2) {
		$("#kzxx").click();
	} else if (_index == 4) {
		$("#upwd").click();
	} else if (_index == 5) {
		$("#uphone").click();
	}
}

function bindUpdateDiv() {
	$('div[name="expandDiv"]').show().siblings('.basic-info').hide();
	$('#remould-box').removeClass('right-body').addClass('hidden');
	$('#expand-info').removeClass('hidden').addClass('right-body');
	$('#updateexpand').bind("click", function() {
				$('#remould-box').removeClass('hidden').addClass('right-body');
				$('#expand-info').removeClass('right-body').addClass('hidden');

			});

	$('#backButton').bind("click", function() {
				$('#remould-box').removeClass('right-body').addClass('hidden');
				$('#expand-info').removeClass('hidden').addClass('right-body');

			});

	$('#saveexpand').bind("click", updateExpandEnt);
}

// 验证手机号码是否存在
function checkPhone() {
	var phone = $("#phone").val();
	if (phone == "") {
		Core.PopUtil.warn("请先输入手机号码！");
		return;
	}
	// 正则表达式
	if (!phone.match(/(1[3-9]\d{9}$)/)) {
		Core.PopUtil.warn("请输入正确格式的手机号码！");
		return;
	}
	$.get("checkMobile", {
				mobile : phone
			}, function(data) {
				if (!data.data) {
					getCode(phone);
				} else {
					Core.PopUtil.warn("该号码已经存在！");
				}
			});
}

// 验证验证码
function checkCode() {
	// 手机号码
	var phone = $("#phone").val();
	if (phone == "") {
		Core.PopUtil.warn("请先输入新手机号码！");
		return;
	}
	if (!phone.match(/(1[3-9]\d{9}$)/)) {
		Core.PopUtil.warn("请输入正确格式的手机号码！");
		return;
	}

	var code = $("#code").val();
	if (code == "") {
		Core.PopUtil.warn("请输入验证码！");
		return;
	}
	var url = "validatePhone";
	$.get(url, {
				code : code
			}, function(data) {
				if (data.data) {
					updatePhone(phone);// 为真 则提交
				} else {
					Core.PopUtil.warn("验证码错误！");
				}
			});
}

// 更新手机号码
function updatePhone(phone) {
	$.post("updateMobile", {
				mobile : phone,
				id : $("#entId").val()
			}, function(data) {
				if (data) {
					Core.PopUtil.warn("手机更换成功！");
					Core.UrlUtil.redirect(_backUrl + "?index=1");
				} else {
					Core.PopUtil.warn("更新失败！");
				}
			});
}

var intervalId;
// 获取验证码
function getCode(phone) {
	var url = "getPhoneCode";
	var parameter = {
		mobile : phone
	};

	$.post(url, parameter, function(data) {
				if (data) {
					// 该处防止用户短时间内重复获取验证码，脚本+发送时间控制
					// (60秒后)重新获取验证码
					$("#phonecode").unbind("click");
					$("#phonecode").removeClass('button')
							.addClass('button_down');
					currentSeconds = 60;
					intervalId = window.setInterval(changeCodebtn, 1000);
					newphoneCache = phone;
				} else {
					Core.PopUtil.warn("获取是失败，请重新获取！");
				}
			});
}

// 验证码获取时间控制
function changeCodebtn() {
	if (currentSeconds == 0) {
		$("#phonecode").val("重新获取验证码").click(function() {
					getCode()
				});
		$("#phonecode").removeClass('button_down').addClass('button');
		window.clearInterval(intervalId);
	} else {
		$("#phonecode").val("(" + currentSeconds + "秒)重新获取验证码");
		currentSeconds--;
	}
}

// 密码修改
function updatePwd() {
	// 旧密码
	var oldpwd = $("#oldpwd").val();
	if (oldpwd == "") {
		Core.PopUtil.warn("请输入原密码！");
		return;
	}
	// 新密码
	var pwd = $("#newpwd").val();
	if (pwd == "") {
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

	// 确认新密码
	var repwd = $("#repwd").val();
	if (repwd == "") {
		Core.PopUtil.warn("请再次输入新密码！");
		return;
	}

	if (pwd != repwd) {
		Core.PopUtil.warn("两次新密码输入不正确！请重新输入！");
		return;
	}
	var parameter = {
		newPwd : Core.DigestUtils.sha256(pwd),
		oldPwd : Core.DigestUtils.sha256(oldpwd),
		entId : $("#entId").val()
	};
	$.post("updatePwd", parameter, function(data) {
				if (data.data) {
					Core.PopUtil.success("更改密码成功！", function() {
								Core.UrlUtil.redirect(_backUrl + "?index=1");
							})
				} else {
					Core.PopUtil.warn("请输入正确的旧密码！");
					$("#oldpwd").val("");
					$("#newpwd").val("");
					$("#repwd").val("");
				}
			});

}

// 修改公司扩展信息
function updateExpandEnt() {
	$('#entinfos').validate({
		ignore : ':hidden',
		rules : {
			orgEname : {
				onlyEnglish : true,
				maxlength : 200
			},
			officialWebsite : {
				website : true,
				maxlength : 200
			},
			phone : {
				phoneOrMobile : true
			},
			email : {
				vEmail : true
			},
			entweixin : {
				onlyEnglishAndNumber : true,
				maxlength : 32
			},
			orgIntroduction : {
				maxlength : 2000
			}

		},
		messages : {
			orgEname : {
				maxlength : "英文名称不能超过200位"
			},
			officialWebsite : {
				maxlength : "企业官网长度不能超过200位"
			},
			entweixin : {
				maxlength : "微信公众号不能超过32位"
			},
			orgIntroduction : {
				maxlength : "公司简介不能超过2000位"
			}
		},

		submitHandler : function(f) {

			$(f).ajaxSubmit({
				url : "regist",
				type : 'post',
				success : function(result) {
					// var entInfo = result.data;
					// $('#ename').html(entInfo.orgEname);
					// $('#resume').html(entInfo.orgIntroduction);
					// $('#website').html(entInfo.officialWebsite);
					// $('#kfphone').html(entInfo.phone);
					// $('#entemail').html(entInfo.email);
					// $("#head_img").attr("src",storeUrl+entInfo.logoUrl);
					// $('#entweixin').html(entInfo.weiXin);
					// $("#showLogoUrl").attr("src",storeUrl+entInfo.logoUrl);
					// $("#wxewm").attr("src",storeUrl+entInfo.weiXinUrl);
					// $('#remould-box').removeClass('right-body').addClass('hidden');
					// $('#expand-info').removeClass('hidden').addClass('right-body');
					Core.PopUtil.success("修改成功！", function() {
								Core.UrlUtil.redirect(_backUrl
										+ "?index=2&ifRemember=true");
							});

				}
			});
		}
	});
}
