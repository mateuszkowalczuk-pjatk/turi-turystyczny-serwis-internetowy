import { useReset } from '../../store/slices/reset.ts'
import { usePremium } from '../../store/slices/premium.ts'
import { useLoading } from '../../store/slices/loading.ts'
import { usePersonal } from '../../store/slices/personal.ts'
import { usePremiumBuy } from '../../store/slices/premiumBuy.ts'
import { useActivation } from '../../store/slices/activate.ts'
import { useReservation } from '../../store/slices/reservation.ts'
import { usePremiumLogin } from '../../store/slices/premiumLogin.ts'
import { useAuthenticated } from '../../store/slices/auth.ts'
import { useReservationBuy } from '../../store/slices/reservationBuy.ts'
import { useReservationPersonal } from '../../store/slices/reservationPersonal.ts'
import { usePremiumPaymentFailed } from '../../store/slices/premiumPaymentFailed.ts'
import { useReservationPaymentFailed } from '../../store/slices/reservationPaymentFailed.ts'

export const useStates = () => {
    const isReset = useReset()
    const isPremium = usePremium()
    const isLoading = useLoading()
    const isPersonal = usePersonal()
    const isPremiumBuy = usePremiumBuy()
    const isActivation = useActivation()
    const isReservation = useReservation()
    const isPremiumLogin = usePremiumLogin()
    const isAuthenticated = useAuthenticated()
    const isReservationBuy = useReservationBuy()
    const isReservationPersonal = useReservationPersonal()
    const isPremiumPaymentFailed = usePremiumPaymentFailed()
    const isReservationPaymentFailed = useReservationPaymentFailed()

    return {
        isReset,
        isPremium,
        isLoading,
        isPersonal,
        isPremiumBuy,
        isActivation,
        isReservation,
        isPremiumLogin,
        isAuthenticated,
        isReservationBuy,
        isReservationPersonal,
        isPremiumPaymentFailed,
        isReservationPaymentFailed
    }
}
