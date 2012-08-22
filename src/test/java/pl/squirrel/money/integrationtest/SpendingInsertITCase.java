package pl.squirrel.money.integrationtest;

import static org.mockito.Mockito.verifyZeroInteractions;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pl.squirrel.money.data.SpendingDao;
import pl.squirrel.money.entity.Spending;
import pl.squirrel.money.web.SpendingCommand;
import pl.squirrel.money.web.SpendingEditionController;

//@RunWith(SpringJUnit4ClassRunner.class)
//@TransactionConfiguration(defaultRollback = true)
@ContextConfiguration(classes = { IntegrationTestConfig.class })
// @Transactional
public class SpendingInsertITCase extends
		AbstractTransactionalTestNGSpringContextTests {

	@Autowired
	private SpendingDao spendingDao;

	@Autowired
	private SpendingEditionController controller;

	@Mock
	private BindingResult bindingResult;

	@Mock
	private ModelMap modelMap;

	@BeforeTest
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldInsertSpendingToDbFromForm() {
		// Sanity check
		Assert.assertTrue(spendingDao.getAllSpendings().isEmpty());

		// Create new valid command...
		SpendingCommand cmd = new SpendingCommand();
		cmd.setCategory("Jedzenie");
		cmd.setQuantity(2);
		cmd.setSpendingDate("7/8/12");
		cmd.setUnitPrice(new BigDecimal("5.30"));

		// ... and perform insert
		controller.addSpending(cmd, bindingResult, modelMap);

		// Don't expect any interactions with bindingResult, the command is
		// valid
		verifyZeroInteractions(bindingResult);

		// Expect this single spending to be saved with the right (multiplied)
		// price
		Spending expect = new Spending();
		expect.setCategory("Jedzenie");
		expect.setQuantity(2);
		expect.setSpendingDate(new LocalDate("2012-08-07"));
		expect.setUnitPrice(new BigDecimal("5.3"));
		expect.setTotalPrice(new BigDecimal("10.6"));

		List<Spending> spendings = spendingDao.getAllSpendings();
		Assert.assertEquals(Arrays.asList(expect), spendings);
	}
}
