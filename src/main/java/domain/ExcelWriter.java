package domain;

import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import db.AfdelingDb;
import db.InschrijvingenDb;
import db.OpenLesdagDb;
import db.OpleidingDb;
import db.SessieDb;

public class ExcelWriter{
	
	private ResultSet resultset;
	
	
	public ExcelWriter(ResultSet result) throws ClassNotFoundException, SQLException{
		this.resultset = result;
	}
	
	public void getAlleInschrijving(OpenLesdagDb db, OutputStream ouputstream) throws SQLException{
		
	}
	
	
	
}
