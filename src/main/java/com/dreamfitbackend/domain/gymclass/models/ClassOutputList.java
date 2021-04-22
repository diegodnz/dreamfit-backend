package com.dreamfitbackend.domain.gymclass.models;

import java.util.ArrayList;
import java.util.List;

public class ClassOutputList {
	
	List<ClassOutputListElement> classes;		
	
	public ClassOutputList(List<ClassOutputListElement> classes) {
		this.classes = classes;
	}

	public List<ClassOutputListElement> getClasses() {
		return classes;
	}

	public void setClasses(List<ClassOutputListElement> classes) {
		this.classes = classes;
	}
	
	public void addClass(ClassOutputListElement gymClass) {
		classes.add(gymClass);
	}

}
