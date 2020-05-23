$(function() {
	var loading = false;
	// max number of items to be returned
	var maxItems = 20;
	// number of products in one page
	var pageSize = 3;
	// url to get data from backend
	var listUrl = '/o2o/frontend/listproductsbyshop';
	// default page number
	var pageNum = 1;
	// extract shopId from url
	var shopId = getQueryString('shopId');
	var productCategoryId = '';
	var productName = '';
	// url to get shop information and shop category information
	var searchDivUrl = '/o2o/frontend/listshopdetailpageinfo?shopId=' + shopId;
	// render shop information and shop categories
	getSearchDivData();
	// load 10 product information
	addItems(pageSize, pageNum);

	// $('#exchangelist').attr('href', '/o2o/frontend/awardlist?shopId=' +
	// shopId);
	//  get shop information and product categories
	function getSearchDivData() {
		var url = searchDivUrl;
		$
				.getJSON(
						url,
						function(data) {
							if (data.success) {
								var shop = data.shop;
								$('#shop-cover-pic').attr('src', shop.shopImg);
								$('#shop-update-time').html(
										new Date(shop.lastEditTime)
												.Format("yyyy-MM-dd"));
								$('#shop-name').html(shop.shopName);
								$('#shop-desc').html(shop.shopDesc);
								$('#shop-addr').html(shop.shopAddr);
								$('#shop-phone').html(shop.phone);
								// get list of shop category from JSON response from backend
								var productCategoryList = data.productCategoryList;
								var html = '';
								// loop through list of products to generate a tags
								productCategoryList
										.map(function(item, index) {
											html += '<a href="#" class="button" data-product-search-id='
													+ item.productCategoryId
													+ '>'
													+ item.productCategoryName
													+ '</a>';
										});
								// put a tags into html
								$('#shopdetail-button-div').html(html);
							}
						});
	}
	/**
	 * list product information page by page
	 * 
	 * @param pageSize
	 * @param pageIndex
	 * @returns
	 */
	function addItems(pageSize, pageIndex) {
		// combine url to get search conditions
		var url = listUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize='
				+ pageSize + '&productCategoryId=' + productCategoryId
				+ '&productName=' + productName + '&shopId=' + shopId;
		// to avoid multiple concurrent loading
		loading = true;
		// get list of products
		$.getJSON(url, function(data) {
			if (data.success) {
				maxItems = data.count; // max allowed number of products is the current count
				var html = '';
				// loop through product list and generate a tags
				data.productList.map(function(item, index) {
					html += '' + '<div class="card" data-product-id='
							+ item.productId + '>'
							+ '<div class="card-header">' + item.productName
							+ '</div>' + '<div class="card-content">'
							+ '<div class="list-block media-list">' + '<ul>'
							+ '<li class="item-content">'
							+ '<div class="item-media">' + '<img src="'
							+ item.imgAddr + '" width="44">' + '</div>'
							+ '<div class="item-inner">'
							+ '<div class="item-subtitle">' + item.productDesc
							+ '</div>' + '</div>' + '</li>' + '</ul>'
							+ '</div>' + '</div>' + '<div class="card-footer">'
							+ '<p class="color-gray">'
							+ 'last udpate: '
							+ new Date(item.lastEditTime).Format("yyyy-MM-dd")
							+ '</p>' + '<span>View</span>' + '</div>'
							+ '</div>';
				});
				// put a tags into html
				$('.list-div').append(html);
				// get number of products shown
				var total = $('.list-div .card').length;
				// shop loading if maxItems is reached
				if (total >= maxItems) {
					$('.infinite-scroll-preloader').hide();
				} else {
					$('.infinite-scroll-preloader').show();
				}
				// otherwise load the next page
				pageNum += 1;
				// can load again now
				loading = false;
				// refresh the page
				$.refreshScroller();
			}
		});
	}

	// load next page when scrolled to bottom
	$(document).on('infinite', '.infinite-scroll-bottom', function() {
		if (loading)
			return;
		addItems(pageSize, pageNum);
	});
	// reset the page after a new category is selected
	$('#shopdetail-button-div').on(
			'click',
			'.button',
			function(e) {
				// get productCategoryId
				productCategoryId = e.target.dataset.productSearchId;
				if (productCategoryId) {
					// change the effects of previous selected category
					if ($(e.target).hasClass('button-fill')) {
						$(e.target).removeClass('button-fill');
						productCategoryId = '';
					} else {
						$(e.target).addClass('button-fill').siblings()
								.removeClass('button-fill');
					}
					$('.list-div').empty();
					pageNum = 1;
					addItems(pageSize, pageNum);
				}
			});
	// enter product detail page
	$('.list-div').on(
			'click',
			'.card',
			function(e) {
				var productId = e.currentTarget.dataset.productId;
				window.location.href = '/o2o/frontend/productdetail?productId='
						+ productId;
			});
	// reset list of products on name change in search bar
	$('#search').on('change', function(e) {
		productName = e.target.value;
		$('.list-div').empty();
		pageNum = 1;
		addItems(pageSize, pageNum);
	});
	// open side bar
	$('#me').click(function() {
		$.openPanel('#panel-right-demo');
	});
	$.init();
});
