package savings.service.impl;

import org.joda.money.Money;
import savings.model.*;
import savings.repository.AccountRepository;
import savings.repository.MerchantRepository;
import savings.repository.PaybackRepository;

public class PaybackBookKeeperImpl implements savings.service.PaybackBookKeeper{

    /**
     * Register payback on an account for the purchase.
     * <p>
     * For a purchase to be accepted it must have been made using a registered credit card of a valid account.
     * Also it must have been made from a merchant participating in the payback program.
     *
     * @param purchase a charge made to a credit card for a purchase.
     * @return confirmation of registered payback.
     */

    private final AccountRepository accountRepository;
    private final MerchantRepository merchantRepository;
    private final PaybackRepository paybackRepository;

    public PaybackBookKeeperImpl(AccountRepository accountRepository, MerchantRepository merchantRepository, PaybackRepository paybackRepository) {
        this.accountRepository = accountRepository;
        this.merchantRepository = merchantRepository;
        this.paybackRepository = paybackRepository;
    }

    @Override
    public PaybackConfirmation registerPaybackFor(Purchase purchase) {
        Account account = accountRepository.findByCreditCard(purchase.getCreditCardNumber());
        Merchant merchant = merchantRepository.findByNumber(purchase.getMerchantNumber());
        Money payback = merchant.calculatePaybackFor(account, purchase);
        AccountIncome income = account.addPayback(payback);
        accountRepository.update(account);


        return paybackRepository.save(income, purchase);
    }
}
