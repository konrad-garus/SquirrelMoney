package pl.squirrel.money.data;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pl.squirrel.money.entity.Spending;

@Repository
@Transactional
public class SpendingDao {
	@PersistenceContext
	EntityManager entityManager;

	public List<Spending> getAllSpendings() {
		return entityManager.createQuery("from Spending").getResultList();
	}
}
