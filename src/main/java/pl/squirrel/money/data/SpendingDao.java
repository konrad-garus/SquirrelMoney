package pl.squirrel.money.data;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pl.squirrel.money.api.SpendingCategory;
import pl.squirrel.money.api.SpendingTotal;
import pl.squirrel.money.entity.Spending;

@Repository
@Transactional
@SuppressWarnings("unchecked")
public class SpendingDao {
	@PersistenceContext
	EntityManager entityManager;

	public Object getLastSpendings(int i) {
		return entityManager
				.createQuery("from Spending order by id desc", Spending.class)
				.setMaxResults(i).getResultList();
	}

	public List<Spending> getAllSpendings() {
		return entityManager.createQuery("from Spending order by id",
				Spending.class).getResultList();
	}

	public List<SpendingTotal> getTotalsByCategory() {
		return entityManager
				.createQuery(
						"select new pl.squirrel.money.api.SpendingTotal("
								+ "category, to_char(spendingDate, 'YYYY-MM'), sum(totalPrice)) "
								+ "from Spending "
								+ "where spendingDate is not null "
								+ "group by category, to_char(spendingDate, 'YYYY-MM') "
								+ "order by to_char(spendingDate, 'YYYY-MM'), category")
				.getResultList();
	}

	public List<SpendingCategory> findSpendingCategoriesByNamePart(
			String category) {
		return entityManager
				.createQuery(
						"select distinct new pl.squirrel.money.api.SpendingCategory(category, subcategory) "
								+ "from Spending "
								+ "where lower(category) like lower(:category) "
								+ "order by category, subcategory")
				.setParameter("category", "%" + category + "%").getResultList();
	}

	// KG test: category == null, category == empty string
	public List<SpendingCategory> findSpendingSubcategoriesByCategoryAndNameParts(
			String category, String subcategory) {
		// TODO Auto-generated method stub
		return null;
	}

	public void persist(Spending s) {
		entityManager.persist(s);
	}

}
