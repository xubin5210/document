var index;
jQuery(function($) {
			$('.p-4').on('click', '.white-button', function() {
						$(this).siblings().removeClass('cur').end()
								.addClass('cur');
						var v = $.trim($(this).val());
						if (v === '政府') {
							$('#goverment').show().siblings('.main-type')
									.hide();
							index = "#goverment";
						} else if (v === '企业') {
							$('#enterprise').show().siblings('.main-type')
									.hide();
							index = "#enterprise";

						} else if (v === '社会组织') {
							$('#organization').show().siblings('.main-type')
									.hide();
							index = "#organization";
						}
						$("#s1").val(v);
						$("#s2").val(v);
						$("#s3").val(v);
					});

			$.validator.setDefaults(Core.Validator.Options2);

			$("#loginNow").bind("click", loginNow);
			$("#nextTip1").bind("click", checkRegistInfo);
			$("#previousTip1").bind("click", previous);
			$("#govermentsub").bind("click", submitForm);

			$("#nextTip2").bind("click", checkRegistInfo);
			$("#previousTip2").bind("click", previous);
			$("#enterprisesub").bind("click", submitForm);

			$("#nextTip3").bind("click", checkRegistInfo);
			$("#previousTip3").bind("click", previous);
			$("#organizationsub").bind("click", submitForm);

		});

// 返回上一步
function previous() {
	window.location.href = "regist-basic?mobile=" + $("#mobile").val();
}


// 立即登录
function loginNow() {
	window.location.href = "index";
}

// 验证企业信息
function checkRegistInfo() {
	var param = {
		orgName : $(index + " input[name=orgName]").val(),
		orgCode : $(index + " input[name=orgCode]").val(),
		registrationno : $(index + " input[name=registrationno]").val()
	}
	$.post("checkRegistInfo", param, function(data) {
				if (data.code == 120009) {
					Core.PopUtil.warn(data.msg);
				} else {
					$(index + 'sub').click();
				}
			});
	return false;
}

// 表单提交
function submitForm() {
	var count = 0;
	$(index + " input[type=checkbox]:checked").each(function() {
				count++;
			});
	if (count == 0) {
		Core.PopUtil.warn("请至少选择一种单位类型！");
		return false;
	}
	if (count > 5) {
		Core.PopUtil.warn("行业领域最多可选5项！");
		return false;
	}
	var fromD = $(index + " input[id=validDateFrom]").val();
	var fromT = $(index + " input[id=validDateTo]").val();
	var timeD = new Date(fromD).getTime();
	var timeT = new Date(fromT).getTime();

	if (timeD >= timeT) {
		Core.PopUtil.warn("有效期开始时间不能大于有效期结束时间！");
		return false;
	}
	var fileName = $(index + " input[type=file]").val();
	if (fileName == "") {
		Core.PopUtil.warn("请先选择要上传的证书！");
		return false;
	}
	$(index).validate({
		ignore : ':hidden',
		rules : {
			orgCode : {
				required : true,
				onlyEnglishAndNumber : true
			},
			orgName : {
				required : true,
				companyname : true
			},
			artificialPerson : {
				required : true,
				username : true
			},
			registrationno : {
				required : true,
				onlyEnglishAndNumber : true
			},
			validDateFrom : {
				required : true,
				date : true
			},
			validDateTo : {
				required : true,
				date : true
			},
			zsfile : {
				required : true,
				image : true
			}

		},
		messages : {
			orgCode : {
				required : "企业代码不能为空",
				maxlength : "规则名称长度不能超过{0}位"
			},
			orgName : {
				required : "企业名称不能为空"
			},
			artificialPerson : {
				required : "法定代表人不能为空"
			},
			registrationno : {
				required : "登记号不能为空"
			},
			validDateFrom : {
				required : "起始时间不能为空"
			},
			validDateTo : {
				required : "到期时间不能为空"
			},
			zsfile : {
				required : "请至少选择一张证书",
				image : "请上传正确的图片文件,格式为:(jpg|jpeg|gif|png)"
			}
		},

		submitHandler : function(f) {
			$(f).ajaxSubmit({
				url : "regist",
				type : 'post',
				success : function(result) {
					window.location.href = "regist-third?mobile="
							+ $("#mobile").val();
				}
			});
		}
	});
}
