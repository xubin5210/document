
jQuery(function($) {
	$('.close-button').on('click', function(){
        $(this).parents('.hidden').hide();
    });
	

    $('.alteration .fr').on('click', function(){
        $(this).toggleClass('clicked');
        $('.info-record, .company-info').toggle();
    })
	
});

//设置值
function setValue(cardId,cardIdFrom){
	$.get("getOneRecord",{cardId:cardId,cardIdFrom:cardIdFrom},function(data){
		$("#history").empty();
		if(data.data){
			if(data.data.iconUrl)
				$("#icon_url").attr("src",dealStr(storeUrl+data.data.iconUrl));
			else
				$("#icon_url").attr("src",defautIma2);
			if(data.data.logoUrl)
				$("#logUrl").attr("src",dealStr(storeUrl+data.data.logoUrl));
			else
				$("#logUrl").attr("src",defautIma1);
			$("#true_name").html(dealStr(data.data.true_name));
			$("#ename").html(dealStr("("+data.data.ename+")"));
			$("#position_name").html(dealStr(data.data.position_name));
			$("#card_id_from").html(dealStr(data.data.card_id_from));
			$("#mobile").html(dealStr(data.data.mobile));
			$("#cardphone").html(dealStr(data.data.cardphone));
			$("#nick_name").html(dealStr(data.data.nick_name));
			$("#cardemail").html(dealStr(data.data.cardemail));
			$("#org_name").html(dealStr(data.data.org_name));
			$("#org_ename").html(dealStr(data.data.org_ename));
			$("#ent_address").html(dealStr(data.data.ent_address));
			$("#phone").html(dealStr(data.data.phone));
			$("#org_introduction").html(dealStr(data.data.org_introduction));
			$("#official_website").html("网址："+dealStr(data.data.official_website));
			$("#email").html("企业邮箱："+dealStr(data.data.email));
			$("#info").attr("style","");
			$.each(data.data.changeMapList,function(n,value) {  
		       $("#history").append('<li><span class="now">'+value.columnValue+'</span><br class="clr"></li>');
		    }); 
			
            
		}
	});
}





