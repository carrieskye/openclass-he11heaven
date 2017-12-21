package domain;

import java.io.OutputStream;
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
	
	private AfdelingDb afdelingdb;
	private OpleidingDb opleidingdb;
	private InschrijvingenDb inschrijvingenDb;
	private SessieDb sessieDb;
	
	
	public ExcelWriter(SessieDb sessiedb) throws ClassNotFoundException, SQLException{
		afdelingdb = new AfdelingDb();
		opleidingdb = new OpleidingDb();
		inschrijvingenDb = new InschrijvingenDb();
		sessieDb = sessiedb;
	}
	
	public void getAlleInschrijving(OutputStream ouputstream){
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("alle inschrijvingen");
		
		Row header = sheet.createRow(0);
		
		header.createCell(0).setCellValue("Afdeling");
		header.createCell(1).setCellValue("Opleiding");
		header.createCell(0).setCellValue("Sessie");
		header.createCell(0).setCellValue("Voornaam");
		header.createCell(0).setCellValue("Achternaam");
		header.createCell(0).setCellValue("email");
				
		try {
			workbook.write(ouputstream);
			workbook.close();
		} catch (Exception e) {
			throw new DomainException("fout bij schrijven naar excel file");
		}
		
	}
	
	
	
}
