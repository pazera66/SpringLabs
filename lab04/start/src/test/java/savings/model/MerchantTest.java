package savings.model;

import static org.fest.assertions.Assertions.assertThat;
import static org.joda.money.CurrencyUnit.EUR;
import static org.joda.time.DateTime.now;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.fest.assertions.Assertions;
import org.joda.money.Money;
import org.junit.Test;

import common.math.Percentage;
import org.mockito.Mockito;

public class MerchantTest {

    Account account = new Account("123456789", "Jane & John Smith")
            .withObjective("Glock", Percentage.of("50%"))
            .withObjective("M60", Percentage.of("50%"));

    String merchantNumber = "1234567890";

    Purchase purchase = new Purchase(Money.of(EUR, 100L), "1234123412341234", merchantNumber, now());

    PaybackPolicy paybackPolicy = mock(PaybackPolicy.class);

    Merchant merchant = new Merchant(merchantNumber, "Guns & Bombs", Percentage.of("6%"), paybackPolicy);

    // TODO #0 a good practice when implementing tests is to start with an assertion to get the red bar first

    @Test
    public void shouldCalculatePaybackIfEligible() {
        // TODO #1 implement the case when the policy allows for the payback to be granted
        when(paybackPolicy.isEligible(account, purchase)).thenReturn(true);

        Money payback = merchant.calculatePaybackFor(account,purchase);

        assertThat(payback).isEqualTo(Money.of(EUR, 6L));
    }

    @Test
    public void shouldReturnZeroIfNotEligible() {
        // TODO #2 implement the case when the policy does not allow for the payback to be granted
        when(paybackPolicy.isEligible(account, purchase)).thenReturn(false);

        Money payback = merchant.calculatePaybackFor(account,purchase);

        assertThat(payback).isEqualTo(Money.of(EUR, 0L));

    }
}
