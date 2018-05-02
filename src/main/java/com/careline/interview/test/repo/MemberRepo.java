package com.careline.interview.test.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.careline.interview.test.model.Member;

@Repository
public class MemberRepo {
	@Autowired
	JdbcTemplate jdbcTemplate;

	public Member findByEmail(String pEmail) {
		Member member = null;
		try {
			member = jdbcTemplate.queryForObject("select * from Member where email=?", new Object[] { pEmail },
					new BeanPropertyRowMapper<Member>(Member.class));

		} catch (EmptyResultDataAccessException ex) {
			// return null;
		}

		return member;
	}
	
	public Member findByToken(String pToken) {
		Member member = null;
		try {
			member = jdbcTemplate.queryForObject("select * from Member where token=?", new Object[] { pToken },
					new BeanPropertyRowMapper<Member>(Member.class));

		} catch (EmptyResultDataAccessException ex) {
			// return null;
		}

		return member;
	}

	public List<Member> findAll() {
		List<Member> members = null;
		try {
			members = jdbcTemplate.query("select * from Member", new Object[] {},
					new BeanPropertyRowMapper<Member>(Member.class));

		} catch (EmptyResultDataAccessException ex) {
			// return null;
		}

		return members;
	}

	public int insert(Member pMember) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		final String INSERT_SQL = "insert into Member (name, email, password) " + "values(?,  ?, ?)";

		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				final PreparedStatement ps = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, pMember.getName());
				ps.setString(2, pMember.getEmail());
				ps.setString(3, pMember.getPassword());
				return ps;
			}
		}, keyHolder);

		return keyHolder.getKey().intValue();
	}

	public String login(String pEmail) {
		String token = UUID.randomUUID().toString();
		jdbcTemplate.update("update member " + " set token = ?, login_time= NOW() where email = ?", new Object[] { token, pEmail });
		return token;
	}

	public int logout(String pEmail) {
		return jdbcTemplate.update("update member " + " set token = '' where email = ?", new Object[] { pEmail });
	}

	public int updateMemberName(String pEmail, String pName) {
		return jdbcTemplate.update("update member " + " set name = ? where email = ?", new Object[] { pName, pEmail });
	}

	public int updateMemberPassword(String pEmail, String pPassword) {
		return jdbcTemplate.update("update member " + " set password = ? where email = ?", new Object[] { pPassword, pEmail });
	}
}
