package com.dreamfitbackend.configs.security;

import java.util.Arrays;
import java.util.List;

import com.dreamfitbackend.domain.usuario.enums.Role;

public class Permissions {
	
	public static final List<Integer> ADM = Arrays.asList(Role.ADMIN.getCod());
	
	public static final List<Integer> ADM_PROF = Arrays.asList(Role.ADMIN.getCod(), Role.TEACHER.getCod());
	
	public static final List<Integer> ADM_PROF_STUDENT = Arrays.asList(Role.ADMIN.getCod(), Role.TEACHER.getCod(), Role.STUDENT.getCod());
	
	public static final List<Integer> PROF_STUDENT = Arrays.asList(Role.TEACHER.getCod(), Role.STUDENT.getCod());

}
