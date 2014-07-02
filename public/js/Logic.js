function showtip(current, e, text) {
	if ((current.type == "select-one")) {
		if (current.length < 1) {
			return

		}
		if (current.selectedIndex > -1) {
			text = current.options[current.selectedIndex].text;
		}
	} else {
		if (current.type == "select-multiple") {
			if (current.length < 1) {
				return

			}
			if (current.selectedIndex > -1) {
				text = "";
				var selLength = current.length;
				var i;
				for (i = 0; i <= selLength - 1; i++) {
					if (current.options[i].selected) {
						if (text == "") {
							text = current.options[i].text;
						} else {
							text = text + "<br>" + current.options[i].text;
						}
					}
				}
			}
		}
	}
	if (document.layers) {
		theString = "<DIV CLASS='ttip'>" + text + "</DIV>";
		document.tooltip.document.write(theString);
		document.tooltip.document.close();
		document.tooltip.left = e.pageX + 5;
		document.tooltip.top = e.pageY + 5;
		document.tooltip.visibility = "show";
	} else {
		if (document.getElementById) {
			elm = document.getElementById("tooltip");
			elml = current;
			elm.innerHTML = text;
			elm.style.height = elml.style.height;
			elm.style.top = parseInt(elml.offsetTop + elml.offsetHeight);
			elm.style.left = parseInt(elml.offsetLeft + elml.offsetWidth + 10);
			elm.style.zIndex = 100;
			elm.style.visibility = "visible";
		}
	}
}
function hidetip() {
	if (document.layers) {
		document.tooltip.visibility = "hidden";
	} else {
		if (document.getElementById) {
			elm = document.getElementById("tooltip");
			elm.style.visibility = "hidden";
		}
	}
}
function addOption(theSel, theText, theValue) {
	var newOpt = new Option(theText, theValue);
	var selLength = theSel.length;
	theSel.options[selLength] = newOpt;
}
function deleteOption(theSel, theIndex) {
	var selLength = theSel.length;
	if (selLength > 0) {
		theSel.options[theIndex] = null;
	}
}
function moveOptions(theSelFrom, theSelTo) {
	var selLength = theSelFrom.length;
	
	var selectedText = new Array();
	var selectedValues = new Array();
	var selectedCount = 0;
	var i;
	for (i = selLength - 1; i >= 0; i--) {
		if (theSelFrom.options[i].selected) {
			selectedText[selectedCount] = theSelFrom.options[i].text;
			selectedValues[selectedCount] = theSelFrom.options[i].value;
			deleteOption(theSelFrom, i);
			selectedCount++;
		}
	}
	for (i = selectedCount - 1; i >= 0; i--) {
		
		addOption(theSelTo, selectedText[i], selectedValues[i]);
	}
}
function transferData(outSel) {
	if (null != outSel) {
		var selLength = outSel.length;
		outSel.multiple = true;
		for (i = selLength - 1; i >= 0; i--) {
			outSel.options[i].selected = true;
		}
	}
}
function selectAll(theSelFrom, isSelect) {
    for(var count=0; count < theSelFrom.options.length; count++) {
    	theSelFrom.options[count].selected = isSelect;
    	
}
};

