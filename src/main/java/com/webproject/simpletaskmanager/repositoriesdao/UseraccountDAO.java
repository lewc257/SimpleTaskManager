package com.webproject.simpletaskmanager.repositoriesdao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.webproject.simpletaskmanager.entities.Useraccount;
import com.webproject.simpletaskmanager.repositories.UseraccountDAOLocal;

@Repository
public class UseraccountDAO implements UseraccountDAOLocal {

	@PersistenceContext
	private EntityManager em;

	public UseraccountDAO(EntityManager em) {
		this.em = em;
	}

	@Override
	public Useraccount findUseraccountById(Integer id) {
		return em.find(Useraccount.class, id);
	}

	@Override
	public Useraccount findUseraccountByUsername(String username) {
		String strQuery = "SELECT u FROM Useraccount u WHERE u.username = :username";
		List<Useraccount> results = em.createQuery(strQuery, Useraccount.class).setParameter("username", username)
				.getResultList();
		return results.size() == 0 ? null : results.get(0);
	}

	@Override
	public Useraccount saveUseraccount(Useraccount useraccount) {
		if (useraccount != null && useraccount.getId() == null) {
			em.persist(useraccount);
		} else {
			useraccount = em.merge(useraccount);
		}
		return useraccount;
	}

	@Override
	public Useraccount deleteAccount(Useraccount useraccount) {
		if (em.contains(useraccount)) {
			em.remove(useraccount);
		} else {
			useraccount = em.merge(useraccount);
		}
		return useraccount;
	}

	public Useraccount findUseraccount(String username, String password) {
		String strQuery = "SELECT u FROM Useraccount u WHERE u.username = :username AND u.password = :password";
		List<Useraccount> resultList = em.createQuery(strQuery, Useraccount.class).setParameter("username", username)
				.setParameter("password", password).getResultList();
		if (resultList.isEmpty()) {
			return null;
		}
		return resultList.get(0);
	}

	public List<Useraccount> findAll() {
		return em.createQuery("SELECT u FROM Useraccount u", Useraccount.class).getResultList();
	}
}
