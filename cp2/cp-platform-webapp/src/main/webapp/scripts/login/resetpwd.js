jQuery(function($) {
	$('#pwd').val("");
	$('#repwd').val("");
	

	$('#setpwd').validate({
		errorElement : 'span',
		errorClass : 'error1',
		rules : {
			pwd : {
				required : true,
				pwdRegex : true
			},
			repwd : {
				required : true,
				equalTo : "#pwd"
			}
		},
		messages : {
			pwd : {
				required : '密码不能为空',
				pwdRegex : '密码需为6-20个字符,由字母、数字或符号组成。'
			},
			repwd : {
				required : '重复密码不能为空',
				equalTo : '两次输入的密码不一致'
			}
			
		},
		errorPlacement : function(error, element) { // 指定错误信息位置
			var box = element.parent();
			// console.log(error);
			error.appendTo(box);
			box.find('.tip_show').hide();
			box.find(this.errorElement + '.success').remove();
		},
		success : function(label) {
			var me = this;
			label.html('&nbsp;').addClass('success').removeClass(me.errorClass);
		},
		submitHandler : function(f) {
			account = $('#account').val();
			var btnSubmit = $('#setpwdSubmit');
			Core.appendLoadingIcon(btnSubmit);
			btnSubmit.val('提交中...').attr('disabled', true);
			
			$.post(_wsServer+"/register/save", { registerName: $('#registerName').val(), pwd: App.EncodePWD($('#pwd').val())},
			   function(result){
				Core.removeLoadingIcon(btnSubmit);
				btnSubmit.val('提  交').attr('disabled', false);
				if (App.isSubmitSuccess(result)) {
					var rp = $("#rp").val();
					$("#fl_content").remove();
					$("#Tab1").hide();
					
					if(rp=="rp"){//找回密码的
						$("#rps").show();
					}else{
						$("#res").show();
						var temp = 3;
						mytimer = setInterval(function() {
							temp = temp - 1;
							$("#mytime").html(temp);
							if (temp == 0) {
								Core.UrlUtil.redirect(_ua);
							}
						}, 1000);// 一秒钟调用一次
					}
				}else{
					Core.PopUtil.success(popStr, function() {
						//Core.UrlUtil.redirect(_loginUrl);
					}, {
						autohide : false
					});
				}
			});
			
			
			return false;
		}
	});
	
	
});

	
function afterfunction(){
	var temp = 3;
	mytimer = setInterval(function() {
		temp = temp - 1;
		$("#mytime").html(temp);
		if (temp == 0) {
			Core.UrlUtil.redirect(_wsServer+"/ua");
		}
	}, 1000);// 一秒钟调用一次
}
