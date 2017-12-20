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
}
