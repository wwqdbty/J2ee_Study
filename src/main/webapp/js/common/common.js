var imelite = {};
imelite.enumBusType = {
	"all" : "0",
	"course" : "1",
	"service" : "2",
	"knowledge" : "3",
	"resource" : "4",
	"teacher" : "5",
	"toefl" : "6",
	"finance" : "10",
	"cmsInfo" : "20"
};

//弹出模对话框调用
//参数：url要链接的页面
//参数：Width弹出对话框的宽度
//参数：Height弹出对话框的高度
//参数：scrollPar弹出的对话框是否滚动（'yes'或'no'两种）
function openDialog(url, Width, Height, scrollPar, e) {
    // 相对于浏览器的居中位置
    var bleft = ($(window).width() - Width) / 2;
    var btop = ($(window).height() - Height) / 2;

    // 根据鼠标点击位置算出绝对位置
    var tleft = 0;
    var ttop = 0;

    if(e){
        tleft = e.screenX - e.clientX;
      ttop = e.screenY - e.clientY;
    }

    // 最终模态窗口的位置
    var left = bleft + tleft;
    var top = btop + ttop;
    url = url + (url.indexOf("?") == -1 ? "?" : "&") + "date=" + new Date();
    var sFeatures = "dialogWidth:" + Width + "px; dialogHeight:" + Height + "px;dialogLeft:" + left + "px;dialogTop:" + top + "px;edge:sunken; help:no; scroll:" + scrollPar + "; status:no;menubar:no; location:no";
    return window.showModalDialog(url, window, sFeatures);
}
/*ajax加载数据实现隔行换色*/
function getTrColor(trobj) {
    $(trobj + " tr:odd").css("background-color", "#F2F9FF");
    $(trobj + " tr:even").css("background-color", "#E8F2FA");
    $(trobj + " tr").bind("mouseover", function() {
        $(this).css("background-color", "#D7E8FB");
    });
    $(trobj + " tr").bind("mouseout", function() {
        $(this).css("background-color", "#F2F9FF");
        $(trobj + " tr:odd").css("background-color", "#F2F9FF");
        $(trobj + " tr:even").css("background-color", "#E8F2FA");
    });
    judgeFuncPower();
    //judgeFieldPower();暂时不用
}


$(document).ready(function() {
    $(".table_list tr:odd").css("background-color", "#F2F9FF");
    $(".table_list tr:even").css("background-color", "#E8F2FA");
    $(".table_list tr").bind("mouseover", function() {
        $(this).css("background-color", "#D7E8FB");
    });

    $(".table_list tr").bind("mouseout", function() {
        $(this).css("background-color", "#F2F9FF");
        $(".table_list tr:odd").css("background-color", "#F2F9FF");
        $(".table_list tr:even").css("background-color", "#E8F2FA");
    });

    /* 原来的live方式注释
     $("table a").live("mouseup", function() {
        if ($(this).parent().parent().parent().find("tr").attr("id") == 'pages') {
        } else {
            //		清除所有的背景色
            $(this).parent().parent().parent().find("tr").removeClass("trBgcolor");
            // 		设置该行背景色
            $(this).parent().parent().addClass("trBgcolor");
        }
    });*/
    
    /* 隔行换色（） */
    $("table").delegate("a", "mouseup", function() {
        if ($(this).parent().parent().parent().find("tr").attr("id") == 'pages') {
        } else {
            //		清除所有的背景色
            $(this).parent().parent().parent().find("tr").removeClass("trBgcolor");
            // 		设置该行背景色
            $(this).parent().parent().addClass("trBgcolor");
        }
    });

//	$("input[type='text']").live("keyup",function(event){   
//		if(event.keyCode == 37 || event.keyCode == 39 ) return ;
//		 var pattern =/["'",""","%","@",";","^","$","!","<",">"," ","&"]/g;
//         if(pattern.exec($(this).val()))
//         {
//          $(this).val(this.value.replace(/["'",""","%","@",";","^","$","!","<",">"," ","&"]/g,""));
//         }
//	});


    $(document).keydown(function(e) {
        var target = e.target;
        var tag = e.target.tagName.toUpperCase();
        if (e.keyCode == 8) {
            if ((tag == 'INPUT' && !$(target).attr("readonly")) || (tag == 'TEXTAREA' && !$(target).attr("readonly"))) {
                if ((target.type.toUpperCase() == "RADIO") || (target.type.toUpperCase() == "CHECKBOX")) {
                    return false;
                } else {
                    return true;
                }
            } else {
                return false;
            }
        }
    });
});

/* 选项卡页面调用 */
function setTab(name, n, cur) {
    for (var i = 1; i <= n; i++) {
        var menu = document.getElementById(name + i);
        var con = document.getElementById(name + "_" + i);
        menu.className = i == cur ? "curTag" : "";
        con.style.display = i == cur ? "block" : "none";
    }
}

function getIds() {
    var chks = $("input:checkbox[flag='chkChild']");
    var ids = "";
    $.each(chks, function(k, v) {
        if (v.checked == true) {
            if (ids != "") {
                ids += ",";
            }
            ids += v.value;
        }
    });
    return $.trim(ids);
}
function getIdsByFlag(flag) {
    var chks = $("input:checkbox[flag='" + flag + "']");
    var ids = "";
    $.each(chks, function(k, v) {
        if (v.checked == true) {
            if (ids != "") {
                ids += ",";
            }
            ids += v.value;
        }
    });
    return $.trim(ids);
}

$(function() {
    $("#chk").click(function() {
        var chks = $("input:checkbox[flag='chkChild']");
        var isChked = this.checked;
        $.each(chks, function(k, v) {
            if (isChked) {
                v.checked = true;
            } else {
                v.checked = false;
            }
        });
    });
    
    /**
     * 清空选中的树内容
     */
    $("#clearSelTreeNode").on("click", function() {
    	var domTreeValShow = $(this).prev();
    	//console.log(domTreeValShow);
    	domTreeValShow.val("");
    	domTreeValShow.attr("selIds", "");
    	
    	var hidTreeId = $(this).next();
    	hidTreeId.val("");
    });
});

function resetChk(){
    var chks = $("input:checkbox[flag='chkChild']");
    $.each(chks, function(k, v) {
        v.checked = false;
    });
    $("#chk").attr("checked",false);
}

/**
 * 清空parentElement下所有input, select, textarea
 * 
 * @param parentElementId 父节点ID
 * 
 * @author WangWenQiang
 */
function clearElements(parentElementId) {
	$("#" + parentElementId + " input, #" + parentElementId + " select, #" + parentElementId + " textarea").each(function() {
		var type = this.type;
	    var tag = this.tagName.toLowerCase();
	    if (type == "text" || type == "password" || type == "hidden" || tag == "textarea") {
	    	this.value = "";
	    } else if (type == "checkbox" || type == "radio") {
	    	this.checked = false;
	    } else if (tag == "select") {
	    	this.selectedIndex = 0;
	    }
	});
}