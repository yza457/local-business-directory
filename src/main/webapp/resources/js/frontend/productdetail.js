$(function() {
	// get productId from url
	var productId = getQueryString('productId');
	// url to get product detail
	var productUrl = '/o2o/frontend/listproductdetailpageinfo?productId='
			+ productId;
	// get product detail from backend
	$.getJSON(productUrl, function(data) {
		if (data.success) {
			// get product information
			var product = data.product;
			// set attr for html tag related to product details
			// thumbnail
			$('#product-img').attr('src', product.imgAddr);
			// last update time
			$('#product-time').text(
					new Date(product.lastEditTime).Format("yyyy-MM-dd"));
			if (product.point != undefined) {
				$('#product-point').text('get' + product.point + 'points');
			}
			// product name
			$('#product-name').text(product.productName);
			// product description
			$('#product-desc').text(product.productDesc);
			// if normal price and promotion price are both null, then do render any price information
			if (product.normalPrice != undefined
					&& product.promotionPrice != undefined) {
				// if both prices are not null, show both prices and add strikethrough to normal price
				$('#price').show();
				$('#normalPrice').html(
						'<del>' + '\$' + product.normalPrice + '</del>');
				$('#promotionPrice').text('$' + product.promotionPrice);
			} else if (product.normalPrice != undefined
					&& product.promotionPrice == undefined) {
				// only show normal price
				$('#price').show();
				$('#promotionPrice').text('$' + product.normalPrice);
			} else if (product.normalPrice == undefined
					&& product.promotionPrice != undefined) {
				// only show promotion price
				$('#promotionPrice').text('$' + product.promotionPrice);
			}
			var imgListHtml = '';
			// loop through product detail image list and generate img tags
			product.productImgList.map(function(item, index) {
				imgListHtml += '<div> <img src="' + item.imgAddr
						+ '" width="100%" /></div>';
			});
			// if (data.needQRCode) {
			// // generate qr code
			// imgListHtml += '<div> <img
			// src="/o2o/frontend/generateqrcode4product?productId='
			// + product.productId
			// + '" width="100%"/></div>';
			// }
			$('#imgList').html(imgListHtml);
		}
	});
	// open side bar
	$('#me').click(function() {
		$.openPanel('#panel-right-demo');
	});
	$.init();
});
