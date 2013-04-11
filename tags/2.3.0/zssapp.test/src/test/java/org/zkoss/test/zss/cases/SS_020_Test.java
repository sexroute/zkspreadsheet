/* SS_020_Test.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Mar 15, 2012 10:45:32 AM , Created by sam
}}IS_NOTE

Copyright (C) 2012 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
}}IS_RIGHT
*/
package org.zkoss.test.zss.cases;

import org.junit.Test;
import org.zkoss.test.Color;
import org.zkoss.test.JQuery;
import org.zkoss.test.zss.ZSSAppTest;
import org.zkoss.test.zss.ZSSTestCase;

/**
 * @author sam
 *
 */
@ZSSTestCase
public class SS_020_Test extends ZSSAppTest {
	
	@Test
	public void font_color() {
		int tRow = 11;
		int lCol = 8;
		int bRow = 16;
		int rCol = 9;
		spreadsheet.setSelection(tRow, lCol, bRow, rCol);
		click(".zstbtn-fontColor .zstbtn-cave");
		
		JQuery target = jqFactory.create("'.z-colorpalette-colorbox'").eq(68);
		String color = target.css("background-color");
		click(target);
		
		verifyFontColor(new Color(color), tRow, lCol, bRow, rCol);
	}
}
