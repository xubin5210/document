var _imgVcode;
var masklayer='<div class="boxy-modal-blackout" style="z-index:3;" id="masklayer"></div>';
jQuery(function($) {
	$("#liucheng li").mousemove(function(){
		$(this).addClass("cur").siblings().removeClass();
		var k=$('#liucheng li').index(this)
		if(k==2)
			$("#app").show();
	}).on('mouseleave',function(){
		$(this).removeClass("cur"); 
		$("#app").hide();
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
    
});

//提交文件
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
							openWin(result.data.errorList);
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



var _win;
/**
 * 打开错误窗口
 */
function openWin(list){
	var tableDiv = $("#r_list");
	tableDiv.empty();
	var tableElement = '<table  class="table"><tr><th>错误行</th><th>错误信息</th></tr>'
	$.each(list,function(n,value) {   
             tableElement += " <tr  class='odd'><td> " + value.row +" </td><td>" + value.error +"</td></tr>";  
           }); 
    tableElement += '</table>';
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



