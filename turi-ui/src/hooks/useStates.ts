import { usePremiumPaymentFailed } from '../store/slices/premiumPaymentFailed.ts'
import { useAuthenticated } from '../store/slices/auth.ts'
import { usePremiumLogin } from '../store/slices/premiumLogin.ts'
import { usePremiumBuy } from '../store/slices/premiumBuy.ts'
import { useActivation } from '../store/slices/activate.ts'
import { usePersonal } from '../store/slices/personal.ts'
import { usePremium } from '../store/slices/premium.ts'
import { useReset } from '../store/slices/reset.ts'

export const useStates = () => {
    const isPremiumPaymentFailed = usePremiumPaymentFailed()
    const isAuthenticated = useAuthenticated()
    const isPremiumLogin = usePremiumLogin()
    const isPremiumBuy = usePremiumBuy()
    const isActivation = useActivation()
    const isPersonal = usePersonal()
    const isPremium = usePremium()
    const isReset = useReset()

    return {
        isPremiumPaymentFailed,
        isAuthenticated,
        isPremiumLogin,
        isPremiumBuy,
        isActivation,
        isPersonal,
        isPremium,
        isReset
    }
}
