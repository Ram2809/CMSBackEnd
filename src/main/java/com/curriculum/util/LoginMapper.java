package com.curriculum.util;

import com.curriculum.dto.Login;
import com.curriculum.entity.LoginEntity;
import com.curriculum.entity.TeacherEntity;

public class LoginMapper {
	public static LoginEntity loginMapper(Login login)
	{
		LoginEntity loginEntity=new LoginEntity();
		loginEntity.setLoginId(login.getLoginId());
		loginEntity.setPassword(login.getPassword());
		TeacherEntity teacherEntity=new TeacherEntity();
		teacherEntity.setId(login.getTeacher().getId());
		loginEntity.setTeacher(teacherEntity);
		return loginEntity;
	}
}
