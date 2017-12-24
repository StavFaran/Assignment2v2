package bgu.spl.a2.sim;

import java.util.List;
import java.util.Map;

public class Computer {

	String computerType;
	long failSig;
	long successSig;

	private SuspendingMutex suspendingMutex;
	
	public Computer(String computerType) {
		this.computerType = computerType;
	}

	public SuspendingMutex getSuspendingMutex(){
		return suspendingMutex;
	}

	public String getComputerType(){
		return computerType;
	}

	/**
	 * this method checks if the courses' grades fulfill the conditions
	 * @param courses
	 * 							courses that should be pass
	 * @param coursesGrades
	 * 							courses' grade
	 * @return a signature if couersesGrades grades meet the conditions
	 *
	 * We can maybe change it so that it loads isValidRegister or vice versa
	 */
	public long checkAndSign(List<String> courses, Map<String, Integer> coursesGrades) {
		boolean isValid = true;
		for (int i = 0; i < courses.size() && isValid; i++) {
			if (!coursesGrades.containsKey(courses.get(i)))
				isValid = false;
			else {
				if (coursesGrades.get(courses.get(i)) < 56)
					isValid = false;
			}
		}

		if (isValid) return successSig; else return failSig;
	}

	public void setFailSig(long failSig){
		this.failSig = failSig;
	}
	public void setSuccessSig(long successSig){
		this.successSig = successSig;
	}
}
