$(function() {
	var url = '/o2o/frontend/listmainpageinfo';
	// get headline and shop category
	$.getJSON(url, function(data) {
		if (data.success) {
			var headLineList = data.headLineList;
			var swiperHtml = '';
			// iterate headline list and get swiper images
			headLineList.map(function(item, index) {
				swiperHtml += '' + '<div class="swiper-slide img-wrap">'
						+ '<a href="' + item.lineLink
						+ '" external><img class="banner-img" src="' + item.lineImg
						+ '" alt="' + item.lineName + '"></a>' + '</div>';
			});
			// set swiper html
			$('.swiper-wrapper').html(swiperHtml);

			$(".swiper-container").swiper({
				autoplay : 3000, // 3 seconds to change images
				autoplayDisableOnInteraction : false
			});
			var shopCategoryList = data.shopCategoryList;
			var categoryHtml = '';
			// make url for top level shop categories
			shopCategoryList.map(function(item, index) {
				categoryHtml += ''
						+ '<div class="col-50 shop-classify" data-category='
						+ item.shopCategoryId + '>' + '<div class="word">'
						+ '<p class="shop-title">' + item.shopCategoryName
						+ '</p>' + '<p class="shop-desc">'
						+ item.shopCategoryDesc + '</p>' + '</div>'
						+ '<div class="shop-classify-img-warp">'
						+ '<img class="shop-img" src="' + item.shopCategoryImg
						+ '">' + '</div>' + '</div>';
			});
			// set the html
			$('.row').html(categoryHtml);
		}
	});

	// show side bar
	$('#me').click(function() {
		$.openPanel('#panel-right-demo');
	});

	$('.row').on('click', '.shop-classify', function(e) {
		var shopCategoryId = e.currentTarget.dataset.category;
		var newUrl = '/o2o/frontend/shoplist?parentId=' + shopCategoryId;
		window.location.href = newUrl; // route to the new page
	});

});
