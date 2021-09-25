package org.jxls.transform.poi;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.jxls.area.Area;
import org.jxls.command.AbstractCommand;
import org.jxls.command.Command;
import org.jxls.common.CellRef;
import org.jxls.common.Context;
import org.jxls.common.Size;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.impl.CTRowImpl;

import javax.xml.namespace.QName;


public class AutoColumnWidthCommand extends AbstractCommand {

    private Area area;

    @Override
    public String getName() {
        return "autoWidth";
    }

    @Override
    public Size applyAt(CellRef cellRef, Context context) {
        Size size = area.applyAt(cellRef, context);

        PoiTransformer transformer = (PoiTransformer) area.getTransformer();
        Workbook workbook = transformer.getWorkbook();
//        workbook.getSheet(cellRef.getSheetName()).setColumnWidth(1,200*256);
//        workbook.getSheet(cellRef.getSheetName()).setColumnWidth(2,200*256);
//        workbook.getSheet(cellRef.getSheetName()).setColumnWidth(3,200*256);
//        workbook.getSheet(cellRef.getSheetName()).setColumnWidth(4,200*256);
        workbook.getSheet(cellRef.getSheetName()).setColumnWidth(0,200*256);
        return size;
    }

    @Override
    public Command addArea(Area area) {
        super.addArea(area);
        this.area = area;
        return this;
    }

    private void removeDyDescentAttr(Row row) {
        if (row instanceof XSSFRow) {
            XSSFRow xssfRow = (XSSFRow) row;
            CTRowImpl ctRow = (CTRowImpl) xssfRow.getCTRow();
            QName dyDescent = new QName("http://schemas.microsoft.com/office/spreadsheetml/2009/9/ac");
            if (ctRow.get_store().find_attribute_user(dyDescent) != null) {
                ctRow.get_store().remove_attribute(dyDescent);
            }
        } else {
            System.out.println("This method applicable only for xlsx-templates");
        }
    }


}