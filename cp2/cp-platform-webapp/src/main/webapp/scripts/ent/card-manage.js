var positionArr=[];
var deptArr=[];
jQuery(function($) {
	initAction();
	$("#sector span").bind("click",bindDeptClick);
	$("#jobs span").bind("click",bindPositionClick);
	
	$.validator.setDefaults(Core.Validator.Options2);
	
	$("#deptButton").bind("click",addDept);
	$("#positionButton").bind("click",addPosition);
	
	$("#commit").bind("click", submitForm);
	$("#noPass").bind("click", unpass);
	$("#qx2").bind("click",function(){
		$("#noendorse").hide();		
	});
});


function initAction(){
	$('#endorse .btn_ok').click(function(){
        $('#endorse').hide();
    });
	$('.pop-up .close-button').on('click', function(){
    	$(this).parents('.pop-up').hide();
    }); 
}


//填充不通过信息
function unpassWin(id,uname,nickname,uphone,usex,ucard,entId){
	if(dealStr(nickname)=='')
		$("#name1").html(uname);
	else
		$("#name1").html(uname+dealStr('('+nickname+')'));
	$("#sex1").html(usex);
	$("#phone1").html(uphone);
	$("#ucard1").html(ucard);
	$("#cardid1").val(id)
    $('#noendorse').show();
}

//填充名片信息，弹出界面
function fillWin(id,uname,uphone,usex,ucard,entId,positions,depts){
	positionArr=[];
	deptArr=[];
	$("#jobs").children().each(function(i,n){
	    var obj = $(n)
	    obj.removeClass();
	    obj.attr("name","");
	});
	
	$("#sector").children().each(function(i,n){
	    var obj = $(n)
	    obj.removeClass();
	    obj.attr("name","");
	});
	$("#uname").html(uname);
	$("#uphone").html(uphone);
	$("#usex").html(usex);
	$("#ucard").html(ucard);
	$("#cardid").val(id);
	$("input[name='entId']").val(entId)
	if(positions!=""){
		positions = positions+',';
		positions = positions.substring(0,positions.lastIndexOf(','));
		positionArr = positions.split(",");
	}
	if(depts!=""){
		depts = depts+',';
		depts = depts.substring(0,depts.lastIndexOf(','));
		deptArr = depts.split(",");
	}
	$("#jobs").children().each(function(i,n){
	    var obj = $(n)
	    if($.inArray(obj.html(), positionArr)!=-1){
	     	obj.addClass("cur");
	     	obj.attr("name","entPositionStr");
	    }
	});
	
	$("#sector").children().each(function(i,n){
	    var obj = $(n)
	    if($.inArray(obj.html(), deptArr)!=-1){
	     	obj.addClass("cur");
	     	obj.attr("name","entDepartmentStr");
	    }
	});
	
	
    $('#endorse').show();
}
//通过审批
function pass(){
	$.post("passUser",{id:$("#cardid").val()},function(data){
		if(data.data){
			Core.PopUtil.success("操作成功！", function() {
								window.location.href = "card-manage";
							})
		}
	});
}

//不通过审批
function unpass(){
	$("#unPass").ajaxSubmit({
		url : "unpassUser",
		type : 'post',
		success : function(result) {
			if(result.data){
				Core.PopUtil.success("操作成功！", function() {
								window.location.href = "card-manage";
							})
			}
		}
	});
}


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
	$("#updateForm").validate({
		ignore : ':hidden',
		rules : {
			entDepartmentStr : {
				required : true
			},
			entPositionStr : {
				required : true
			}
		},
		messages : {
			entDepartmentStr : {
				required : "请选择部门！"
			},
			entPositionStr : {
				required : "请选择职位！"
			}
		},

		submitHandler : function(f) {

			$(f).ajaxSubmit({
				url : "updateEmployee",
				type : 'post',
				success : function(result) {
					pass();
				}
			});
		}
	});
}

