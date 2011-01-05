/* SheetCtrlImpl.java

	Purpose:
		
	Description:
		
	History:
		Dec 14, 2010 3:20:42 PM, Created by henrichen

Copyright (C) 2010 Potix Corporation. All Rights Reserved.
*/

package org.zkoss.zss.model.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.zkoss.poi.ss.usermodel.Cell;
import org.zkoss.poi.ss.usermodel.Row;
import org.zkoss.poi.ss.util.CellRangeAddress;
import org.zkoss.zss.model.Book;
import org.zkoss.zss.model.Worksheet;

/**
 * Common implementation of the {@link SheetCtrl} interface. 
 * @author henrichen
 *
 */
public class SheetCtrlImpl implements SheetCtrl {
	private final Book _book;
	private final Worksheet _sheet;
	private boolean _evalAll;
	private String _uuid;
	
	public SheetCtrlImpl(Book book, Worksheet sheet) {
		_book = book;
		_sheet = sheet;
	}
    /*package*/ void initUuid() {
    	if (_uuid == null) {
    		_uuid = (String) ((BookCtrl)_book).nextSheetId();
    	}
    }
    
	@Override
	public void evalAll() {
		// TODO Auto-generated method stub
		for(Row row : _sheet) {
			if (row != null) {
				for(Cell cell : row) {
					if (cell != null && cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
						BookHelper.evaluate(_sheet.getBook(), cell);
					}
				}
			}
		}
		_evalAll = true;
	}

	@Override
	public boolean isEvalAll() {
		return _evalAll;
	}
	
	@Override
	public String getUuid() {
    	if (_uuid == null) {
    		_uuid = (String)((BookCtrl)_book).nextSheetId();
    	}
		return _uuid;
	}
    private Map<String, CellRangeAddress> _mergedRegions;
	@Override
	public void initMerged() {
    	final int num = _sheet.getNumMergedRegions();
    	_mergedRegions = new HashMap<String, CellRangeAddress>(num);
    	for (int j = 0; j < num; ++j) {
    		final CellRangeAddress addr = _sheet.getMergedRegion(j);
    		final int col = addr.getFirstColumn();
    		final int row = addr.getFirstRow();
    		final int rcol = addr.getLastColumn();
    		_mergedRegions.put(mergeId(row, col), addr);
    		_mergedRegions.put(mergeId(row, rcol), addr);
    	}
	}
	@Override
	public CellRangeAddress getMerged(int row, int col) {
		return _mergedRegions.get(mergeId(row, col));	
	}
	@Override
	public void addMerged(CellRangeAddress addr) {
		final int tRow = addr.getFirstRow();
		final int lCol = addr.getFirstColumn();
		final int rCol = addr.getLastColumn();
		_mergedRegions.put(mergeId(tRow, lCol), addr);
		_mergedRegions.put(mergeId(tRow, rCol), addr);
	}
	@Override
	public void deleteMerged(CellRangeAddress addr) {
		final int tRow = addr.getFirstRow();
		final int lCol = addr.getFirstColumn();
		final int rCol = addr.getLastColumn();
		_mergedRegions.remove(mergeId(tRow, lCol));
		_mergedRegions.remove(mergeId(tRow, rCol));
	}
	private String mergeId(int row, int col) {
		return row+"_"+col;
	}

}