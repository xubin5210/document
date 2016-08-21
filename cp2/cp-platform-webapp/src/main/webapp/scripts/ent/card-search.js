var masklayer='<div class="boxy-modal-blackout" style="z-index:3;" id="masklayer"></div>';
jQuery(function($) {
	
	$("#push").click(function(){
		if($(this).hasClass('icon_down'))
		{
			$(this).removeClass('icon_down').addClass('icon_up');
			$(".option").show();
		}
		else{
		  $(this).removeClass('icon_up').addClass('icon_down');
		  $(".option").hide();
		}
	});
	
	$('.upfile').click(function(){
        $('#upfile').show();
        $(masklayer).appendTo('body');
    });
	$('.btn_ok').click(function(){
        $('#upfile').hide();
        $('#masklayer').remove();
    });
	$('.pop-up .close-button').on('click', function(){
    	$(this).parents('.pop-up').hide();
    	$('#masklayer').remove();
    }); 
    
	$.validator.setDefaults(Core.Validator.Options2);
    
    $('#uploadBut').bind("click",submitFile);
    
    initFilterInfo();
});

function submitFile(){
	var file = $("#upload").val();
	if(file==""){
		Core.PopUtil.warn("请先选择文件！");
		return false;
	}
	$('#uploadBut').val("正在导入中...");
	$("#upcard").validate({
		submitHandler : function(f) {
			$(f).ajaxSubmit({
				url : "exportEmployees",
				type : 'post',
				success : function(data) {
					var result = jQuery.parseJSON(data);
					$("#upload").val('');
					$("#viewfile").val('');
					if(result.data.iftrue){
						$('#uploadBut').val("开始上传");
						Core.PopUtil.success("导入成功"+result.data.successSize+"条！", function() {
								window.location.href = "card-search";
							})
					}else if(result.data.errorList){
						$('#uploadBut').val("开始上传");
						Core.PopUtil.confirm("模板中有"+result.data.errorSize+"条错误,是否查看?",function (){
							$("#upfile").hide();
							openWin(result.data.errorList,result.data.errorPath);
							//window.location.href = "downLoadError?error="+JSON.stringify(result.data.errorList);
						}
						);
					}else if(!result.data.iftrue){
						$('#uploadBut').val("开始上传");
						Core.PopUtil.warn("请导入正确的卡片模板！");
					}
				}
			});
		}
	});
}

function getCardInfo(id,entId){
	window.location.href = "getCardInfo?id="
					+ id ;
	
}

var _win;
/**
 * 打开错误窗口
 */
function openWin(list,fileName){
	var tableDiv = $("#r_list");
	tableDiv.empty();
	var tableElement = '<table  class="table"><tr><th>错误行</th><th>错误信息</th></tr>'
	$.each(list,function(n,value) {   
             tableElement += " <tr  class='odd'><td> " + value.row +" </td><td>" + value.error +"</td></tr>";  
           }); 
    tableElement += '</table><br/><div  style="margin:0 auto;width:100px;"><a style="font-size:15px;color:blue" href='+webResouse+fileName+'>下载</a><div>';
	if(!_win){
		_win = new Core.Window('r_list',{
			width:500,
			height:300,	
			unloadOnHide : false,
			title : '错误信息',
			afterShow: function(){	
				$('#masklayer').remove();
			}
		});		
	}
	_win.open();
	tableDiv.html(tableElement);
}

//过滤条件
function filterInfo(type,value,currObj,from){
	if(!currObj){
		currObj=$("#id_a_"+type+"_"+value);
	}
	if($("#id_span_"+type+"_"+value).length>0){
		return;
	}
	var typeName="";
	if(type=="activa"){
		typeName="状态";
		$("#jhzt").hide();
	}else if(type=="dept"){
		typeName="部门";
		$("#bm").hide();
	}else if(type=="position"){
		typeName="职位";
		$("#zw").hide();
	}
//	else if(type=="sex"){
//		typeName="性别";
//		$("#xb").hide();
//	}
	
	$("#id_span_filter_last").before('<span class="box" id="id_span_'+type+'_'+value+'">'+typeName+'：'+$(currObj).html()+'<em onclick="filterDelete(\''+type+'\',\''+value+'\',this)">x</em></span>');
//	if(type=="sex"){
//		$("#search_form").append('<input type="hidden"  id="'+type+'_'+value+'" name="sex" value="'+$(currObj).html()+'"/>');
//	}else 
	if($("#"+type+"_"+value).length==0){
		$("#search_form").append('<input type="hidden" id="'+type+'_'+value+'" name="'+type+'Array" value="'+value+'"/>');
	}
	if(from==1)
		$("#search_form").submit();
	
}

//过滤条件删除
function filterDelete(type,value,currObj){
	if(type=="activa"){
		$("#jhzt").show();
	}else if(type=="dept"){
		$("#bm").show();
	}else if(type=="position"){
		$("#zw").show();
	}
//	else if(type=="sex"){
//		$("#xb").show();
//	}
	$(currObj).parent().remove();
	$("#"+type+"_"+value).remove();
	$("#search_form").submit();
}

//初始化过滤信息
function initFilterInfo(){
	$("#filterD").remove();
	var typeArr = new Array();
	typeArr[0]="activa";
	typeArr[1]="dept";
	typeArr[2]="position";
//	typeArr[3]="sex";
	for(var i=0;i<typeArr.length;i++){
//		if(typeArr[i]!="sex"){
			$("input[name='"+typeArr[i]+"Array']").each(function(index,n){
				filterInfo(typeArr[i],$(n).val());
			});
//		}else{
//			if($("#sex_1"))
//				filterInfo(typeArr[i],1);
//			else if($("#sex_0"))
//				filterInfo(typeArr[i],0);
//		}
	}
	
}



