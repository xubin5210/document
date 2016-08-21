var _imgVcode;
jQuery(function($) {
	$("#sector span,#jobs span").mousemove(function(){
		$(this).addClass("cur").siblings().removeClass(); 
		$(this).children('i').show();
	}).on('mouseleave',function(){
		$(this).removeClass("cur"); 
		$(this).children('i').hide();
	});
	$("#deptButton").bind("click",addDept);
	$("#positionButton").bind("click",addPosition);
	$("#tags .close").click(function(){
		var type = $(this).attr("name");
		var id = $(this).attr("id");
		deleteByType(type,id);
	});
	$('.placeholder').on('focus', function(){
				var holder = $(this).attr('data-holder'),
					v = $.trim($(this).val());
				if(holder === v){
					$(this).val('');
				}
			}).on('blur', function(){
				var holder = $(this).attr('data-holder'),
					v = $.trim($(this).val());
				if(!v){
					$(this).val(holder);
				}
			});
			
			$("#tags li").dblclick(function () { 
				$(this).find('span').hide();
				var w=$(this).find('span').width();
				var input=$(this).find('input');
				input.show().css('width',w+15);
				input.show().css('marginRight','15PX');
				
			}).on('mouseleave',function(){
				var v=$(this).find('input').val();
				$(this).find('h5').html(v);
				$(this).find('span').show();
				$(this).find('input').hide();
				
			});
});

//更新部门
function updateDept(object,id,entId){
	if(object.defaultValue==object.value){
		return;
	}
	if(object.value.length>10){
		Core.PopUtil.warn("部门名称长度不得超过10位！");
		return;
	}
	$.post("updateDeptLabel",{entId:entId,id:id,deptName:object.value},function(data){
		if(data.data){
			refalsh();
		}else{
			Core.PopUtil.warn("该部门名称已经存在！请更换！");
		}
	});
}

//更新职位
function updatePosition(object,id,entId){
	if(object.defaultValue==object.value){
		return;
	}
	if(object.value.length>10){
		Core.PopUtil.warn("职位名称长度不得超过10位！");
		return;
	}
	alert(object.value);
	$.post("updatePositionLabel",{entId:entId,id:id,positionName:object.value},function(data){
		if(data.data){
			refalsh();
		}else{
			Core.PopUtil.warn("该职位名称已经存在！请更换！");
		}
	});
}

//添加部门新标签
function addDept(){
	var deptName = $("#deptName").val();
	if($.trim(deptName)=="" || $.trim(deptName)=="部门标签"){
		Core.PopUtil.warn("部门名称不能为空！");
		return;
	}
	
	if(deptName.length>10){
		Core.PopUtil.warn("部门最大长度为10个字节！");
		return;
	}
	
	var parameter = {entId:$("#entId").val(),deptName:deptName};
	$.post("addDeptLabel",parameter,function(data){
		if(data.data){
			refalsh();
		}else{
			Core.PopUtil.warn("该部门已经存在无须添加！");
		}
	});
}

//添加职位新标签
function addPosition(){
	var positionName = $("#positionName").val();
	if($.trim(positionName)=="" || $.trim(positionName)=="职业中文名称"){
		Core.PopUtil.warn("职位名称不能为空！");
		return;
	}
	
	if(positionName.length>10){
		Core.PopUtil.warn("职位名称最大长度为10个字节！");
		return;
	}
	var parameter = {entId:$("#entId").val(),positionName:positionName};
	$.post("addPositionLabel",parameter,function(data){
		if(data.data){
			refalsh();
		}else{
			Core.PopUtil.warn("该职位已经存在无须添加！");
		}
	});
}

//刷新页面
function refalsh(){
	window.location.href = "ent-tags?index="+$("#index").val();
}


//删除
function deleteByType(type,id){
	if("d" == type)
		deleteDept(id);
	else
		deletePosition(id);
}

//删除职位
function deletePosition(id){
	
	var parameter = {id:id};
	$.post("deletePositionLabel",parameter,function(data){
		if(data.data){
			Core.PopUtil.alert("删除成功！", {
				autohide : true,
				callback : function() {
					refalsh();
				}
			});	
		}else{
			Core.PopUtil.warn("该职位标签在使用中，无法删除！");
		}
	});
}

//删除部门
function deleteDept(id){
	var parameter = {id:id};
	$.post("deleteDeptLabel",parameter,function(data){
		if(data.data){
			Core.PopUtil.alert("删除成功！", {
				autohide : true,
				callback : function() {
					refalsh();
				}
			});		
		}else{
			Core.PopUtil.warn("该部门标签在使用中，无法删除！");
		}
	});
	
}




