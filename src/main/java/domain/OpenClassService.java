package domain;

import java.sql.SQLException;
import java.util.List;

import db.AfdelingDb;
import db.DbException;
import db.InschrijvingenDb;
import db.OpenLesdagDb;
import db.OpleidingDb;
import db.SessieDb;
import db.StudentDb;

// interface/facade voor alle onderliggende klassen van de OpenClass applicatie
public class OpenClassService {
	private AfdelingDb afdelingDb;
	private OpleidingDb opleidingDb;
	private OpenLesdagDb openlesdagDb;
	private SessieDb sessionDb;
	
	private InschrijvingenDb inschrijvingenDb;
	private StudentDb studentDb;
	
	private SimpleMail mail;
	
	
	public OpenClassService() {
		try {
			openlesdagDb = new OpenLesdagDb();
			sessionDb = new SessieDb(this);
			inschrijvingenDb = new InschrijvingenDb();
			studentDb = new StudentDb();
			afdelingDb = new AfdelingDb();
			opleidingDb = new OpleidingDb();
			mail = new SimpleMail(this);
		} catch (ClassNotFoundException e) {
			throw new DomainException(e.getMessage());
		} catch (SQLException e) {
			throw new DomainException(e.getMessage());
		}
	}
	
	public OpenLesDag getOpenlesdagVanSessie(int sessieId) {
		try {
			return openlesdagDb.getOpenlesdagVanSessie(sessieId);
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
	}

	public void sendMail(Student student, OpenClassSession sessie) {
		try {
			mail.sendMail(student, sessie);
		} catch (Exception e) {
			throw new DomainException(e.getMessage());
		}
	}

	public void updateStudent(Student nieuweStudent, int sessionId) {
		studentDb.updateStudent(nieuweStudent);
	}

	public List<Afdeling> getAfdelingen() {
		return afdelingDb.getAfdelingen();
	}

	public void addNewSession(OpenClassSession sessie) {
		sessionDb.addNewSession(sessie);
	}

	public OpenClassSession getSessie(int sessieId) {
		return sessionDb.get(sessieId);
	}

	public List<OpenClassSession> getAllSessions() {
		return sessionDb.getAll();
	}

	public List<OpenClassSession> getSessiesVanOpenLesDag(int openLesDagId) {
		return sessionDb.getSessiesOpenLesDag(openLesDagId);
	}

	public int telAantalInschrijvingen(int sessionId) {
		return inschrijvingenDb.telIngeschrevenStudenten(sessionId);
	}

	public void addInschrijving(Student student, int sessieId) {
		inschrijvingenDb.add(student, sessieId);
	}

	public List<Student> getAllStudents(int sessionId) {
		return inschrijvingenDb.get(sessionId);
	}

	public void removeInschrijving(int studentId, int sessionId) {
		inschrijvingenDb.remove(studentId, sessionId);
	}

	public int telIngeschrevenStudenten(int sessieId) {
		System.out.println(inschrijvingenDb.telIngeschrevenStudenten(sessieId));
		return inschrijvingenDb.telIngeschrevenStudenten(sessieId);
	}

	public void addOpenDay(OpenLesDag openDay) {
		openlesdagDb.addOpenDay(openDay);
	}

	public List<OpenLesDag> getOpenlesdagen(int opleidingId) {
		return openlesdagDb.getLesdagen(opleidingId);
	}

	public int getOpenlesdagId(String date, int opleidingId) {
		return openlesdagDb.getOpenlesdagID(date, opleidingId);
	}

	public Student getStudent(int studentId) {
		return studentDb.get(studentId);
	}

	public int addStudent(Student student) {
		return studentDb.add(student);
	}

	public String getOpleiding(int opleidingId) {
		return opleidingDb.getOpleiding(opleidingId);
	}

	public List<Opleiding> getOpleidingen() {
		return opleidingDb.getOpleidingen();
	}
	
}
