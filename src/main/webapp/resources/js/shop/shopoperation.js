// all actions are async -> not to affect page load
/**
 *
 */
$(function() {
	let shopId = getQueryString('shopId');
	let isEdit = shopId? true : false;
	let initUrl = '/o2o/shopadmin/getshopinitinfo';
	let registerShopUrl = '/o2o/shopadmin/registershop';
	let shopInfoUrl = '/o2o/shopadmin/getshopbyid?shopId=' + shopId;
	let editShopUrl = '/o2o/shopadmin/modifyshop';
	// alert(initUrl);
	if (!isEdit) {
		getShopInitInfo();
	} else {
		getShopInfo(shopId);
	}

	function getShopInfo(shopId) {
		$.getJSON(shopInfoUrl, function(data) {
			if (data.success) {
				let shop = data.shop;
				$('#shop-name').val(shop.shopName);
				$('#shop-addr').val(shop.shopAddr);
				$('#shop-phone').val(shop.phone);
				$('#shop-desc').val(shop.shopDesc);
				// give the shop its preset shop category
				let shopCategory = '<option data-id="'
					+ shop.shopCategory.shopCategoryId + '" selected>'
					+ shop.shopCategory.shopCategoryName + '</option>';
				let tempAreaHtml = '';
				// initialize area list
				data.areaList.map(function(item, index) {
					tempAreaHtml += '<option data-id="' + item.areaId + '">'
						+ item.areaName + '</option>';
				});
				$('#shop-category').html(shopCategory);
				// not allowing selection of shop category
				$('#shop-category').attr('disabled', 'disabled');
				$('#area').html(tempAreaHtml);
				// give the shop its previously set area
				$("#area option[data-id='"+shop.area.areaId+"']").attr("selected", "selected");
			}
		})
	}


	// retrieve shop category and area category from backend to form drop-down items
	function getShopInitInfo() {
		$.getJSON(initUrl, function(data) {
			if (data.success) {
				let tempHtml = "";
				let tempAreaHtml = "";
				data.shopCategoryList.map(function(item, index) {
					tempHtml += '<option data-id="' + item.shopCategoryId + '">'
						+ item.shopCategoryName + '</option>';
				});
				data.areaList.map(function(item, index) {
					tempAreaHtml += '<option data-id="' + item.areaId + '">'
						+ item.areaName + '</option>';
				});
				$('#shop-category').html(tempHtml);
				$('#area').html(tempAreaHtml);
			}
		});
	}

	$('#submit').click(function() {
		let shop = {};
		if (isEdit) {
			shop.shopId = shopId;
		}
		shop.shopName = $('#shop-name').val();
		shop.shopAddr = $('#shop-addr').val();
		shop.phone = $('#shop-phone').val();
		shop.shopDesc = $('#shop-desc').val();
		shop.shopCategory = {
			shopCategoryId: $('#shop-category').find('option').not(function() {
				return !this.selected;
			}).data('id')
		}
		shop.area = {
			areaId: $('#area').find('option').not(function() {
				return !this.selected;
			}).data('id')
		}
		let shopImg = $('#shop-img')[0].files[0];
		var formData = new FormData();
		formData.append('shopImg', shopImg);
		formData.append('shopStr', JSON.stringify(shop));
		var verifyCodeActual = $('#j_captcha').val();
		if (!verifyCodeActual) {
			$.toast('please enter kaptcha');
			return;
		}
		formData.append('verifyCodeActual', verifyCodeActual);
		$.ajax({
			url : (isEdit? editShopUrl : registerShopUrl),
			type : 'POST',
			data : formData,
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
				if (data.Success) {
					$.toast('submit success');
				} else {
					$.toast('submit failed: ' + data.errMsg);
				}
				$('#captcha_img').click();
			}
		});
	});
})