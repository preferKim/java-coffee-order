package coffee.order.service;

import coffee.order.model.Order;
import coffee.order.model.Transaction;
import coffee.order.repository.CouponRepository;
import coffee.order.view.model.NumberChoice;
import coffee.order.view.model.PhoneNumber;

import java.util.List;

public class CouponService {
    private static final int COUPON_APPLICATION_CRITERIA = 10;

    private final CouponRepository couponRepository;

    public CouponService() {
        couponRepository = new CouponRepository();
    }

    public Integer getCouponQuantity(PhoneNumber phoneNumber) {
        checkCustomerExists(phoneNumber);
        return couponRepository.findCouponQuantity(phoneNumber);
    }

    public boolean isCouponApplicable(Integer couponQuantity) {
        return couponQuantity >= COUPON_APPLICATION_CRITERIA;
    }

    public void addCoupon(Transaction transaction, PhoneNumber phoneNumber) {
        checkCustomerExists(phoneNumber);
        couponRepository.addCoupon(phoneNumber, transaction);
    }

    private void checkCustomerExists(PhoneNumber phoneNumber) {
        if (!couponRepository.customerExists(phoneNumber)) {
            couponRepository.addNewCustomer(phoneNumber);
        }
    }

    public void applyCoupon(Transaction transaction, NumberChoice numberChoice) {
        List<Order> orders = transaction.getOrders();
        Order orderToApplyCoupon = orders.get(numberChoice.getChoice());
        orderToApplyCoupon.applyCoupon();
    }
}
