package me.whitewin.couponcore.model

import me.whitewin.couponcore.error.ApplicationException
import me.whitewin.couponcore.model.Coupon
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import java.time.ZonedDateTime

internal class CouponTest {

    @Test
    fun `발급 수량이 남아있다면 정상적으로 쿠폰을 발급한다`() {
        val coupon = Coupon(
            totalQuantity = 100,
            issuedQuantity = 99
        )

        val result = coupon.hasAvailableQuantity()

        assertThat(result).isEqualTo(true)
    }

    @Test
    fun `발급 수량이 소진되었다면 쿠폰을 발급할 수 없다`() {
        val coupon = Coupon(
            totalQuantity = 100,
            issuedQuantity = 100
        )

        val result = coupon.hasAvailableQuantity()

        assertThat(result).isEqualTo(false)
    }

    @Test
    fun `최대 발급 수량이 설정되지 않았다면 쿠폰을 발급한다`() {
        val coupon = Coupon(
            issuedQuantity = 99
        )

        val result = coupon.hasAvailableQuantity()

        assertThat(result).isEqualTo(true)
    }

    @Test
    fun `발급 기간이 시작되지 않았다면 쿠폰을 발급할 수 없다`() {
        val coupon = Coupon(
            dateIssueStart = ZonedDateTime.now().plusDays(1),
            dateIssueEnd = ZonedDateTime.now().plusDays(2),
        )

        val result = coupon.isValidDate()

        assertThat(result).isEqualTo(false)
    }

    @Test
    fun `발급 기간이 시작 되었으면 쿠폰을 발급한다`() {
        val coupon = Coupon(
            dateIssueStart = ZonedDateTime.now().minusDays(1),
            dateIssueEnd = ZonedDateTime.now().plusDays(1)
        )

        val result = coupon.isValidDate()

        assertThat(result).isEqualTo(true)
    }

    @Test
    fun `발급 기간이 종료 되었으면 쿠폰을 발급할 수 없다`() {
        val coupon = Coupon(
            dateIssueStart = ZonedDateTime.now().minusDays(2),
            dateIssueEnd = ZonedDateTime.now().minusDays(1),
        )

        val result = coupon.isValidDate()

        assertThat(result).isEqualTo(false)
    }

    @Test
    fun `발급 수량과 발급 기간이 유효하다면 정상적으로 쿠폰을 발급한다`() {
        val coupon = Coupon(
            totalQuantity = 100,
            issuedQuantity = 99,
            dateIssueStart = ZonedDateTime.now().minusDays(1),
            dateIssueEnd = ZonedDateTime.now().plusDays(1)
        )

        coupon.issue()

        assertThat(coupon.issuedQuantity).isEqualTo(100)
    }

    @Test
    fun `발급 수량을 초과하면 ApplicationException 에러가 발생한다`() {
        val coupon = Coupon(
            totalQuantity = 100,
            issuedQuantity = 100,
            dateIssueStart = ZonedDateTime.now().minusDays(1),
            dateIssueEnd = ZonedDateTime.now().plusDays(1)
        )

        assertThatThrownBy { coupon.issue() }.isInstanceOf(ApplicationException::class.java)
    }

    @Test
    fun `발급 기간이 유효하지 않다면 ApplicationException 에러가 발생한다`() {
        val coupon = Coupon(
            totalQuantity = 100,
            issuedQuantity = 99,
            dateIssueStart = ZonedDateTime.now().plusDays(1),
            dateIssueEnd = ZonedDateTime.now().plusDays(2)
        )

        assertThatThrownBy { coupon.issue() }.isInstanceOf(ApplicationException::class.java)

    }
}
