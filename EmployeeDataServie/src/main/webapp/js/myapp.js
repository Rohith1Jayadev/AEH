$(document)
		.ready(
				function() {
					/**
					 * Comment Function calls the access servlet on user click
					 */
					$('#access')
							.click(
									function(event) {
										var name = $('#userName').val();
										var cookie = $('#cookie').val();
										var xhr = $
												.ajax(
														{
															type : "GET",
															url : "./OAuth/resource",
															data : {
																userName : name
															},
															dataType : 'html',
															beforeSend : function(
																	xhr) {
																console
																		.log(cookie);
																xhr
																		.setRequestHeader(
																				'x-request-type',
																				'ACCESS');
																xhr
																		.setRequestHeader(
																				'x-bearer-token',
																				cookie);
															},
															success : function(
																	data) {
																if (xhr
																		.getResponseHeader("x-clredirect-status")) {
																	console
																			.log(xhr
																					.getResponseHeader("x-clredirect-url"));
																	window.location = xhr
																			.getResponseHeader("x-clredirect-url");

																}
															},
															error : function(
																	data) {
																if (xhr
																		.getResponseHeader("redirect")) {
																	console
																			.log(xhr
																					.getResponseHeader("uri"));
																}
															}
														})
												.done(
														function(data) {
															$(
																	'#ajaxGetUserServletResponse')
																	.html(data);
														});

										
									});

					/**
					 * Comment Function calls the LogOut servlet  on user click
					 */
					$('#logout').click(
							function(event) {
								var cookie = $('#cookie').val();
								var logout = $.ajax({
									type : 'GET',
									url : "./OAuth/logout",

									beforeSend : function(logout) {
										logout.setRequestHeader(
												'x-request-type', 'LOGOUT');
										logout.setRequestHeader(
												'x-bearer-token', cookie);
									},
									error : function(data) {
										$('#ajaxGetUserServletResponse').html(
												data.responseText);
									},
								})
							});
				});