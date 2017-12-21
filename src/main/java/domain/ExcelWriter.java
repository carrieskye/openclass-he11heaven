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
import db.OpleidingDb;
import db.SessieDb;

public class ExcelWriter{
	
	private ResultSet resultset;
	
	
	public ExcelWriter(ResultSet result) throws ClassNotFoundException, SQLException{
		this.resultset = result;
	}
	
	public void getAlleInschrijving(OutputStream ouputstream) throws SQLException{
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("alle inschrijvingen");
		
		Row header = sheet.createRow(0);
		
		header.createCell(0).setCellValue("Afdeling");
		header.createCell(1).setCellValue("Opleiding");
		header.createCell(2).setCellValue("Sessie");
		header.createCell(3).setCellValue("Voornaam");
		header.createCell(4).setCellValue("Achternaam");
		header.createCell(5).setCellValue("email");
		
		int counter = 1;
		while(resultset.next()){
			Row row = sheet.createRow(counter);
			row.createCell(0).setCellValue(resultset.getString("afdeling"));
			row.createCell(1).setCellValue(resultset.getString("naam"));
			row.createCell(2).setCellValue(resultset.getString("naam"));
			row.createCell(3).setCellValue(resultset.getString("voornaam"));
			row.createCell(4).setCellValue(resultset.getString("naam"));
			row.createCell(5).setCellValue(resultset.getString("email"));
			counter++;
		}
		
		resultset.close();
				
		try {
			workbook.write(ouputstream);
			workbook.close();
		} catch (Exception e) {
			throw new DomainException("fout bij schrijven naar excel file");
		}
	}
	
	
	
}
