/*
 *Powered by:iisquare.com
 */

function lazyGoBack() {
	var sec = $("#sec").text();
	$("#sec").text(--sec);
	if (sec > 0)
		setTimeout(lazyGoBack, 1000);
	else
		window.history.back();
}