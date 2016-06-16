var CategoryUtil = {
	getChild : function getChild(id, type) {
		var child;

		var loadIndex;
		$.ajax({
			async : false,
			type : "POST",
			url : "/bbs/forumCategory/admin/ajax_childCategory.do",
			data : {
				"id" : id,
				"type" : type
			},
			beforeSend : function() {
				loadIndex = layer.load(1);
			},
			dataType : "json",
			success : function(data) {
				layer.close(loadIndex);
				child = data;
			},
			error : function(error) {
				layer.alert("服务器异常，请联系管理员");
				layer.close(loadIndex);
			}
		});

		return child;
	},

	// 获取子分类
	getChildCategory : function getChildCategory(id) {
		var childCategory = CategoryUtil.getChild(id, 1);
		return childCategory;
	},

	// 获取子板块
	getChildBoard : function getChildCategory(id) {
		var childBoard = CategoryUtil.getChild(id, 2);
		return childBoard;
	}
};