$(function() {
	getlist();
	function getlist(e) {
		$.ajax({
			url : "/o2o/shopadmin/getshoplist",
			type : "get",
			dataType : "json",
			success : function(data) {
				if (data.success) {
					handleList(data.shopList); // render shop list first
					handleUser(data.user);
				}
			}
		});
	}
	function handleUser(data) {
		$('#user-name').text(data.name);
	}

	function handleList(data) {
		var html = '';
		data.map(function(item, index) {
			html += '<div class="row row-shop"><div class="col-40">'
					+ item.shopName + '</div><div class="col-40">'
					+ shopStatus(item.enableStatus)
					+ '</div><div class="col-20">'
					+ goShop(item.enableStatus, item.shopId) + '</div></div>';

		});
		$('.shop-wrap').html(html);
	}

	function shopStatus(status) {
		if (status == 0) {
			return 'Under Review';
		} else if (status == -1) {
			return 'Invalid Shop';
		} else if (status == 1) {
			return 'Review ok';
		}
	}

	function goShop(status, id) {
		if (status == 1) {
			return '<a href="/o2o/shopadmin/shopmanagement?shopId=' + id
					+ '">Enter</a>';
		} else {
			return '';
		}
	}
});